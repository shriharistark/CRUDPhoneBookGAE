package phonebook;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * Servlet implementation class Update
 */
@WebServlet("/updateserv")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DatastoreService datastore;
	public void init(ServletConfig config) throws ServletException {
		datastore = DatastoreServiceFactory.getDatastoreService();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Key key = KeyFactory.createKey("Phonebook", request.getParameter("number"));
		
		if(key == null){
			request.setAttribute("status", "key not found");
		}
		try {
			Entity e = datastore.get(key);
			request.setAttribute("name",e.getProperty("Name"));
			request.setAttribute("number", e.getProperty("Number"));
			request.setAttribute("group", e.getProperty("Group"));
			System.out.println(e.toString());
			request.setAttribute("status", "true");

		} catch (EntityNotFoundException e) {
			request.setAttribute("status", "entity not found");
			System.out.println("seems like entity is not found .. ");
		}
		
		request.getRequestDispatcher("/update.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
