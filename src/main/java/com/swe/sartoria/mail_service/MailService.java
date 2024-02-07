package com.swe.sartoria.mail_service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailService {

    @Autowired
    private MailSender mailSender;


    public void sendMail(String to, String subject, String body) {
        // send mail
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gegareni@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);

    }
}
