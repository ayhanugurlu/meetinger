package com.au.example.meetinger.service;

import org.springframework.mail.javamail.JavaMailSender;

import com.au.example.meetinger.service.data.MailProperty;

public interface MailService {

	JavaMailSender buildMailSender(MailProperty property);

	String sendMessage(String mailAddresses, String text,String subject);

}
