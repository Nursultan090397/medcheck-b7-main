package com.example.medcheckb7.db.entities.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender javaMailSender;

    public void sendEmail(String email, String text) {
        SimpleMailMessage mimeMessage = new SimpleMailMessage();
        mimeMessage.setFrom("medcheckb7@gmail.com");
        mimeMessage.setTo(email);
        mimeMessage.setSubject("Clinic");
        mimeMessage.setText(text);
        javaMailSender.send(mimeMessage);
    }
}
