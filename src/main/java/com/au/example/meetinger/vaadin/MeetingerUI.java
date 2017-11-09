package com.au.example.meetinger.vaadin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.au.example.meetinger.util.Constant;
import com.au.example.meetinger.vaadin.view.general.AcceptView;
import com.au.example.meetinger.vaadin.view.user.LoginView;
import com.au.example.meetinger.vaadin.view.user.MeetingerView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

@SpringUI(path = "MeetingerUI")
@Theme("valo")
public class MeetingerUI extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6163253691324232558L;

	@Autowired
	SpringViewProvider viewProvider;

	Navigator navigator;

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Meetinger");
		Map<String, String> urlParamMap = new HashMap<>();
		String fragment = getPage().getLocation().getRawFragment();
		if (fragment != null && fragment.indexOf("?") != -1) {
			String[] uriParams = fragment.substring(fragment.indexOf("?") + 1).split("&");
			for (String param : uriParams) {
				urlParamMap.put(param.substring(0, param.indexOf("=")), param.substring(param.indexOf("=") + 1));
			}
		}
		// Create a navigator to control the views
		navigator = new Navigator(this, this);
		Navigator navigator = new Navigator(this, this);
		navigator.addProvider(viewProvider);
		
		
		// Navigate to start view
		if(urlParamMap.get(Constant.TOKEN) != null){
			getSession().setAttribute(Constant.TOKEN,urlParamMap.get(Constant.TOKEN));
			VaadinService.getCurrentRequest().getWrappedSession().setAttribute(Constant.TOKEN,urlParamMap.get(Constant.TOKEN));
			navigator.navigateTo(AcceptView.NAME);
			
		}else{
			if (getSession().getAttribute(Constant.ACTIVE_USER) != null) {
				navigator.navigateTo(MeetingerView.NAME);
			} else {
				navigator.navigateTo(LoginView.NAME);
			}	
		}
		

	}

}
