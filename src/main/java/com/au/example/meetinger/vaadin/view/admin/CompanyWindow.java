package com.au.example.meetinger.vaadin.view.admin;

import com.au.example.meetinger.resource.dto.CompanyDto;
import com.au.example.meetinger.service.MeetingService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


/**
 * 
 * @author ayhanu
 *
 */
public class CompanyWindow extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6643380711043034542L;
	private TextField companyName = new TextField();
	private TextField companyMailHost = new TextField();
	private TextField companyMailPort = new TextField();

	private TextField companyMailUserName = new TextField();
	private TextField companyMailPassword = new TextField();

	private Button processButton = new Button();

	private CompanyDto companyDto;

	Grid grid = null;

	public CompanyWindow(MeetingService meetingService, CompanyDto companyDto, Grid grid) {
		this.companyDto = companyDto;

		this.setWidth("400px");
		this.setHeight("400px");
		VerticalLayout layout = new VerticalLayout();

		layout.setMargin(true);
		layout.setSpacing(true);

		if (companyDto == null) {
			processButton.setCaption("Save");

		} else {
			processButton.setCaption("Edit");
			companyName.setValue(this.companyDto.getCompanyName()!= null ?this.companyDto.getCompanyName() :"");
			companyMailHost.setValue(this.companyDto.getMailHost() != null ? this.companyDto.getMailHost() : "");
			companyMailPort.setValue(this.companyDto.getMailPort()+"");
			companyMailUserName.setValue(this.companyDto.getMailUserName()!= null ?this.companyDto.getMailUserName() : "");
			companyMailPassword.setValue(this.companyDto.getMailPassword() != null ? this.companyDto.getMailPassword() : "");
		}

		companyName.setCaption("Company Name");
		companyMailHost.setCaption("Company Host");
		companyMailPort.setCaption("Company Mail Port");
		companyMailPort.setConverter(Integer.class);
		companyMailUserName.setCaption("Company Mail Username");
		companyMailPassword.setCaption("Company Mail Passowrd");

		processButton.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5944115789207638814L;

			@Override
			public void buttonClick(ClickEvent event) {

				if (CompanyWindow.this.companyDto == null) {
					CompanyDto dto = new CompanyDto();
					dto.setCompanyName(companyName.getValue());
					dto.setMailHost(companyMailHost.getValue());
					dto.setMailPort(companyMailPort.getValue() != null ? new Integer(companyMailPort.getValue()) : 0);
					dto.setMailUserName(companyMailUserName.getValue());
					dto.setMailPassword(companyMailPassword.getValue());
					meetingService.createCompany(dto);
					grid.setContainerDataSource(new BeanItemContainer<>(CompanyDto.class, meetingService.getAllCompany()));
				}else{
					
					CompanyWindow.this.companyDto.setCompanyName(companyName.getValue());
					CompanyWindow.this.companyDto.setMailHost(companyMailHost.getValue());
					CompanyWindow.this.companyDto.setMailPort(companyMailPort.getValue() != null ? new Integer(companyMailPort.getValue()) : 0);
					CompanyWindow.this.companyDto.setMailUserName(companyMailUserName.getValue());
					CompanyWindow.this.companyDto.setMailPassword(companyMailPassword.getValue());
					meetingService.updateCompany(CompanyWindow.this.companyDto);
					grid.refreshRows(CompanyWindow.this.companyDto);
				}
				getUI().removeWindow(CompanyWindow.this);

			}
		});

		layout.addComponent(companyName);
		layout.addComponent(companyMailHost);
		layout.addComponent(companyMailPort);
		layout.addComponent(companyMailUserName);
		layout.addComponent(companyMailPassword);
		layout.addComponent(processButton);

		layout.setSizeFull();
		setModal(true);
		setContent(layout);

	}
}
