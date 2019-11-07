package com.neo.bookameetingroom.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    public String sendEmail(String to, String token);
}
