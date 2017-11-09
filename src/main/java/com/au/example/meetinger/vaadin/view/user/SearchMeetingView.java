package com.au.example.meetinger.vaadin.view.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.au.example.meetinger.resource.dto.MeetingDto;
import com.au.example.meetinger.service.MeetingService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("prototype")
public class SearchMeetingView extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8671486115366577699L;

	public static final String NAME = "SearchMeetingView";

	@Autowired
	private MeetingService meetingService;

	private VerticalLayout layout;

	private TextField userName = new TextField("User Name");
	private TextField surName = new TextField("Surname");
	private TextField meetingName = new TextField("Meeting Name");
	private TextField meetingSubject = new TextField("Meeting Subject");
	private TextField meetingData = new TextField("Meeting Value");
	private Button processButton = new Button("Search");

	private Grid grid = new Grid("Search Result");

	public SearchMeetingView() {

		buildView();
		processButton.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5071577182430278454L;

			@Override
			public void buttonClick(ClickEvent event) {
				List<MeetingDto> dtos = SearchMeetingView.this.meetingService.searchMeeting(userName.getValue(),
						surName.getValue(), meetingName.getValue(), meetingSubject.getValue(), meetingData.getValue());
				if (dtos != null) {
					grid.setVisible(true);
					grid.removeAllColumns();
					grid.setContainerDataSource(new BeanItemContainer<>(MeetingDto.class, dtos));
				}

			}
		});

		setCompositionRoot(layout);

	}

	private void buildView() {
		setSizeFull();
		this.layout = new VerticalLayout();
		layout.addComponent(userName);
		layout.addComponent(surName);
		layout.addComponent(meetingName);
		layout.addComponent(meetingSubject);
		layout.addComponent(meetingData);
		layout.addComponent(processButton);
		grid.setVisible(false);
		layout.addComponent(grid);

	}

}
