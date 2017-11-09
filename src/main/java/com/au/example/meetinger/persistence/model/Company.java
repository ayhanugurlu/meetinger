package com.au.example.meetinger.persistence.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Company {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "COMPANY_NAME")
	private String companyName;
	
	@Column(name = "MAIL_HOST")
	private String mailHost;
	
	@Column(name = "MAIL_PORT")
	private int mailPort;
	
	@Column(name = "MAIL_USERNAME")
	private String mailUserName;
	
	@Column(name = "MAIL_PASSWORD")
	private String mailPassword;
	
	@OneToMany(targetEntity = User.class, mappedBy = "company")
	@OrderBy("name ASC")
	private List<User> users;
	
	@OneToMany(targetEntity = Meeting.class, mappedBy = "company")
	@OrderBy("name ASC")
	private List<Meeting> meetings;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<Meeting> meetings) {
		this.meetings = meetings;
	}

	public String getMailHost() {
		return mailHost;
	}

	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	public int getMailPort() {
		return mailPort;
	}

	public void setMailPort(int mailPort) {
		this.mailPort = mailPort;
	}

	public String getMailUserName() {
		return mailUserName;
	}

	public void setMailUserName(String mailUserName) {
		this.mailUserName = mailUserName;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}
	
	
	
	
}
