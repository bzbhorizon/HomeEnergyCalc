package bzb.gwt.hec.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import bzb.gwt.hec.client.HomeEnergyCalc.Format;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.LegendPosition;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.ColumnChart;
import com.google.gwt.visualization.client.visualizations.ColumnChart.Options;
import com.google.gwt.visualization.client.visualizations.PieChart;

public class ResultsPanel extends VerticalPanel {
	
	private static Options options;
	private static VerticalPanel results;
	static ArrayList<String> order;
	
	private static final double KWH_EMISSIONS = 0.544; // 1kWh = 0.544kg CO2
	private static final double KWH_COST = 0.1; // 1kWh = 10p
	private static final double KM_PER_MILE = 1.609344;
	
	private static double totalKwh = 0.0;
	private static double targetKwh = -1.0;
	
	public static double getTotalKwh() {
		return totalKwh;
	}

	public static void setTotalKwh(double totalKwh) {
		ResultsPanel.totalKwh = totalKwh;
	}

	public static String html;
	public static String totalHtml;

	public ResultsPanel () {
		order = new ArrayList<String>();
		
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				setWidth("550px");
				
				options = Options.create();
			    options.setWidth(360);
			    options.setHeight(280);
			    options.set3D(false);
			    //options.setTitle("Results");
			    options.setStacked(false);
			    options.setAxisFontSize(8.0);
			    options.setLegend(LegendPosition.NONE);
			    options.setBackgroundColor("#919892");
			    //options.setAxisColor("black");
			    //options.setShowCategories(false);
			    //options.setTitleX("Categories");
			    options.setTitleY(getUnitName() + " (" + getUnits() + ")");
				
				setStyleName("meter");
				
				results = new VerticalPanel();
				add(results);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
		
		setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		setStyleName("results");
	}

	public void updateResults (final HomeEnergyCalc home) {
		clear();
		results = new VerticalPanel();
		results.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		html = "";
		totalHtml = "";
		DataTable data = DataTable.create();
	    data.addColumn(ColumnType.STRING, getUnitName());
	    data.addColumn(ColumnType.NUMBER, getUnits());
	    data.addRows(home.getCategories().length);
		totalKwh = 0;
		
		for (int i = 0; i < home.getCategories().length; i++) {
			ArrayList<Appliance> apps = home.getAppliancesInCategory(i);
			Iterator<Appliance> appI = apps.iterator();
			double catKwh = 0.0;
			while (appI.hasNext()) {
				Appliance app = appI.next();
				if (app.getUse() == Appliance.USE_TIMED && (app.getHours() > 0 || app.getMinutes() > 0)) {
					double kw = (double)app.getWatts() / 1000.0 * app.getQuantity();
					double hours = (double)app.getHours() + (double)app.getMinutes() * 5.0 / 60.0;
					double kwh = kw * hours;
					catKwh += kwh;
					if (app.isUsesStandby() && app.getStandbyWatts() > 0) {
						kw = (double)app.getStandbyWatts() / 1000.0 * app.getQuantity();
						hours = 24 - hours;
						kwh = kw * hours;
						catKwh += kwh;
					}
				} else if (app.getUse() == Appliance.USE_SINGLE && app.getUses() > 0) {
					double kwh = (double)app.getWatts() / 1000.0 * app.getQuantity();
					double tkwh = kwh * app.getUses();
					catKwh += tkwh;
				} else if (app.getUse() == Appliance.USE_CONSTANT && app.isConstant()) {
					double kwh = (double)app.getWatts() / 1000.0 * app.getQuantity();
					catKwh += kwh;
				} else if (app.getUse() == Appliance.USE_DISTANCE && app.getQuantity() > 0) {
					double kwh = (double)app.getWatts() * KWH_EMISSIONS * app.getQuantity() / 1000 * KM_PER_MILE;
					catKwh += kwh;
				} else if (app.getUse() == Appliance.USE_PROPS) {
					double kwh = (double)app.getWatts() * (double)app.getProps() / 100.0 * app.getQuantity() / 1000 * app.getUses();
					catKwh += kwh;
				} else if (app.getUse() == Appliance.USE_TEMPS) {
					double kwh = (double)app.getWatts() * (double)app.getTemps() / 100.0 * app.getQuantity() / 1000 * app.getUses();
					catKwh += kwh;
				}
			}
			
			data.setValue(i, 0, home.getCategories()[i]);
		    data.setValue(i, 1, toCorrectUnits(catKwh));
		}
		
		Collections.reverse(order);
		Iterator<String> appI = order.iterator();
		while (appI.hasNext()) {
			Appliance app = home.getAppliance(appI.next());
			if (app.getUse() == Appliance.USE_TIMED && (app.getHours() > 0 || app.getMinutes() > 0)) {
				double kw = (double)app.getWatts() / 1000.0 * app.getQuantity();
				double hours = (double)app.getHours() + (double)app.getMinutes() * 5.0 / 60.0;
				double kwh = kw * hours;
				totalKwh += kwh;
				html += "<p>" + app.getName();
				if (app.getQuantity() > 1) {
					html += " (x" + app.getQuantity() + ")";
				}
				html += " x " + hours + " hours = " + formatUnits(kwh) + "</p>";
				
				if (app.isUsesStandby() && app.getStandbyWatts() > 0) {
					kw = (double)app.getStandbyWatts() / 1000.0 * app.getQuantity();
					hours = 24 - hours;
					kwh = kw * hours;
					totalKwh += kwh;
					html += "<p>" + app.getName();
					if (app.getQuantity() > 1) {
						html += " (x" + app.getQuantity() + ")";
					}
					html += " on standby x " + hours + " hours = " + formatUnits(kwh) + "</p>";
				}
			} else if (app.getUse() == Appliance.USE_PROPS) {
				double kw = (double)app.getWatts() / 1000.0 * app.getQuantity();
				double kwh = kw * (double)app.getProps() / 100 * app.getUses();
				totalKwh += kwh;
				html += "<p>" + app.getName();
				if (app.getQuantity() > 1) {
					html += " (x" + app.getQuantity() + ")";
				}
				html += " x " + app.getUses() + " uses x " + app.getProps() + "% = " + formatUnits(kwh) + "</p>";
			} else if (app.getUse() == Appliance.USE_TEMPS) {
				double kw = (double)app.getWatts() / 1000.0 * app.getQuantity();
				double kwh = kw * (double)app.getTemps() / 100 * app.getUses();
				totalKwh += kwh;
				html += "<p>" + app.getName();
				if (app.getQuantity() > 1) {
					html += " (x" + app.getQuantity() + ")";
				}
				html += " x " + app.getUses() + " uses at " + app.getTemps() + " degrees = " + formatUnits(kwh) + "</p>";
			} else if (app.getUse() == Appliance.USE_SINGLE && app.getUses() > 0) {
				double kwh = (double)app.getWatts() / 1000.0 * app.getQuantity();
				double tkwh = kwh * app.getUses();
				totalKwh += tkwh;
				html += "<p>" + app.getName();
				if (app.getQuantity() > 1) {
					html += " (x" + app.getQuantity() + ")";
				}
				html += " x " + app.getUses() + " uses = " + formatUnits(tkwh) + "</p>";
			} else if (app.getUse() == Appliance.USE_CONSTANT && app.isConstant()) {
				double kwh = (double)app.getWatts() / 1000.0 * app.getQuantity();
				totalKwh += kwh;
				html += "<p>" + app.getName();
				if (app.getQuantity() > 1) {
					html += " (x" + app.getQuantity() + ")";
				}
				html += " = " + formatUnits(kwh) + "</p>";
			} else if (app.getUse() == Appliance.USE_DISTANCE && app.getQuantity() > 0) {
				double kwh = (double)app.getWatts() * KWH_EMISSIONS * app.getQuantity() / 1000 * KM_PER_MILE;
				totalKwh += kwh;
				html += "<p>" + app.getName();
				if (app.getQuantity() > 1) {
					html += " (x" + app.getQuantity() + " miles)";
				}
				html += " = " + formatUnits(kwh) + "</p>";
			}
		}
		Collections.reverse(order);
			
		if (targetKwh >= 0.0) {
			String totalStyle;
			if (totalKwh > targetKwh) {
				totalStyle = "red";
			} else {
				totalStyle = "green";
			}
			totalHtml += "<div class='runningTarget' style='background: " + totalStyle + " !important;'>Total " + getUnitName() + " = " + formatUnits(totalKwh) + "; Target = " + formatUnits(targetKwh) + "</div>";
		}
		
		ColumnChart col = new ColumnChart(data, options);
		
		results.add(col);
		
		results.add(new HTML(html));
		
		add(results);
		
		HomeEnergyCalc.getFloating().clear();
		HomeEnergyCalc.getFloating().add(new HTML(totalHtml));
	}
	
	public void reset () {
		remove(results);
	}
	
	public static String getUnitName () {
		if (HomeEnergyCalc.getFormat() == Format.COST) {
			return "Cost";
		} else if (HomeEnergyCalc.getFormat() == Format.EMISSIONS) {
			return "Emissions";
		} else if (HomeEnergyCalc.getFormat() == Format.ENERGY) {
			return "Energy";
		} else {
			return null;
		}
	}
	
	public static String getUnits () {
		if (HomeEnergyCalc.getFormat() == Format.COST) {
			return "&pound;";
		} else if (HomeEnergyCalc.getFormat() == Format.EMISSIONS) {
			return "kg CO<sub>2</sub>";
		} else if (HomeEnergyCalc.getFormat() == Format.ENERGY) {
			return "kWh";
		} else {
			return null;
		}
	}
	
	public static double toEmissions (double kWh) {
		return kWh * KWH_EMISSIONS;
	}
	
	public static double toCost (double kWh) {
		return kWh * KWH_COST;
	}
	
	public static double toCorrectUnits (double kWh) {
		if (HomeEnergyCalc.getFormat() == Format.COST) {
			return toCost(kWh);
		} else if (HomeEnergyCalc.getFormat() == Format.EMISSIONS) {
			return toEmissions(kWh);
		} else if (HomeEnergyCalc.getFormat() == Format.ENERGY) {
			return kWh;
		} else {
			return kWh;
		}
	}
	
	public static String formatUnits (double kWh) {
		if (HomeEnergyCalc.getFormat() == Format.COST) {
			return getUnits() + roundToTwo(toCorrectUnits(kWh));
		} else if (HomeEnergyCalc.getFormat() == Format.EMISSIONS) {
			return roundToTwo(toCorrectUnits(kWh)) + getUnits();
		} else if (HomeEnergyCalc.getFormat() == Format.ENERGY) {
			return roundToTwo(kWh) + getUnits();
		} else {
			return null;
		}
	}

	public static void setTargetKwh(double targetKwh) {
		ResultsPanel.targetKwh = targetKwh;
	}

	public static double getTargetKwh() {
		return targetKwh;
	}
	
	public static String roundToTwo (double value) {
		value = Math.round(value * 100.0) / 100.0;
		String rounded = String.valueOf(value);
		if (rounded.indexOf('.') + 2 < rounded.length()) {
			return rounded.substring(0, rounded.indexOf('.') + 3);
		} else {
			return rounded;
		}
	}
}
