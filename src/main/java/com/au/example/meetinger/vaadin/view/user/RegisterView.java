package com.au.example.meetinger.vaadin.view.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.au.example.meetinger.resource.dto.CompanyDto;
import com.au.example.meetinger.resource.dto.UserDto;
import com.au.example.meetinger.service.MeetingService;
import com.au.example.meetinger.vaadin.view.validator.PasswordValidator;
import com.au.example.meetinger.vaadin.view.validator.UsernameValidator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SpringView(name = RegisterView.NAME)
public class RegisterView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8085069509250002225L;

	public static final String NAME = "RegisterView";

	private TextField email;

	private TextField name;

	private TextField lastName;

	private PasswordField password;

	private ComboBox companys;

	private Button createUser;

	@Autowired
	MeetingService meetingService;

	public RegisterView() {
		bulidView();
	}

	public void bulidView() {

		setSizeFull();

		// Create the email input field
		email = new TextField("Email:");
		email.setWidth("300px");
		email.setRequired(true);
		email.setInputPrompt("Your username (eg. joe@email.com)");
		email.addValidator(new UsernameValidator());
		email.setInvalidAllowed(false);

		// Create the password input field
		password = new PasswordField("Password:");
		password.setWidth("300px");
		password.addValidator(new PasswordValidator());
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");

		// Create the username input field
		name = new TextField("Name:");
		name.setWidth("300px");
		name.setRequired(true);
		name.setInputPrompt("Your name (eg. ayhan)");
		name.setInvalidAllowed(false);

		// Create the surname input field
		lastName = new TextField("Surname:");
		lastName.setWidth("300px");
		lastName.setRequired(true);
		lastName.setInputPrompt("Your surname (eg. uğurlu)");
		lastName.setInvalidAllowed(false);
		companys = new ComboBox();

		// Create user button
		createUser = new Button("Create User");
		createUser.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2825326700544383317L;

			@Override
			public void buttonClick(ClickEvent event) {

				//
				// Validate the fields using the navigator. By using validors
				// for the
				// fields we reduce the amount of queries we have to use to the
				// database
				// for wrongly entered passwords
				//
				if (!email.isValid() || !password.isValid() || !name.isValid() || !lastName.isValid()) {
					return;
				}

				UserDto userDto = new UserDto();
				userDto.setName(RegisterView.this.name.getValue());
				userDto.setSurname(RegisterView.this.lastName.getValue());
				userDto.setPassword(RegisterView.this.password.getValue());
				userDto.setAdmin(false);
				userDto.setUserName(RegisterView.this.email.getValue());
				CompanyDto companyDto = (CompanyDto) companys.getValue();
				userDto.setCompanyId(companyDto != null ? companyDto.getId() : null);
				meetingService.createUser(userDto);

				// Navigate to main view
				getUI().getNavigator().navigateTo(LoginView.NAME);

			}
		});

		// Add both to a panel
		VerticalLayout fields = new VerticalLayout(email, password, name, lastName, companys, createUser);
		fields.setCaption("Create an account");
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();

		// The view root layout
		this.addComponent(fields);
		this.setSizeFull();
		this.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
		this.setStyleName(Reindeer.LAYOUT_BLUE);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		companys.setContainerDataSource(new BeanItemContainer<>(CompanyDto.class, meetingService.getAllCompany()));
		companys.setRequired(true);
	
	}

}
