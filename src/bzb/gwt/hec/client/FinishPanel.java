package bzb.gwt.hec.client;

import bzb.gwt.hec.client.HomeEnergyCalc.Format;
import bzb.gwt.hec.client.HomeEnergyCalc.State;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FinishPanel extends VerticalPanel {
	
	private static String[] bits;
	
	private static HTML respCharCount;
	private static HTML fbCharCount;
	private static HorizontalPanel submitPanel;
	
	public FinishPanel () {
		if (HomeEnergyCalc.getFormat() == Format.COST) {
			bits = new String[]{"<span style='font-weight: normal;'>costs</span>",
					"energy <span style='font-weight: normal;'>costs</span> were ",
					"<span style='font-weight: normal;'>costs</span>"};
		} else if (HomeEnergyCalc.getFormat() == Format.EMISSIONS) {
			bits = new String[]{"<span style='font-weight: normal;'>CO<sub>2</sub></span>",
					"CO<sub>2</sub> emissions were ",
					"CO<sub>2</sub>"};
		} else if (HomeEnergyCalc.getFormat() == Format.ENERGY) {
			bits = new String[]{"<span style='font-weight: normal;'>energy</span>",
					"<span style='font-weight: normal;'>energy</span> usage was ",
					"<span style='font-weight: normal;'>energy</span>"};
		}
	}

	JavaScriptObject window;
	
	public void update () {
		int i = 0;
		
		String html = "<p><span style='font-size: 24pt;'>Congratulations!</span></p><p>You've reached your " + bits[i++] + " target.</p>" +
				"<h2>Summary</h2>" +
				"<p>Your daily " + bits[i] + ResultsPanel.formatUnits(ResultsPanel.getTotalKwh()) + "</p>" +
				"<p>Your monthly " + bits[i++] + ResultsPanel.formatUnits(ResultsPanel.getTotalKwh() * 28) + "</p>" +
				"<p>With your 5% reduction, you could save " + ResultsPanel.formatUnits(ResultsPanel.getTotalKwh() * 28.0 / 95.0 * 5.0) + " per month</p>" +
				"<p>Finally, please answer the following question:</p>" +
				"<p>In the space below (in 500 characters or less) please describe why saving this amount of " + bits[i++] + " is important to you:</p>";
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
		
		Hidden email = new Hidden();
		email.setName("email");
		email.setValue(HomeEnergyCalc.getEmail());
		vp.add(email);
		
		final Hidden sidField = new Hidden();
		sidField.setName("sid");
		vp.add(sidField);
		
		final Hidden time = new Hidden();
		time.setName("time");
		vp.add(time);
		
		final Hidden target = new Hidden();
		target.setName("target");
    	target.setValue(Double.toString(ResultsPanel.getTargetKwh()));
		vp.add(target);
		
		respCharCount = new HTML();
		
		final TextArea response = new TextArea();
		response.setSize(Window.getClientWidth() * 0.8 + "px", "100px");
		response.setName("response");
		response.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (response.getText().length() > 500) {
					response.setText(response.getText().substring(0, 500));
				}
				respCharCount.setHTML(String.valueOf(500 - response.getText().length()));
			}
		});
		HorizontalPanel respPanel = new HorizontalPanel();
		respPanel.add(response);
		respPanel.add(respCharCount);
		vp.add(respPanel);
		
		vp.add(new HTML("<p>If you have any feedback on the Calculator, please provide it (in 500 characters or less) in the space below:</p>"));
		
		fbCharCount = new HTML();
		
		final TextArea feedback = new TextArea();
		feedback.setSize(Window.getClientWidth() * 0.8 + "px", "100px");
		feedback.setName("feedback");
		feedback.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (feedback.getText().length() > 500) {
					feedback.setText(feedback.getText().substring(0, 500));
				}
				fbCharCount.setHTML(String.valueOf(500 - feedback.getText().length()));
			}
		});
		HorizontalPanel fbPanel = new HorizontalPanel();
		fbPanel.add(feedback);
		fbPanel.add(fbCharCount);
		vp.add(fbPanel);
		
		submitPanel = new HorizontalPanel();
		submitPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		final Button submit = new Button("Submit");
		submit.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {
		    	window = WindowUtil.newWindow("", "file_download", "");
		    	
		    	String sid;
		    	String urlParams;
				//int rand = (int)Math.floor(Math.random() * 2.0);
				//if (rand == 0) {
					sid = "65157";
					urlParams = sid + "&" + sid + "X727X4252";
				//} else {
				//	sid = "39141";
				//	urlParams = sid + "&" + sid + "X724X4247";
				//}
				final String url = "http://www.psychology.nottingham.ac.uk/limesurvey/index.php?sid=" + urlParams + "=" + HomeEnergyCalc.getEmail();
				
				WindowUtil.setWindowTarget(window, url);
		    	
				sidField.setValue(sid);
		    	time.setValue(Long.toString(System.currentTimeMillis() - HomeEnergyCalc.getStartTime()));
		        form.submit();
		        response.setEnabled(false);
		        feedback.setEnabled(false);
		    	submit.setEnabled(false);
		    }
		});
		submitPanel.add(submit);
		submitPanel.add(new HTML("<p style='color: green;'>Thank you for completing the Energy Calculator; when you click Submit a short survey will load in a new page - please give this a few seconds to load.<br />Once you have completed the survey, feel free to close both pages.</p>"));
		vp.add(submitPanel);

		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			public void onSubmitComplete(SubmitCompleteEvent event) {
				//HomeEnergyCalc.setUid(event.getResults().replaceAll("\\<.*?>",""));
				
				//HomeEnergyCalc.updateRootPanel(State.LEAVE);
				
				HomeEnergyCalc.setState(State.LEAVE);
			}
		});
		
		add(form);
	}

}
