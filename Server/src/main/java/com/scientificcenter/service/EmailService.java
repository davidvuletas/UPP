package com.scientificcenter.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmailService {

    @Async
    void sendMail(List<String> to, String subject, String message);
}
