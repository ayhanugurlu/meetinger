package com.au.example.meetinger.vaadin.view.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.au.example.meetinger.resource.dto.CompanyDto;
import com.au.example.meetinger.resource.dto.ItemDto;
import com.au.example.meetinger.resource.dto.MeetingDto;
import com.au.example.meetinger.service.MailService;
import com.au.example.meetinger.service.MeetingService;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
@Scope("prototype")
public class MeetingView extends CustomComponent {

	public final static String NAME = "MeetingView";

	/**
	 * 
	 */
	private static final long serialVersionUID = 6791294028187696213L;

	@Autowired
	MeetingService meetingService;
	
	@Autowired
	MailService mailService;

	private Grid grid;
	private MeetingDto meetingDto;
	private ComboBox companys;

	private VerticalLayout mainLayout;

	public MeetingView() {
		setSizeFull();
		mainLayout = new VerticalLayout();
		setCompositionRoot(mainLayout);

	}

	public void buildView() {

		mainLayout.removeAllComponents();
		companys = new ComboBox();
		List<CompanyDto> companyDtos = new ArrayList<>();
		CompanyDto companyDto = new CompanyDto();
		companyDto.setCompanyName("All Company");
		companyDtos.add(companyDto);
		companyDtos.addAll(meetingService.getAllCompany());
		companys.setContainerDataSource(new BeanItemContainer<>(CompanyDto.class,companyDtos));
		grid = new Grid();
		grid.setSizeFull();
		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.setContainerDataSource(new BeanItemContainer<>(MeetingDto.class, meetingService.getAllMeeting()));
		
		companys.addValueChangeListener(new ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -7561038666712476866L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				CompanyDto selectedCompany = (CompanyDto) event.getProperty().getValue();
				if(selectedCompany != null && selectedCompany.getId() != null){
					grid.setContainerDataSource(new BeanItemContainer<>(MeetingDto.class, meetingService.findMeetingByCompany(selectedCompany.getId())));
				}else{
					grid.setContainerDataSource(new BeanItemContainer<>(MeetingDto.class, meetingService.getAllMeeting()));	
				}
				
				
			}
		});
		
		

		Column idColumn = grid.getColumn("items");
		idColumn.setHidden(true);

		Column usersColumn = grid.getColumn("users");
		usersColumn.setHidden(true);

		Column organizatorColumn = grid.getColumn("organizator");
		organizatorColumn.setHidden(true);

		Column meetingIdColumn = grid.getColumn("meetingId");
		meetingIdColumn.setHidden(true);

		Column endMeetingColumn = grid.getColumn("endMeeting");
		endMeetingColumn.setHidden(true);

		grid.addSelectionListener(new SelectionListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5180181470054913297L;

			@Override
			public void select(SelectionEvent event) {
				Set<Object> selecteds = event.getSelected();
				meetingDto = (MeetingDto) selecteds.iterator().next();
				if (meetingDto.getEndMeeting() != null && meetingDto.getEndMeeting().booleanValue()) {
					return;
				}
				grid = new Grid();
				grid.setSizeFull();
				grid.setSelectionMode(SelectionMode.SINGLE);
				grid.setContainerDataSource(new BeanItemContainer<>(ItemDto.class,
						meetingService.findMeetingItems(meetingDto.getMeetingId())));
				Column idColumn = grid.getColumn("id");
				idColumn.setHidden(true);
				Column meeetingIdColumn = grid.getColumn("meeetingId");
				meeetingIdColumn.setHidden(true);

				mainLayout.removeAllComponents();
				Button addButton = new Button("Add Item");
				Button updateButton = new Button("Update Item");
				updateButton.setEnabled(false);
				Button deleteButton = new Button("Delete Item");
				Button meetingDetialButton = new Button("Meeting detail");
				deleteButton.setEnabled(false);
				Button backButton = new Button("Return");
				Button endMeetingButton = new Button("End Meeting");
				HorizontalLayout horizontalLayout = null;
				if (meetingDto.getEndMeeting() != null && meetingDto.getEndMeeting().booleanValue()) {
					horizontalLayout = new HorizontalLayout(meetingDetialButton, backButton);
				} else {
					horizontalLayout = new HorizontalLayout(addButton, updateButton, deleteButton, meetingDetialButton,
							backButton);
				}

				grid.addSelectionListener(new SelectionListener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 7797324149127808999L;

					@Override
					public void select(SelectionEvent event) {
						deleteButton.setEnabled(true);
						updateButton.setEnabled(true);

					}

				});

				addButton.addClickListener(new ClickListener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = -7118090380065710818L;

					@Override
					public void buttonClick(ClickEvent event) {

						MeetingWindow meetingWindow = new MeetingWindow(meetingService, null, meetingDto, grid);
						getUI().addWindow(meetingWindow);

					}
				});

				updateButton.addClickListener(new ClickListener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 7012577965675861277L;

					@Override
					public void buttonClick(ClickEvent event) {
						ItemDto itemDto = (ItemDto) grid.getSelectedRow();
						MeetingWindow meetingWindow = new MeetingWindow(meetingService, itemDto, meetingDto, grid);
						getUI().addWindow(meetingWindow);
						grid.deselectAll();
						grid.setContainerDataSource(new BeanItemContainer<>(ItemDto.class,
								meetingService.findMeetingItems(meetingDto.getMeetingId())));

					}
				});

				deleteButton.addClickListener(new ClickListener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = -641217498196128279L;

					@Override
					public void buttonClick(ClickEvent event) {
						ItemDto itemDto = (ItemDto) grid.getSelectedRow();
						meetingService.deleteItem(itemDto);
						grid.deselectAll();
						grid.setContainerDataSource(new BeanItemContainer<>(ItemDto.class,
								meetingService.findMeetingItems(meetingDto.getMeetingId())));

					}
				});

				meetingDetialButton.addClickListener(new ClickListener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = -641217498196128279L;

					@Override
					public void buttonClick(ClickEvent event) {
						MeetingDetialWindow meetingDetialWindow = new MeetingDetialWindow(meetingService, meetingDto);
						getUI().addWindow(meetingDetialWindow);
					}
				});

				backButton.addClickListener(new ClickListener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = -641217498196128279L;

					@Override
					public void buttonClick(ClickEvent event) {
						MeetingView.this.buildView();

					}
				});

				endMeetingButton.addClickListener(new ClickListener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = -641217498196128279L;

					@Override
					public void buttonClick(ClickEvent event) {
						meetingDto.setEndMeeting(true);
						meetingService.updateMeeting(meetingDto);
						MeetingView.this.buildView();

					}
				});
				mainLayout.addComponent(horizontalLayout);
				mainLayout.addComponent(grid);
				mainLayout.addComponent(endMeetingButton);
				mainLayout.setComponentAlignment(endMeetingButton, Alignment.MIDDLE_CENTER);

			}
		});

		mainLayout.addComponent(companys);
		mainLayout.addComponent(new Label());
		mainLayout.addComponent(grid);
	}

}
