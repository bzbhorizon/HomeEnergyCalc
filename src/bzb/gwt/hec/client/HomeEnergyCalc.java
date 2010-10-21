package bzb.gwt.hec.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HomeEnergyCalc implements EntryPoint {
	
	private HashMap<String,Appliance> appliances = new HashMap<String,Appliance>();
	private final String[] categories = new String[]{
		"Entertainment","Cooking","Cleaning","Lighting","Heating","Personal care"
	};
	
	private ResultsPanel rp;
	
	public HomeEnergyCalc () { //http://www.carbonfootprint.com/energyconsumption.html
		appliances.put("Microwave", new Appliance("Microwave", 945, Appliance.USE_SINGLE, 1));
		appliances.put("Washing machine", new Appliance("Washing machine", 630, Appliance.USE_SINGLE, 2));
		appliances.put("Tumble dryer", new Appliance("Tumble dryer", 2500, Appliance.USE_SINGLE, 2));
		appliances.put("Kettle", new Appliance("Kettle", 110, Appliance.USE_SINGLE, 1));
		appliances.put("Gas oven", new Appliance("Gas oven", 1520, Appliance.USE_SINGLE, 1));
		appliances.put("Gas hob", new Appliance("Gas hob", 900, Appliance.USE_SINGLE, 1));
		appliances.put("Electric oven", new Appliance("Electric oven", 1560, Appliance.USE_SINGLE, 1));
		appliances.put("Electric hob", new Appliance("Electric hob", 710, Appliance.USE_SINGLE, 1));
		appliances.put("Dishwasher (warm)", new Appliance("Dishwasher (warm)", 1070, Appliance.USE_SINGLE, 2));
		appliances.put("Dishwasher (hot)", new Appliance("Dishwasher (hot)", 1440, Appliance.USE_SINGLE, 2));
		appliances.put("Fridge+freezer (A++)", new Appliance("Fridge+freezer (A++)", 564, Appliance.USE_CONSTANT, 1));
		appliances.put("Fridge+freezer (A)", new Appliance("Fridge+freezer (A)", 1118, Appliance.USE_CONSTANT, 1));
		appliances.put("CRT TV", new Appliance("CRT TV", 199, Appliance.USE_TIMED, 0));
		appliances.put("LCD TV", new Appliance("LCD TV", 211, Appliance.USE_TIMED, 0));
		appliances.put("Plasma TV", new Appliance("Plasma TV", 264, Appliance.USE_TIMED, 0));
		appliances.put("Rear projection TV", new Appliance("Rear projection TV", 192, Appliance.USE_TIMED, 0));
	}

	public void onModuleLoad() {
		AppliancePanels ap = new AppliancePanels(this);
		
		rp = new ResultsPanel();
		
		VerticalPanel vp = new VerticalPanel();
		vp.setStyleName("app");
		vp.add(ap);
		vp.add(rp);
		RootPanel.get("app").add(vp);
	}
	
	public ArrayList<Appliance> getAppliancesInCategory (int category) {
		ArrayList<Appliance> matchingAppliances = new ArrayList<Appliance>();
		for (Appliance app : appliances.values()) {
			if (app.getCategory() == category) {
				matchingAppliances.add(app);
			}
		}
		return matchingAppliances;
	}
	
	public String[] getCategories () {
		return categories;
	}
	
	public Appliance getAppliance (String appName) {
		return appliances.get(appName);
	}
	
	public void updateResults () {
		rp.updateResults(this);
	}
	
	public HashMap<String, Appliance> getAppliances () {
		return appliances;
	}
}
