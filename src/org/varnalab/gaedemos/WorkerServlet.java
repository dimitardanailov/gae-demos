package org.varnalab.gaedemos;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class WorkerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 3551411231361997025L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		   throws ServletException, IOException
	{
		String lecture = request.getParameter("lecture");
		System.out.println("Task with param lecture : " + lecture + " processed");
	}	
}
