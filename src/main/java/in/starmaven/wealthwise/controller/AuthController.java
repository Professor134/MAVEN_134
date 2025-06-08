package in.starmaven.wealthwise.controller;

import in.starmaven.wealthwise.security.JwtUtil;
import in.starmaven.wealthwise.service.EmailService;
import in.starmaven.wealthwise.entity.User;
import in.starmaven.wealthwise.repository.UserRepository;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthController(JwtUtil jwtUtil, UserRepository userRepository,EmailService emailService ) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.emailService = emailService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid login Credentials");
        }

        User user = userOptional.get();

        System.out.println("Raw password: " + password);
        System.out.println("Raw password after encode: " + passwordEncoder.matches(password, user.getPassword()));
        System.out.println("Encoded password from DB: " + user.getPassword());
        System.out.println("Match result: " + passwordEncoder.matches(password, user.getPassword()));

        // Check given password matches the hashed password in the db
        if (!passwordEncoder.matches(password, user.getPassword()))
        {
            return ResponseEntity.status(401).body("Invalid Password");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());
        claims.put("family_name", user.getFamily_name());
        claims.put("contactNumber", user.getContactNumber());

        String token = jwtUtil.generateToken(email, claims);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("id", String.valueOf(user.getId()));
        response.put("email", user.getEmail());
        response.put("role", user.getRole());
        response.put("family_name", user.getFamily_name());
        response.put("contactNumber", user.getContactNumber());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        System.out.println(email);
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required.");
        }
 
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        String token = jwtUtil.generateResetToken(email);
        String resetLink = "http://localhost:5173/reset?token=" + token;

        String body = "Hi, click the link below to reset your password:\n" + resetLink + "\nNote: This link will expire in 15 minutes.";
        boolean sent = emailService.sendEmail(email, "Password Reset", body);
            if (!sent) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send reset email. Please try again later.");
            }
        return ResponseEntity.ok("Reset link sent to your email"); 
        
        // return ResponseEntity.ok(Collections.singletonMap("resetLink", resetLink));
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        System.out.println(token);
        System.out.println(newPassword);
        String email; 
        try {
            email = jwtUtil.extractEmail(token);
            System.out.println(email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok("Password reset successfully. Please login.");
    }
}