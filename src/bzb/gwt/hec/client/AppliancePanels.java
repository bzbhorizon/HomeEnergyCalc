package bzb.gwt.hec.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.ToggleButton;

public class AppliancePanels extends TabPanel {
	
	private ToggleButton[][] buttons;
	private FlowPanel[] panels;
	private static HomeEnergyCalc home;
	private TimingDialog td;
	
	public AppliancePanels (HomeEnergyCalc home) {
		AppliancePanels.home = home;
		buttons = new ToggleButton[home.getCategories().length][];
		panels = new FlowPanel[home.getCategories().length];
		
		for (int i = 0; i < panels.length; i++) {
			panels[i] = new FlowPanel();
			ArrayList<Appliance> thisApps = home.getAppliancesInCategory(i);
			buttons[i] = new ToggleButton[thisApps.size()];
			int j = 0;
			for (Appliance app : home.getAppliancesInCategory(i)) {
				buttons[i][j] = new ToggleButton(app.getName(), app.getName() + " (click to remove)");
				buttons[i][j].addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						if (((ToggleButton) event.getSource()).isDown()) {
							AppliancePanels.home.getAppliance(((ToggleButton) event.getSource()).getUpFace().getText()).setQuantity(1);
							if (AppliancePanels.home.getAppliance(((ToggleButton) event.getSource()).getUpFace().getText()).getUse() == Appliance.USE_CONSTANT) {
								AppliancePanels.home.getAppliance(((ToggleButton) event.getSource()).getUpFace().getText()).setConstant(true);
								AppliancePanels.home.updateResults();
							} else {
								td = new TimingDialog(AppliancePanels.home, (ToggleButton) event.getSource());
							}
					    } else {
					    	AppliancePanels.home.getAppliance(((ToggleButton) event.getSource()).getUpFace().getText()).reset();
					    	AppliancePanels.home.updateResults();
					    }
					}
				});
				panels[i].add(buttons[i][j++]);
			}
			add(panels[i], home.getCategories()[i]);
		}

	    selectTab(0);
	    
	    setWidth(Window.getClientWidth() * 0.5 + "px");
	    
	    setStyleName("appliancePanels");
	    
	}
	
	public void reset () {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j].setDown(false);
				home.getAppliance(buttons[i][j].getUpFace().getText()).reset();
			}
		}
	}

}
