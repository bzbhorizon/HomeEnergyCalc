package bzb.gwt.hec.client;

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
		String url = "<a href='http://www.psychology.nottingham.ac.uk/limesurvey/index.php?sid=" + urlParams + "=" + HomeEnergyCalc.getUid() + "'>Survey</a>";
		add(new HTML(url));
	}

}
