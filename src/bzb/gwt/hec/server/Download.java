package bzb.gwt.hec.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Download extends HttpServlet {

	private static final long serialVersionUID = -4992432806210191962L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
    	throws ServletException, IOException {
		res.setContentType("text/csv");
		res.setHeader("Content-disposition", "attachment;filename=responses.csv");
		PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
        	PrintWriter p = res.getWriter();
        	
        	String query = "select from " + Response.class.getName();
            List<Response> responses = (List<Response>) pm.newQuery(query).execute();
            if (!responses.isEmpty()) {
            	Iterator<Response> i = responses.iterator();
            	while (i.hasNext()) {
            		Response r = i.next();
            		p.write(r.getKey() + ',' + r.getIp() + ',' + r.getTimestamp() + ',' + r.getDuration() + ',' + r.getTarget() + ',' + r.getUnits() + ',' + r.getResponse() + "," + r.getFeedback() + "\n");
            	}
            }
        	
        	p.flush();
        	p.close();
        } finally {
        	pm.close();
        }
	}
	
}
