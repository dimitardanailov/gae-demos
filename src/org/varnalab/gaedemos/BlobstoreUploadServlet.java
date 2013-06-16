package org.varnalab.gaedemos;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class BlobstoreUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5768486676679813346L;

	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{	
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		Map<String, List<BlobKey>> uploadedFiles = blobstoreService.getUploads(request);
		
		if (uploadedFiles != null) {
			List<BlobKey> fileList = uploadedFiles.get("file");
			if ((fileList != null) && (fileList.size() > 0)) {
				BlobKey fileKey = fileList.get(0);
				if (fileKey != null) {
					response.sendRedirect("/blobstorepreview?key=" + fileKey.getKeyString());
				} else {
					response.getWriter().println("<p>No uploaded file found</p>");
				}
			}
		}
	}
}
