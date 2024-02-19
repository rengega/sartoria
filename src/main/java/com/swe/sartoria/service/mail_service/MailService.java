package com.swe.sartoria.service.mail_service;


import com.swe.sartoria.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService {


    private MailSender mailSender;



    public void notifyCostumer(Order order) {
        // send mail
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gegareni@gmail.com");
        message.setTo(order.getCostumer().getEmail());
        message.setSubject("Order is ready");
        message.setText("Dear " + order.getCostumer().getName()  + "\n Your order with number "+ order.getId() + " is ready for pickup!!! \n Thank you for choosing us! \n Sartoria Team");
        mailSender.send(message);
    }
}
