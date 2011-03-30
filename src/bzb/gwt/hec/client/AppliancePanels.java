package bzb.gwt.hec.client;

import java.util.ArrayList;

import bzb.gwt.hec.client.appliances.Appliance;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.TabPanel;

public class AppliancePanels extends TabPanel {
	
	private static final int FANCY_BUTTON_WIDTH = 170;
	
	private static ApplianceFancyButton[][] buttons;
	private static Grid[] grids;
	private static DetailsDialog td;
	
	public AppliancePanels () {
		buttons = new ApplianceFancyButton[HomeEnergyCalc.getCategories().length][];
		grids = new Grid[HomeEnergyCalc.getCategories().length];
		
		final int width = (int) (Window.getClientWidth() * 0.5);
		final int buttonsWide = width / FANCY_BUTTON_WIDTH;
		
		for (int i = 0; i < grids.length; i++) {
			ArrayList<Appliance> thisApps = HomeEnergyCalc.getAppliancesInCategory(i);
			grids[i] = new Grid(thisApps.size() / buttonsWide + 1, buttonsWide);
			grids[i].setBorderWidth(0);
			grids[i].setCellPadding(0);
			grids[i].setCellSpacing(0);
			CellFormatter f = grids[i].getCellFormatter();
			buttons[i] = new ApplianceFancyButton[thisApps.size()];
			int j = 0;
			int r = 0;
			int c = 0;
			for (Appliance app : thisApps) {
				buttons[i][j] = new ApplianceFancyButton(app);
				grids[i].setWidget(r, c, buttons[i][j++]);
				f.setAlignment(r, c++, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
				if (c >= buttonsWide) {
					r++;
					c = 0;
				}
			}
			add(grids[i], HomeEnergyCalc.getCategories()[i]);
		}

	    selectTab(0);
	    
	    setWidth(width + "px");
	    
	    setStyleName("appliancePanels");
	    
	}
	
	public static void reset () {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j].getAdd().setEnabled(true);
				buttons[i][j].getRemove().setEnabled(true);
				HomeEnergyCalc.getAppliance(buttons[i][j].getAppName()).reset();
			}
		}
		ResultsPanel.order = new ArrayList<String>();
	}
	
	public static void setTd(DetailsDialog td) {
		AppliancePanels.td = td;
	}

	public static DetailsDialog getTd() {
		return td;
	}

}
