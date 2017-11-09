package com.au.example.meetinger.vaadin.view.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.au.example.meetinger.resource.dto.CompanyDto;
import com.au.example.meetinger.resource.dto.UserDto;
import com.au.example.meetinger.service.MeetingService;
import com.au.example.meetinger.util.Constant;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = MeetingerView.NAME)
public class MeetingerView extends AbsoluteLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8489371346933971573L;

	private VerticalLayout mainView = new VerticalLayout();

	private Component activeComponent = null;

	private Label activeUserLabel = null;
	private Label activeUserCompanyLabel = null;

	@Autowired
	private SpringViewProvider viewProvider;

	@Autowired
	private MeetingService meetingService;

	public static final String NAME = "MeetingerView";

	public MeetingerView() {
		buildView();

	}

	public void buildView() {
		HorizontalLayout heeaderLayout = new HorizontalLayout();
		VerticalLayout headerUserInfoLayout = new VerticalLayout();
		activeUserLabel = new Label();
		activeUserCompanyLabel = new Label();
		headerUserInfoLayout.addComponent(activeUserLabel);
		headerUserInfoLayout.addComponent(activeUserCompanyLabel);
		heeaderLayout.addComponent(headerUserInfoLayout);
		heeaderLayout.setComponentAlignment(headerUserInfoLayout, Alignment.BOTTOM_LEFT);
		Button logoutButton = new Button("Logout");
		heeaderLayout.addComponent(logoutButton);
		heeaderLayout.setComponentAlignment(logoutButton, Alignment.BOTTOM_RIGHT);
		heeaderLayout.setSizeFull();
		logoutButton.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 9170548889383397640L;

			@Override
			public void buttonClick(ClickEvent event) {

				VaadinService.getCurrentRequest().getWrappedSession().removeAttribute(Constant.ACTIVE_USER);
				getUI().getNavigator().navigateTo(LoginView.NAME);

			}
		});

		this.addComponent(heeaderLayout, "left: 5%; right: 5%;" + "top: 0%; bottom: 95%;");
		activeComponent = new Label();
		mainView.addComponent(activeComponent);
		this.addComponent(mainView, "left: 30%; right: 5%;" + "top: 10%; bottom: 0%;");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		@SuppressWarnings("static-access")
		Long activeUserId = (Long) getUI().getCurrent().getSession().getAttribute(Constant.ACTIVE_USER);
		UserDto activeUser = meetingService.findByIdUser(activeUserId);
		CompanyDto activeUserCompany = meetingService.findByIdCompany(activeUser.getCompanyId());
		activeUserLabel.setValue(activeUser.getName()+ " " + activeUser.getSurname());
		activeUserCompanyLabel.setValue(activeUserCompany.getCompanyName());
		activeComponent = new Label("Welcome " + activeUser.getName() + " " + activeUser.getSurname());
		MeetingerMenuView meetingerMenuView = (MeetingerMenuView) viewProvider.getView(MeetingerMenuView.NAME);
		if(activeUser.getAdmin() != null && !activeUser.getAdmin()){
			meetingerMenuView.getAdminLabel().setVisible(false);	
		}
		
		this.addComponent(meetingerMenuView, "left: 5%; right: 70%;" + "top: 10%; bottom: 0%;");
	
	}

	public VerticalLayout getMainView() {
		return mainView;
	}

	public void setActiveComponent(Component activeComponent) {
		mainView.replaceComponent(this.activeComponent, activeComponent);
		this.activeComponent = activeComponent;
	}

}
