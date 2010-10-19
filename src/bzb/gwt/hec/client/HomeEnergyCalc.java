package bzb.gwt.hec.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HomeEnergyCalc implements EntryPoint {

	public void onModuleLoad() {
		HorizontalPanel hpAp = new HorizontalPanel();
		HTML number1 = new HTML("1");
		number1.setStyleName("container");
		hpAp.add(number1);
		AppliancePanels ap = new AppliancePanels();
		hpAp.add(ap);
		hpAp.setStyleName("appliancePanelsContainer");
		hpAp.setWidth(Window.getClientWidth() * 0.75 + "px");
		
		HorizontalPanel hpRp = new HorizontalPanel();
		HTML number2 = new HTML("2");
		number2.setStyleName("container");
		hpRp.add(number2);
		ResultsPanel rp = new ResultsPanel();
		hpRp.add(rp);
		hpRp.setStyleName("resultsPanelContainer");
		hpRp.setWidth(Window.getClientWidth() * 0.75 + "px");
		
		VerticalPanel vp = new VerticalPanel();
		vp.setStyleName("app");
		vp.add(hpAp);
		vp.add(hpRp);
		RootPanel.get("app").add(vp);
	}
}
