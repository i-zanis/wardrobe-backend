//package io.noblackhole.wardrobe.wardrobebackend.utils;
//
//import io.noblackhole.wardrobe.wardrobebackend.domain.User;
//import org.springframework.stereotype.Component;
//
//import java.security.SignatureException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.logging.Logger;
//
//@Component
//public class JwtUtil {
//
//  private static final Logger logger = Logger.getLogger(JwtUtil.class.getName());
//  private static final String SECRET_KEY = "secret-key";
//  private static final long EXPIRE_TIME = 864000000;
//
//  public String generateToken(User user) {
//    Map<String, Object> claims = new HashMap<>();
//    claims.put("id", user.getId());
//    claims.put("email", user.getEmail());
//    return createToken(claims);
//  }
//
//  private String createToken(Map<String, Object> claims) {
//    Date now = new Date();
//    Date expireDate = new Date(now.getTime() + EXPIRE_TIME);
//    return Jwts.builder()
//      .setClaims(claims)
//      .setIssuedAt(now)
//      .setExpiration(expireDate)
//      .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//      .compact();
//  }
//
//  public boolean validateToken(String token) {
//    try {
//      Jwts.parser()
//        .setSigningKey(SECRET_KEY)
//        .parseClaimsJws(token);
//      return true;
//    } catch (SignatureException ex) {
//      logger.warning("Invalid JWT signature");
//    } catch (MalformedJwtException ex) {
//      logger.warning("Invalid JWT token");
//    } catch (ExpiredJwtException ex) {
//      logger.warning("Expired JWT token");
//    } catch (UnsupportedJwtException ex) {
//      logger.warning("Unsupported JWT token");
//    } catch (IllegalArgumentException ex) {
//      logger.warning("JWT claims string is empty.");
//    }
//    return false;
//  }
//}