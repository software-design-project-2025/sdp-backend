// package com.ctrlaltdelinquents.backend.config;
//
// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;
//
// import java.io.IOException;
//
// @Component
// public class SupabaseJwtFilter extends OncePerRequestFilter {
//    
//     @Autowired
//     private SupabaseConfig supabaseConfig;
//    
//     @Override
//     protected void doFilterInternal(HttpServletRequest request, 
//                                   HttpServletResponse response, 
//                                   FilterChain filterChain) throws IOException, ServletException {
//        
//         String authHeader = request.getHeader("Authorization");
//         if (authHeader != null && authHeader.startsWith("Bearer ")) {
//             String token = authHeader.substring(7);
//             try {
//                 Claims claims = Jwts.parserBuilder()
//                     .setSigningKey(supabaseConfig.getSupabaseJwtSecret().getBytes())
//                     .build()
//                     .parseClaimsJws(token)
//                     .getBody();
//                
//                 String supabaseUserId = claims.getSubject();
//                 request.setAttribute("supabaseUserId", supabaseUserId);
//                
//             } catch (Exception e) {
//                 response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                 response.getWriter().write("Invalid JWT token");
//                 return;
//             }
//         }
//        
//         filterChain.doFilter(request, response);
//     }
// }