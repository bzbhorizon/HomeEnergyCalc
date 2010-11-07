package bzb.gwt.hec.client;

import bzb.gwt.hec.client.HomeEnergyCalc.State;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

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
		newTotal = ResultsPanel.getTotalKwh() * 0.75;
		newTotalHTML = new HTML("Target = " + ResultsPanel.formatUnits(newTotal));
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(newTotalHTML);
		
		VerticalPanel vp = new VerticalPanel();
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		Button up = new Button("Raise");
		up.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if ((newTotal + ResultsPanel.getTotalKwh() * 0.05) <= ResultsPanel.getTotalKwh()) {
					newTotal += ResultsPanel.getTotalKwh() * 0.05;
					newTotalHTML.setHTML("Target = " + ResultsPanel.formatUnits(newTotal));
				}
			}
		});
		vp.add(up);
		Button down = new Button("Lower");
		down.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if ((newTotal - ResultsPanel.getTotalKwh() * 0.05) >= 0) {
					newTotal -= ResultsPanel.getTotalKwh() * 0.05;
					newTotalHTML.setHTML("Target = " + ResultsPanel.formatUnits(newTotal));
				}
			}
		});
		vp.add(down);
		hp.add(vp);
		add(hp);
		/*SliderBar s = new SliderBar(0, 100);
		s.setCurrentValue(100);
		s.setNumLabels(20);
		s.setNumTicks(20);
		s.setStepSize(2);
		s.setWidth("100px");
		s.addValueChangeHandler(new ValueChangeHandler<Double>() {
			public void onValueChange(ValueChangeEvent<Double> event) {
				newTotal = event.getValue() / 100.0 * ResultsPanel.getTotalKwh();
				newTotalHTML.setHTML("Target = " + ResultsPanel.formatUnits(newTotal));
			}
		});
		add(s);*/
		
		
		
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
