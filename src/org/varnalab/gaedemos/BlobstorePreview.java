package org.varnalab.gaedemos;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class BlobstorePreview extends HttpServlet {

	private static final long serialVersionUID = -2260979158973492288L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		   throws ServletException, IOException
	{
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		String blobKeyString = request.getParameter("key");
		
		if (blobKeyString != null) {
			BlobKey blobKey = new BlobKey(blobKeyString);
			blobstoreService.serve(blobKey , response);
		}
	}	
}
