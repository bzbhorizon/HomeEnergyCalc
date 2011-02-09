package bzb.gwt.hec.client;

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
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TimingDialog extends DialogBox {

	private static String appName;
	private static ToggleButton source;
	
	public TimingDialog(ToggleButton source) {
		TimingDialog.source = source;
		TimingDialog.appName = source.getUpFace().getText();
		
		setGlassEnabled(true);
		
		VerticalPanel content = new VerticalPanel();
		content.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		final Button doneButton = new Button("Done");
		
		final TextBox quantity = new TextBox();
		if (HomeEnergyCalc.getAppliance(appName).isMultiple()) {
			if (HomeEnergyCalc.getAppliance(appName).getUse() == Appliance.USE_DISTANCE) {
				hp.add(new HTML("Distance:"));
			} else {
				hp.add(new HTML("Quantity:"));
			}
			quantity.setText("1");
			hp.add(quantity);
			if (HomeEnergyCalc.getAppliance(appName).getUse() == Appliance.USE_DISTANCE) {
				hp.add(new HTML("miles"));
			}
		}
		
		final CheckBox usesStandby = new CheckBox();
		usesStandby.setValue(false);
		
		if (HomeEnergyCalc.getAppliance(appName).getUse() == Appliance.USE_SINGLE) {
			final ListBox uses = new ListBox();
			for (int i = 0; i <= 20; i++) {
				uses.addItem(String.valueOf(i));
			}
			uses.setSelectedIndex(HomeEnergyCalc.getAppliance(appName).getUses());
			hp.add(uses);
			hp.add(new HTML("uses"));
			
			doneButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (uses.getSelectedIndex() > 0 || HomeEnergyCalc.getAppliance(TimingDialog.appName).isUsesStandby()) {
						if (HomeEnergyCalc.getAppliance(TimingDialog.appName).isMultiple()) {
							try {
								HomeEnergyCalc.getAppliance(TimingDialog.appName).setQuantity(Integer.parseInt(quantity.getValue()));
							} catch (Exception e) {
								
							}
						} else {
							HomeEnergyCalc.getAppliance(TimingDialog.appName).setQuantity(1);
						}
						HomeEnergyCalc.getAppliance(TimingDialog.appName).setUses(uses.getSelectedIndex());
						HomeEnergyCalc.getAppliance(TimingDialog.appName).setUsesStandby(usesStandby.getValue());
						ResultsPanel.order.add(TimingDialog.appName);
						WorkingPanel.updateResults();
					} else {
						TimingDialog.source.setDown(false);
						ResultsPanel.order.remove(TimingDialog.appName);
						WorkingPanel.updateResults();
					}
					TimingDialog.this.hide();
				}
			});
		} else if (HomeEnergyCalc.getAppliance(appName).getUse() == Appliance.USE_TIMED) {
			final ListBox hours = new ListBox();
			for (int i = 0; i <= 24; i++) {
				hours.addItem(String.valueOf(i));
			}
			hours.setSelectedIndex(HomeEnergyCalc.getAppliance(appName).getHours());
			hp.add(hours);
			hp.add(new HTML("hours"));
			final ListBox minutes = new ListBox();
			for (int i = 0; i < 60; i+=5) {
				minutes.addItem(String.valueOf(i));
			}
			minutes.setSelectedIndex(HomeEnergyCalc.getAppliance(appName).getMinutes());
			hp.add(minutes);
			hp.add(new HTML("minutes"));
			
			doneButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (hours.getSelectedIndex() > 0 || minutes.getSelectedIndex() > 0 || HomeEnergyCalc.getAppliance(TimingDialog.appName).isUsesStandby()) {
						if (HomeEnergyCalc.getAppliance(TimingDialog.appName).isMultiple()) {
							try {
								HomeEnergyCalc.getAppliance(TimingDialog.appName).setQuantity(Integer.parseInt(quantity.getValue()));
							} catch (Exception e) {
								
							}
						}
						HomeEnergyCalc.getAppliance(TimingDialog.appName).setHours(hours.getSelectedIndex());
						HomeEnergyCalc.getAppliance(TimingDialog.appName).setMinutes(minutes.getSelectedIndex());
						HomeEnergyCalc.getAppliance(TimingDialog.appName).setUsesStandby(usesStandby.getValue());
						ResultsPanel.order.add(TimingDialog.appName);
						WorkingPanel.updateResults();
					} else {
						TimingDialog.source.setDown(false);
						ResultsPanel.order.remove(TimingDialog.appName);
						WorkingPanel.updateResults();
					}
					TimingDialog.this.hide();
				}
			});
		} else if (HomeEnergyCalc.getAppliance(appName).getUse() == Appliance.USE_DISTANCE) {
			doneButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					try {
						HomeEnergyCalc.getAppliance(TimingDialog.appName).setQuantity(Integer.parseInt(quantity.getValue()));
					} catch (Exception e) {
						
					}
					if (HomeEnergyCalc.getAppliance(TimingDialog.appName).getQuantity() > 0) {
						ResultsPanel.order.add(TimingDialog.appName);
						WorkingPanel.updateResults();
					} else {
						TimingDialog.source.setDown(false);
						ResultsPanel.order.remove(TimingDialog.appName);
						WorkingPanel.updateResults();
					}
					TimingDialog.this.hide();
				}
			});
		} else if (HomeEnergyCalc.getAppliance(appName).getUse() == Appliance.USE_PROPS) {
			final ListBox uses = new ListBox();
			for (int i = 0; i <= 20; i++) {
				uses.addItem(String.valueOf(i));
			}
			uses.setSelectedIndex(HomeEnergyCalc.getAppliance(appName).getUses());
			hp.add(uses);
			hp.add(new HTML("uses; "));
			
			hp.add(new HTML("proportion filled:"));
			final ListBox props = new ListBox();
			for (int i = 0; i <= 100; i += 25) {
				props.addItem(String.valueOf(i));
			}
			props.setSelectedIndex((int) (HomeEnergyCalc.getAppliance(appName).getProps() / 25.0));
			hp.add(props);
			hp.add(new HTML("%"));
			
			doneButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (uses.getSelectedIndex() > 0) {
						if (HomeEnergyCalc.getAppliance(TimingDialog.appName).isMultiple()) {
							try {
								HomeEnergyCalc.getAppliance(TimingDialog.appName).setQuantity(Integer.parseInt(quantity.getValue()));
							} catch (Exception e) {
								
							}
						} else {
							HomeEnergyCalc.getAppliance(TimingDialog.appName).setQuantity(1);
						}
						HomeEnergyCalc.getAppliance(TimingDialog.appName).setProps(props.getSelectedIndex() * 25);
						HomeEnergyCalc.getAppliance(TimingDialog.appName).setUses(uses.getSelectedIndex());
						ResultsPanel.order.add(TimingDialog.appName);
						WorkingPanel.updateResults();
					} else {
						TimingDialog.source.setDown(false);
						ResultsPanel.order.remove(TimingDialog.appName);
						WorkingPanel.updateResults();
					}
					TimingDialog.this.hide();
				}
			});
		} else if (HomeEnergyCalc.getAppliance(appName).getUse() == Appliance.USE_TEMPS) {
			final ListBox uses = new ListBox();
			for (int i = 0; i <= 20; i++) {
				uses.addItem(String.valueOf(i));
			}
			uses.setSelectedIndex(HomeEnergyCalc.getAppliance(appName).getUses());
			hp.add(uses);
			hp.add(new HTML("uses; "));
			
			hp.add(new HTML("temperature: approx."));
			final ListBox props = new ListBox();
			for (int i = 25; i <= 100; i += 25) {
				props.addItem(String.valueOf(i));
			}
			props.setSelectedIndex((int) (HomeEnergyCalc.getAppliance(appName).getProps() / 25.0) - 1);
			hp.add(props);
			hp.add(new HTML("degrees"));
			
			doneButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (uses.getSelectedIndex() > 0) {
						if (HomeEnergyCalc.getAppliance(TimingDialog.appName).isMultiple()) {
							try {
								HomeEnergyCalc.getAppliance(TimingDialog.appName).setQuantity(Integer.parseInt(quantity.getValue()));
							} catch (Exception e) {
								
							}
						} else {
							HomeEnergyCalc.getAppliance(TimingDialog.appName).setQuantity(1);
						}
						HomeEnergyCalc.getAppliance(TimingDialog.appName).setTemps((props.getSelectedIndex() + 1) * 25);
						HomeEnergyCalc.getAppliance(TimingDialog.appName).setUses(uses.getSelectedIndex());
						ResultsPanel.order.add(TimingDialog.appName);
						WorkingPanel.updateResults();
					} else {
						TimingDialog.source.setDown(false);
						ResultsPanel.order.remove(TimingDialog.appName);
						WorkingPanel.updateResults();
					}
					TimingDialog.this.hide();
				}
			});
		}
		
		/*
		if (HomeEnergyCalc.getAppliance(appName).getStandbyWatts() > 0) {
			hp.add(new HTML("Left on standby?"));
			hp.add(usesStandby);
		}*/
		
		content.add(hp);
		content.add(doneButton);
		setText("Today's use");
		setWidget(content);
		
		center();
	}

}

	