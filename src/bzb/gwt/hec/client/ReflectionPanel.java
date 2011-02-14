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
import com.google.gwt.visualization.client.LegendPosition;
import com.google.gwt.visualization.client.visualizations.ColumnChart;
import com.google.gwt.visualization.client.visualizations.ColumnChart.Options;

public class ReflectionPanel extends FlowPanel {
	
	private static double newTotal;
	private static HTML newTotalHTML;

	private static Button back;

	private static VerticalPanel vp;
	
	private static String[] bits;
	
	public ReflectionPanel() {
		if (HomeEnergyCalc.getFormat() == Format.COST) {
			bits = new String[]{//"does your daily energy use <span style='font-weight: bold;'>cost</span>",
					"energy costs"};
		} else if (HomeEnergyCalc.getFormat() == Format.EMISSIONS) {
			bits = new String[]{//"<span style='font-weight: bold;'>CO<sub>2</sub></span> does your daily energy use produce",
					"CO<sub>2</sub> emissions"};
		} else if (HomeEnergyCalc.getFormat() == Format.ENERGY) {
			bits = new String[]{//"<span style='font-weight: bold;'>energy</span> do you use daily",
					"energy consumption"};
		}
	}
	
	public void onLoad() {
		back.setHeight(vp.getOffsetHeight() + "px");
	}
	
	public void update () {
		clear();
		
		int i = 0;
		
		add(new HTML("<p>This is an illustration of your daily energy use:</p>" +
				//"<p>How much " + bits[i++] + "?</p>" +
				"<p>Your current total is: <span style='font-size: 24pt; font-weight: bold;'>" + ResultsPanel.formatUnits(ResultsPanel.getTotalKwh()) + "</span></p>"));
		
		HorizontalPanel hp = new HorizontalPanel();
		
		hp.add(new HTML(ResultsPanel.html));
		
		if (ResultsPanel.data != null) {
			Options options = Options.create();
		    options.setWidth(500);
		    options.setHeight(280);
		    options.set3D(true);
		    options.setStacked(false);
		    options.setAxisFontSize(8.0);
		    options.setLegend(LegendPosition.NONE);
		    options.setBackgroundColor("white");
		    options.setTitleY(ResultsPanel.getUnitName().replaceAll("\\<.*?>","").replaceAll("&.+;","£") + " (" + ResultsPanel.getUnits().replaceAll("\\<.*?>","").replaceAll("&.+;","£") + ")");
	    
	    	ColumnChart col = new ColumnChart(ResultsPanel.data, options);
	    	hp.add(col);
	    }
		
		add(hp);
		add(new HTML("<p style='font-size: 14pt;'>Please now revisit the game and see if you can reduce your <span style='font-weight: bold;'>" + bits[i++] + "</span> by 5%</p>"));
		
		HorizontalPanel buttonsHp = new HorizontalPanel();
		
		vp = new VerticalPanel();
		vp.setStyleName("targetPanel");
		String message = "Try to reduce your <span style='font-weight: bold;'>";
		if (HomeEnergyCalc.getFormat() == Format.COST) {
			message += " costs ";
		} else if (HomeEnergyCalc.getFormat() == Format.EMISSIONS) {
			message += " emissions ";
		} else if (HomeEnergyCalc.getFormat() == Format.ENERGY) {
			message += " energy use ";
		}
		message += "</span> by 5%";
		vp.add(new HTML(message));
		
		newTotal = ResultsPanel.getTotalKwh() * 0.95;
		newTotalHTML = new HTML("Your target is " + ResultsPanel.formatUnits(newTotal));
		newTotalHTML.setStyleName("target");
		
		//HorizontalPanel hp = new HorizontalPanel();
		//hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		vp.add(newTotalHTML);
		buttonsHp.add(vp);
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
				HomeEnergyCalc.updateRootPanel(State.WORKING);
				WorkingPanel.reset.setVisible(true);
				WorkingPanel.submit.setVisible(true);
			}
		});
		back.addStyleName("recalcButton");
		buttonsHp.add(back);
		
		add(buttonsHp);
	}

}
