package com.au.example.meetinger.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import com.au.example.meetinger.resource.dto.ItemDto;
import com.au.example.meetinger.resource.dto.ItemTypeDto;
import com.au.example.meetinger.resource.dto.MeetingDto;
import com.au.example.meetinger.resource.dto.UserDto;

public class Utility {

	public static String createMeetingUrl(String token) {
		return "http://localhost:8080/MeetingerUI#!AcceptView?token=" + token;
	}

	public static String createToken() {
		return UUID.randomUUID().toString();
	}

	public String createMeetingDetialString(MeetingDto meetingDto) {

		StringBuilder builder = new StringBuilder();

		builder.append("Meeting Subject\n");
		builder.append(meetingDto.getSubject());
		builder.append("\n");
		builder.append("Organizer");
		builder.append(meetingDto.getOrganizator().getName() + " " + meetingDto.getOrganizator().getSurname());

		builder.append("Participant Users");
		for (UserDto user : meetingDto.getUsers()) {
			builder.append(user.getName() + " " + user.getSurname() + "\n");
		}

		if (meetingDto.getItems() != null && meetingDto.getItems().size() > 0) {
			List<ItemDto> items = meetingDto.getItems();
			Collections.sort(items, new Comparator<ItemDto>() {

				@Override
				public int compare(ItemDto o1, ItemDto o2) {
					return o1.getItemType().getId().compareTo(o2.getItemType().getId());
				}
			});
			ItemTypeDto itemTypeDto = null;
			builder.append("Meeting Notes");
			int counter = 1;
			for (ItemDto item : items) {
				if (itemTypeDto == null || itemTypeDto.getId() != item.getItemType().getId()) {
					itemTypeDto = item.getItemType();
					builder.append(itemTypeDto.getDescription() + "\n");
					counter = 1;
				}
				builder.append(counter++ + item.getValue() + "\n");
			}

		}

		return builder.toString();
	}

}
