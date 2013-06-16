package org.varnalab.gaedemos;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class DatastoreEntitiyServlet extends HttpServlet {
	
	private static final long serialVersionUID = -8724968590048313021L;

	private DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
	private String kind = "Line";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		   throws ServletException, IOException
	{
		createEntityLine("Line 20", "Varna", 1, 40);
		createEntityLine("Line 14", "Varna", 1, 40);
		createEntityLine("Line 46", "Varna", (float)1.4, 25);
		createEntityLine("Line 9", "Varna", (float)2.4, 55);
		createEntityLine("Line 2", "Varna", 1, 35);
		
		createEntityLine("Line 2", "Sofia", (float)0.8, 45);
		createEntityLine("Line 72", "Sofia", (float)1.3, 25);
		createEntityLine("Line 64", "Sofia", (float)1.2, 65);
		createEntityLine("Line 309", "Sofia", 1, 85);
		createEntityLine("Line 20", "Sofia", 1, 42);
		
		response.getWriter().println("Ten " + kind +  " entities added to datastore");
	}
	
	private void createEntityLine(String name, String city, float price, int seats) {
		Entity line = new Entity(kind);
		line.setProperty("name", name);
		line.setProperty("city", city);
		line.setProperty("price", price);
		line.setProperty("seats", seats);
		this.datastoreService.put(line);
	}
}
