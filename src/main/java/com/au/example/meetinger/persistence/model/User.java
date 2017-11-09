package com.au.example.meetinger.persistence.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity(name = "USER")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	
	@Column(name = "USER_NAME",unique=true)
	private String userName;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "SURNAME")
	private String surname;
	
	@Column(name = "ADMIN")
	private Boolean admin = false;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COMPANY_ID", nullable = false)
	private Company company;
	
	
	@OneToMany(targetEntity = Meeting.class, mappedBy = "organizator")
	@OrderBy("name ASC")
	private List<Meeting> meetings;
	
	
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
	private Set<Meeting> userMeeting = new HashSet<Meeting>(0);


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public List<Meeting> getMeetings() {
		return meetings;
	}


	public void setMeetings(List<Meeting> meetings) {
		this.meetings = meetings;
	}


	public Set<Meeting> getUserMeeting() {
		return userMeeting;
	}


	public void setUserMeeting(Set<Meeting> userMeeting) {
		this.userMeeting = userMeeting;
	}


	public Company getCompany() {
		return company;
	}


	public void setCompany(Company company) {
		this.company = company;
	}


	public Boolean getAdmin() {
		return admin;
	}


	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	
}
