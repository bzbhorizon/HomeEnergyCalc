package bzb.gwt.hec.client;

import bzb.gwt.hec.client.HomeEnergyCalc.State;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WorkingPanel extends HorizontalPanel {

	private static AppliancePanels ap;
	private static ResultsPanel rp;
	
	static Button reset;
	static Button submit;
	static VerticalPanel lhsPanel;
	
	public WorkingPanel () {
		setStyleName("horApp");
		
		lhsPanel = new VerticalPanel();
		lhsPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
		ap = new AppliancePanels();
		lhsPanel.add(ap);
		
		HorizontalPanel buttons = new HorizontalPanel();
		
		reset = new Button("Clear all choices");
		reset.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ap.reset();
				rp.updateResults();
			}
			
		});
		reset.addStyleName("clearButton");
		buttons.add(reset);
		
		submit = new Button("Calculate target");
		submit.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				reset.setVisible(false);
				submit.setVisible(false);
				HomeEnergyCalc.getFloating().clear();
				if (!ResultsPanel.metTarget) {
					HomeEnergyCalc.updateRootPanel(State.REFLECTION);
					submit.setText("Finish");
				} else {
					HomeEnergyCalc.updateRootPanel(State.FINISH);
				}
			}
		});
		submit.addStyleName("getResultsButton");
		buttons.add(submit);
		
		RootPanel.get("floating").add(buttons);
		
		
		
		add(lhsPanel);
		
		rp = new ResultsPanel();
		add(rp);
	}
	
	public static void updateResults () {
		rp.updateResults();
	}
	
}
