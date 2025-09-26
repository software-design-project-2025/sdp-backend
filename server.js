// server.js
const express = require('express');
const cors = require('cors');
const jwt = require('jsonwebtoken');
const { Pool } = require('pg');
const rateLimit = require('express-rate-limit');

const app = express();
const PORT = 3001;

// Middleware
app.use(cors());
app.use(express.json());

// PostgreSQL connection pool for YOUR SDP database
const pool = new Pool({
  user: 'postgres',
  host: 'localhost',
  database: 'sdp',
  password: 'studybuddy@25',
  port: 5432,
});

const signupLimiter = rateLimit({
  windowMs: 15 * 60 * 1000,
  max: 5, 
  message: { error: 'Too many signup attempts, please try again later.' }
});

const verifyDatabase = async () => {
  try {
    const usersTable = await pool.query(`
      SELECT column_name, data_type 
      FROM information_schema.columns 
      WHERE table_name = 'users'
    `);
    
    console.log('✅ Users table columns:', usersTable.rows);
    
    // Check if profiles table exists
    const profilesTable = await pool.query(`
      SELECT column_name, data_type 
      FROM information_schema.columns 
      WHERE table_name = 'profiles'
    `);
    
    console.log('✅ Profiles table columns:', profilesTable.rows);
    
  } catch (error) {
    console.error('❌ Database verification error:', error);
  }
};

verifyDatabase();

const JWT_SECRET = process.env.SUPABASE_JWT_SECRET || 'your-supabase-jwt-secret';
const authMiddleware = async (req, res, next) => {
  try {
    const authHeader = req.headers.authorization;
    
    if (!authHeader || !authHeader.startsWith('Bearer ')) {
      return res.status(401).json({ 
        error: 'Access denied. No token provided.' 
      });
    }

    const token = authHeader.substring(7);
    let decoded;
    try {
      decoded = jwt.verify(token, JWT_SECRET);
    } catch (jwtError) {
      return res.status(401).json({ 
        error: 'Invalid or expired token' 
      });
    }
    req.user = {
      id: decoded.sub,
      email: decoded.email,
      supabaseUserId: decoded.sub
    };

    next();

  } catch (error) {
    console.error('Auth middleware error:', error);
    return res.status(500).json({ 
      error: 'Authentication failed' 
    });
  }
};
app.get('/', (req, res) => {
  res.json({ 
    message: 'Campus Study Buddy API',
    version: '1.0.0',
    database: 'SDP'
  });
});
app.get('/api/health', async (req, res) => {
  try {
    await pool.query('SELECT 1');
    res.json({ 
      status: 'OK', 
      database: 'SDP Connected',
      timestamp: new Date().toISOString()
    });
  } catch (error) {
    res.status(500).json({ 
      status: 'Error', 
      database: 'SDP Disconnected',
      error: error.message 
    });
  }
});
app.post('/api/users', signupLimiter, async (req, res) => {
  try {
    const { supabaseUserId, email, displayName, major, year } = req.body;
    
    if (!supabaseUserId || !email) {
      return res.status(400).json({ 
        error: 'supabaseUserId and email are required' 
      });
    }

    console.log('Creating user in SDP database:', { supabaseUserId, email, displayName });
    const userResult = await pool.query(
      `INSERT INTO users (supabase_user_id, email, display_name) 
       VALUES ($1, $2, $3) 
       RETURNING user_id, supabase_user_id, email, display_name, created_at`,
      [supabaseUserId, email, displayName || '']
    );

    const userId = userResult.rows[0].user_id;
    if (major || year) {
      await pool.query(
        `INSERT INTO profiles (user_id, major, year) 
         VALUES ($1, $2, $3)`,
        [userId, major || null, year || null]
      );
    }
    
    res.json({ 
      success: true, 
      message: 'User created successfully in SDP database',
      user: userResult.rows[0] 
    });

  } catch (error) {
    if (error.code === '23505') { // Unique violation
      return res.status(409).json({ 
        error: 'User already exists in SDP database' 
      });
    }
    
    console.error('Database error:', error);
    res.status(500).json({ error: 'Internal server error' });
  }
});

app.get('/api/profile', authMiddleware, async (req, res) => {
  try {
    const userResult = await pool.query(
      `SELECT u.user_id, u.supabase_user_id, u.email, u.display_name, u.created_at,
              p.major, p.year, p.bio, p.interests
       FROM users u
       LEFT JOIN profiles p ON u.user_id = p.user_id
       WHERE u.supabase_user_id = $1`,
      [req.user.supabaseUserId]
    );
    
    if (userResult.rows.length === 0) {
      return res.status(404).json({ error: 'User profile not found in SDP database' });
    }
    
    res.json({
      user: req.user,
      profile: userResult.rows[0]
    });

  } catch (error) {
    console.error('Database error:', error);
    res.status(500).json({ error: 'Failed to fetch profile' });
  }
});

// Get current user data from SDP database
app.get('/api/me', authMiddleware, async (req, res) => {
  try {
    const result = await pool.query(
      `SELECT u.user_id, u.supabase_user_id, u.email, u.display_name, u.created_at,
              p.major, p.year, p.bio, p.interests
       FROM users u
       LEFT JOIN profiles p ON u.user_id = p.user_id
       WHERE u.supabase_user_id = $1`,
      [req.user.supabaseUserId]
    );
    
    if (result.rows.length === 0) {
      return res.status(404).json({ error: 'User not found in SDP database' });
    }
    
    res.json(result.rows[0]);

  } catch (error) {
    console.error('Database error:', error);
    res.status(500).json({ error: 'Failed to fetch user data' });
  }
});
app.put('/api/profile', authMiddleware, async (req, res) => {
  try {
    const { displayName, major, year, bio, interests } = req.body;
    const userResult = await pool.query(
      `UPDATE users 
       SET display_name = $1
       WHERE supabase_user_id = $2 
       RETURNING user_id`,
      [displayName, req.user.supabaseUserId]
    );
    
    if (userResult.rows.length === 0) {
      return res.status(404).json({ error: 'User not found' });
    }

    const userId = userResult.rows[0].user_id;
    const profileResult = await pool.query(
      `INSERT INTO profiles (user_id, major, year, bio, interests)
       VALUES ($1, $2, $3, $4, $5)
       ON CONFLICT (user_id) 
       DO UPDATE SET 
         major = EXCLUDED.major,
         year = EXCLUDED.year,
         bio = EXCLUDED.bio,
         interests = EXCLUDED.interests
       RETURNING *`,
      [userId, major, year, bio, interests]
    );
    
    res.json({ 
      success: true, 
      message: 'Profile updated successfully',
      profile: profileResult.rows[0] 
    });

  } catch (error) {
    console.error('Database error:', error);
    res.status(500).json({ error: 'Failed to update profile' });
  }
});

// Get all users from SDP database (for testing)
app.get('/api/users', authMiddleware, async (req, res) => {
  try {
    const result = await pool.query(
      `SELECT u.user_id, u.supabase_user_id, u.email, u.display_name, u.created_at,
              p.major, p.year
       FROM users u
       LEFT JOIN profiles p ON u.user_id = p.user_id
       ORDER BY u.created_at DESC`
    );
    
    res.json(result.rows);

  } catch (error) {
    console.error('Database error:', error);
    res.status(500).json({ error: 'Failed to fetch users' });
  }
});

// Error handling
app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).json({ error: 'Something went wrong!' });
});

app.use('*', (req, res) => {
  res.status(404).json({ error: 'Endpoint not found' });
});

// Start server
app.listen(PORT, () => {
  console.log(`✅ Server running on http://localhost:${PORT}`);
  console.log(`📋 Connected to SDP database`);
  console.log(`📋 API Documentation: http://localhost:${PORT}`);
});