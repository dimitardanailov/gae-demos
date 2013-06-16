package org.varnalab.gaedemos;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class DatastoreQueryServlet extends HttpServlet {

	private static final long serialVersionUID = 6595396452015754674L;

	private DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
	private String kind = "Line";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		   throws ServletException, IOException
	{
		this.getAllLines(response);
		this.getLinesByCityName(response, "Varna");
		this.getLinesByCityName(response, "Sofia");
		
		this.getLinesBySortedSeats(response, "DESC", 5);
		this.getLinesBySortedSeats(response, "ASC", 3);
	}
	
	private void getAllLines(HttpServletResponse response) throws IOException {
		Query query = new Query(this.kind);
		PreparedQuery preparedQuery = this.datastoreService.prepare(query);
		List<Entity> lines = preparedQuery.asList(FetchOptions.Builder.withDefaults());
		
		response.getWriter().println("All lines");
		this.printEntityRecords(response, lines);
		response.getWriter().println("");
	}
	
	private void getLinesByCityName(HttpServletResponse response, String cityFilter) throws IOException {
		Filter filterByCity = new FilterPredicate("city", FilterOperator.EQUAL, cityFilter); 
		Query query = new Query(this.kind);
		query.setFilter(filterByCity);
		PreparedQuery preparedQuery = this.datastoreService.prepare(query);
		List<Entity> lines = preparedQuery.asList(FetchOptions.Builder.withDefaults());
		
		response.getWriter().println("All lines travel in " + cityFilter);
		this.printEntityRecords(response, lines);
		response.getWriter().println("");
	}
	
	private void getLinesBySortedSeats(HttpServletResponse response, String sortDirection, int limit) throws IOException {
		Query query = new Query(this.kind);
		if (sortDirection.equals("ASC")) {
			query.addSort("seats", Query.SortDirection.ASCENDING);
		} else {
			query.addSort("seats", Query.SortDirection.DESCENDING);
		}
		PreparedQuery preparedQuery = this.datastoreService.prepare(query);
		List<Entity> iterator = preparedQuery.asList(FetchOptions.Builder.withLimit(limit));
		
		response.getWriter().println("Lines sorted by seats");
		this.printEntityRecords(response, iterator);
		response.getWriter().println("");
	}
	
	private void printEntityRecords(HttpServletResponse response, List<Entity> lines) throws IOException {
		if (!lines.isEmpty()) {
			for (Entity line : lines) {
				// The value returned may not be the same type as originally 
				String name = (String) line.getProperty("name");
				String city = (String) line.getProperty("city");
				Float price = new Float(line.getProperty("price").toString());
				Integer seats = new Integer(line.getProperty("seats").toString());
				response.getWriter().print("Name : " + name + ", ");
				response.getWriter().print("City : " + city + ", ");
				response.getWriter().print("Price : " + price + ", ");
				response.getWriter().print("Seats : " + seats + ".");
				response.getWriter().println("");
			}
		} else {
			response.getWriter().println("No results found");
		}
	}
}
