// server.js
const express = require('express');
const cors = require('cors');
const jwt = require('jsonwebtoken');
const { createClient } = require('@supabase/supabase-js');

const app = express();
const PORT = 3001;

// Middleware
app.use(cors());
app.use(express.json());

// Supabase client
const supabase = createClient(
  process.env.SUPABASE_URL || 'https://cixdigfxjvranfleyamm.supabase.co',
  process.env.SUPABASE_SERVICE_KEY || 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNpeGRpZ2Z4anZyYW5mbGV5YW1tIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc1NTIwOTI3MCwiZXhwIjoyMDcwNzg1MjcwfQ.nMqnTnX4eqyiyjaGFv_YyE2RBxVchQ66uXvY32tgiD4',
  {
    auth: {
      lock: false // disables Navigator.locks
    }
  }
);

const JWT_SECRET = process.env.SUPABASE_JWT_SECRET || 'tSDJ/iNL86HXDpNUX8QqOXV5In+bcyrWx6CYRfigDRLzfQlPD+U13ASZdRo0fpIzxIEc4/QwHVcUuAfqicnYnA==';

// Auth Middleware
const authMiddleware = async (req, res, next) => {
  try {
    const authHeader = req.headers.authorization;
    
    if (!authHeader || !authHeader.startsWith('Bearer ')) {
      return res.status(401).json({ 
        error: 'Access denied. No token provided.' 
      });
    }

    const token = authHeader.substring(7);

    // Verify JWT token
    let decoded;
    try {
      decoded = jwt.verify(token, JWT_SECRET);
    } catch (jwtError) {
      return res.status(401).json({ 
        error: 'Invalid or expired token' 
      });
    }

    // Verify user exists in Supabase
    const { data: user, error } = await supabase.auth.getUser(token);
    
    if (error || !user) {
      return res.status(401).json({ 
        error: 'User not found' 
      });
    }

    // Add user to request object
    req.user = {
      id: user.user.id,
      email: user.user.email,
      supabaseUserId: user.user.id
    };

    next();

  } catch (error) {
    console.error('Auth middleware error:', error);
    return res.status(500).json({ 
      error: 'Authentication failed' 
    });
  }
};

// Routes
app.get('/', (req, res) => {
  res.json({ 
    message: 'Campus Study Buddy API',
    version: '1.0.0',
    endpoints: {
      health: '/api/health',
      signup: 'POST /api/users',
      getProfile: 'GET /api/profile',
      updateProfile: 'PUT /api/profile',
      getCurrentUser: 'GET /api/me'
    }
  });
});

// Health check (public)
app.get('/api/health', (req, res) => {
  res.json({ 
    status: 'OK', 
    message: 'Campus Study Buddy API is running',
    timestamp: new Date().toISOString()
  });
});

// Sign up route (public)
app.post('/api/users', async (req, res) => {
  try {
    const { supabaseUserId, email, displayName } = req.body;
    
    if (!supabaseUserId || !email) {
      return res.status(400).json({ 
        error: 'supabaseUserId and email are required' 
      });
    }

    console.log('Creating user:', { supabaseUserId, email, displayName });

    // Insert into your custom users table
    const { data, error } = await supabase
      .from('users')
      .insert([
        { 
          supabase_user_id: supabaseUserId,
          email: email,
          display_name: displayName
        }
      ])
      .select();
    
    if (error) {
      console.error('Database error:', error);
      return res.status(400).json({ error: error.message });
    }
    
    res.json({ 
      success: true, 
      message: 'User created successfully',
      user: data[0] 
    });

  } catch (error) {
    console.error('Server error:', error);
    res.status(500).json({ error: 'Internal server error' });
  }
});

// 🔐 PROTECTED ROUTES

// Get current user's profile
app.get('/api/profile', authMiddleware, async (req, res) => {
  try {
    const { data, error } = await supabase
      .from('users')
      .select('*')
      .eq('supabase_user_id', req.user.supabaseUserId)
      .single();
    
    if (error) {
      return res.status(404).json({ error: 'User profile not found' });
    }
    
    res.json({
      user: req.user,
      profile: data
    });

  } catch (error) {
    res.status(500).json({ error: 'Failed to fetch profile' });
  }
});

// Get current user data
app.get('/api/me', authMiddleware, async (req, res) => {
  try {
    const { data, error } = await supabase
      .from('users')
      .select('*')
      .eq('supabase_user_id', req.user.supabaseUserId)
      .single();
    
    if (error) throw error;
    
    res.json(data);

  } catch (error) {
    res.status(500).json({ error: 'Failed to fetch user data' });
  }
});

// Update user profile
app.put('/api/profile', authMiddleware, async (req, res) => {
  try {
    const { displayName, email } = req.body;
    
    const updates = {};
    if (displayName) updates.display_name = displayName;
    if (email) updates.email = email;

    const { data, error } = await supabase
      .from('users')
      .update(updates)
      .eq('supabase_user_id', req.user.supabaseUserId)
      .select();
    
    if (error) throw error;
    
    res.json({ 
      success: true, 
      message: 'Profile updated successfully',
      user: data[0] 
    });

  } catch (error) {
    res.status(500).json({ error: 'Failed to update profile' });
  }
});

// Get all users (for testing)
app.get('/api/users', authMiddleware, async (req, res) => {
  try {
    const { data, error } = await supabase
      .from('users')
      .select('*');
    
    if (error) throw error;
    
    res.json(data);

  } catch (error) {
    res.status(500).json({ error: 'Failed to fetch users' });
  }
});

// Error handling middleware
app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).json({ error: 'Something went wrong!' });
});

// 404 handler
app.use('*', (req, res) => {
  res.status(404).json({ error: 'Endpoint not found' });
});

// Start server
app.listen(PORT, () => {
  console.log(`✅ Server running on http://localhost:${PORT}`);
  console.log(`📋 API Documentation: http://localhost:${PORT}`);
});