package by.training.restaurant.web.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.restaurant.util.SqlUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class ContextListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		context.setAttribute("sortTypes", SqlUtils.sortingTypes);

		String path = context.getRealPath("/WEB-INF/log4j2.log");
		System.setProperty("logFile", path);

		final Logger log = LogManager.getLogger(ContextListener.class);
		log.debug("logPath = " + System.getProperty("logFile"));
	}
}
