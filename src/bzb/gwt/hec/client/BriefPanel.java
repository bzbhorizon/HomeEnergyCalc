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
		setHTML("<h1>Brief</h1>");
		
		vp = new VerticalPanel();
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setWidth(Window.getClientWidth() * 0.75 + "px");
		vp.setWidth("100%");
		
		String[] bits = null;
		if (HomeEnergyCalc.getFormat() == Format.COST) {
			bits = new String[]{"energy use costs",
					"your everyday energy costs",
					"it costs to use each appliance",
					"you pay",
					"consumption costs",
					"cost",
					"money you spent",
					"money you spent"};
		} else if (HomeEnergyCalc.getFormat() == Format.EMISSIONS) {
			bits = new String[]{"carbon footprint is",
					"CO<sub>2</sub> you generate every day",
					"CO<sub>2</sub> each appliance generates",
					"CO<sub>2</sub> you generate",
					"CO<sub>2</sub> emissions",
					"CO<sub>2</sub> emissions",
					"CO<sub>2</sub> emissions you generated",
					"CO<sub>2</sub> emissions you generated"};
		} else if (HomeEnergyCalc.getFormat() == Format.ENERGY) {
			bits = new String[]{"energy use is",
					"energy you consume every day",
					"energy each appliance uses",
					"you use",
					"consumption",
					"consumption",
					"energy you used",
					"energy you used"};
		}
		
		int i = 0;
		
		text = new HTML("<p>Hi!</p>" +
				"<p>Thank you for logging in to the Home Energy Calculator. Do you want to know what your daily " + bits[i++] + "?</p>" +
				"<p>With the Home Energy Calculator, you can:" +
					"<ul><li>calculate how much " + bits[i++] + "</li>" + 
					"<li>see how much " + bits[i++] + "</li>" +
					"<li>compare how much " + bits[i++] + " each day</li>" +
					"<li>see how you can cut your " + bits[i++] + "</li>" +
					"<li>set yourself " + bits[i++] + " targets and see how you can reach them</li></ul></p>");

		text.setStyleName("briefText");
		
		text2 = new HTML("<p>The Home Energy Calculator is very easy to use. " +
					"Just click on the appliances that you used yesterday and enter for how long (or how many times) you used them (NOTE: if yesterday was an unusual day for you then please think about the day before or a previous normal day for you). " +
					"How much " + bits[i++] + " will appear on the right hand side of the screen.</p>" + 
					"<p>When you finish, please press <span style='color: white; font-weight: bold; background: green; padding: 5px; -webkit-border-radius: 5px !important; -moz-border-radius: 5px !important;'>Calculate target</span></p>" +
					"<p>You will then see the total amount of " + bits[i++] + " at home in one day, as well as how it is distributed among activities.</p>");
		text2.setStyleName("briefText");
		
		setWidget(vp);
		
		center();
		
		updatePage();
	}
	
	private void updatePage () {
		vp.clear();
		if (page == 0) {
			vp.add(text);
			final Button cont = new Button("Continue");
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
	}
	
}
