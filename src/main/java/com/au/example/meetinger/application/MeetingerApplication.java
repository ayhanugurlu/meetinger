package com.au.example.meetinger.application;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.au.example.meetinger.service.MeetingService;

@SpringBootApplication
@EnableJpaRepositories("com.au.example.meetinger.persistence.repository")
@EntityScan("com.au.example.meetinger.persistence.model")
@ComponentScan("com.au.example.meetinger")
public class MeetingerApplication {

	private static final Logger LOGGER = Logger.getLogger(MeetingerApplication.class);

	@Autowired
	MeetingService meetingService;

	public static void main(String[] args) {
		LOGGER.info("Meter aplication started..");
		SpringApplication.run(MeetingerApplication.class, args);

	}
	


}