package bzb.gwt.hec.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class HomeEnergyCalc implements EntryPoint {
	
	private static HashMap<String,Appliance> appliances = new HashMap<String,Appliance>();
	private static final String[] categories = new String[]{
		"Kitchen","Laundry","Health/Personal Care","Lighting, Heating and Cooling","Home Entertainment","Office, PCs and Phones","Travel"
	};
	
	public enum State { WORKING, REFLECTION };
	private static State state = State.WORKING;
	
	private static WorkingPanel wp;
	private static BriefPanel bp;
	private static ReflectionPanel rp;
	
	public enum Format { COST, EMISSIONS, ENERGY };
	private static Format format = Format.ENERGY;

	public HomeEnergyCalc () { //http://www.carbonfootprint.com/energyconsumption.html
		// kitchen
		appliances.put("Microwave", new Appliance("Microwave", "microwave.png", 945, Appliance.USE_SINGLE, 0, false, 3));
		appliances.put("Kettle", new Appliance("Kettle", "kettle.png", 110, Appliance.USE_SINGLE, 0, false, 0));
		appliances.put("Oven (Gas)", new Appliance("Oven (Gas)", "oven.png", 1520, Appliance.USE_SINGLE, 0, false, 0));
		appliances.put("Oven (Electric)", new Appliance("Oven (Electric)", "oven.png", 1560, Appliance.USE_SINGLE, 0, false, 0));
		appliances.put("Hob (Gas)", new Appliance("Hob (Gas)", "hob.png", 900, Appliance.USE_SINGLE, 0, false, 0));
		appliances.put("Hob (Electric)", new Appliance("Hob (Electric)", "hob.png", 710, Appliance.USE_SINGLE, 0, false, 0));
		appliances.put("Dishwasher (warm)", new Appliance("Dishwasher (warm)", "dishwasher.png", 1070, Appliance.USE_SINGLE, 0, false, 0));
		appliances.put("Dishwasher (hot)", new Appliance("Dishwasher (hot)", "dishwasher.png", 1440, Appliance.USE_SINGLE, 0, false, 0));
		appliances.put("Combined fridge-freezer", new Appliance("Combined fridge-freezer", "fridgefreezer.png", 800, Appliance.USE_CONSTANT, 0, false, 0));
		appliances.put("Separate fridge", new Appliance("Separate fridge", "fridge.png", 40, Appliance.USE_CONSTANT, 0, false, 0));
		appliances.put("Toaster", new Appliance("Toaster", "toaster.png", 158, Appliance.USE_SINGLE, 0, false, 0));
		appliances.put("Blender/mixer", new Appliance("Blender/mixer", "blender.png", 6, Appliance.USE_SINGLE, 0, false, 0));
		appliances.put("Coffee Machine", new Appliance("Coffee Machine", "coffee.png", 100, Appliance.USE_SINGLE, 0, false, 0));
		
		// laundry
		appliances.put("Washing machine", new Appliance("Washing machine", "washing.png", 630, Appliance.USE_SINGLE, 1, false, 0));
		appliances.put("Tumble dryer", new Appliance("Tumble dryer", "tumble.png", 2500, Appliance.USE_SINGLE, 1, false, 0));
		appliances.put("Iron", new Appliance("Iron", "iron.png", 250, Appliance.USE_TIMED, 1, false, 0));
		appliances.put("Vacuum cleaner (small)", new Appliance("Vacuum cleaner (small)", "vacsmall.png", 900, Appliance.USE_TIMED, 1, false, 0));
		appliances.put("Vacuum cleaner (large)", new Appliance("Vacuum cleaner (large)", "vaclarge.png", 2000, Appliance.USE_TIMED, 1, false, 0));
		
		// health
		appliances.put("Hair dryer", new Appliance("Hair dryer", "hairDryer.png", 2000, Appliance.USE_TIMED, 2, false, 0));
		appliances.put("Hair straighteners", new Appliance("Hair straighteners", "straighteners.png", 100, Appliance.USE_TIMED, 2, false, 0));
		appliances.put("Shaver (plugged in)", new Appliance("Shaver (plugged in)", "shaver.png", 5, Appliance.USE_TIMED, 2, false, 0));
		appliances.put("Electric shower", new Appliance("Electric shower", "electricShower.png", 8500, Appliance.USE_TIMED, 2, false, 0));
		appliances.put("Power shower", new Appliance("Power shower", "powerShower.png", 150, Appliance.USE_TIMED, 2, false, 0));
		// baby stuff
		
		// lighting/heating
		appliances.put("Standard light bulb", new Appliance("Standard light bulb", "highBulb.png", 100, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Low energy light bulb", new Appliance("Low energy light bulb", "lowBulb.png", 18, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Electric fire (warm)", new Appliance("Electric fire (warm)", "fire.png", 1000, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Electric fire (hot)", new Appliance("Electric fire (hot)", "fire.png", 2000, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Gas fire (warm)", new Appliance("Gas fire (warm)", "fire.png", 400, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Gas fire (hot)", new Appliance("Gas fire (hot)", "fire.png", 1100, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Desk fan", new Appliance("Desk fan", "fan.png", 45, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Air conditioning (small unit)", new Appliance("Air conditioning (small unit)", "smallAirconditioning.png", 1000, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Air conditioning (large unit)", new Appliance("Air conditioning (large unit)", "airconditioning.png", 3000, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Dehumidifier", new Appliance("Dehumidifier", "dehumidifier.png", 400, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Fan heater", new Appliance("Fan heater", "fanHeater.png", 2000, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Electric radiator/Panel heater", new Appliance("Electric radiator/Panel heater", "panelHeater.png", 1500, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Electric blanket", new Appliance("Electric blanket", "electricBlanket.png", 100, Appliance.USE_TIMED, 3, true, 0));
		appliances.put("Central heating (warm)", new Appliance("Central heating (warm)", "central.png", 1700, Appliance.USE_TIMED, 3, false, 0));
		appliances.put("Central heating (hot)", new Appliance("Central heating (hot)", "central.png", 4400, Appliance.USE_TIMED, 3, false, 0));
		appliances.put("Immersion heater (Electric)", new Appliance("Immersion heater (Electric)", "immersion.png", 3000, Appliance.USE_TIMED, 3, false, 0));
		
		// home entertainment
		appliances.put("TV (CRT)", new Appliance("TV (CRT)", "crt.png", 199, Appliance.USE_TIMED, 4, true, 4));
		appliances.put("Flat-screen TV (LCD)", new Appliance("Flat-screen TV (LCD)", "flatTv.png", 211, Appliance.USE_TIMED, 4, true, 2));
		appliances.put("Flat-screen TV (Plasma)", new Appliance("Flat-screen TV (Plasma)", "flatTv.png", 264, Appliance.USE_TIMED, 4, true, 4));
		appliances.put("Set-top box", new Appliance("Set-top box", "settopFancy.png", 7, Appliance.USE_TIMED, 4, true, 6));
		appliances.put("Set-top box with recorder", new Appliance("Set-top box with recorder", "settopFancy.png", 18, Appliance.USE_TIMED, 4, true, 11));
		appliances.put("Wii", new Appliance("Wii", "wii.png", 18, Appliance.USE_TIMED, 4, true, 1));
		appliances.put("XBox", new Appliance("XBox", "xbox.png", 70, Appliance.USE_TIMED, 4, true, 0));
		appliances.put("XBox 360", new Appliance("XBox 360", "360.png", 185, Appliance.USE_TIMED, 4, true, 2));
		appliances.put("Playstation 1", new Appliance("Playstation 1", "ps1.png", 6, Appliance.USE_TIMED, 4, true, 0));
		appliances.put("Playstation 2", new Appliance("Playstation 2", "ps2.png", 30, Appliance.USE_TIMED, 4, true, 2));
		appliances.put("Playstation 3", new Appliance("Playstation 3", "ps3.png", 194, Appliance.USE_TIMED, 4, true, 2));
		appliances.put("Hifi (all-in-one)", new Appliance("Hifi (all-in-one)", "hifiSmall.png", 20, Appliance.USE_TIMED, 4, true, 1));
		appliances.put("Hifi (stack of separates)", new Appliance("Hifi (stack of separates)", "hifi.png", 50, Appliance.USE_TIMED, 4, true, 2));
		// instruments
		
		// office
		appliances.put("Laptop (plugged in)", new Appliance("Laptop (plugged in)", "laptop.png", 47, Appliance.USE_TIMED, 5, true, 0));
		appliances.put("Computer monitor", new Appliance("Computer monitor", "monitor.png", 28, Appliance.USE_TIMED, 5, true, 2));
		appliances.put("Desktop computer (large)", new Appliance("Desktop computer (large)", "computerLarge.png", 120, Appliance.USE_TIMED, 5, true, 60));
		appliances.put("Desktop computer (small)", new Appliance("Desktop computer (small)", "computerSmall.png", 80, Appliance.USE_TIMED, 5, true, 40));
		appliances.put("Computer speakers", new Appliance("Computer speakers", "compSpeakers.png", 5, Appliance.USE_TIMED, 5, true, 0));
		appliances.put("ADSL/Cable router-modem", new Appliance("ADSL/Cable router-modem", "router.png", 10, Appliance.USE_TIMED, 5, false, 0));
		appliances.put("Printer", new Appliance("Printer", "printer.png", 14, Appliance.USE_TIMED, 5, true, 4));
		appliances.put("Mobile phone (plugged in)", new Appliance("Mobile phone (plugged in)", "mobilePhone.png", 5, Appliance.USE_TIMED, 5, true, 0));
		appliances.put("iPad (plugged in)", new Appliance("iPad (plugged in)", "ipad.png", 10, Appliance.USE_TIMED, 5, true, 0));
		appliances.put("Cordless phone", new Appliance("Cordless phone", "cordlessPhone.png", 2, Appliance.USE_CONSTANT, 5, true, 0));
		
		// travel based on 1kWh = 544g CO2
		appliances.put("Bus", new Appliance("Bus", "bus.png", 134, Appliance.USE_DISTANCE, 6, true, 0));
		appliances.put("Plane", new Appliance("Plane", "plane.png", 172, Appliance.USE_DISTANCE, 6, true, 0));
		appliances.put("Coach", new Appliance("Coach", "coach.png", 30, Appliance.USE_DISTANCE, 6, true, 0));
		appliances.put("Rail", new Appliance("Rail", "train.png", 53, Appliance.USE_DISTANCE, 6, true, 0));
		appliances.put("Car (<1.2l petrol; <1.4l diesel)", new Appliance("Car (<1.2l petrol; <1.4l diesel)", "car0.png", 150, Appliance.USE_DISTANCE, 6, true, 0));
		appliances.put("Car (<1.5l petrol; <2.0l diesel)", new Appliance("Car (<1.5l petrol; <2.0l diesel)", "car1.png", 165, Appliance.USE_DISTANCE, 6, true, 0));
		appliances.put("Car (<2.0l petrol; <2.2l diesel)", new Appliance("Car (<2.0l petrol; <2.2l diesel)", "car2.png", 185, Appliance.USE_DISTANCE, 6, true, 0));
		appliances.put("Car (>2.0l petrol; >2.2l diesel)", new Appliance("Car (>2.0l petrol; >2.2l diesel)", "car3.png", 225, Appliance.USE_DISTANCE, 6, true, 0));
	}

	public void onModuleLoad() {
		
		updateRootPanel(State.WORKING);
		
		showBrief();
		
	}
	
	public void updateRootPanel (State newState) {
		if (newState != null) {
			setState(newState);
		}
		if (state == State.WORKING) {
			if (wp == null) {
				wp = new WorkingPanel(this);
			}
			RootPanel.get("app").clear();
			RootPanel.get("app").add(wp);
		} else if (state == State.REFLECTION) {
			if (rp == null) {
				rp = new ReflectionPanel(this);
			}
			rp.update();
			RootPanel.get("app").clear();
			RootPanel.get("app").add(rp);
		}
		if (Window.Location.getParameter("type") != null) {
			setFormat(Format.valueOf(Window.Location.getParameter("type").toUpperCase()));
			RootPanel.get("body").addStyleName(getFormat().name());
		}
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
	
	public HashMap<String, Appliance> getAppliances () {
		return appliances;
	}

	public static void setState(State state) {
		HomeEnergyCalc.state = state;
	}

	public static State getState() {
		return state;
	}
	
	public void showBrief () {
		if (bp == null) {
			bp = new BriefPanel();
		}
		bp.center();
	}
	
	public static void hideBrief () {
		bp.hide();
	}

	public static void setFormat(Format format) {
		HomeEnergyCalc.format = format;
	}

	public static Format getFormat() {
		return format;
	}
}