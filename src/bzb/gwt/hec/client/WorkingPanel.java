package bzb.gwt.hec.client;

import bzb.gwt.hec.client.HomeEnergyCalc.State;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WorkingPanel extends HorizontalPanel {

	private static AppliancePanels ap;
	private static ResultsPanel rp;
	
	static Button reset;
	static Button submit;
	static Button restart;
	static Button help;
	static VerticalPanel lhsPanel;
	static DialogBox d;
	
	public WorkingPanel () {
		setStyleName("horApp");
		
		d = new DialogBox();
		d.setGlassEnabled(true);
		d.setAutoHideEnabled(true);
		d.setWidth(Window.getClientWidth() * 0.75 + "px");
		HTML h = new HTML("<p>To finish the Calculator you'll need to achieve your 5% reduction in " + ResultsPanel.getUnitName().toLowerCase() + ". Look back through the appliances you chose previously (outlined in green) - to decide not to use an appliance, click on it once. To change how you intend to use it, click on it once to remove it, then again to specify how you intend you use it.</p><p>Once you have beaten your target, the Finish button will turn green.</p>");
		h.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				d.hide();
			}
		});
		d.setWidget(h);
		
		lhsPanel = new VerticalPanel();
		lhsPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
		ap = new AppliancePanels();
		lhsPanel.add(ap);
		
		HorizontalPanel buttons = new HorizontalPanel();
		
		help = new Button("Help");
		help.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				HomeEnergyCalc.showBrief();
			}
			
		});
		help.addStyleName("helpButton");
		buttons.add(help);
		
		restart = new Button("Restart");
		restart.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				reload();
			}
			
		});
		restart.addStyleName("restartButton");
		buttons.add(restart);
		
		reset = new Button("Clear all choices");
		reset.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				AppliancePanels.reset();
				rp.updateResults();
			}
			
		});
		reset.addStyleName("clearButton");
		buttons.add(reset);
		
		submit = new Button("Calculate target");
		submit.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (ResultsPanel.getTargetKwh() == -1.0) {
					reset.setVisible(false);
					submit.setVisible(false);
					restart.setVisible(false);
					HomeEnergyCalc.getFloating().clear();
					HomeEnergyCalc.updateRootPanel(State.REFLECTION);
					submit.setText("Finish");
				} else if (ResultsPanel.metTarget) {
					reset.setVisible(false);
					submit.setVisible(false);
					restart.setVisible(false);
					HomeEnergyCalc.getFloating().clear();
					HomeEnergyCalc.updateRootPanel(State.FINISH);
				} else {
					showHint();
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
	
	private native void reload() /*-{ 
		$wnd.location.reload(); 
	}-*/; 
	
	public static void updateResults () {
		rp.updateResults();
	}
	
	public static void showHint () {
		d.center();
	}
	
}
