package com.au.example.meetinger.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.au.example.meetinger.persistence.model.Company;
import com.au.example.meetinger.persistence.model.Item;
import com.au.example.meetinger.persistence.model.ItemType;
import com.au.example.meetinger.persistence.model.Meeting;
import com.au.example.meetinger.persistence.model.MeetingUserToken;
import com.au.example.meetinger.persistence.model.User;
import com.au.example.meetinger.resource.dto.CompanyDto;
import com.au.example.meetinger.resource.dto.ItemDto;
import com.au.example.meetinger.resource.dto.ItemTypeDto;
import com.au.example.meetinger.resource.dto.MeetingDto;
import com.au.example.meetinger.resource.dto.MeetingUserTokenDto;
import com.au.example.meetinger.resource.dto.UserDto;

/**
 * Dto to modek or model to dto
 * 
 * @author ayhanu
 *
 */
@Transactional
public class CopyStrategy {

	public static void convert(UserDto to, User from) {
		if (to == null || from == null) {
			return;
		}
		to.setUserId(from.getUserId());
		to.setName(from.getName());
		to.setSurname(from.getSurname());
		to.setUserName(from.getUserName());
		to.setPassword(from.getPassword());
		to.setAdmin(from.getAdmin());
		to.setCompanyId(from.getCompany().getId());
	}

	public static void convert(User to, UserDto from) {
		if (to == null || from == null) {
			return;
		}
		to.setUserId(from.getUserId());
		to.setName(from.getName());
		to.setSurname(from.getSurname());
		to.setUserName(from.getUserName());
		to.setPassword(from.getPassword());
		Company comp = new Company();
		comp.setId(from.getCompanyId());
		to.setCompany(comp);
		to.setAdmin(from.getAdmin());
	}

	public static void convertUserList(List<UserDto> to, List<User> from) {
		if (to == null || from == null) {
			return;
		}
		for (User user : from) {
			UserDto dto = new UserDto();
			convert(dto, user);
			to.add(dto);
		}
	}

	public static void convertUserDtoList(List<User> to, List<UserDto> from) {
		if (to == null || from == null) {
			return;
		}
		for (UserDto userDto : from) {
			User user = new User();
			convert(user, userDto);
			to.add(user);
		}
	}

	public static void convert(Company to, CompanyDto from) {
		if (to == null || from == null) {
			return;
		}
		to.setId(from.getId());
		to.setCompanyName(from.getCompanyName());
		to.setMailHost(from.getMailHost());
		to.setMailPort(from.getMailPort());
		to.setMailUserName(from.getMailUserName());
		to.setMailPassword(from.getMailPassword());

	}

	public static void convert(CompanyDto to, Company from) {
		if (to == null || from == null) {
			return;
		}
		to.setId(from.getId());
		to.setCompanyName(from.getCompanyName());
		to.setMailHost(from.getMailHost());
		to.setMailPort(from.getMailPort());
		to.setMailUserName(from.getMailUserName());
		to.setMailPassword(from.getMailPassword());
	}

	public static void convertCompanyList(List<CompanyDto> to, List<Company> from) {
		if (to == null || from == null) {
			return;
		}
		for (Company company : from) {
			CompanyDto dto = new CompanyDto();
			convert(dto, company);
			to.add(dto);
		}
	}

	public static void convert(ItemTypeDto to, ItemType from) {
		if (to == null || from == null) {
			return;
		}
		to.setId(from.getId());
		to.setDescription(from.getDescription());
	}

	public static void convert(ItemType to, ItemTypeDto from) {
		if (to == null || from == null) {
			return;
		}
		to.setId(from.getId());
		to.setDescription(from.getDescription());
	}

	public static void convert(Item to, ItemDto from) {
		if (to == null || from == null) {
			return;
		}
		to.setId(from.getId());
		ItemType itemType = new ItemType();
		convert(itemType, from.getItemType());
		to.setItemType(itemType);
		to.setValue(from.getValue());
		Meeting meeting = new Meeting();
		meeting.setMeetingId(from.getMeeetingId());
		to.setMeeting(meeting);
	}
	


	public static void convertItemTypeList(List<ItemTypeDto> to, List<ItemType> from) {
		if (to == null || from == null) {
			return;
		}
		for (ItemType itemType : from) {
			ItemTypeDto dto = new ItemTypeDto();
			convert(dto, itemType);
			to.add(dto);
		}
	}

	public static void convert(ItemDto to, Item from) {
		if (to == null || from == null) {
			return;
		}
		to.setId(from.getId());
		ItemTypeDto itemTypeDto = new ItemTypeDto();
		convert(itemTypeDto, from.getItemType());
		to.setItemType(itemTypeDto);
		to.setValue(from.getValue());
		to.setMeeetingId(from.getMeeting().getMeetingId());
	}

	public static void convertItemList(List<ItemDto> to, List<Item> from) {
		if (to == null || from == null) {
			return;
		}
		for (Item item : from) {
			ItemDto dto = new ItemDto();
			convert(dto, item);
			to.add(dto);
		}
	}

	public static void convertItemDtoList(List<Item> to, List<ItemDto> from) {
		if (to == null || from == null) {
			return;
		}
		for (ItemDto dto : from) {
			Item item = new Item();
			convert(item, dto);
			to.add(item);
		}
	}

	public static void convert(MeetingDto to, Meeting from) {
		if (to == null || from == null) {
			return;
		}
		to.setMeetingId(from.getMeetingId());
		to.setName(from.getName());
		to.setSubject(from.getSubject());
		to.setMeetingDate(from.getMeetingDate());
		to.setEndMeeting(from.getEndMeeting());
		CompanyDto companyDto = new CompanyDto();
		convert(companyDto, from.getCompany());
		to.setCompany(companyDto);
		List<ItemDto> items = new ArrayList<>();
		convertItemList(items, from.getItems());
		to.setItems(items);
		UserDto userDto = new UserDto();
		convert(userDto, from.getOrganizator());
		to.setOrganizator(userDto);
		List<UserDto> users = new ArrayList<>();
		convertUserList(users, from.getUsers());
		to.setUsers(users);
	}

	public static void convert(Meeting to, MeetingDto from) {
		if (to == null || from == null) {
			return;
		}
		to.setMeetingId(from.getMeetingId());
		to.setName(from.getName());
		to.setSubject(from.getSubject());
		to.setMeetingDate(from.getMeetingDate());
		to.setEndMeeting(from.getEndMeeting());
		Company company = new Company();
		convert(company, from.getCompany());
		to.setCompany(company);
		List<Item> items = new ArrayList<>();
		convertItemDtoList(items, from.getItems());
		to.setItems(items);
		User user = new User();
		convert(user, from.getOrganizator());
		to.setOrganizator(user);
		List<User> userDtos = new ArrayList<>();
		convertUserDtoList(userDtos, from.getUsers());
		to.setUsers(userDtos);
	}

	public static void convertMeetingList(List<MeetingDto> to, List<Meeting> from) {
		if (to == null || from == null) {
			return;
		}
		for (Meeting meeting : from) {
			MeetingDto meetingDto = new MeetingDto();
			convert(meetingDto, meeting);
			to.add(meetingDto);
		}
	}

	public static void convert(MeetingUserToken to, MeetingUserTokenDto from) {
		if (to == null || from == null) {
			return;
		}
		to.setId(from.getId());
		to.setMeetingId(from.getMeetingId());
		to.setUserId(from.getUserId());

		to.setToken(from.getToken());
	}

	public static void convert(MeetingUserTokenDto to, MeetingUserToken from) {
		if (to == null || from == null) {
			return;
		}
		to.setId(from.getId());
		to.setMeetingId(from.getMeetingId());
		to.setUserId(from.getUserId());

		to.setToken(from.getToken());
	}

}
