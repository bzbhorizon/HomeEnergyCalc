package bzb.gwt.hec.client;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.PieChart;
import com.google.gwt.visualization.client.visualizations.PieChart.Options;

public class ResultsPanel extends FlowPanel {
	
	private static Options options;
	private static FlowPanel results;
	
	public enum Format { COST, EMISSIONS, ENERGY };
	private static Format format = Format.ENERGY;
	
	private static final double KWH_EMISSIONS = 0.544; // 1kWh = 0.544kg CO2
	private static final double KWH_COST = 0.1; // 1kWh = 10p
	private static final double KM_PER_MILE = 1.609344;
	
	public ResultsPanel (Format format) {
		ResultsPanel.setFormat(format);
		
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				setWidth(Window.getClientWidth() * 0.35 + "px");
				
				options = Options.create();
			    options.setWidth((int)(Window.getClientWidth() * 0.35));
			    options.setHeight(300);
			    options.set3D(true);
			    options.setTitle("Contribution");
				
				setStyleName("results");
				
				results = new FlowPanel();
				add(results);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
	}

	public void updateResults (HomeEnergyCalc home) {
		remove(results);
		results = new FlowPanel();
		String html = "";
		DataTable data = DataTable.create();
	    data.addColumn(ColumnType.STRING, getUnitName());
	    data.addColumn(ColumnType.NUMBER, getUnits());
	    data.addRows(home.getCategories().length);
		double totalKwh = 0;
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
					totalKwh += kwh;
					catKwh += kwh;
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
						catKwh += kwh;
						html += "<p>" + app.getName();
						if (app.getQuantity() > 1) {
							html += " (x" + app.getQuantity() + ")";
						}
						html += " on standby x " + hours + " hours = " + formatUnits(kwh) + "</p>";
					}
				} else if (app.getUse() == Appliance.USE_SINGLE && app.getUses() > 0) {
					double kwh = (double)app.getWatts() / 1000.0 * app.getQuantity();
					double tkwh = kwh * app.getUses();
					totalKwh += tkwh;
					catKwh += tkwh;
					html += "<p>" + app.getName();
					if (app.getQuantity() > 1) {
						html += " (x" + app.getQuantity() + ")";
					}
					html += " x " + app.getUses() + " uses = " + formatUnits(tkwh) + "</p>";
				} else if (app.getUse() == Appliance.USE_CONSTANT && app.isConstant()) {
					double kwh = (double)app.getWatts() / 1000.0 * app.getQuantity();
					totalKwh += kwh;
					catKwh += kwh;
					html += "<p>" + app.getName();
					if (app.getQuantity() > 1) {
						html += " (x" + app.getQuantity() + ")";
					}
					html += " = " + formatUnits(kwh) + "</p>";
				} else if (app.getUse() == Appliance.USE_DISTANCE && app.getQuantity() > 0) {
					double kwh = (double)app.getWatts() * KWH_EMISSIONS * app.getQuantity() / 1000 * KM_PER_MILE;
					totalKwh += kwh;
					catKwh += kwh;
					html += "<p>" + app.getName();
					if (app.getQuantity() > 1) {
						html += " (x" + app.getQuantity() + " miles)";
					}
					html += " = " + formatUnits(kwh) + "</p>";
				}
			}
			data.setValue(i, 0, home.getCategories()[i]);
		    data.setValue(i, 1, toCorrectUnits(catKwh));
		}
		html += "<p>Total " + getUnitName() + " = " + formatUnits(totalKwh) + "</p>";
		
		PieChart pie = new PieChart(data, options);
		pie.addStyleName("pie");
		results.add(pie);
		results.add(new HTML(html));
		
		add(results);
	}
	
	public void reset () {
		remove(results);
	}

	public static void setFormat(Format format) {
		ResultsPanel.format = format;
	}

	public static Format getFormat() {
		return format;
	}
	
	public static String getUnitName () {
		if (getFormat() == Format.COST) {
			return "Cost";
		} else if (getFormat() == Format.EMISSIONS) {
			return "Emissions";
		} else if (getFormat() == Format.ENERGY) {
			return "Energy";
		} else {
			return null;
		}
	}
	
	public static String getUnits () {
		if (getFormat() == Format.COST) {
			return "&pound;";
		} else if (getFormat() == Format.EMISSIONS) {
			return "kg CO<sub>2</sub>";
		} else if (getFormat() == Format.ENERGY) {
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
		if (getFormat() == Format.COST) {
			return toCost(kWh);
		} else if (getFormat() == Format.EMISSIONS) {
			return toEmissions(kWh);
		} else if (getFormat() == Format.ENERGY) {
			return kWh;
		} else {
			return kWh;
		}
	}
	
	public static String formatUnits (double kWh) {
		if (getFormat() == Format.COST) {
			return getUnits() + toCorrectUnits(kWh);
		} else if (getFormat() == Format.EMISSIONS) {
			return toCorrectUnits(kWh) + getUnits();
		} else if (getFormat() == Format.ENERGY) {
			return kWh + getUnits();
		} else {
			return null;
		}
	}
	
}
