package bzb.gwt.hec.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.ToggleButton;

public class AppliancePanels extends TabPanel {
	
	private static ApplianceButton[][] buttons;
	private static FlowPanel[] panels;
	//private static HomeEnergyCalc home;
	private static TimingDialog td;
	
	public AppliancePanels (/*HomeEnergyCalc home*/) {
		//AppliancePanels.home = home;
		buttons = new ApplianceButton[HomeEnergyCalc.getCategories().length][];
		panels = new FlowPanel[HomeEnergyCalc.getCategories().length];
		
		for (int i = 0; i < panels.length; i++) {
			panels[i] = new FlowPanel();
			ArrayList<Appliance> thisApps = HomeEnergyCalc.getAppliancesInCategory(i);
			buttons[i] = new ApplianceButton[thisApps.size()];
			int j = 0;
			for (Appliance app : HomeEnergyCalc.getAppliancesInCategory(i)) {
				buttons[i][j] = new ApplianceButton(app);
				panels[i].add(buttons[i][j++]);
			}
			add(panels[i], HomeEnergyCalc.getCategories()[i]);
		}

	    selectTab(0);
	    
	    setWidth(Window.getClientWidth() * 0.5 + "px");
	    
	    setStyleName("appliancePanels");
	    
	}
	
	public void reset () {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j].setDown(false);
				HomeEnergyCalc.getAppliance(buttons[i][j].getUpFace().getText()).reset();
			}
		}
	}
	
	public void setTd(TimingDialog td) {
		AppliancePanels.td = td;
	}

	public TimingDialog getTd() {
		return td;
	}

	class ApplianceButton extends ToggleButton {
		
		private Appliance app;
		
		public ApplianceButton (Appliance app) {
			addStyleName("applianceButton");
			setApp(app);
			setHTML(getUpFaceHTML());			
			addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (isDown()) {
						//setHTML(getDownFaceHTML());
						getApp().setQuantity(1);
						if (getApp().getUse() == Appliance.USE_CONSTANT) {
							getApp().setConstant(true);
							ResultsPanel.order.add(getApp().getName());
							WorkingPanel.updateResults();
						} else {
							setTd(new TimingDialog(ApplianceButton.this));
						}
				    } else {
				    	setHTML(getUpFaceHTML());
				    	getApp().reset();
				    	ResultsPanel.order.remove(getApp().getName());
				    	WorkingPanel.updateResults();
				    }
				}
			});
		}

		public void setApp(Appliance app) {
			this.app = app;
		}

		public Appliance getApp() {
			return app;
		}
		
		private String getUpFaceHTML () {
			if (getApp().getIconURL() != null) {
				return app.getName() + "<br /><img src='" + app.getIconURL() + "' />";
			} else {
				return app.getName();
			}
		}
		
		private String getDownFaceHTML () {
			if (getApp().getIconURL() != null) {
				return app.getName() + " (click to remove)<br /><img src='" + app.getIconURL() + "' />";
			} else {
				return app.getName() + " (click to remove)";
			}
		}
		
	}

}
