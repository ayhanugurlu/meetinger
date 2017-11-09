package com.au.example.meetinger.resource.dto;

import java.util.Date;
import java.util.List;

public class MeetingDto {

	private Long meetingId;

	private String name;

	private String subject;
	
	private Boolean endMeeting;

	private List<UserDto> users;

	private List<ItemDto> items;

	private UserDto organizator;

	private CompanyDto company;
	
	private Date meetingDate;

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

	public List<ItemDto> getItems() {
		return items;
	}

	public void setItems(List<ItemDto> items) {
		this.items = items;
	}

	public UserDto getOrganizator() {
		return organizator;
	}

	public void setOrganizator(UserDto organizator) {
		this.organizator = organizator;
	}

	public CompanyDto getCompany() {
		return company;
	}

	public void setCompany(CompanyDto company) {
		this.company = company;
	}

	public Date getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}

	public Boolean getEndMeeting() {
		return endMeeting;
	}

	public void setEndMeeting(Boolean endMeeting) {
		this.endMeeting = endMeeting;
	}

	
}
