package bzb.gwt.hec.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LeavePanel extends VerticalPanel {
	
	public LeavePanel () {
		add(new HTML("<a href='http://www.psychology.nottingham.ac.uk/limesurvey/index.php?sid=92547&92547X680X4175=" + HomeEnergyCalc.getUid() + "'>Survey</a>"));
	}

}
