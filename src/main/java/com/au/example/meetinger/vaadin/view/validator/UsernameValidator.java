package com.au.example.meetinger.vaadin.view.validator;

import com.vaadin.data.validator.EmailValidator;

import de.steinwedel.messagebox.MessageBox;

public class UsernameValidator extends EmailValidator {

    /**
	 * 
	 */
	private static final long serialVersionUID = -800233913466424634L;

	public UsernameValidator() {
        super("The username provided is not valid");
    }

    @Override
    protected boolean isValidValue(String value) {

        if (!super.isValidValue(value)) {
            MessageBox.createError().asModal(true).withCaption("username password")
                    .withMessage("Username must be an email address")
                    .open();
            return false;
        }
        return true;
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }
}