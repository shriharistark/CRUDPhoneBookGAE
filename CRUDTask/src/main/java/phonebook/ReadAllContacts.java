package phonebook;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Stack;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.spi.WebServiceFeatureAnnotation;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Query q = new Query("Phonebook").addSort("Name", SortDirection.ASCENDING);
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(5);

		// If this servlet is passed a cursor parameter, let's use it.
		String startCursor = "";
		String action = "";
		String nextCursor = "";
		String prevCursor = "";

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		PreparedQuery pq = datastore.prepare(q);
		QueryResultList<Entity> results;
		
		Stack<Cursor> cursorslist = null;
		if (request.getAttribute("cursorslist") == null) {
			cursorslist = new Stack<Cursor>();
			request.setAttribute("cursorslist", new Stack<Cursor>());
		}

		else {
			cursorslist = (Stack<Cursor>) request.getAttribute("cursorslist");
		}

		System.out.println("Entered here");
		startCursor = request.getParameter("cursor");
		String nav_next = "";
		String nav_prev = "";

		if (request.getParameter("action").equals("next")) {
			
			cursorslist.push(Cursor.fromWebSafeString(startCursor));
			fetchOptions.startCursor(cursorslist.peek());
			request.setAttribute("cursorslist", cursorslist);
		}

		else if (request.getParameter("action").equals("prev")) {
			cursorslist.pop();
			request.setAttribute("cursorslist", cursorslist);
			fetchOptions.startCursor(cursorslist.peek());
		}

		//fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor));
		//cursorslist.push(results.getCursor());

		/*
		 * System.out.println("Startcursor: "+startCursor);
		 * fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor));
		 */
		System.out.println("Entered here II");

		try {
			results = pq.asQueryResultList(fetchOptions);
		} catch (IllegalArgumentException e) {

			System.out.println("Oopsie");
			e.printStackTrace();
			System.out.println(request.getServletPath());
			response.sendRedirect("/index.html");
			return;
		}

		nextCursor = results.getCursor().toWebSafeString();

		System.out.println("Entered III");
		out.write("<ul>");
		for (Entity res : results) {
			out.write("<li>");
			out.write(res.getProperty("Name") + " -> " + res.getProperty("Number"));
			out.write("</li>");
		}
		out.write("</ul>");

		// Cursor cursorprev = Cursor.fromWebSafeString(startCursor);
		/*
		 * String cursorStringprev = startCursor;
		 * 
		 * System.out.println("NExt: " + cursorStringnext + " prev: " +
		 * cursorStringprev); System.out.println("-----");
		 */

		// String nav = "<a href='/readall.jsp?cursor=" + cursorStringnext +
		// "&prev=" + cursorStringprev + "&action="
		// + "'>Next page</a>";
		// String prevPage = "<a href='/readall.jsp?cursor=" + cursorStringprev
		// + "'>Prev page</a>";
		// dont show previous since it is the first entry

		String a_next = "<a href='/readall.jsp?cursor=" + nextCursor + "&action=next" + "'>Next page</a>";
		String a_prev = "<a href='/readall.jsp?cursor=" + startCursor + "&action=prev" + "'>Previous page</a>";
		// String cursorstart = (String)request.getAttribute("start");
		//
		//
		// fetchOptions.startCursor(Cursor.fromWebSafeString(cursorstart));
		// PreparedQuery pq = datastore.prepare(q);
		//
		// QueryResultList<Entity> res = pq.asQueryResultList(fetchOptions);
		//
		// PrintWriter ou = response.getWriter();
		// ou.write("<ul>");
		// for(Entity n : res){
		// ou.write("<li>");
		// ou.write(n.getProperty("Name") + " -> " + n.getProperty("Number"));
		// ou.write("</li>");
		// }
		// ou.write("</ul>");
		//
		// String nextcursor = Cursor.res.getCursor();
		System.out.println("Entered here IVs");

		out.write(a_next+"  "+a_prev);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
