package com.au.example.meetinger.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.au.example.meetinger.persistence.MeetingerRepositroy;
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
import com.au.example.meetinger.util.CopyStrategy;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class MeetingServiceImpl implements MeetingService {

	private static final Logger LOGGER = Logger.getLogger(MeetingServiceImpl.class);

	@Autowired
	MeetingerRepositroy meetingerRepository;

	@PostConstruct
	public void initApplication() {
		User user = meetingerRepository.findUserbyUsername("ayhanugurlu@gmail.com");
		if (user == null) {
			Company comp = new Company();
			comp.setCompanyName("admin");
			comp.setMailHost("smtp.gmail.com");
			comp.setMailPort(587);
			comp.setMailUserName("devayhanu@gmail.com");
			comp.setMailPassword("development");
			meetingerRepository.createCompany(comp);
			user = new User();
			user.setName("Ayhan");
			user.setSurname("Ugurlu");
			user.setPassword("passw0rd");
			user.setUserName("ayhanugurlu@gmail.com");
			user.setAdmin(true);
			user.setCompany(comp);
			meetingerRepository.createUser(user);

			ItemType itemType = new ItemType();
			itemType.setDescription("Action Item");
			meetingerRepository.createItemType(itemType);
			ItemType itemType2 = new ItemType();
			itemType2.setDescription("Decision");
			meetingerRepository.createItemType(itemType2);
		}

		LOGGER.info("admin user created");

	}

	@Override
	public void createUser(UserDto userDto) {
		User user = new User();
		CopyStrategy.convert(user, userDto);
		meetingerRepository.createUser(user);
	}

	@Override
	public Long login(String userName, String password) {
		Long userId = null;
		User user = meetingerRepository.findUserbyUsername(userName);
		if (user != null && user.getPassword().equals(password)) {
			userId = user.getUserId();
		}
		return userId;
	}

	@Override
	public Long createCompany(CompanyDto companyDto) {
		Company company = new Company();
		CopyStrategy.convert(company, companyDto);
		meetingerRepository.createCompany(company);
		return company.getId();

	}

	@Override
	public void updateCompany(CompanyDto companyDto) {
		Company company = meetingerRepository.findUserIdCompany(companyDto.getId());
		CopyStrategy.convert(company, companyDto);
		meetingerRepository.updateCompany(company);
	}

	@Override
	public UserDto findByIdUser(Long id) {
		User user = meetingerRepository.findUserIdUser(id);
		if (user == null) {
			return null;
		}
		UserDto dto = new UserDto();
		CopyStrategy.convert(dto, user);
		return dto;
	}

	@Override
	public List<UserDto> getCompanyUser(Long companyId) {
		List<User> users = meetingerRepository.findCompanyUser(companyId);
		List<UserDto> dtos = new ArrayList<>();
		CopyStrategy.convertUserList(dtos, users);
		return dtos;
	}

	@Override
	public CompanyDto findByIdCompany(Long id) {
		Company company = meetingerRepository.findUserIdCompany(id);
		if (company == null) {
			return null;
		}
		CompanyDto dto = new CompanyDto();
		CopyStrategy.convert(dto, company);
		return dto;
	}

	@Override
	public List<CompanyDto> getAllCompany() {
		List<CompanyDto> dtos = new ArrayList<>();
		CopyStrategy.convertCompanyList(dtos, meetingerRepository.getAllCompany());
		return dtos;
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users = meetingerRepository.getAllUser();
		List<UserDto> dtos = new ArrayList<>();
		CopyStrategy.convertUserList(dtos, users);
		return dtos;
	}

	@Override
	public Long createMeeting(MeetingDto meetingDto) {
		Meeting meeting = new Meeting();
		CopyStrategy.convert(meeting, meetingDto);
		meetingerRepository.createMeeting(meeting);
		return meeting.getMeetingId();
	}
	

	

	public MeetingDto getMeetingById(Long meetingId) {
		Meeting meeting = meetingerRepository.findMeetingById(meetingId);
		MeetingDto meetingDto = new MeetingDto();
		CopyStrategy.convert(meetingDto, meeting);
		return meetingDto;
	}

	@Override
	public List<ItemDto> findMeetingItems(Long id) {
		List<Item> items = meetingerRepository.findMeetingItem(id);
		List<ItemDto> itemDtos = new ArrayList<>();
		CopyStrategy.convertItemList(itemDtos, items);
		return itemDtos;
	}

	@Override
	public List<MeetingDto> findMeetingByCompany(Long id) {
		List<Meeting> meetings = meetingerRepository.findMeetingByCompany(id);
		List<MeetingDto> meetingDtos = new ArrayList<>();
		CopyStrategy.convertMeetingList(meetingDtos, meetings);
		return meetingDtos;
	}

	@Override
	public List<MeetingDto> getAllMeeting() {
		List<Meeting> meetings = meetingerRepository.getAllMeeting();
		List<MeetingDto> meetingDtos = new ArrayList<>();
		CopyStrategy.convertMeetingList(meetingDtos, meetings);
		return meetingDtos;
	}

	@Override
	public void createMeetingUserToken(MeetingUserTokenDto meetingUserTokenDto) {
		MeetingUserToken meetingUserToken = new MeetingUserToken();
		CopyStrategy.convert(meetingUserToken, meetingUserTokenDto);
		meetingerRepository.createMeetingUserToken(meetingUserToken);
	}

	@Override
	public MeetingUserTokenDto findMeetingUserTokenBytoken(String token) {
		MeetingUserToken meetingUserToken = meetingerRepository.findMeetingUserTokenBytoken(token);
		MeetingUserTokenDto meetingUserTokenDto = new MeetingUserTokenDto();
		CopyStrategy.convert(meetingUserTokenDto, meetingUserToken);
		return meetingUserTokenDto;
	}

	@Override
	public MeetingDto findByIdMeeting(Long id) {
		Meeting meeting = meetingerRepository.findMeetingById(id);
		MeetingDto meetingDto = new MeetingDto();
		CopyStrategy.convert(meetingDto, meeting);
		return meetingDto;
	}

	@Override
	public void updateMeeting(MeetingDto meetingDto) {

		Meeting meeting = new Meeting();
		CopyStrategy.convert(meeting, meetingDto);
		meetingerRepository.createMeeting(meeting);
		if (meeting.getEndMeeting() != null && meeting.getEndMeeting().booleanValue()) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				mapper.writeValue(new File("/Users/ayhanugurlu/meetinger/" + meeting.getMeetingId() + ".txt"), meeting);
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<ItemTypeDto> getAllItemType() {
		List<ItemType> itemTypes = meetingerRepository.getAllItemType();
		List<ItemTypeDto> itemTypeDtos = new ArrayList<>();
		CopyStrategy.convertItemTypeList(itemTypeDtos, itemTypes);
		return itemTypeDtos;
	}

	@Override
	public Long createItem(ItemDto itemDto) {
		Item item = new Item();
		CopyStrategy.convert(item, itemDto);
		return meetingerRepository.createItem(item);
	}

	@Override
	public void updateItem(ItemDto itemDto) {
		Item item = new Item();
		CopyStrategy.convert(item, itemDto);
		meetingerRepository.createItem(item);

	}

	@Override
	public void deleteItem(ItemDto itemDto) {
		Item item = new Item();
		CopyStrategy.convert(item, itemDto);
		meetingerRepository.deleteItem(item);
	}

	@Override
	public List<MeetingDto> searchMeeting(String userName, String userSurname, String meetingName,
			String meetingSubject, String meetingData) {
		List<Meeting> meetings = meetingerRepository.searchMeeting(userName, userSurname, meetingName, meetingSubject,
				meetingData);
		List<MeetingDto> meetingDtos = new ArrayList<>();
		CopyStrategy.convertMeetingList(meetingDtos, meetings);
		return meetingDtos;

	}

	@Override
	public MeetingDto addUserMeeting(Long meetingId, Long userId) {
	
		Meeting meeting =meetingerRepository.addUserMeeting(meetingId, userId);
		MeetingDto meetingDto = new MeetingDto();
		CopyStrategy.convert(meetingDto, meeting);
		return meetingDto;
	}

}
