package com.au.example.meetinger.persistence;

import java.util.List;

import com.au.example.meetinger.persistence.model.Company;
import com.au.example.meetinger.persistence.model.Item;
import com.au.example.meetinger.persistence.model.ItemType;
import com.au.example.meetinger.persistence.model.Meeting;
import com.au.example.meetinger.persistence.model.MeetingUserToken;
import com.au.example.meetinger.persistence.model.User;

public interface MeetingerRepositroy {

	void createUser(User user);

	User findUserbyUsername(String userName);

	User findUserIdUser(Long id);

	Long createItem(Item item);

	void updateItem(Item item);

	void createItemType(ItemType itemType);

	void createMeeting(Meeting meeting);

	void createCompany(Company company);

	void updateCompany(Company company);

	Company findUserIdCompany(Long id);

	List<Company> getAllCompany();

	List<Meeting> getAllMeeting();

	List<Meeting> findMeetingByCompany(Long id);

	List<User> findCompanyUser(Long companyId);

	List<User> getAllUser();

	Meeting findMeetingById(Long id);

	List<Item> findMeetingItem(Long id);

	Meeting addUserMeeting(Long meetingId, Long userId);

	MeetingUserToken findMeetingUserTokenBytoken(String token);

	void createMeetingUserToken(MeetingUserToken meetingUserToken);

	List<ItemType> getAllItemType();

	void deleteItem(Item item);

	List<Meeting> searchMeeting(String userName, String userSurname, String meetingName, String meetingSubject,
			String meetingData);

}
