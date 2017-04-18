package br.com.alexandre.customer.infra;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceSessionListener implements HttpSessionListener {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public void sessionCreated(HttpSessionEvent event) {
		logger.info("Session Created: " + event.getSession().getId());
		
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		logger.info("Session Destroyed: " + event.getSession().getId());		
	}
}