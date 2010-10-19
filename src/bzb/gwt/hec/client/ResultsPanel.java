package bzb.gwt.hec.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class ResultsPanel extends FlowPanel {
	
	public ResultsPanel () {
		add(new HTML("Some results"));
		
		setWidth(Window.getClientWidth() * 0.6 + "px");
		
		setStyleName("resultsPanel");
	}

}
