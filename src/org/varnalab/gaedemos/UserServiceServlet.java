package org.varnalab.gaedemos;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


public class UserServiceServlet extends  HttpServlet{

	private static final long serialVersionUID = -9162904499709944679L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		   throws ServletException, IOException
	{
		UserService userService = UserServiceFactory.getUserService();
		String requestURI = request.getRequestURI();
		
		//response.getWriter().println("Request uri" + requestURI);
		
		if (requestURI != null) {
			String htmlHello = "<p>Hello</p>" + request.getUserPrincipal().getName() + "</p>";
			String logoutButton = "<a href=\"" + userService.createLogoutURL(requestURI) + "\">Sign out</a>";
			response.getWriter().println(htmlHello + logoutButton);
		} else {
			String loginButton = "<a href=\"" + userService.createLoginURL(requestURI) + "\"></a>";
			response.getWriter().println(loginButton);
		}
	}
}
