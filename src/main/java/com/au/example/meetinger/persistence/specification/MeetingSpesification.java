package com.au.example.meetinger.persistence.specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;

import com.au.example.meetinger.persistence.model.Item;
import com.au.example.meetinger.persistence.model.Item_;
import com.au.example.meetinger.persistence.model.Meeting;
import com.au.example.meetinger.persistence.model.Meeting_;
import com.au.example.meetinger.persistence.model.User;
import com.au.example.meetinger.persistence.model.User_;


public class MeetingSpesification {

	public static Specification<Meeting> likeUsername(String username) {
		return (root, query, cb) -> {
			final Join<Meeting, User> users = root.join(Meeting_.users, JoinType.LEFT);
			return cb.like(users.get(User_.name), username);
		};
	}

	public static Specification<Meeting> likeSurname(String surname) {
		return (root, query, cb) -> {

			return cb.like(root.get("users").get("surname"), surname);
		};
	}

	public static Specification<Meeting> likeName(String name) {
		return (root, query, cb) -> {
			return cb.like(root.get("name"), name);
		};
	}

	public static Specification<Meeting> likeSubject(String subject) {
		return (root, query, cb) -> {
			return cb.like(root.get("subject"), subject);
		};
	}

	public static Specification<Meeting> likeItemsValue(String itemsValue) {
		return (root, query, cb) -> {
			final Join<Meeting, Item> items = root.join(Meeting_.items, JoinType.LEFT);
			return cb.like(items.get(Item_.value), itemsValue);
		};
	}

}
