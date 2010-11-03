package bzb.gwt.hec.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WorkingPanel extends HorizontalPanel {

	private static AppliancePanels ap;
	private static ResultsPanel rp;
	
	private static HomeEnergyCalc hec;
	
	public WorkingPanel (HomeEnergyCalc hec) {
		setHec(hec);
		
		setStyleName("horApp");
		
		VerticalPanel vp = new VerticalPanel();
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		ap = new AppliancePanels(getHec());
		vp.add(ap);
		Button reset = new Button("Clear all");
		reset.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ap.reset();
				rp.reset();
			}
			
		});
		reset.addStyleName("clearButton");
		vp.add(reset);
		
		add(vp);
		
		String type = Window.Location.getParameter("type");
		if (type != null) {
			if (type.equals("emissions")) {
				rp = new ResultsPanel(ResultsPanel.Format.EMISSIONS);
			} else if (type.equals("energy")) {
				rp = new ResultsPanel(ResultsPanel.Format.ENERGY);
			} else {
				rp = new ResultsPanel(ResultsPanel.Format.COST);
			}
		} else {
			rp = new ResultsPanel(ResultsPanel.Format.COST);
		}
		
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