// middleware/auth.js
const jwt = require('jsonwebtoken');
const { createClient } = require('@supabase/supabase-js');
const supabase = createClient(
  process.env.SUPABASE_URL || 'https://cixdigfxjvranfleyamm.supabase.co',
  process.env.SUPABASE_KEY || 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNpeGRpZ2Z4anZyYW5mbGV5YW1tIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTUyMDkyNzAsImV4cCI6MjA3MDc4NTI3MH0.ZgbRo8kxPZzhJe0BEw56seYrUlf3UiylCkeRPzdGWEQ'
);

const JWT_SECRET = process.env.SUPABASE_JWT_SECRET || 'tSDJ/iNL86HXDpNUX8QqOXV5In+bcyrWx6CYRfigDRLzfQlPD+U13ASZdRo0fpIzxIEc4/QwHVcUuAfqicnYnA==';
const authMiddleware = async (req, res, next) => {
  try {
    const authHeader = req.headers.authorization;
    if (!authHeader || !authHeader.startsWith('Bearer ')) {
      return res.status(401).json({ 
        error: 'Access denied. No token provided.',
        message: 'Please include a valid JWT token in the Authorization header'
      });
    }
    const token = authHeader.substring(7); 
    let decoded;
    try {
      decoded = jwt.verify(token, JWT_SECRET);
    } catch (jwtError) {
      return res.status(401).json({ 
        error: 'Invalid token',
        message: 'The provided token is invalid or expired'
      });
    }
    const { data: user, error } = await supabase.auth.getUser(token);
    if (error || !user) {
      return res.status(401).json({ 
        error: 'User not found',
        message: 'The user associated with this token does not exist'
      });
    }
    req.user = {
      id: user.user.id,
      email: user.user.email,
      supabaseUserId: user.user.id, 
    };
    next();

  } catch (error) {
    console.error('Auth middleware error:', error);
    return res.status(500).json({ 
      error: 'Authentication failed',
      message: 'Internal server error during authentication'
    });
  }
};
const adminMiddleware = (req, res, next) => {
  if (!req.user) {
    return res.status(403).json({ 
      error: 'Access denied',
      message: 'Admin privileges required'
    });
  }
  next();
};

module.exports = { authMiddleware, adminMiddleware };