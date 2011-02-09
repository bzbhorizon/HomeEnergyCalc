package bzb.gwt.hec.client;

import bzb.gwt.hec.client.HomeEnergyCalc.Format;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
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
		form.setMethod(FormPanel.METHOD_POST);
		VerticalPanel vp = new VerticalPanel();
		
		Hidden units = new Hidden();
		units.setName("units");
		units.setValue(HomeEnergyCalc.getFormat().name());
		vp.add(units);
		
		TextArea response = new TextArea();
		response.setName("response");
		vp.add(response);
		
		Button submit = new Button("Submit");
		submit.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {
		        form.submit();
		    }
		});
		vp.add(submit);
		
		form.add(vp);
		
		form.addSubmitHandler(new SubmitHandler() {
			public void onSubmit(SubmitEvent event) {
				
			}
		});
		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			public void onSubmitComplete(SubmitCompleteEvent event) {
				Window.alert(event.getResults());
			}
		});
		
		add(form);
	}

}
