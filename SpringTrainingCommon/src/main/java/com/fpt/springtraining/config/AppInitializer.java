package com.fpt.springtraining.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.fpt.springtraining.config.AppConfig;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletRegistration.Dynamic;

/**
 * Configure spring application. Replace the spring configuration xml file
 * 
 * @author ThaiTC
 *
 */
public class AppInitializer implements WebApplicationInitializer {

	private static final String CONFIG_LOCATION = "com.fpt.springtraining.config";
    private static final String MAPPING_URL = "/*";
    
    /**
     * Mapping url during starup
     * 
     */
	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {

		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(AppConfig.class);

		ctx.setServletContext(servletContext);

		Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
		servlet.addMapping(MAPPING_URL);
		servlet.setLoadOnStartup(1);
	}
	
	/**
	 * Map configuration location and create web context
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION);
        return context;
    }
}
