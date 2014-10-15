/**
 * Copyright © 2014 Remi Guillemette <rguillemette@n4dev.ca>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package ca.n4dev.dev.worktime.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
 
/**
 * @author rguillemette
 * @since Sep 30, 2014
 */
public class SpringAppInitializer implements WebApplicationInitializer {
	private static final String CONFIG_LOCATION = "ca.n4dev.dev.worktime.config";
	private static final String MAPPING_URL = "/";


	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		WebApplicationContext context = getContext();
		servletContext.addListener(new ContextLoaderListener(context));

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
				"DispatcherServlet", new DispatcherServlet(context));

		//servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain")).addMappingForUrlPatterns(null, false, "/*");

	
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping(MAPPING_URL);
	}

	private AnnotationConfigWebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation(CONFIG_LOCATION);
		return context;
	}
	
	
}
