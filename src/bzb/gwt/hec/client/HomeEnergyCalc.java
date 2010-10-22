package bzb.gwt.hec.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HomeEnergyCalc implements EntryPoint {
	
	private HashMap<String,Appliance> appliances = new HashMap<String,Appliance>();
	private final String[] categories = new String[]{
		"Kitchen","Laundry","Health/Personal Care","Lighting, Heating and Cooling","Home Entertainment","Office, PCs and Phones"
	};
	
	private ResultsPanel rp;
	
	public HomeEnergyCalc () { //http://www.carbonfootprint.com/energyconsumption.html
		// kitchen
		appliances.put("Microwave", new Appliance("Microwave", 945, Appliance.USE_SINGLE, 0, false, 0));
		appliances.put("Kettle", new Appliance("Kettle", 110, Appliance.USE_SINGLE, 0, false, 0));
		appliances.put("Oven (Gas)", new Appliance("Oven (Gas)", 1520, Appliance.USE_SINGLE, 0, false, 0));
		appliances.put("Hob (Gas)", new Appliance("Hob (Gas)", 900, Appliance.USE_SINGLE, 0, false, 0));
		appliances.put("Oven (Electric)", new Appliance("Oven (Electric)", 1560, Appliance.USE_SINGLE, 0, false, 0));
		appliances.put("Hob (Electric)", new Appliance("Hob (Electric)", 710, Appliance.USE_SINGLE, 0, false, 0));
		appliances.put("Dishwasher (warm)", new Appliance("Dishwasher (warm)", 1070, Appliance.USE_SINGLE, 0, false, 0));
		appliances.put("Dishwasher (hot)", new Appliance("Dishwasher (hot)", 1440, Appliance.USE_SINGLE, 0, false, 0));
		appliances.put("Fridge+freezer", new Appliance("Fridge-freezer", 800, Appliance.USE_CONSTANT, 0, false, 0));
		appliances.put("Fridge", new Appliance("Fridge", 40, Appliance.USE_CONSTANT, 0, false, 0));
		appliances.put("Toaster", new Appliance("Toaster", 158, Appliance.USE_SINGLE, 1, false, 0));
		// juicer, blender/mixer
		appliances.put("Coffee Machine", new Appliance("Coffee Machine", 100, Appliance.USE_SINGLE, 1, false, 0));
		
		// laundry
		appliances.put("Washing machine", new Appliance("Washing machine", 630, Appliance.USE_SINGLE, 1, false, 0));
		appliances.put("Tumble dryer", new Appliance("Tumble dryer", 2500, Appliance.USE_SINGLE, 1, false, 0));
		appliances.put("Iron", new Appliance("Iron", 250, Appliance.USE_TIMED, 1, false, 0));
		appliances.put("Vacuum cleaner (small)", new Appliance("Vacuum cleaner (small)", 900, Appliance.USE_TIMED, 1, false, 0));
		appliances.put("Vacuum cleaner (large)", new Appliance("Vacuum cleaner (large)", 2000, Appliance.USE_TIMED, 1, false, 0));
		
		// health
		appliances.put("Hair dryer", new Appliance("Hair dryer", 2000, Appliance.USE_TIMED, 2, false, 0));
		appliances.put("Hair straighteners", new Appliance("Hair straighteners", 100, Appliance.USE_TIMED, 2, false, 0));
		appliances.put("Shaver (plugged in)", new Appliance("Shaver (plugged in)", 5, Appliance.USE_TIMED, 2, false, 0));
		// shower
		// baby stuff
		
		// lighting/heating
		appliances.put("Standard light bulb", new Appliance("Standard light bulb", 100, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Low energy light bulb", new Appliance("Low energy light bulb", 18, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Electric fire (warm)", new Appliance("Electric fire (warm)", 1000, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Electric fire (hot)", new Appliance("Electric fire (hot)", 2000, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Desk fan", new Appliance("Desk fan", 45, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Air conditioning (small unit)", new Appliance("Air conditioning (small unit)", 1000, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Air conditioning (large unit)", new Appliance("Air conditioning (large unit)", 3000, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Dehumidifier", new Appliance("Dehumidifier", 400, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Fan heater", new Appliance("Fan heater", 2000, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Electric radiator", new Appliance("Electric radiator/Panel heater", 1500, Appliance.USE_TIMED, 3, true, 0));
		//electric blanket
		//central heating/boiler
		
		// home entertainment
		appliances.put("TV (CRT)", new Appliance("TV (CRT)", 199, Appliance.USE_TIMED, 4, true, 4));
		appliances.put("Flat-screen TV (LCD)", new Appliance("Flat-screen TV (LCD)", 211, Appliance.USE_TIMED, 4, true, 2));
		appliances.put("Flat-screen TV (Plasma)", new Appliance("Flat-screen TV (Plasma)", 264, Appliance.USE_TIMED, 4, true, 4));
		appliances.put("Set-top box", new Appliance("Set-top box", 7, Appliance.USE_TIMED, 4, false, 6));
		appliances.put("Fancy set-top box", new Appliance("Fancy set-top box", 18, Appliance.USE_TIMED, 4, false, 11));
		appliances.put("Wii", new Appliance("Wii", 18, Appliance.USE_TIMED, 4, true, 1));
		appliances.put("XBox", new Appliance("XBox", 70, Appliance.USE_TIMED, 4, true, 0));
		appliances.put("XBox 360", new Appliance("XBox 360", 185, Appliance.USE_TIMED, 4, true, 2));
		appliances.put("Playstation 1", new Appliance("Playstation 1", 6, Appliance.USE_TIMED, 4, true, 0));
		appliances.put("Playstation 2", new Appliance("Playstation 2", 30, Appliance.USE_TIMED, 4, true, 2));
		appliances.put("Playstation 3", new Appliance("Playstation 3", 194, Appliance.USE_TIMED, 4, true, 2));
		//hifi
		// instruments
		
		// office
		appliances.put("Laptop (plugged in)", new Appliance("Laptop (plugged in)", 47, Appliance.USE_TIMED, 5, true, 0));
		appliances.put("Computer monitor", new Appliance("Computer monitor", 28, Appliance.USE_TIMED, 5, true, 2));
		appliances.put("Desktop computer (large)", new Appliance("Desktop computer (large)", 120, Appliance.USE_TIMED, 5, true, 60));
		appliances.put("Desktop computer (small)", new Appliance("Desktop computer (small)", 80, Appliance.USE_TIMED, 5, true, 40));
		appliances.put("Computer speakers", new Appliance("Computer speakers", 5, Appliance.USE_TIMED, 5, true, 0));
		appliances.put("ADSL/Cable router-modem", new Appliance("ADSL/Cable router-modem", 10, Appliance.USE_TIMED, 5, false, 0));
		appliances.put("Printer", new Appliance("Printer", 14, Appliance.USE_TIMED, 5, true, 4));
		appliances.put("Mobile phone (plugged in)", new Appliance("Mobile phone (plugged in)", 5, Appliance.USE_TIMED, 5, true, 0));
		appliances.put("iPad (plugged in)", new Appliance("iPad (plugged in)", 10, Appliance.USE_TIMED, 5, true, 0));
		appliances.put("Electric clock", new Appliance("Electric clock", 2, Appliance.USE_CONSTANT, 5, true, 0));
		// landline phone
		// small appliance charging
	}

	public void onModuleLoad() {
		AppliancePanels ap = new AppliancePanels(this);
		
		rp = new ResultsPanel();
		
		VerticalPanel vp = new VerticalPanel();
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
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