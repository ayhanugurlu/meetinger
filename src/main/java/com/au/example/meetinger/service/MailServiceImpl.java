package com.au.example.meetinger.service;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.au.example.meetinger.service.data.MailProperty;

@Service
public class MailServiceImpl implements MailService {
	
	MailProperty property = null;
	
	JavaMailSenderImpl javaMailSender = null;
	
	@Override
	public JavaMailSender buildMailSender(MailProperty property) {
		javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(property.getMailHost());
		javaMailSender.setPort(property.getMailPort());
		javaMailSender.setUsername(property.getMailUserName());
		javaMailSender.setPassword(property.getMailPassword());

		Properties prop = javaMailSender.getJavaMailProperties();
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.debug", "true");
		return javaMailSender;
	}


	
	@Override
	public String sendMessage( String mailAddresses, String text,String subject) {

		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setSubject(subject);
		message.setText(text);
		message.setTo(mailAddresses);
		message.setFrom("meetinger@gmail.com");
		try {
			javaMailSender.send(message);
			return "{\"message\": \"OK\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"message\": \"Error\"}";
		}

	}



}
