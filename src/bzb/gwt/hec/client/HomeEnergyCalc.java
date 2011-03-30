package bzb.gwt.hec.client;

import java.util.ArrayList;
import java.util.HashMap;

import bzb.gwt.hec.client.appliances.Appliance;
import bzb.gwt.hec.client.appliances.ConstantAppliance;
import bzb.gwt.hec.client.appliances.PerUseAppliance;
import bzb.gwt.hec.client.appliances.ProportionAppliance;
import bzb.gwt.hec.client.appliances.TemperatureAppliance;
import bzb.gwt.hec.client.appliances.TimedAppliance;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HomeEnergyCalc implements EntryPoint {
	
	private static HashMap<String,Appliance> appliances = new HashMap<String,Appliance>();
	private static final String[] categories = new String[]{
		"Kitchen","Laundry","Health/Personal Care","Lighting, Heating and Cooling","Home Entertainment","Office, PCs and Phones"//,"Travel"
	};
	
	public enum State { WORKING, REFLECTION, FINISH, LEAVE };
	private static State state = State.WORKING;
	
	public static WorkingPanel wp;
	private static BriefPanel bp;
	private static ReflectionPanel rp;
	private static FinishPanel fp;
	private static VerticalPanel floating;
	private static LeavePanel lp;
	
	public enum Format { COST, EMISSIONS, ENERGY };
	private static Format format;
	
	//private static String Uid;
	private static String email;
	
	private static long startTime = System.currentTimeMillis();

	public HomeEnergyCalc () { //http://www.carbonfootprint.com/energyconsumption.html
		setFloating(new VerticalPanel());
		RootPanel.get("floating").add(getFloating());
		
		// kitchen
		appliances.put("Microwave", new TimedAppliance("Microwave", "microwave.png", 945*12, 0, false, 3));
		appliances.put("Kettle", new ProportionAppliance("Kettle", "kettle.png", 220, 0, false, 0));
		appliances.put("Oven (Gas)", new TimedAppliance("Oven (Gas)", "oven.png", 1520, 0, false, 0));
		appliances.put("Oven (Electric)", new TimedAppliance("Oven (Electric)", "oven.png", 1560, 0, false, 0));
		appliances.put("Hob (Gas)", new TimedAppliance("Hob (Gas)", "hob.png", 900, 0, false, 0));
		appliances.put("Hob (Electric)", new TimedAppliance("Hob (Electric)", "hob.png", 710, 0, false, 0));
		appliances.put("Dishwasher", new TemperatureAppliance("Dishwasher", "dishwasher.png", 1600, 0, false, 0));
		appliances.put("Combined fridge-freezer", new ConstantAppliance("Combined fridge-freezer", "fridgefreezer.png", 800, 0, false, 0));
		appliances.put("Separate fridge", new ConstantAppliance("Separate fridge", "fridge.png", 40, 0, false, 0));
		appliances.put("Toaster", new PerUseAppliance("Toaster", "toaster.png", 158, 0, false, 0));
		appliances.put("Blender/mixer", new PerUseAppliance("Blender/mixer", "blender.png", 6, 0, false, 0));
		appliances.put("Coffee Machine", new PerUseAppliance("Coffee Machine", "coffee.png", 100, 0, false, 0));
		
		// laundry
		appliances.put("Washing machine", new PerUseAppliance("Washing machine", "washing.png", 630, 1, false, 0));
		appliances.put("Tumble dryer", new PerUseAppliance("Tumble dryer", "tumble.png", 2500, 1, false, 0));
		appliances.put("Iron", new TimedAppliance("Iron", "iron.png", 250, 1, false, 0));
		appliances.put("Vacuum cleaner (small)", new TimedAppliance("Vacuum cleaner (small)", "vacsmall.png", 900, 1, false, 0));
		appliances.put("Vacuum cleaner (large)", new TimedAppliance("Vacuum cleaner (large)", "vaclarge.png", 2000, 1, false, 0));
		
		// health
		appliances.put("Hair dryer", new TimedAppliance("Hair dryer", "hairDryer.png", 2000, 2, false, 0));
		appliances.put("Hair straighteners", new TimedAppliance("Hair straighteners", "straighteners.png", 100, 2, false, 0));
		appliances.put("Shaver (plugged in)", new TimedAppliance("Shaver (plugged in)", "shaver.png", 5, 2, false, 0));
		appliances.put("Electric shower", new TimedAppliance("Electric shower", "electricShower.png", 8500, 2, false, 0));
		appliances.put("Power shower", new TimedAppliance("Power shower", "powerShower.png", 150, 2, false, 0));
		// baby stuff
		
		// lighting/heating
		appliances.put("Standard light bulb", new TimedAppliance("Standard light bulb", "highBulb.png", 100, 3, true, 0));
		appliances.put("Low energy light bulb", new TimedAppliance("Low energy light bulb", "lowBulb.png", 18, 3, true, 0));
		appliances.put("Electric fire (warm)", new TimedAppliance("Electric fire (warm)", "fire.png", 1000, 3, true, 0));
		appliances.put("Electric fire (hot)", new TimedAppliance("Electric fire (hot)", "fire.png", 2000, 3, true, 0));
		appliances.put("Gas fire (warm)", new TimedAppliance("Gas fire (warm)", "fire.png", 400, 3, true, 0));
		appliances.put("Gas fire (hot)", new TimedAppliance("Gas fire (hot)", "fire.png", 1100, 3, true, 0));
		appliances.put("Desk fan", new TimedAppliance("Desk fan", "fan.png", 45, 3, true, 0));
		appliances.put("Air conditioning (small unit)", new TimedAppliance("Air conditioning (small unit)", "smallAirconditioning.png", 1000, 3, true, 0));
		appliances.put("Air conditioning (large unit)", new TimedAppliance("Air conditioning (large unit)", "airconditioning.png", 3000, 3, true, 0));
		appliances.put("Dehumidifier", new TimedAppliance("Dehumidifier", "dehumidifier.png", 400, 3, true, 0));
		appliances.put("Fan heater", new TimedAppliance("Fan heater", "fanHeater.png", 2000, 3, true, 0));
		appliances.put("Electric radiator/Panel heater", new TimedAppliance("Electric radiator/Panel heater", "panelHeater.png", 1500, 3, true, 0));
		appliances.put("Electric blanket", new TimedAppliance("Electric blanket", "electricBlanket.png", 100, 3, true, 0));
		appliances.put("Central heating (warm)", new TimedAppliance("Central heating (warm)", "central.png", 1700, 3, false, 0));
		appliances.put("Central heating (hot)", new TimedAppliance("Central heating (hot)", "central.png", 4400, 3, false, 0));
		appliances.put("Immersion heater (Electric)", new TimedAppliance("Immersion heater (Electric)", "immersion.png", 3000, 3, false, 0));
		
		// home entertainment
		appliances.put("TV (CRT)", new TimedAppliance("TV (CRT)", "crt.png", 199, 4, true, 4));
		appliances.put("Flat-screen TV (LCD)", new TimedAppliance("Flat-screen TV (LCD)", "flatTv.png", 211, 4, true, 2));
		appliances.put("Flat-screen TV (Plasma)", new TimedAppliance("Flat-screen TV (Plasma)", "flatTv.png", 264, 4, true, 4));
		appliances.put("Set-top box", new TimedAppliance("Set-top box", "settopFancy.png", 7, 4, true, 6));
		appliances.put("Set-top box with recorder", new TimedAppliance("Set-top box with recorder", "settopFancy.png", 18, 4, true, 11));
		appliances.put("Wii", new TimedAppliance("Wii", "wii.png", 18, 4, true, 1));
		appliances.put("XBox", new TimedAppliance("XBox", "xbox.png", 70, 4, true, 0));
		appliances.put("XBox 360", new TimedAppliance("XBox 360", "360.png", 185, 4, true, 2));
		appliances.put("Playstation 1", new TimedAppliance("Playstation 1", "ps1.png", 6, 4, true, 0));
		appliances.put("Playstation 2", new TimedAppliance("Playstation 2", "ps2.png", 30, 4, true, 2));
		appliances.put("Playstation 3", new TimedAppliance("Playstation 3", "ps3.png", 194, 4, true, 2));
		appliances.put("Hifi (all-in-one)", new TimedAppliance("Hifi (all-in-one)", "hifiSmall.png", 20, 4, true, 1));
		appliances.put("Hifi (stack of separates)", new TimedAppliance("Hifi (stack of separates)", "hifi.png", 50, 4, true, 2));
		// instruments
		
		// office
		appliances.put("Laptop (plugged in)", new TimedAppliance("Laptop (plugged in)", "laptop.png", 47, 5, true, 0));
		appliances.put("Computer monitor", new TimedAppliance("Computer monitor", "monitor.png", 28, 5, true, 2));
		//appliances.put("Desktop computer (large)", new Appliance("Desktop computer (large)", "computerLarge.png", 120, Appliance.USE_TIMED, 5, true, 60));
		//appliances.put("Desktop computer (small)", new Appliance("Desktop computer (small)", "computerSmall.png", 80, Appliance.USE_TIMED, 5, true, 40));
		appliances.put("Desktop computer", new TimedAppliance("Desktop computer", "computerLarge.png", 80, 5, true, 40));
		appliances.put("Computer speakers", new TimedAppliance("Computer speakers", "compSpeakers.png", 5, 5, true, 0));
		appliances.put("ADSL/Cable router-modem", new TimedAppliance("ADSL/Cable router-modem", "router.png", 10, 5, false, 0));
		appliances.put("Printer", new TimedAppliance("Printer", "printer.png", 14, 5, true, 4));
		appliances.put("Mobile phone (plugged in)", new TimedAppliance("Mobile phone (plugged in)", "mobilePhone.png", 5, 5, true, 0));
		appliances.put("iPad (plugged in)", new TimedAppliance("iPad (plugged in)", "ipad.png", 10, 5, true, 0));
		//appliances.put("Cordless phone", new Appliance("Cordless phone", "cordlessPhone.png", 2, Appliance.USE_CONSTANT, 5, true, 0));
		
		// travel based on 1kWh = 544g CO2
		/*appliances.put("Bus", new Appliance("Bus", "bus.png", 134, Appliance.USE_DISTANCE, 6, true, 0));
		appliances.put("Plane", new Appliance("Plane", "plane.png", 172, Appliance.USE_DISTANCE, 6, true, 0));
		appliances.put("Coach", new Appliance("Coach", "coach.png", 30, Appliance.USE_DISTANCE, 6, true, 0));
		appliances.put("Rail", new Appliance("Rail", "train.png", 53, Appliance.USE_DISTANCE, 6, true, 0));
		appliances.put("Car (<1.2l petrol; <1.4l diesel)", new Appliance("Car (<1.2l petrol; <1.4l diesel)", "car0.png", 150, Appliance.USE_DISTANCE, 6, true, 0));
		appliances.put("Car (<1.5l petrol; <2.0l diesel)", new Appliance("Car (<1.5l petrol; <2.0l diesel)", "car1.png", 165, Appliance.USE_DISTANCE, 6, true, 0));
		appliances.put("Car (<2.0l petrol; <2.2l diesel)", new Appliance("Car (<2.0l petrol; <2.2l diesel)", "car2.png", 185, Appliance.USE_DISTANCE, 6, true, 0));
		appliances.put("Car (>2.0l petrol; >2.2l diesel)", new Appliance("Car (>2.0l petrol; >2.2l diesel)", "car3.png", 225, Appliance.USE_DISTANCE, 6, true, 0));*/
	}

	public static ClosingHandler wch;
	
	public void onModuleLoad() {
		Window.addWindowClosingHandler(new ClosingHandler() {
			public void onWindowClosing(ClosingEvent event) {
				if (HomeEnergyCalc.getState() != State.LEAVE) {
					event.setMessage("If you refresh or close the Calculator you will lose your current choices. Do you want to do this?");
				}
			}
		});
		
		if (Window.Location.getParameter("type") != null) {
			if (Window.Location.getParameter("type").equals("a")) {
				setFormat(Format.COST);
			} else if (Window.Location.getParameter("type").equals("b")) {
				setFormat(Format.EMISSIONS);
			} else {
				setFormat(Format.ENERGY);
			}
			RootPanel.get("body").addStyleName(getFormat().name());
		} else {
			int rand = (int) Math.floor(Math.random() * 3.0);
			if (rand == 0) {
				setFormat(Format.COST);
			} else if (rand == 1) {
				setFormat(Format.EMISSIONS);
			} else {
				setFormat(Format.ENERGY);
			}
			RootPanel.get("body").addStyleName(getFormat().name());
		}
		
		updateRootPanel(State.WORKING);
		
		showBrief();
		
	}
	
	public static void updateRootPanel (State newState) {
		if (newState != null) {
			setState(newState);
		}
		if (state == State.WORKING) {
			if (wp == null) {
				wp = new WorkingPanel();
			}
			RootPanel.get("app").clear();
			RootPanel.get("app").add(wp);
		} else if (state == State.REFLECTION) {
			if (rp == null) {
				rp = new ReflectionPanel();
			}
			rp.update();
			RootPanel.get("app").clear();
			RootPanel.get("app").add(rp);
		} else if (state == State.FINISH) {
			if (fp == null) {
				fp = new FinishPanel();
			}
			fp.update();
			RootPanel.get("app").clear();
			RootPanel.get("app").add(fp);
		} else if (state == State.LEAVE) {
			if (lp == null) {
				lp = new LeavePanel();
			}
			RootPanel.get("app").clear();
			RootPanel.get("app").add(lp);
		}
		
		HTML subHTML;
		if (state == State.WORKING && ResultsPanel.getTargetKwh() == -1.0) {
			subHTML = new HTML("How much ");
			if (getFormat() == Format.COST) {
				subHTML.setHTML(subHTML.getHTML() + " does your daily energy use cost?");
			} else if (getFormat() == Format.EMISSIONS) {
				subHTML.setHTML(subHTML.getHTML() + " carbon does your daily energy use produce?");
			} else if (getFormat() == Format.ENERGY) {
				subHTML.setHTML(subHTML.getHTML() + " energy do you use daily?");
			}
		} else {
			subHTML = new HTML("Reduce ");
			if (getFormat() == Format.COST) {
				subHTML.setHTML(subHTML.getHTML() + " costs");
			} else if (getFormat() == Format.EMISSIONS) {
				subHTML.setHTML(subHTML.getHTML() + " climate change");
			} else if (getFormat() == Format.ENERGY) {
				subHTML.setHTML(subHTML.getHTML() + " energy");
			}
		}
		RootPanel.get("sub").clear();
		RootPanel.get("sub").add(subHTML);
	}
	
	public static ArrayList<Appliance> getAppliancesInCategory (int category) {
		ArrayList<Appliance> matchingAppliances = new ArrayList<Appliance>();
		for (Appliance app : appliances.values()) {
			if (app.getCategory() == category) {
				matchingAppliances.add(app);
			}
		}
		return matchingAppliances;
	}
	
	public static String[] getCategories () {
		return categories;
	}
	
	public static Appliance getAppliance (String appName) {
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
	
	public static void showBrief () {
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

	public static void setFloating(VerticalPanel floating) {
		HomeEnergyCalc.floating = floating;
	}

	public static VerticalPanel getFloating() {
		return floating;
	}

	public static long getStartTime() {
		return startTime;
	}

	/*public static String getUid() {
		return Uid;
	}

	public static void setUid(String Uid) {
		HomeEnergyCalc.Uid = Uid;
	}*/

	public static void setEmail(String email) {
		HomeEnergyCalc.email = email;
	}

	public static String getEmail() {
		return email;
	}
}