package bzb.gwt.hec.client;

import bzb.gwt.hec.client.appliances.Appliance;
import bzb.gwt.hec.client.appliances.PerUseAppliance;
import bzb.gwt.hec.client.appliances.ProportionAppliance;
import bzb.gwt.hec.client.appliances.TemperatureAppliance;
import bzb.gwt.hec.client.appliances.TimedAppliance;
import bzb.gwt.hec.client.appliances.TravelMode;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DetailsDialog extends DialogBox {
	
	private Appliance app;
	private ApplianceFancyButton fancyButton;
	private Button doneButton;
	private ListBox quantity;
	private TextBox distance;
	private CheckBox usesStandby;
	private ListBox uses;
	private ListBox hours;
	private ListBox minutes;
	private ListBox temps;
	private ListBox props;
	
	public DetailsDialog(Appliance app, final ApplianceFancyButton fancyButton) {
		this.app = app;
		this.fancyButton = fancyButton;
		
		setGlassEnabled(true);
		
		VerticalPanel content = new VerticalPanel();
		content.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		doneButton = new Button("Done");
		
		content.add(getQuantityField());
		content.add(getDistanceField());
		//content.add(getStandbyField());
		content.add(getNumberOfUsesField());
		content.add(getTimeUsedField());
		content.add(getProportionFilledField());
		content.add(getTemperatureField());
		content.add(getDoneButton());
		
		setText("Today's use");
		setWidget(content);
		
		center();
	}
	
	private HorizontalPanel getQuantityField () {
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		if (app.isMultiple() && !app.getClass().equals(TravelMode.class)) {
			hp.add(new HTML("Quantity:"));
			
			quantity = new ListBox();
			for (int i = 1; i <= 40; i++) {
				quantity.addItem(String.valueOf(i));
			}
			quantity.setSelectedIndex(app.getQuantity() - 1);
			hp.add(quantity);
		}
		
		return hp;
	}
	
	private HorizontalPanel getDistanceField () {
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		if (app.isMultiple() && app.getClass().equals(TravelMode.class)) {
			hp.add(new HTML("Distance:"));
			
			distance = new TextBox(); // change to listbox
			if (app.getQuantity() > 0) {
				distance.setText(String.valueOf(app.getQuantity()));
			} else {
				distance.setText("0");
			}
			hp.add(distance);

			hp.add(new HTML("miles"));
		}
		
		return hp;
	}
	
	private HorizontalPanel getStandbyField () {
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		if (app.getStandbyWatts() > 0) {
			hp.add(new HTML("Left on standby?"));
			
			usesStandby = new CheckBox();
			usesStandby.setValue(app.isUsesStandby());
			hp.add(usesStandby);
		}
		
		return hp;
	}

	private HorizontalPanel getNumberOfUsesField () {
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		if (app.getClass().equals(PerUseAppliance.class) || app.getClass().getSuperclass().equals(PerUseAppliance.class)) {
			uses = new ListBox();
			for (int i = 1; i <= 20; i++) {
				uses.addItem(String.valueOf(i));
			}
			uses.setSelectedIndex(((PerUseAppliance)app).getUses() - 1);
			hp.add(uses);
			hp.add(new HTML("uses"));
		}
		
		return hp;
	}
	
	private HorizontalPanel getTimeUsedField () {
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		if (app.getClass().equals(TimedAppliance.class)) {
			hours = new ListBox();
			for (int i = 0; i <= 24; i++) {
				hours.addItem(String.valueOf(i));
			}
			hours.setSelectedIndex(app.getHours());
			hp.add(hours);
			hp.add(new HTML("hours"));
			
			minutes = new ListBox();
			for (int i = 0; i < 60; i+=5) {
				minutes.addItem(String.valueOf(i));
			}
			minutes.setSelectedIndex(app.getMinutes());
			hp.add(minutes);
			hp.add(new HTML("minutes"));
		}
		
		return hp;
	}
	
	private HorizontalPanel getProportionFilledField () {
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		if (app.getClass().equals(ProportionAppliance.class)) {
			hp.add(new HTML("Proportion filled:"));
			props = new ListBox();
			for (int i = 25; i <= 100; i += 25) {
				props.addItem(String.valueOf(i));
			}
			props.setSelectedIndex((int) (app.getProps() / 25.0) - 1);
			hp.add(props);
			hp.add(new HTML("%"));
		}
		
		return hp;
	}
	
	private HorizontalPanel getTemperatureField () {
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		if (app.getClass().equals(TemperatureAppliance.class)) {
			hp.add(new HTML("Temperature: approx."));
			temps = new ListBox();
			for (int i = 25; i <= 100; i += 25) {
				temps.addItem(String.valueOf(i));
			}
			temps.setSelectedIndex((int) (app.getProps() / 25.0) - 1);
			hp.add(temps);
			hp.add(new HTML("degrees"));
		}
			
		return hp;
	}
	
	private HorizontalPanel getDoneButton () {
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		doneButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (app.isMultiple()) {
					try {
						if (app.getClass().equals(TravelMode.class)) {
							app.setQuantity(Integer.parseInt(distance.getValue()));
						} else {
							app.setQuantity(quantity.getSelectedIndex() + 1);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					app.setQuantity(1);
				}
				
				if ((app.getQuantity() == 0) ||
						(app.getClass().equals(TimedAppliance.class) && (hours.getSelectedIndex() == 0 && minutes.getSelectedIndex() == 0 && !app.isUsesStandby()))) {
					app.reset();
					fancyButton.setRemovedButtons();
					ResultsPanel.order.remove(app.getName());
					WorkingPanel.updateResults();
				} else {
					if (app.getStandbyWatts() > 0 && usesStandby != null) {
						app.setUsesStandby(usesStandby.getValue());
					}
					
					if (app.getClass().equals(PerUseAppliance.class) || app.getClass().getSuperclass().equals(PerUseAppliance.class)) {
						((PerUseAppliance)app).setUses(uses.getSelectedIndex() + 1);
					}
					
					if (app.getClass().equals(TimedAppliance.class)) {
						app.setHours(hours.getSelectedIndex());
						app.setMinutes(minutes.getSelectedIndex());
					}
					
					if (app.getClass().equals(TemperatureAppliance.class)) {
						app.setTemps((temps.getSelectedIndex() + 1) * 25);
					}
					
					if (app.getClass().equals(ProportionAppliance.class)) {
						app.setProps((props.getSelectedIndex() + 1) * 25);
					}
					
					ResultsPanel.order.add(app.getName());
					WorkingPanel.updateResults();
				}
				
				DetailsDialog.this.hide();
			}
		});
		
		hp.add(doneButton);
		
		return hp;
	}
	
}

	