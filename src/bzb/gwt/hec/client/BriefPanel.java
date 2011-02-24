package bzb.gwt.hec.client;

import bzb.gwt.hec.client.HomeEnergyCalc.Format;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BriefPanel extends DialogBox {
	
	private int page = 0;
	private VerticalPanel vp;
	private HTML text;
	private HTML text2;
	
	public BriefPanel () {
		
		setGlassEnabled(true);
		setHTML("<h2>Brief</h2>");
		
		vp = new VerticalPanel();
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setWidth(Window.getClientWidth() * 0.75 + "px");
		vp.setWidth("100%");
		
		String[] bits = null;
		if (HomeEnergyCalc.getFormat() == Format.COST) {
			bits = new String[]{"the energy they use at home <span style='font-weight: normal;'>costs</span>",
					"daily energy use <span style='font-weight: normal;'>costs</span>",
					"it <span style='font-weight: normal;'>costs</span> to use",
					"",
					"it <span style='font-weight: normal;'>costs</span> to use these",
					"<span style='font-weight: normal;'>money</span> the energy you used at home <span style='font-weight: normal;'>cost</span>",
					"<span style='font-weight: normal;'>costs</span>"};
		} else if (HomeEnergyCalc.getFormat() == Format.EMISSIONS) {
			bits = new String[]{"<span style='font-weight: normal;'>CO<sub>2</sub></span> the energy they use at home generates",
					"<span style='font-weight: normal;'>CO<sub>2</sub></span> your daily energy use generates",
					"<span style='font-weight: normal;'>CO<sub>2</sub></span>",
					"generates",
					"<span style='font-weight: normal;'>CO<sub>2</sub></span> they generated",
					"<span style='font-weight: normal;'>CO<sub>2</sub></span> the energy you used at home generated",
					"<span style='font-weight: normal;'>CO<sub>2</sub></span> emissions"};
		} else if (HomeEnergyCalc.getFormat() == Format.ENERGY) {
			bits = new String[]{"<span style='font-weight: normal;'>energy</span> they use at home",
					"daily <span style='font-weight: normal;'>energy</span> use is",
					"<span style='font-weight: normal;'>energy</span>",
					"uses",
					"<span style='font-weight: normal;'>energy</span> you used",
					"<span style='font-weight: normal;'>energy</span> you used at home",
					"<span style='font-weight: normal;'>energy</span> use"};
		}
		
		int i = 0;
		
		text = new HTML("<p>Hi!</p>" +
				"<p>Thank you for logging in to the Home Energy Calculator.</p>" +
				"<p>The Home Energy Calculator is a tool to help people understand how much " + bits[i++] + ".</p>" +
				"<p>It will help you understand what your " + bits[i++] + ", and how much " + bits[i++] + " each appliance in your home " + bits[i++] + ". It takes about 15 minutes to complete.</p>" +
				"<p>Please note that:" +
				"<ul>" +
				"<li>Your participation is entirely voluntary and you may quit the website and the study at any time without giving a reason.</li>" +
				"<li>At the end of this study, you will be provided with additional information and feedback about the purpose of the study.</li>" +
				"<li>All your answers are anonymous and will be held confidentially.</li>" +
				"</ul></p>" +
				"<p>Please click the button below to confirm that you consent to participate in the Home Energy Calculator.</p>");

		text.setStyleName("briefText");
		
		text2 = new HTML("<p>The Home Energy Calculator is very easy to use.</p>" +
					"<p>Please think about a typical day in your life, for example yesterday <span style='font-style: italic;'>(NOTE: if yesterday was an unusual day for you then please think about the day before or a previous normal day for you)</span>.</p>" +
					"<p>Think about the appliances that you used yesterday (or on a typical day). Click on the appliances that you use and enter for how long (or how many times) you used them. " +
					"How much " + bits[i++] + " will appear on the right hand side of the screen. " +
					"<span style='font-style: italic;'>(NOTE: to edit or remove an appliance, please click on it again)</span>.</p>" +
					"<p>Once you are finished with one activity (e.g. kitchen), please select another activity (e.g. home entertainment) by clicking on the corresponding tab. Please don't forget to include communal uses of appliances (e.g., when you're not the one who cooks but the oven is used to prepare your dinner).</p>" + 
					"<p>When you have finished selecting all the appliances you used yesterday, please click on <span style='color: white; font-weight: bold; background: green; padding: 3px; -webkit-border-radius: 3px !important; -moz-border-radius: 3px !important;'>Calculate target</span> in the top right of the screen. " +
					"You will then see the total amount of " + bits[i++] + " in one day, as well as how it is distributed among activities.</p>" +
					"<p>After that, you will be set a 5% reduction target. You shall then revisit the original screen to consider how to reduce your " + bits[i++] + ". Once you've achieved this reduction, you will be able to click on <span style='color: white; font-weight: bold; background: green; padding: 3px; -webkit-border-radius: 3px !important; -moz-border-radius: 3px !important;'>Finish</span> to submit responses and answer some brief final questions.</p>" +
					"<p class='credits'>Figures used in the Energy Calculator are based on material from a range of sources, including the Carbon Trust, the Department for Environment, Food and Affairs and the Environmental Change Institute.</p>");
		text2.setStyleName("briefText");
		
		setWidget(vp);
		
		center();
		
		updatePage();
	}
	
	private void updatePage () {
		vp.clear();
		if (page == 0) {
			vp.add(text);
			final Button cont = new Button("I confirm that I consent to participate");
			cont.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					page++;
					updatePage();
				}
			});
			cont.addStyleName("submitButton");
			vp.add(cont);
		} else if (page == 1) {
			vp.add(text2);
			final Button start = new Button("Start");
			start.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					hide();
				}
			});
			start.addStyleName("submitButton");
			vp.add(start);
		}
		
		center();
	}
	
}
