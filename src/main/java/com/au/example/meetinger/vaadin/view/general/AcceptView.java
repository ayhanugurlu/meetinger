package com.au.example.meetinger.vaadin.view.general;

import org.springframework.beans.factory.annotation.Autowired;

import com.au.example.meetinger.resource.dto.MeetingDto;
import com.au.example.meetinger.resource.dto.MeetingUserTokenDto;
import com.au.example.meetinger.service.MeetingService;
import com.au.example.meetinger.util.Constant;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = AcceptView.NAME)
public class AcceptView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6314850199081502978L;
	public static final String NAME = "AcceptView";

	@Autowired
	public AcceptView(MeetingService meetingService) {

		Label acceptInfo = new Label();

		Link link = new Link("Go to Login view..", new ExternalResource("/MeetingerUI"));

		String token = (String) VaadinService.getCurrentRequest().getWrappedSession().getAttribute(Constant.TOKEN);
		MeetingUserTokenDto dto = meetingService.findMeetingUserTokenBytoken(token);
		String info = "";
		if (dto != null && dto.getId() != null) {

			MeetingDto meetingDto = meetingService.addUserMeeting(dto.getMeetingId(), dto.getUserId());
			info += "You were added to " + meetingDto.getName();

		} else {
			info = "Meeting didn'd find..";
		}
		acceptInfo.setValue(info);

		addComponent(acceptInfo);
		addComponent(link);
		setComponentAlignment(acceptInfo, Alignment.MIDDLE_CENTER);
		setComponentAlignment(link, Alignment.MIDDLE_CENTER);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
