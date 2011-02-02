package bzb.gwt.hec.client;

import bzb.gwt.hec.client.HomeEnergyCalc.Format;
import bzb.gwt.hec.client.HomeEnergyCalc.State;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ReflectionPanel extends FlowPanel {
	
	private static HomeEnergyCalc hec;
	
	private static double newTotal;
	private static HTML newTotalHTML;

	private static Button back;

	private static VerticalPanel vp;
	
	public ReflectionPanel(HomeEnergyCalc hec) {
		setHec(hec);
		
		update();
	}
	
	public void onLoad() {
		back.setHeight(vp.getOffsetHeight() + "px");
	}
	
	public void update () {
		clear();
		add(new HTML("Your current total is " + ResultsPanel.formatUnits(ResultsPanel.getTotalKwh()) +
			"<h2>Summary</h2>" +
			ResultsPanel.html));
		
		HorizontalPanel hp = new HorizontalPanel();
		
		vp = new VerticalPanel();
		vp.setStyleName("targetPanel");
		String message = "Try to reduce your ";
		if (HomeEnergyCalc.getFormat() == Format.COST) {
			message += " costs ";
		} else if (HomeEnergyCalc.getFormat() == Format.EMISSIONS) {
			message += " emissions ";
		} else if (HomeEnergyCalc.getFormat() == Format.ENERGY) {
			message += " energy use ";
		}
		message += " by 10%";
		vp.add(new HTML(message));
		
		newTotal = ResultsPanel.getTotalKwh() * 0.90;
		newTotalHTML = new HTML("Your target is " + ResultsPanel.formatUnits(newTotal));
		newTotalHTML.setStyleName("target");
		
		//HorizontalPanel hp = new HorizontalPanel();
		//hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		vp.add(newTotalHTML);
		hp.add(vp);
		/*
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
		hp.add(vp);*/
		//add(hp);
		
		back = new Button("Click to start reducing");
		back.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ResultsPanel.setTargetKwh(newTotal);
				WorkingPanel.updateResults();
				getHec().updateRootPanel(State.WORKING);
				WorkingPanel.reset.setVisible(true);
				WorkingPanel.submit.setVisible(true);
			}
		});
		back.addStyleName("recalcButton");
		hp.add(back);
		
		add(hp);
	}

	public static void setHec(HomeEnergyCalc hec) {
		ReflectionPanel.hec = hec;
	}

	public static HomeEnergyCalc getHec() {
		return hec;
	}

}
