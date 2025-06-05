// package in.starmaven.wealthwise.service;

// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.stereotype.Service;
// import org.springframework.beans.factory.annotation.Value;

// @Service
// public class EmailService {

//     @Value("${spring.mail.email}")
//     private String senderEmail; 

//     private final JavaMailSender mailSender;
    
//     public EmailService(JavaMailSender mailSender) {
//         this.mailSender =mailSender;
//     }

//     public void sendEmail(String to, String subject, String body) {
//         SimpleMailMessage message = new SimpleMailMessage();
//         message.setFrom(senderEmail); 
//         message.setTo(to);
//         message.setSubject(subject);
//         message.setText(body);
//         mailSender.send(message);
//     }
// }
