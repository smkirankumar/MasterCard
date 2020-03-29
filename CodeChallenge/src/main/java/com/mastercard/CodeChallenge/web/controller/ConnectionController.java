package com.mastercard.CodeChallenge.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mastercard.CodeChallenge.web.service.RouteFinderService;
@Controller
public class ConnectionController {
	@Autowired
	RouteFinderService routefinder;
	
	@RequestMapping(value = "/connected", method = RequestMethod.GET)
	public String connect(@RequestParam(value = "origin") String origin,
						  		@RequestParam(value = "destination") String destination, 
						  		ModelMap model) {
		System.out.println("User Input - Origin =" + origin + ", Destination =" + destination);

		// pass the user input and check if there exist a route.
		String validRoute = routefinder.findRoute(origin, destination);
		System.out.println("Is it a validRoute =" + validRoute);

		// using ModelMap to pass the values to jsp page
		model.put("origin", origin);
		model.put("destination", destination);
		model.put("Message", validRoute);

		return "CityEndPoint";
	}
}