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
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
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
		String param = request.getParameter("params");
		//System.out.println("what?"+param);
		PreparedQuery pq;
		List<Entity> result;
		if (criteria.equals("recent")) {
			if (param.toLowerCase().equals("oldfirst")) {
				System.out.println("oldfirst");
				q.addSort("Name", SortDirection.ASCENDING);
				/*
				 * the sort must be on the same property as the one with the inequality 
				 * operators
				 */
			}
			
			else {
				System.out.println("notoldfirst!");
				q.addSort("TimeAdded",SortDirection.DESCENDING);
			}
			//q.setFilter(f);
			//pq = datastore.prepare(q);
			//result = pq.asList(FetchOptions.Builder.withLimit(10));
			/*
			 * When using multiple filters with inequality operators
			 * you should sort the property that is making use of the inequality operator
			 * first
			 * 	Query q = new Query("Phonebook").addSort("Name",SortDirection.ASCENDING);
			 * 	Filter f1 = new FilterPredicate("Name",FilterOperator.GREATER_THAN,"Hari");
				Filter f2 = new FilterPredicate("Number",FilterOperator.EQUAL,"9962081266");	
			 */
			
			/*
			 * Inequlaity operators are restricted to one property per query
			 * 	
			 * The Below won't work. (Requires custom index table)
			 * Filter f1 = new FilterPredicate("Name",FilterOperator.GREATER_THAN,"Hari");
			   Filter f2 = new FilterPredicate("Number",FilterOperator.GREATER_THAN,"99");
			   
			   >> No restriction on equality operators
			 * */
			Filter f1 = new FilterPredicate("Name",FilterOperator.GREATER_THAN_OR_EQUAL,"Hari");
			Filter f2 = new FilterPredicate("Number",FilterOperator.EQUAL,"9962081266");
			
			//Equality filters are valid on different properties and sort orders are ignored
			//Filter f3 = new FilterPredicate("Number",FilterOperator.EQUAL,"9781006743");
			
			
			//test must throw IllegalArgument exception
			CompositeFilter namefilter = CompositeFilterOperator.and(f1,f2);
			q.setFilter(namefilter);
			pq = datastore.prepare(q);
			result = pq.asList(FetchOptions.Builder.withLimit(5));
		}

		else {
			q.setFilter(new FilterPredicate(criteria, FilterOperator.EQUAL, param));
			pq = datastore.prepare(q);
			result = pq.asList(FetchOptions.Builder.withLimit(10));
		}

		if (result != null) {
			System.out.println(result.toString());
			request.setAttribute("status", "true");
			request.setAttribute("result", result);

		}

		else {
			request.setAttribute("status", "false");
			response.getWriter().write("<br><p>Read unsuccess</p>");
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
