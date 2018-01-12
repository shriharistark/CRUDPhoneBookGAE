package phonebook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceConfig;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

/**
 * Servlet implementation class Read
 */
@WebServlet("/readserv")
public class Read extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Query q;
	DatastoreService datastore;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		q = new Query("Phonebook");
		String criteria = request.getParameter("criteria");
		String param = request.getParameter("params").toLowerCase();
		System.out.println(param);
		PreparedQuery pq;
		List<Entity> result;
		if (criteria.equals("recent")) {
			if (param.equals("oldfirst")) {
				System.out.println("oldfirst");
				q.addSort("TimeAdded", SortDirection.ASCENDING);
			}
			
			else {
				System.out.println("notoldfirst!");
				q.addSort("TimeAdded",SortDirection.DESCENDING);
			}
			Filter f = new FilterPredicate("TimeAdded", FilterOperator.NOT_EQUAL, "");
			q.setFilter(f);
			pq = datastore.prepare(q);
			result = pq.asList(FetchOptions.Builder.withLimit(10));
		}

		else {
			q.setFilter(new FilterPredicate(criteria, FilterOperator.EQUAL, param));
			pq = datastore.prepare(q);
			result = pq.asList(FetchOptions.Builder.withLimit(10));
		}

		if (result != null) {
			request.setAttribute("status", "true");
			request.setAttribute("result", result);

		}

		else {
			request.setAttribute("status", "false");
			response.getWriter().write("<p>Read unsuccess</p>");
		}

		request.getRequestDispatcher("/read.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	@Override
	public void init() throws ServletException {
		datastore = DatastoreServiceFactory.getDatastoreService();
	}

}
