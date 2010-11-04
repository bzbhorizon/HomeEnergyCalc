package bzb.gwt.hec.client;

import bzb.gwt.hec.client.HomeEnergyCalc.State;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.smartgwt.client.widgets.Slider;
import com.smartgwt.client.widgets.events.ValueChangedEvent;
import com.smartgwt.client.widgets.events.ValueChangedHandler;

public class ReflectionPanel extends FlowPanel {
	
	private static HomeEnergyCalc hec;
	
	private static double newTotal;
	private static HTML newTotalHTML;
	
	public ReflectionPanel(HomeEnergyCalc hec) {
		setHec(hec);
		
		update();
	}
	
	public void update () {
		clear();
		add(new HTML("Current total = " + ResultsPanel.formatUnits(ResultsPanel.getTotalKwh())));
		newTotal = ResultsPanel.getTotalKwh();
		newTotalHTML = new HTML("Target = " + ResultsPanel.formatUnits(newTotal));
		add(newTotalHTML);
		
		Slider s = new Slider("New target");
		s.setMaxValue(100);
		s.setMinValue(0);
		s.setValue(100);
		s.setNumValues(50);
		s.addValueChangedHandler(new ValueChangedHandler() {
			public void onValueChanged(ValueChangedEvent event) {
				newTotal = event.getValue() / 100.0 * ResultsPanel.getTotalKwh();
				newTotalHTML.setHTML("Target = " + ResultsPanel.formatUnits(newTotal));
			}
		});
		add(s);
		
		add(new HTML("Some recommendations for changes?"));
		
		Button back = new Button("Back");
		back.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ResultsPanel.setTargetKwh(newTotal);
				WorkingPanel.updateResults();
				getHec().updateRootPanel(State.WORKING);
			}
		});
		back.addStyleName("submitButton");
		add(back);
	}

	public static void setHec(HomeEnergyCalc hec) {
		ReflectionPanel.hec = hec;
	}

	public static HomeEnergyCalc getHec() {
		return hec;
	}

}
