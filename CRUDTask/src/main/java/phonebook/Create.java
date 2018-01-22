package phonebook;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.search.DateUtil;

@WebServlet(
    name = "CreateEntry",
    urlPatterns = {"/createserv"}
)
public class Create extends HttpServlet {
	
	DatastoreService datastore;
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
      
	  Entity entity = new Entity("Phonebook",request.getParameter("contactnumber"));
	  entity.setProperty("Name",request.getParameter("contactname"));
	  entity.setProperty("Number",request.getParameter("contactnumber"));
	  entity.setProperty("Group",request.getParameter("contactgroup"));
	  entity.setProperty("TimeAdded", new Date());

	  try{
		  datastore.put(entity);
		  response.getWriter().print("<div><p>Write success</p></div><br><a href = \"/create.jsp\">Add more contacts</a><br><a href = \"/read.jsp\">Retrieve contacts</a>");
		  response.getWriter().println("<br><br><p>------------</p><a href=\"/index.html\">Go Home</a>");

	  }
	  
	  catch(DatastoreFailureException ds){
		  response.getWriter().print("<div><p>Write Fail</p></div><br><a href = \"/create.jsp\">Add again</a><br><a href = \"/read.jsp\">Retrieve contact</a>");
		  response.getWriter().println("<br><br><p>------------</p><a href=\"/index.html\">Go Home</a>");
	  }

  }
@Override
public void init() throws ServletException {
	
	datastore = DatastoreServiceFactory.getDatastoreService();
}
  
  
}