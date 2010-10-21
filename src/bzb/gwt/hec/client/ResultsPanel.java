package bzb.gwt.hec.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class ResultsPanel extends FlowPanel {
	
	private HTML results = new HTML();
	
	public ResultsPanel () {
		add(results);
		
		setWidth(Window.getClientWidth() * 0.6 + "px");
		
		setStyleName("resultsPanel");
	}

	public void updateResults (HomeEnergyCalc home) {
		results.setHTML("");
		String html = "";
		double totalKwh = 0;
		for (Appliance app : home.getAppliances().values()) {
			if (app.getUse() == Appliance.USE_TIMED && (app.getHours() > 0 || app.getMinutes() > 0)) {
				double kw = (double)app.getWatts() / 1000.0 * app.getQuantity();
				double hours = (double)app.getHours() + (double)app.getMinutes() * 5.0 / 60.0;
				double kwh = kw * hours;
				totalKwh += kwh;
				html += "<p>" + app.getName();
				if (app.getQuantity() > 1) {
					html += " (x" + app.getQuantity() + ")";
				}
				html += " = " + kw + "kW x " + hours + " hours = " + kwh + "kWh</p>";
				
				if (app.isUsesStandby() && app.getStandbyWatts() > 0) {
					kw = (double)app.getStandbyWatts() / 1000.0 * app.getQuantity();
					hours = 24 - hours;
					kwh = kw * hours;
					totalKwh += kwh;
					html += "<p>" + app.getName();
					if (app.getQuantity() > 1) {
						html += " (x" + app.getQuantity() + ")";
					}
					html += " on standby = " + kw + "kW x " + hours + " hours = " + kwh + "kWh</p>";
				}
			} else if (app.getUse() == Appliance.USE_SINGLE && app.getUses() > 0) {
				double kwh = (double)app.getWatts() / 1000.0 * app.getQuantity();
				double tkwh = kwh * app.getUses();
				totalKwh += tkwh;
				html += "<p>" + app.getName();
				if (app.getQuantity() > 1) {
					html += " (x" + app.getQuantity() + ")";
				}
				html += " = " + kwh + "kWh x " + app.getUses() + " uses = " + tkwh + "kWh</p>";
			} else if (app.getUse() == Appliance.USE_CONSTANT && app.isConstant()) {
				double kwh = (double)app.getWatts() / 1000.0 * app.getQuantity();
				totalKwh += kwh;
				html += "<p>" + app.getName();
				if (app.getQuantity() > 1) {
					html += " (x" + app.getQuantity() + ")";
				}
				html += " = " + kwh + "kWh</p>";
			}
		}
		html += "<p>Total energy use = " + totalKwh + "kWh</p><p>Total CO2 emissions = " + totalKwh * 0.544 + "kg</p><p>Total energy cost = &pound;" + totalKwh * 0.1 + "</p>";
		results.setHTML(html);
	}
	
}
