//package io.noblackhole.wardrobe.wardrobebackend.security;
//
//import io.noblackhole.wardrobe.wardrobebackend.domain.User;
//import io.noblackhole.wardrobe.wardrobebackend.exception.JwtException;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class JwtTokenProvider {
//
//  private final String secretKey;
//
//  public JwtTokenProvider(@Value("${app.jwt.secret}") String secretKey) {
//    this.secretKey = secretKey;
//  }
//
//  public String resolveToken(HttpServletRequest request) {
//    String bearerToken = request.getHeader("Authorization");
//    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//      return bearerToken.substring(7);
//    }
//    return null;
//  }
//
//  public String generateToken(User user) {
//    Date now = new Date();
//    Date expiration = new Date(now.getTime() + TimeUnit.HOURS.toMillis(1));
//
//    return Jwts.builder()
//      .setSubject(user.getEmail())
//      .setIssuedAt(now)
//      .setExpiration(expiration)
//      .signWith(SignatureAlgorithm.HS256, secretKey)
//      .compact();
//  }
//
//  public String getUsername(String token) {
//    return Jwts.parser()
//      .setSigningKey(secretKey)
//      .parseClaimsJws(token)
//      .getBody()
//      .getSubject();
//  }
//
//  public boolean validateToken(String token) {
//    try {
//      Jwts.parser()
//        .setSigningKey(secretKey)
//        .parseClaimsJws(token);
//      return true;
//    } catch (JwtException e) {
//      return false;
//    }
//  }
//}