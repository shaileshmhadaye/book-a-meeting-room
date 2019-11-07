package com.neo.bookameetingroom.services;

import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    public String sendEmail(String to, String token) {
        String body = "To complete the password reset process, please click here: "+
                "<a href='http://localhost:8080/reset-password?token=" + token + "'>Reset Password Link</a>";
        MailUtility sendMail = new MailUtility("Reset Password Request", body, to);
        sendMail.start();
        return "Success";
    }
}
