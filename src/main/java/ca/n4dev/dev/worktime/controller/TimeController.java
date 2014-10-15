/**
 * Copyright © 2014 Remi Guillemette <rguillemette@n4dev.ca>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package ca.n4dev.dev.worktime.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author rguillemette
 * @since Oct 14, 2014
 */
@Controller
public class TimeController {

	
	@RequestMapping(value = "/time", method = RequestMethod.GET)
	public String test() {
		return getPage();
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	public String getPage() {
		return "time";
	}
}
