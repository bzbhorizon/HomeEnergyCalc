package bzb.gwt.hec.client;

import bzb.gwt.hec.client.HomeEnergyCalc.Format;
import bzb.gwt.hec.client.HomeEnergyCalc.State;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FinishPanel extends VerticalPanel {
	
	private static String[] bits;
	
	public FinishPanel () {
		if (HomeEnergyCalc.getFormat() == Format.COST) {
			bits = new String[]{"<span style='font-weight: bold;'>costs</span>",
					"energy <span style='font-weight: bold;'>costs</span> were ",
					"<span style='font-weight: bold;'>costs</span>"};
		} else if (HomeEnergyCalc.getFormat() == Format.EMISSIONS) {
			bits = new String[]{"<span style='font-weight: bold;'>CO<sub>2</sub></span>",
					"CO<sub>2</sub> emissions were ",
					"CO<sub>2</sub>"};
		} else if (HomeEnergyCalc.getFormat() == Format.ENERGY) {
			bits = new String[]{"<span style='font-weight: bold;'>energy</span>",
					"<span style='font-weight: bold;'>energy</span> usage was ",
					"<span style='font-weight: bold;'>energy</span>"};
		}
	}

	public void update () {
		int i = 0;
		
		String html = "<p>Congratulations! You've reached your " + bits[i++] + " target.</p>" +
				"<h2>Summary</h2>" +
				"<p>Your daily " + bits[i] + ResultsPanel.formatUnits(ResultsPanel.getTotalKwh()) + "</p>" +
				"<p>Your monthly " + bits[i++] + ResultsPanel.formatUnits(ResultsPanel.getTotalKwh() * 28) + "</p>" +
				"<p>With your 5% reduction, you could save " + ResultsPanel.formatUnits(ResultsPanel.getTotalKwh() * 28.0 / 95.0 * 5.0) + " per month</p>" +
				"<p>Finally, please answer the following question:</p>" +
				"<p>In the space below, please describe why saving this amount of " + bits[i++] + " is important to you:</p>";
		add(new HTML(html));
		
		final FormPanel form = new FormPanel();
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_GET);
		VerticalPanel vp = new VerticalPanel();
		form.setWidget(vp);
		form.setAction("/upload");
		
		Hidden units = new Hidden();
		units.setName("units");
		units.setValue(HomeEnergyCalc.getFormat().name());
		vp.add(units);
		
		final Hidden time = new Hidden();
		time.setName("time");
		vp.add(time);
		
		final TextArea response = new TextArea();
		response.setSize(Window.getClientWidth() * 0.8 + "px", "200px");
		response.setName("response");
		vp.add(response);
		
		final Button submit = new Button("Submit");
		submit.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {
		    	time.setValue(Long.toString(System.currentTimeMillis() - HomeEnergyCalc.getStartTime()));
		        form.submit();
		        response.setEnabled(false);
		    	submit.setEnabled(false);
		    }
		});
		vp.add(submit);

		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			public void onSubmitComplete(SubmitCompleteEvent event) {
				HomeEnergyCalc.updateRootPanel(State.LEAVE);
			}
		});
		
		add(form);
	}

}
