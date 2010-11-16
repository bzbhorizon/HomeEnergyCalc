package bzb.gwt.hec.client;

import java.util.ArrayList;
import java.util.Iterator;

import bzb.gwt.hec.client.HomeEnergyCalc.Format;
import bzb.gwt.hec.client.HomeEnergyCalc.State;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
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
	
	private static final double KWH_EMISSIONS = 0.544; // 1kWh = 0.544kg CO2
	private static final double KWH_COST = 0.1; // 1kWh = 10p
	private static final double KM_PER_MILE = 1.609344;
	
	private static double totalKwh = 0.0;
	private static double targetKwh = 0.0;
	
	public static double getTotalKwh() {
		return totalKwh;
	}

	public static void setTotalKwh(double totalKwh) {
		ResultsPanel.totalKwh = totalKwh;
	}

	public ResultsPanel () {
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				setWidth(Window.getClientWidth() * 0.48 + "px");
				
				options = Options.create();
			    options.setWidth((int)(Window.getClientWidth() * 0.28));
			    options.setHeight(300);
			    options.set3D(false);
			    //options.setTitle("Results");
			    options.setStacked(false);
			    options.setAxisFontSize(8.0);
			    options.setLegend(LegendPosition.NONE);
			    options.setBackgroundColor("#919892");
			    //options.setAxisColor("black");
			    //options.setShowCategories(false);
			    //options.setTitleX("Categories");
			    options.setTitleY(getUnitName());
				
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
		String html = "";
		String totalHtml = "";
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
		String totalStyle;
		if (totalKwh > targetKwh) {
			totalStyle = "red";
		} else {
			totalStyle = "green";
		}
		totalHtml += "<p style='color:" + totalStyle + ";'>Total " + getUnitName() + " = " + formatUnits(totalKwh) + "; Target = " + formatUnits(targetKwh) + "</p>";
		
		//PieChart pie = new PieChart(data, options);
		//pie.addStyleName("pie");
		ColumnChart col = new ColumnChart(data, options);
		
		results.add(new HTML(totalHtml));
		//results.add(pie);
		results.add(col);
		results.add(new HTML(html));
		add(results);
		
		Button submit = new Button("Submit");
		submit.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				home.updateRootPanel(State.REFLECTION);
			}
		});
		submit.addStyleName("submitButton");
		add(submit);
		
		
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
			return getUnits() + toCorrectUnits(kWh);
		} else if (HomeEnergyCalc.getFormat() == Format.EMISSIONS) {
			return toCorrectUnits(kWh) + getUnits();
		} else if (HomeEnergyCalc.getFormat() == Format.ENERGY) {
			return kWh + getUnits();
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
	
}
