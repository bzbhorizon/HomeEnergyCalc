package bzb.gwt.hec.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BriefPanel extends DialogBox {
	
	public BriefPanel () {
		
		setGlassEnabled(true);
		setText("Brief");
		
		VerticalPanel vp = new VerticalPanel();
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		vp.add(new HTML("Blah blah blah some crap here"));
		
		final Button start = new Button("Start");
		start.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		start.addStyleName("submitButton");
		vp.add(start);
		
		setWidget(vp);
		
		center();
	}
	
}
