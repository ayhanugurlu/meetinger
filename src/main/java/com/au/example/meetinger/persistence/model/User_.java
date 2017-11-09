package com.au.example.meetinger.persistence.model;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public class User_ {

	public static volatile SingularAttribute<User, Long> userId;

	public static volatile SingularAttribute<User, String> userName;

	public static volatile SingularAttribute<User, String> surname;

	public static volatile SingularAttribute<User, String> password;

	public static volatile SingularAttribute<User, Boolean> admin;

	public static volatile SingularAttribute<User, Company> company;

	public static volatile SingularAttribute<User, String> name;

	public static volatile ListAttribute<User, User> meetings;

	public static volatile SetAttribute<User, Meeting> userMeeting;

}
