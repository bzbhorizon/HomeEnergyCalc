package bzb.gwt.hec.server;

import java.io.IOException;
import java.io.PrintWriter;

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
    		Response r = new Response(req.getParameter("units"), req.getParameter("response"), Long.parseLong(req.getParameter("time")), Double.parseDouble(req.getParameter("target")), req.getRemoteAddr(), req.getParameter("feedback"));
	    	PersistenceManager pm = PMF.get().getPersistenceManager();
	        PrintWriter w = null;
			try {
	        	pm.makePersistent(r);
	        	w = res.getWriter();
	        	w.write(r.getKey());
	        	w.flush();
	        } finally {
	        	if (w != null) {
	        		w.close();
	        	}
	        	pm.close();
	        }
    	}
    }
}
