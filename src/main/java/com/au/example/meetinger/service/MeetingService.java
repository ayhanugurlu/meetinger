package com.au.example.meetinger.service;

import java.util.List;

import com.au.example.meetinger.resource.dto.CompanyDto;
import com.au.example.meetinger.resource.dto.ItemDto;
import com.au.example.meetinger.resource.dto.ItemTypeDto;
import com.au.example.meetinger.resource.dto.MeetingDto;
import com.au.example.meetinger.resource.dto.MeetingUserTokenDto;
import com.au.example.meetinger.resource.dto.UserDto;

public interface MeetingService {

	void createUser(UserDto userDto);

	Long login(String userName, String password);

	UserDto findByIdUser(Long id);

	Long createCompany(CompanyDto companyDto);

	void updateCompany(CompanyDto companyDto);

	CompanyDto findByIdCompany(Long id);

	List<CompanyDto> getAllCompany();

	List<UserDto> getCompanyUser(Long companyId);

	List<UserDto> getAllUser();

	List<MeetingDto> getAllMeeting();

	List<ItemTypeDto> getAllItemType();

	MeetingDto findByIdMeeting(Long id);

	List<MeetingDto> findMeetingByCompany(Long id);

	Long createMeeting(MeetingDto meetingDto);

	void updateMeeting(MeetingDto meetingDto);

	Long createItem(ItemDto itemDto);

	void updateItem(ItemDto itemDto);

	List<ItemDto> findMeetingItems(Long id);

	void createMeetingUserToken(MeetingUserTokenDto meetingUserTokenDto);

	MeetingUserTokenDto findMeetingUserTokenBytoken(String token);

	void deleteItem(ItemDto itemDto);

	List<MeetingDto> searchMeeting(String userName, String userSurname, String meetingName, String meetingSubject,
			String meetingData);

	MeetingDto addUserMeeting(Long meetingId, Long userId);
}
