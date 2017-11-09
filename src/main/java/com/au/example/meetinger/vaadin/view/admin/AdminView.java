package com.au.example.meetinger.vaadin.view.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.au.example.meetinger.resource.dto.CompanyDto;
import com.au.example.meetinger.service.MeetingService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("prototype")
public class AdminView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8649801114804952916L;

	MeetingService meetingService;

	private Button companyEdit = new Button();
	private Button companyAdd = new Button();
	private Grid grid = new Grid();
	private CompanyDto localDto = null;

	@Autowired
	public AdminView(MeetingService meetingService) {
		this.meetingService = meetingService;
		setSizeFull();
		grid = new Grid();
		grid.setSizeFull();
		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.setContainerDataSource(new BeanItemContainer<>(CompanyDto.class, meetingService.getAllCompany()));
		Column idColumn = grid.getColumn("id");
		idColumn.setHidden(true);
		HorizontalLayout addLayout = new HorizontalLayout();
		addLayout.addComponent(companyAdd);

		addLayout.addComponent(companyEdit);

		VerticalLayout mainLayout = new VerticalLayout(addLayout, grid);

		grid.addSelectionListener(new SelectionListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -806368891995585268L;

			@Override
			public void select(SelectionEvent event) {

				CompanyDto dto = (CompanyDto) event.getSelected().iterator().next();
				localDto = dto;
				companyEdit.setVisible(true);

			}

		});
		companyEdit.setVisible(false);
		companyEdit.setCaption("Edit Company");
		companyEdit.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 8772365087336745572L;

			@Override
			public void buttonClick(ClickEvent event) {

				CompanyWindow companyWindow = new CompanyWindow(meetingService, localDto,AdminView.this.grid);				
				getUI().addWindow(companyWindow);

			}
		});
		companyAdd.setCaption("Add Company");
		companyAdd.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 9008981928568368466L;

			@Override
			public void buttonClick(ClickEvent event) {
				CompanyWindow companyWindow = new CompanyWindow(meetingService, null,AdminView.this.grid);				
				getUI().addWindow(companyWindow);
			}
		});
		this.addComponent(mainLayout);
		this.setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);

	}



}
