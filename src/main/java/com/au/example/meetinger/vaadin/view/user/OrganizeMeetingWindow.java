package com.au.example.meetinger.vaadin.view.user;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.au.example.meetinger.resource.dto.CompanyDto;
import com.au.example.meetinger.resource.dto.MeetingDto;
import com.au.example.meetinger.resource.dto.MeetingUserTokenDto;
import com.au.example.meetinger.resource.dto.UserDto;
import com.au.example.meetinger.service.MailService;
import com.au.example.meetinger.service.MeetingService;
import com.au.example.meetinger.service.data.MailProperty;
import com.au.example.meetinger.util.Utility;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.steinwedel.messagebox.MessageBox;

public class OrganizeMeetingWindow extends Window {

	private static final Logger LOGGER = Logger.getLogger(OrganizeMeetingWindow.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 7465581245852626331L;
	private TextField meetingName = new TextField("Meeting Name");
	private TextField meetingSubject = new TextField("Meeting Subject");
	private DateField meetingDate = new DateField("Meeting Date");
	private Button processButton = new Button("Create Meeting");

	public OrganizeMeetingWindow(MeetingService meetingService, MailService mailService, CompanyDto companyDto,
			List<UserDto> users, UserDto organizator, Grid userGrid) {

		Logger LOGGER = Logger.getLogger(OrganizedMeetingView.class);
		this.setWidth("300px");
		this.setHeight("300px");
		VerticalLayout layout = new VerticalLayout();

		layout.setMargin(true);
		layout.setSpacing(true);

		layout.addComponent(meetingName);
		layout.addComponent(meetingSubject);
		layout.addComponent(meetingDate);
		layout.addComponent(processButton);

		processButton.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1228462263351258970L;

			@Override
			public void buttonClick(ClickEvent evet) {
				MailProperty mailProperty = new MailProperty();
				mailProperty.setMailHost(companyDto.getMailHost());
				mailProperty.setMailPort(companyDto.getMailPort());
				mailProperty.setMailUserName(companyDto.getMailUserName());
				mailProperty.setMailPassword(companyDto.getMailPassword());
				MeetingDto meetingDto = new MeetingDto();
				meetingDto.setCompany(companyDto);
				meetingDto.setOrganizator(organizator);
				meetingDto.setName(meetingName.getValue());
				meetingDto.setSubject(meetingSubject.getValue());
				meetingDto.setMeetingDate(meetingDate.getValue());
				ExecutorService es = Executors.newSingleThreadExecutor();

				final Future<String> contentsFuture = es
						.submit(new OrganizedMeetingMail(meetingService, mailProperty, mailService, users, meetingDto));

				getUI().removeWindow(OrganizeMeetingWindow.this);
				MessageBox.createInfo().withCaption("Info").withMessage("Meeting created.")
						.withOkButton(() -> userGrid.deselectAll()).open();

				if (contentsFuture.isDone()) {
					// MessageBox.createInfo().withCaption("Meeting Organizer")
					// .withMessage(meetingDto.getName() + " is organizede")
					// .withOkButton(() -> System.out.println("Ok
					// pressed.")).open();
					LOGGER.info("All mail has been send..");
				}

			}
		});
		layout.setSizeFull();
		setModal(true);
		setContent(layout);
	}

	class OrganizedMeetingMail implements Callable<String> {
		MeetingService meetingService;
		MailProperty mailProperty;
		MailService mailService;
		List<UserDto> users;
		MeetingDto meetingDto;

		public OrganizedMeetingMail(MeetingService meetingService, MailProperty mailProperty, MailService mailService,
				List<UserDto> users, MeetingDto meetingDto) {
			this.meetingService = meetingService;
			this.mailProperty = mailProperty;
			this.mailService = mailService;
			this.users = users;
			this.meetingDto = meetingDto;
		}

		@Override
		public String call() {
			Long meetingId = meetingService.createMeeting(meetingDto);
			mailService.buildMailSender(mailProperty);
			for (UserDto userDto : users) {
				Utility.createToken();
				MeetingUserTokenDto meetingUserTokenDto = new MeetingUserTokenDto();
				meetingUserTokenDto.setMeetingId(meetingId);
				meetingUserTokenDto.setUserId(userDto.getUserId());

				meetingUserTokenDto.setToken(Utility.createToken());
				meetingService.createMeetingUserToken(meetingUserTokenDto);

				String resultMessage = mailService.sendMessage(userDto.getUserName(),
						Utility.createMeetingUrl(meetingUserTokenDto.getToken()), "Meeting Invation");
				LOGGER.info(resultMessage);

			}
			return "OK";

		}
	}

}
