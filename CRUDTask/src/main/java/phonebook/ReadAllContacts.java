package phonebook;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultList;

/**
 * Servlet implementation class ReadAllContacts
 */
@WebServlet("/readallserv")
public class ReadAllContacts extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DatastoreService datastore;
	
	public void init(ServletConfig config) throws ServletException {
		datastore = DatastoreServiceFactory.getDatastoreService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(5);

	    // If this servlet is passed a cursor parameter, let's use it.
	    String startCursor = null;
	    if(request.getParameter("cursor") != null){
	    	startCursor = request.getParameter("cursor");
	    }
	    
	    if (startCursor != null) {
	      fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor));
	    }

	    Query q = new Query("Phonebook").addSort("Name", SortDirection.ASCENDING);
	    PreparedQuery pq = datastore.prepare(q);

	    QueryResultList<Entity> results;
	    try {
	      results = pq.asQueryResultList(fetchOptions);
	    } catch (IllegalArgumentException e) {

	      response.sendRedirect("/index.html");
	      return;
	    }
	    
    	response.setContentType("text/html");
    	PrintWriter out = response.getWriter();
    	out.write("<ul>");
	    for(Entity res : results){
	    	out.write("<li>");
	    	out.write(res.getProperty("Name")+" -> "+res.getProperty("Number"));
	    	out.write("</li>");
	    }
	    out.append("</ul>");
	    String cursorString = results.getCursor().toWebSafeString();
	    out.write("<a href='/readall.jsp?cursor=" + cursorString + "'>Next page</a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
