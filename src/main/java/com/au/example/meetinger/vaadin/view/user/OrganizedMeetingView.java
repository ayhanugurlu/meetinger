package com.au.example.meetinger.vaadin.view.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.au.example.meetinger.resource.dto.CompanyDto;
import com.au.example.meetinger.resource.dto.UserDto;
import com.au.example.meetinger.service.MailService;
import com.au.example.meetinger.service.MeetingService;
import com.au.example.meetinger.util.Constant;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
@Scope("prototype")
public class OrganizedMeetingView extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1579878993261494868L;

	@Autowired
	MeetingService meetingService;

	@Autowired
	MailService mailService;

	private UserDto activeUser = null;
	private CompanyDto activeUserCompany = null;
	private Grid grid;

	public OrganizedMeetingView() {

	}

	public void buildView() {

		setSizeFull();
		@SuppressWarnings("static-access")
		Long activeUserId = (Long) getUI().getCurrent().getSession().getAttribute(Constant.ACTIVE_USER);
		activeUser = meetingService.findByIdUser(activeUserId);
		activeUserCompany = meetingService.findByIdCompany(activeUser.getCompanyId());

		ComboBox companys = new ComboBox();
		List<CompanyDto> companyDtos = new ArrayList<>();
		CompanyDto companyDto = new CompanyDto();
		companyDto.setCompanyName("All Company");
		companyDtos.add(companyDto);
		companyDtos.addAll(meetingService.getAllCompany());
		companys.setContainerDataSource(new BeanItemContainer<>(CompanyDto.class, companyDtos));
		
		
		companys.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7561038666712476866L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				CompanyDto selectedCompany = (CompanyDto) event.getProperty().getValue();
				if (selectedCompany != null && selectedCompany.getId() != null) {
					grid.setContainerDataSource(
							new BeanItemContainer<>(UserDto.class, meetingService.getCompanyUser(selectedCompany.getId())));
				} else {
					grid.setContainerDataSource(
							new BeanItemContainer<>(UserDto.class, meetingService.getAllUser()));
				}

			}
		});

		Button button = new Button("Organize Meeting");
		Label emptyLabel = new Label("");
		emptyLabel.setHeight("1em");
		grid = new Grid();
		grid.setSizeFull();
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setContainerDataSource(new BeanItemContainer<>(UserDto.class, meetingService.getAllUser()));
		Column passwordColumn = grid.getColumn("password");
		passwordColumn.setHidden(true);
		Column idColumn = grid.getColumn("userId");
		idColumn.setHidden(true);
		VerticalLayout mainLayout = new VerticalLayout(companys, emptyLabel, grid, emptyLabel, button);
		mainLayout.setComponentAlignment(button, Alignment.BOTTOM_CENTER);
		setCompositionRoot(mainLayout);

		button.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7794529421414712124L;

			@Override
			public void buttonClick(ClickEvent event) {
				Collection<Object> selectedObject = grid.getSelectedRows();
				List<UserDto> users = new ArrayList<>();
				for (Object o : selectedObject) {
					users.add((UserDto) o);
				}
				OrganizeMeetingWindow organizeMeetingWindow = new OrganizeMeetingWindow(meetingService, mailService,
						OrganizedMeetingView.this.activeUserCompany, users, OrganizedMeetingView.this.activeUser, grid);

				getUI().addWindow(organizeMeetingWindow);

			}
		});

	}

}
