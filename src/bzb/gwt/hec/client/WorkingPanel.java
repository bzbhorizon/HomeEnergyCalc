package bzb.gwt.hec.client;

import bzb.gwt.hec.client.HomeEnergyCalc.State;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WorkingPanel extends HorizontalPanel {

	private static AppliancePanels ap;
	private static ResultsPanel rp;
	
	private static HomeEnergyCalc hec;
	
	static Button reset;
	static Button submit;
	
	public WorkingPanel (HomeEnergyCalc hec) {
		setHec(hec);
		
		setStyleName("horApp");
		
		VerticalPanel vp = new VerticalPanel();
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		ap = new AppliancePanels(getHec());
		vp.add(ap);
		
		HorizontalPanel buttons = new HorizontalPanel();
		
		reset = new Button("Clear choices");
		reset.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ap.reset();
				rp.updateResults(getHec());
			}
			
		});
		reset.addStyleName("clearButton");
		buttons.add(reset);
		
		submit = new Button("Calculate target");
		submit.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				getHec().updateRootPanel(State.REFLECTION);
				reset.setVisible(false);
				submit.setVisible(false);
				HomeEnergyCalc.getFloating().clear();
			}
		});
		submit.addStyleName("getResultsButton");
		buttons.add(submit);
		
		RootPanel.get("floating").add(buttons);
		
		add(vp);
		
		rp = new ResultsPanel();
		add(rp);
	}

	public static void setHec(HomeEnergyCalc hec) {
		WorkingPanel.hec = hec;
	}

	public static HomeEnergyCalc getHec() {
		return hec;
	}
	
	public static void updateResults () {
		rp.updateResults(getHec());
	}
	
}
