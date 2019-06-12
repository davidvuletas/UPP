package com.scientificcenter.service.impl;

import com.scientificcenter.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Async
    public void sendMail(List<String> to, String subject, String message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject(subject);
        msg.setText(message);
        for(String rec: to) {
            msg.setTo(rec);
        }
        System.out.println("To: ".concat(to.get(0)));
        javaMailSender.send(msg);
    }
}
