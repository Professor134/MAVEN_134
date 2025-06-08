package in.starmaven.wealthwise.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class EmailService {

    @Value("${spring.mail.email}")
    private String senderEmail; 

    private final JavaMailSender mailSender;
    
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail); // change this
            message.setTo(to);
                        // System.out.println(to);
            message.setSubject(subject);
                        // System.out.println( subject);
            message.setText(body);
                        // System.out.println( body);
            mailSender.send(message);
                        // System.out.println( message);
            return true;
        } catch (MailException e) {
            System.out.println("Failed to send email ll: " + e.getMessage());
            return false;
        }
    }
}
