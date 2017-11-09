package com.au.example.meetinger.vaadin.view.user;

import java.text.DateFormat;

import com.au.example.meetinger.resource.dto.MeetingDto;
import com.au.example.meetinger.service.MeetingService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class MeetingDetialWindow extends Window {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1972274941356030577L;
	
	private Grid userList = new Grid("Accepted User");
	private Button processButton = new Button("Close");
	private Label meetingNameLabel = new Label("Meeting Name");
	private Label meetingSubjectLabel = new Label("Meeting Subject");
	private Label organizatorLabel = new Label("Organizator");
	private Label organizaDateLabel = new Label("Organiza Date");
	

	public MeetingDetialWindow(MeetingService meetingService,MeetingDto meetingDto) {
		this.setWidth("500px");
		this.setHeight("500px");
		VerticalLayout layout = new VerticalLayout();

		layout.setMargin(true);
		layout.setSpacing(true);
		
		
		userList.setData(meetingDto.getUsers());
		layout.addComponent(meetingNameLabel);
		meetingNameLabel.setValue(meetingDto.getName());
		layout.addComponent(meetingSubjectLabel);
		meetingSubjectLabel.setValue(meetingDto.getSubject());
		layout.addComponent(organizatorLabel);
		organizatorLabel.setValue(meetingDto.getOrganizator().getUserName());
		layout.addComponent(organizaDateLabel);
		organizaDateLabel.setValue(DateFormat.getInstance().format(meetingDto.getMeetingDate()));
		userList.setSizeFull();
		layout.addComponent(userList);
		layout.addComponent(processButton);
		layout.setComponentAlignment(processButton, Alignment.BOTTOM_CENTER);

		processButton.addClickListener(new ClickListener() {


			/**
			 * 
			 */
			private static final long serialVersionUID = 1604737800476091016L;

			@Override
			public void buttonClick(ClickEvent evet) {

				getUI().removeWindow(MeetingDetialWindow.this);

			}
		});
		layout.setSizeFull();
		setModal(true);
		setContent(layout);
	}
}
