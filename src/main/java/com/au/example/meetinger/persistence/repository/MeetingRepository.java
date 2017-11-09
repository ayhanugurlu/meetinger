package com.au.example.meetinger.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.au.example.meetinger.persistence.model.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long>, JpaSpecificationExecutor<Meeting> {

	List<Meeting> findByUsers_NameLikeOrUsers_SurnameLikeOrNameLikeOrSubjectLikeOrItems_ValueLike(String userName,
			String surName, String meetingName, String meetingSubject, String meetingData);

	List<Meeting> findByCompany_Id(Long id);

	Meeting findByMeetingIdAndUsers_UserId(Long meetingId, Long userId);
}
