package bzb.gwt.hec.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LeavePanel extends VerticalPanel {
	
	public LeavePanel () {
		String urlParams;
		int rand = (int)Math.floor(Math.random() * 2.0);
		if (rand == 0) {
			urlParams = "92547&92547X680X4175";
		} else {
			urlParams = "69218&69218X696X4190";
		}
		final String url = "http://www.psychology.nottingham.ac.uk/limesurvey/index.php?sid=" + urlParams + "=" + HomeEnergyCalc.getUid();
		
		add(new HTML("<p>Thank you for completing the Calculator. Please click the button below to launch a brief survey.</p>"));
		
		final Button leave = new Button("Launch survey");
		leave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				leave.setEnabled(false);
				Window.open(url, "_blank", "");
			}
		});
		add(leave);
		
	}

}
