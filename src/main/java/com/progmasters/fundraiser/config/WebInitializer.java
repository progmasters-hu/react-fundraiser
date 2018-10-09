package com.progmasters.fundraiser.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;

public class WebInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	private int maxUploadSizeInMB = 5 * 1024 * 1024;

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { SpringWebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[]{new CharacterEncodingFilter("UTF-8")};
	}

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));
		MultipartConfigElement mce =
				new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
						maxUploadSizeInMB,
						maxUploadSizeInMB * 2,
						maxUploadSizeInMB / 2);
		registration.setMultipartConfig(mce);
	}


}