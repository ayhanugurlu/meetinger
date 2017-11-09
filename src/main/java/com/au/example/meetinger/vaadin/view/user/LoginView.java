package com.au.example.meetinger.vaadin.view.user;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.au.example.meetinger.service.MeetingService;
import com.au.example.meetinger.util.Constant;
import com.au.example.meetinger.vaadin.view.validator.PasswordValidator;
import com.au.example.meetinger.vaadin.view.validator.UsernameValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import de.steinwedel.messagebox.MessageBox;

@SpringView(name = LoginView.NAME)
public class LoginView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1766138798902176540L;

	public static final String NAME = "LoginView";

	private final TextField user;

	private final PasswordField password;

	private final Button loginButton;

	private final Button createUserButton;

	@Autowired
	MeetingService meetingService;

	public LoginView() {
		setSizeFull();

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("images/Meeting.jpg").getFile());

		// Image as a file resource
		FileResource resource = new FileResource(file);

		// Show the image in the application
		Image image = new Image("", resource);
		image.setWidth("300px");
		image.setHeight("300px");

		// Create the user input field
		user = new TextField("User:");
		user.setWidth("300px");
		user.setRequired(true);
		user.setInputPrompt("Your username (eg. ayhan@email.com)");
		user.addValidator(new UsernameValidator());
		user.setInvalidAllowed(false);

		// Create the password input field
		password = new PasswordField("Password:");
		password.setWidth("300px");
		password.addValidator(new PasswordValidator());
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");

		// Create login button
		loginButton = new Button("Login");
		loginButton.addClickListener(new Button.ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -7619594833167973184L;

			@Override
			public void buttonClick(ClickEvent clickEvent) {

				if (!LoginView.this.user.isValid() || !LoginView.this.password.isValid()) {
					return;
				}

				String username = LoginView.this.user.getValue();
				String password = LoginView.this.password.getValue();

				Long userId = meetingService.login(username, password);

				if (userId != null) {

					// Store the current user in the service session
					getSession().setAttribute(Constant.ACTIVE_USER, userId);

					// Navigate to main view
					getUI().getNavigator().navigateTo(MeetingerView.NAME);

				} else {

					// Wrong password clear the password field and refocuses it
					LoginView.this.password.setValue(null);
					LoginView.this.password.focus();
				    MessageBox.createError().asModal(true).withCaption("Invalid Login")
                    .withMessage("Your password or username is invalid...")
                    .open();

				}

			}

		});
		createUserButton = new Button("Create User", clickEvent -> {

			getUI().getNavigator().navigateTo(RegisterView.NAME);
		});

		HorizontalLayout buttonLayout = new HorizontalLayout(loginButton, createUserButton);

		// Add both to a panel
		VerticalLayout fields = new VerticalLayout(image, user, password, buttonLayout);
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();

		this.addComponent(fields);
		this.setSizeFull();
		this.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
		this.setStyleName(Reindeer.LAYOUT_BLUE);
		this.setResponsive(true);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// focus the username field when user arrives to the login view
		user.focus();
	}

}
