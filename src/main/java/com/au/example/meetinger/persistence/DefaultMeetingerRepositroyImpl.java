package com.au.example.meetinger.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.au.example.meetinger.persistence.model.Company;
import com.au.example.meetinger.persistence.model.Item;
import com.au.example.meetinger.persistence.model.ItemType;
import com.au.example.meetinger.persistence.model.Meeting;
import com.au.example.meetinger.persistence.model.MeetingUserToken;
import com.au.example.meetinger.persistence.model.User;
import com.au.example.meetinger.persistence.repository.CompanyRepository;
import com.au.example.meetinger.persistence.repository.ItemRepository;
import com.au.example.meetinger.persistence.repository.ItemTypeRepository;
import com.au.example.meetinger.persistence.repository.MeetingRepository;
import com.au.example.meetinger.persistence.repository.MeetingUserTokenRepository;
import com.au.example.meetinger.persistence.repository.UserRepository;
import com.au.example.meetinger.persistence.specification.MeetingSpesification;
import com.google.common.collect.Lists;

@Repository
@Transactional
public class DefaultMeetingerRepositroyImpl implements MeetingerRepositroy {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	ItemTypeRepository itemTypeRepository;

	@Autowired
	MeetingRepository meetingRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	MeetingUserTokenRepository meetingUserTokenRepository;

	@Override
	public void createUser(User user) {
		userRepository.save(user);
	}

	@Override
	public User findUserbyUsername(String userName) {
		User result = null;
		List<User> users = userRepository.findUserbyUsername(userName);
		if (users != null && users.size() > 0) {
			result = users.get(0);
		}
		return result;
	}

	@Override
	public User findUserIdUser(Long id) {

		return userRepository.findOne(id);
	}

	@Override
	public Long createItem(Item item) {
		item.setMeeting(meetingRepository.findOne(item.getMeeting().getMeetingId()));
		itemRepository.save(item);
		return item.getId();
	}

	@Override
	public void updateItem(Item item) {
		item.setMeeting(meetingRepository.findOne(item.getMeeting().getMeetingId()));
		itemRepository.save(item);
	}

	@Override
	public void deleteItem(Item item) {
		itemRepository.delete(item.getId());
	}

	@Override
	public void createItemType(ItemType itemType) {
		itemTypeRepository.save(itemType);
	}

	@Override
	public void createMeeting(Meeting meeting) {
		meetingRepository.save(meeting);
	}

	@Override
	public void createCompany(Company company) {
		companyRepository.save(company);
	}

	@Override
	public void updateCompany(Company company) {
		companyRepository.save(company);
	}

	@Override
	public Company findUserIdCompany(Long id) {

		return companyRepository.findOne(id);
	}

	@Override
	public List<Company> getAllCompany() {
		return Lists.newArrayList(companyRepository.findAll());
	}

	@Override
	public List<User> findCompanyUser(Long companyId) {
		return userRepository.findCompanyUser(companyId);
	}

	@Override
	public List<User> getAllUser() {

		return Lists.newArrayList(userRepository.findAll());
	}

	@Override
	public Meeting findMeetingById(Long id) {
		return meetingRepository.findOne(id);
	}

	@Override
	public List<Item> findMeetingItem(Long id) {
		return itemRepository.findMeetingItems(id);
	}

	@Override
	public List<Meeting> findMeetingByCompany(Long id) {
		return meetingRepository.findByCompany_Id(id);
	}

	@Override
	public List<Meeting> getAllMeeting() {

		return Lists.newArrayList(meetingRepository.findAll());
	}

	@Override
	public MeetingUserToken findMeetingUserTokenBytoken(String token) {

		return meetingUserTokenRepository.findByToken(token);
	}

	@Override
	public void createMeetingUserToken(MeetingUserToken meetingUserToken) {
		meetingUserTokenRepository.save(meetingUserToken);

	}

	@Override
	public List<ItemType> getAllItemType() {
		return Lists.newArrayList(itemTypeRepository.findAll());
	}

	@Override
	public List<Meeting> searchMeeting(String userName, String userSurname, String meetingName, String meetingSubject,
			String meetingData) {

		List<Specification<Meeting>> specifications = new ArrayList<>();
		if (userName != null && userName.length() > 0) {
			specifications.add(MeetingSpesification.likeUsername("%" + userName + "%"));
		}

		if (userSurname != null && userSurname.length() > 0) {
			specifications.add(MeetingSpesification.likeSurname("%" + userSurname + "%"));
		}

		if (meetingName != null && meetingName.length() > 0) {
			specifications.add(MeetingSpesification.likeName("%" + meetingName + "%"));
		}

		if (meetingSubject != null && meetingSubject.length() > 0) {
			specifications.add(MeetingSpesification.likeSubject("%" + meetingSubject + "%"));
		}

		if (meetingData != null && meetingData.length() > 0) {
			specifications.add(MeetingSpesification.likeItemsValue("%" + meetingData + "%"));
		}

		Specifications<Meeting> specs = null;
		for (Specification<Meeting> s : specifications) {
			if (specs == null) {
				specs = Specifications.where(s);
			} else {
				specs = specs.and(s);
			}

		}

		List<Meeting> meetings = meetingRepository.findAll(specs);

		return meetings;
	}

	@Override
	public Meeting addUserMeeting(Long meetingId, Long userId) {
		User user = userRepository.findOne(userId);
		Meeting userMeeting = meetingRepository.findByMeetingIdAndUsers_UserId(meetingId, user.getUserId());
		if (userMeeting == null && user != null) {
			Meeting meeting = meetingRepository.getOne(meetingId);
			if (meeting != null) {
				if (meeting.getUsers() == null) {
					meeting.setUsers(new ArrayList<>());
				}
				meeting.getUsers().add(user);
				meetingRepository.save(meeting);
			}
		}
		return userMeeting;
	}

}
