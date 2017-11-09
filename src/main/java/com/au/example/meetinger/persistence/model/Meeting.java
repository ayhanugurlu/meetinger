package com.au.example.meetinger.persistence.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 * 
 * @author ayhanu
 *
 */
@Entity(name = "MEETING")
public class Meeting {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long meetingId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "SUBJECT")
	private String subject;
	
	@Column(name = "MEETING_DATE")
	private Date meetingDate;
	
	@Column(name = "END_MEETING")
	private Boolean endMeeting = false;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinTable(name = "meeting_user", joinColumns = {
			@JoinColumn(name = "userId", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "ID", nullable = false, updatable = false) })
	private List<User> users;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinTable(name = "meeting_accept_user", joinColumns = {
			@JoinColumn(name = "userId", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "ID", nullable = false, updatable = false) })
	private List<User> acceptUsers;
	
	
	@OneToMany(targetEntity = Item.class, mappedBy = "meeting",cascade=CascadeType.ALL)
	@OrderBy("id ASC")
	private List<Item> items;

	@ManyToOne(fetch = FetchType.LAZY,cascade =CascadeType.DETACH)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User organizator;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade =CascadeType.DETACH)
	@JoinColumn(name = "COMPANY_ID", nullable = false)
	private Company company;

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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public User getOrganizator() {
		return organizator;
	}

	public void setOrganizator(User organizator) {
		this.organizator = organizator;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<User> getAcceptUsers() {
		return acceptUsers;
	}

	public void setAcceptUsers(List<User> acceptUsers) {
		this.acceptUsers = acceptUsers;
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
