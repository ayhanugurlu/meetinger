package com.au.example.meetinger.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.au.example.meetinger.persistence.model.MeetingUserToken;

public interface MeetingUserTokenRepository  extends JpaRepository<MeetingUserToken, Long>{

	
	MeetingUserToken findByToken(String token);
}
