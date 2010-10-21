package bzb.gwt.hec.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.ToggleButton;

public class AppliancePanels extends TabPanel {
	
	private ToggleButton[][] buttons;
	private HorizontalPanel[] panels;
	private HomeEnergyCalc home;
	private TimingDialog td;
	
	public AppliancePanels (HomeEnergyCalc home) {
		this.home = home;
		buttons = new ToggleButton[home.getCategories().length][];
		panels = new HorizontalPanel[home.getCategories().length];
		
		for (int i = 0; i < panels.length; i++) {
			panels[i] = new HorizontalPanel();
			ArrayList<Appliance> thisApps = home.getAppliancesInCategory(i);
			buttons[i] = new ToggleButton[thisApps.size()];
			int j = 0;
			for (Appliance app : home.getAppliancesInCategory(i)) {
				buttons[i][j] = new ToggleButton(app.getName(), app.getName() + " (click to remove)");
				buttons[i][j].addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						if (((ToggleButton) event.getSource()).isDown()) {
							if (AppliancePanels.this.home.getAppliance(((ToggleButton) event.getSource()).getUpFace().getText()).getUse() == Appliance.USE_CONSTANT) {
								AppliancePanels.this.home.getAppliance(((ToggleButton) event.getSource()).getUpFace().getText()).setConstant(true);
								AppliancePanels.this.home.updateResults();
							} else {
								td = new TimingDialog(AppliancePanels.this.home, ((ToggleButton) event.getSource()).getUpFace().getText());
							}
					    } else {
					    	AppliancePanels.this.home.getAppliance(((ToggleButton) event.getSource()).getUpFace().getText()).setHours(0);
					    	AppliancePanels.this.home.getAppliance(((ToggleButton) event.getSource()).getUpFace().getText()).setMinutes(0);
					    	AppliancePanels.this.home.updateResults();
					    }
					}
				});
				panels[i].add(buttons[i][j++]);
			}
			add(panels[i], home.getCategories()[i]);
		}

	    selectTab(0);
	    
	    setWidth(Window.getClientWidth() * 0.6 + "px");
	    
	    setStyleName("appliancePanels");
	    
	}

}
