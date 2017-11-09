package com.au.example.meetinger.vaadin.view.user;

import com.au.example.meetinger.resource.dto.ItemDto;
import com.au.example.meetinger.resource.dto.ItemTypeDto;
import com.au.example.meetinger.resource.dto.MeetingDto;
import com.au.example.meetinger.service.MeetingService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class MeetingWindow extends Window {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1972274941356030577L;
	private ComboBox itemType = new ComboBox("Item Type");
	private TextArea itemDescription = new TextArea("Item Description");
	private Button processButton = new Button();

	

	public MeetingWindow(MeetingService meetingService, ItemDto itemDto, MeetingDto meetingDto,Grid grid) {
		this.setWidth("400px");
		this.setHeight("400px");
		VerticalLayout layout = new VerticalLayout();

		layout.setMargin(true);
		layout.setSpacing(true);
		itemType.addItems(meetingService.getAllItemType());
		
		if(itemDto != null){
			while(itemType.getItemIds().iterator().hasNext()){
				ItemTypeDto itemTypeDto = (ItemTypeDto) itemType.getItemIds().iterator().next();
				if(itemTypeDto.getId().longValue() == itemDto.getItemType().getId().longValue()){
					itemType.select(itemTypeDto);
					break;
				}
					
				
			}
			itemType.setValue(itemDto.getItemType());
			itemDescription.setValue(itemDto.getValue());

			processButton.setCaption("Update Item");
		}else{
			processButton.setCaption("Add Item");
		}
		
		itemDescription.setSizeFull();
		layout.addComponent(itemType);
		layout.addComponent(itemDescription);
		layout.addComponent(processButton);
		layout.setComponentAlignment(processButton, Alignment.BOTTOM_CENTER);

		processButton.addClickListener(new ClickListener() {


			/**
			 * 
			 */
			private static final long serialVersionUID = 1604737800476091016L;

			@Override
			public void buttonClick(ClickEvent evet) {

				if (itemDto == null) {
					ItemDto dto = new ItemDto();
					ItemTypeDto itemTypeDto = (ItemTypeDto) itemType.getValue();
					dto.setItemType(itemTypeDto);
					dto.setValue(itemDescription.getValue());					
					dto.setMeeetingId(meetingDto.getMeetingId());
					meetingService.createItem(dto);
					meetingService.updateMeeting(meetingDto);
					grid.setContainerDataSource(new BeanItemContainer<>(ItemDto.class,
							meetingService.findMeetingItems(meetingDto.getMeetingId())));
				} else {

					ItemTypeDto itemTypeDto = (ItemTypeDto) itemType.getValue();
					itemDto.setItemType(itemTypeDto);
					itemDto.setValue(itemDescription.getValue());
					meetingService.updateItem(itemDto);
					grid.deselectAll();
					grid.setContainerDataSource(new BeanItemContainer<>(ItemDto.class,
							meetingService.findMeetingItems(meetingDto.getMeetingId())));
				}

				getUI().removeWindow(MeetingWindow.this);

			}
		});
		layout.setSizeFull();
		setModal(true);
		setContent(layout);
	}
}
