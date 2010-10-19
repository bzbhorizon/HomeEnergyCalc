package bzb.gwt.hec.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.TabPanel;

public class AppliancePanels extends TabPanel {
	
	public AppliancePanels () {
		HorizontalPanel entertainment = new HorizontalSplitPanel();
		entertainment.add(new HTML("Hifi"));
	    add(entertainment, "Entertainment");
	    
	    HorizontalSplitPanel heating = new HorizontalSplitPanel();
	    add(heating, "Heating");
	    
	    HorizontalSplitPanel lighting = new HorizontalSplitPanel();
	    add(lighting, "Lighting");
	    
	    HorizontalSplitPanel cooking = new HorizontalSplitPanel();
	    add(cooking, "Cooking");
	    
	    HorizontalSplitPanel travel = new HorizontalSplitPanel();
	    add(travel, "Travel");
	    
	    HorizontalSplitPanel personalCare = new HorizontalSplitPanel();
	    add(personalCare, "Personal Care");

	    selectTab(0);
	    
	    setWidth(Window.getClientWidth() * 0.6 + "px");
	    
	    setStyleName("appliancePanels");
	    
	}

}
