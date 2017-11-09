package com.au.example.meetinger.persistence.model;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Meeting.class)
public class Meeting_ {

	public static volatile SingularAttribute<Meeting, Long> meetingId;

	public static volatile SingularAttribute<Meeting, String> name;

	public static volatile SingularAttribute<Meeting, String> subject;

	public static volatile SingularAttribute<Meeting, Date> meetingDate;

	public static volatile SingularAttribute<Meeting, Boolean> endMeeting;

	public static volatile ListAttribute<Meeting, User> users;

	public static volatile ListAttribute<Meeting, User> acceptUsers;

	public static volatile ListAttribute<Meeting, Item> items;

	public static volatile SingularAttribute<Meeting, User> organizator;

	public static volatile SingularAttribute<Meeting, Company> company;

}
