package phonebook;

import java.io.IOException;
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
 * Servlet implementation class Delete
 */
@WebServlet("/deleteserv")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DatastoreService datastore;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Key removekey = KeyFactory.createKey("Phonebook", request.getParameter("number"));

		try {
			if (removekey != null) {
				Entity removed = datastore.get(removekey);
				datastore.delete(removekey);
				request.setAttribute("status", true);
				request.setAttribute("removed", removed.getProperty("Name"));
				System.out.println(removed.toString());
				removed = null;
			}

			else {
				request.setAttribute("status", false);
				request.setAttribute("removed", "cannot be read");
				response.getWriter().println("<br>key not found ");
			}
		} catch (EntityNotFoundException ef) {
			request.setAttribute("status", false);
			request.setAttribute("removed", "Entity not found.. invalid number");
		}
		
		request.getRequestDispatcher("/delete.jsp").forward(request, response);
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

	@Override
	public void init() throws ServletException {
		datastore = DatastoreServiceFactory.getDatastoreService();
	}

	// namespace

}
