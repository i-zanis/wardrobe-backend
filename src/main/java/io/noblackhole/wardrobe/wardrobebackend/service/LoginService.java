//package io.noblackhole.wardrobe.wardrobebackend.service;
//
//import io.noblackhole.wardrobe.wardrobebackend.domain.User;
//import io.noblackhole.wardrobe.wardrobebackend.utils.JwtUtil;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.logging.Logger;
//
//@Service
//public class LoginService {
//
//  private static final Logger logger = Logger.getLogger(LoginService.class.getName());
//  private final UserService userService;
//  private final PasswordEncoder passwordEncoder;
//  private final JwtUtil jwtUtil;
//
//  public LoginService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
//    this.userService = userService;
//    this.passwordEncoder = passwordEncoder;
//    this.jwtUtil = jwtUtil;
//  }
//
//  public String login(String email, String password) {
//    logger.info("Logging in user with email " + email);
//    User user = userService.findByEmail(email);
//    if (user == null) {
//      throw new RuntimeException("Invalid email or password");
//    }
//    if (!passwordEncoder.matches(password, user.getPassword())) {
//      throw new RuntimeException("Invalid email or password");
//    }
//    return jwtUtil.generateToken(user);
//  }
//}