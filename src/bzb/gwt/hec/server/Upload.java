package bzb.gwt.hec.server;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Upload extends HttpServlet {

	private static final long serialVersionUID = -2511229006029489239L;
	
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
    	if (req.getParameter("response") != null) {
    		Response r = new Response(req.getParameter("units"), req.getParameter("response"), Long.parseLong(req.getParameter("time")));
	    	PersistenceManager pm = PMF.get().getPersistenceManager();
	        try {
	        	pm.makePersistent(r); 
	        } finally {
	        	pm.close();
	        }
    	}
    }
}
