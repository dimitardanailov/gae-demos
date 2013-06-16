package org.varnalab.gaedemos;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;

public class DatastoreTransactionServlet extends HttpServlet {
	
	private static final long serialVersionUID = -7821625494725542224L;

	private DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
	private String kind = "Line";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		   throws ServletException, IOException
	{
		simpleTransaction(response);
		XGTransaction(response);
	}
	
	private void simpleTransaction(HttpServletResponse response) throws IOException {
		Transaction transaction = this.datastoreService.beginTransaction();
		try {
			Entity line = new Entity(kind);
			line.setProperty("name", "Line 31");
			line.setProperty("city", "Varna");
			line.setProperty("price", 1);
			line.setProperty("seats", 46);
			
			this.datastoreService.put(transaction, line);
			
			transaction.commit();
			
			response.getWriter().println("Transacation Successfull");
			
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
	
	private void XGTransaction(HttpServletResponse response) throws IOException {
		TransactionOptions transactionOptions = TransactionOptions.Builder.withXG(true);
		Transaction transaction = this.datastoreService.beginTransaction(transactionOptions);
		try {
			Entity line = new Entity(kind);
			line.setProperty("name", "Line 31A");
			line.setProperty("city", "Varna");
			line.setProperty("price", (float)1.5);
			line.setProperty("seats", 26);
			
			this.datastoreService.put(transaction, line);
			
			transaction.commit();
			
			response.getWriter().println("XG Transacation Successfull");
			
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
}
