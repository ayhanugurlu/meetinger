package com.au.example.meetinger.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.au.example.meetinger.persistence.MeetingerRepositroy;
import com.au.example.meetinger.persistence.model.User;
import com.au.example.meetinger.resource.dto.UserDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication
public class MeetingServiceTest {

	@MockBean
	MeetingerRepositroy meetingerRepositroy;

	@Autowired
	private MeetingService meetingService;

	@Test
	public void login() {
		User user = new User();
		user.setUserId(1l);
		user.setPassword("1");
		user.setUserName("test");
		when(meetingerRepositroy.findUserbyUsername("test")).thenReturn(user);

		Long userId = meetingService.login("test", "1");
		assertEquals(userId, new Long(1));
	}

	@Test
	public void findByIdUser() {
		User user = new User();
		user.setUserId(1l);
		user.setPassword("1");
		user.setUserName("test");
		when(meetingerRepositroy.findUserIdUser(1l)).thenReturn(user);

		UserDto dto = meetingService.findByIdUser(1l);

		assertEquals(dto.getUserId(), new Long(1));

	}

}
