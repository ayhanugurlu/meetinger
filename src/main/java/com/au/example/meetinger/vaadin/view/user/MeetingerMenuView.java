package com.au.example.meetinger.vaadin.view.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.addons.mspapant.clickableLabel.ClickableLabel;

import com.au.example.meetinger.service.MeetingService;
import com.au.example.meetinger.vaadin.view.admin.AdminView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button.ClickEvent;

@SpringView(name = MeetingerMenuView.NAME)
public class MeetingerMenuView extends AbsoluteLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1069548163871949358L;

	public final static String NAME = "MeetingerMenuView";

	@Autowired
	OrganizedMeetingView organizedMeetingView;

	@Autowired
	MeetingView meetingView;

	@Autowired
	SearchMeetingView searchMeetingView;

	@Autowired
	AdminView adminView;

	@Autowired
	MeetingService meetingService;

	ClickableLabel adminLabel;

	public MeetingerMenuView() {

		final ClickableLabel organizeMeetingLabel = new ClickableLabel("Organize Meeting");

		organizeMeetingLabel.addClickListener(new com.vaadin.ui.Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -1802038029609821918L;

			@Override
			public void buttonClick(ClickEvent event) {
				MeetingerMenuView.this.organizedMeetingView.buildView();
				((MeetingerView) MeetingerMenuView.this.getParent())
						.setActiveComponent(MeetingerMenuView.this.organizedMeetingView);

			}
		});

		final ClickableLabel searchLabel = new ClickableLabel("Search Meeting");

		searchLabel.addClickListener(new com.vaadin.ui.Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -1802038029609821918L;

			@Override
			public void buttonClick(ClickEvent event) {

				((MeetingerView) MeetingerMenuView.this.getParent())
						.setActiveComponent(MeetingerMenuView.this.searchMeetingView);

			}
		});

		adminLabel = new ClickableLabel("Company Create");

		adminLabel.addClickListener(new com.vaadin.ui.Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6486344704630143425L;

			@Override
			public void buttonClick(ClickEvent event) {

				((MeetingerView) MeetingerMenuView.this.getParent())
						.setActiveComponent(MeetingerMenuView.this.adminView);

			}
		});

		final ClickableLabel meetingLabel = new ClickableLabel("Meetings");

		meetingLabel.addClickListener(new com.vaadin.ui.Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6486344704630143425L;

			@Override
			public void buttonClick(ClickEvent event) {
				MeetingerMenuView.this.meetingView.buildView();
				((MeetingerView) MeetingerMenuView.this.getParent())
						.setActiveComponent(MeetingerMenuView.this.meetingView);

			}
		});

		organizeMeetingLabel.setSizeFull();

		searchLabel.setSizeFull();

		adminLabel.setSizeFull();

		meetingLabel.setSizeFull();

		this.addComponent(organizeMeetingLabel, "left: 10%; right: 10%;" + "top: 12%; bottom: 80%;");

		this.addComponent(searchLabel, "left: 10%; right: 10%;" + "top: 22%; bottom: 70%;");

		this.addComponent(meetingLabel, "left: 10%; right: 10%;" + "top: 32%; bottom: 60%;");

		this.addComponent(adminLabel, "left: 10%; right: 10%;" + "top: 42%; bottom: 50%;");

	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

	public ClickableLabel getAdminLabel() {
		return adminLabel;
	}

}
