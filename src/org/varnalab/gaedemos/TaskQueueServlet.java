package org.varnalab.gaedemos;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

public class TaskQueueServlet extends HttpServlet {
	
	private static final long serialVersionUID = -6833655057974469233L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		   throws ServletException, IOException
	{
		//Maxim 5 tasks 
		Queue defaultQueue = QueueFactory.getDefaultQueue();
		defaultQueue.add(TaskOptions.Builder.withUrl("/worker").param("lecture", "Android").method(Method.GET));
		defaultQueue.add(TaskOptions.Builder.withUrl("/worker").param("lecture", "GAE").method(Method.GET));
		defaultQueue.add(TaskOptions.Builder.withUrl("/worker").param("lecture", "HTML 5").method(Method.GET));
		defaultQueue.add(TaskOptions.Builder.withUrl("/worker").param("lecture", "jQuery").method(Method.GET));
		defaultQueue.add(TaskOptions.Builder.withUrl("/worker").param("lecture", "iOS").method(Method.GET));
		
		response.getWriter().println("Tasks pushed to default queue");
	}	
}
