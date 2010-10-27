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

	private HomeEnergyCalc home;
	private String appName;
	private ToggleButton source;
	
	public TimingDialog(HomeEnergyCalc home, ToggleButton source) {
		this.home = home;
		this.source = source;
		this.appName = source.getUpFace().getText();
		
		VerticalPanel content = new VerticalPanel();
		content.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		final Button doneButton = new Button("Done");
		
		final TextBox quantity = new TextBox();
		if (this.home.getAppliance(appName).isMultiple()) {
			if (this.home.getAppliance(appName).getUse() == Appliance.USE_DISTANCE) {
				hp.add(new HTML("Distance:"));
			} else {
				hp.add(new HTML("Quantity:"));
			}
			quantity.setText("1");
			hp.add(quantity);
			if (this.home.getAppliance(appName).getUse() == Appliance.USE_DISTANCE) {
				hp.add(new HTML("miles"));
			}
		}
		
		final CheckBox usesStandby = new CheckBox();
		usesStandby.setValue(false);
		
		if (this.home.getAppliance(appName).getUse() == Appliance.USE_SINGLE) {
			final ListBox uses = new ListBox();
			for (int i = 0; i <= 20; i++) {
				uses.addItem(String.valueOf(i));
			}
			uses.setSelectedIndex(this.home.getAppliance(appName).getUses());
			hp.add(uses);
			hp.add(new HTML("uses"));
			
			doneButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (uses.getSelectedIndex() > 0 || TimingDialog.this.home.getAppliance(TimingDialog.this.appName).isUsesStandby()) {
						if (TimingDialog.this.home.getAppliance(TimingDialog.this.appName).isMultiple()) {
							try {
								TimingDialog.this.home.getAppliance(TimingDialog.this.appName).setQuantity(Integer.parseInt(quantity.getValue()));
							} catch (Exception e) {
								
							}
						} else {
							TimingDialog.this.home.getAppliance(TimingDialog.this.appName).setQuantity(1);
						}
						TimingDialog.this.home.getAppliance(TimingDialog.this.appName).setUses(uses.getSelectedIndex());
						TimingDialog.this.home.getAppliance(TimingDialog.this.appName).setUsesStandby(usesStandby.getValue());
						TimingDialog.this.home.updateResults();
					} else {
						TimingDialog.this.source.setDown(false);
						TimingDialog.this.home.updateResults();
					}
					TimingDialog.this.hide();
				}
			});
		} else if (this.home.getAppliance(appName).getUse() == Appliance.USE_TIMED) {
			final ListBox hours = new ListBox();
			for (int i = 0; i <= 24; i++) {
				hours.addItem(String.valueOf(i));
			}
			hours.setSelectedIndex(this.home.getAppliance(appName).getHours());
			hp.add(hours);
			hp.add(new HTML("hours"));
			final ListBox minutes = new ListBox();
			for (int i = 0; i < 60; i+=5) {
				minutes.addItem(String.valueOf(i));
			}
			minutes.setSelectedIndex(this.home.getAppliance(appName).getMinutes());
			hp.add(minutes);
			hp.add(new HTML("minutes"));
			
			doneButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (hours.getSelectedIndex() > 0 || minutes.getSelectedIndex() > 0 || TimingDialog.this.home.getAppliance(TimingDialog.this.appName).isUsesStandby()) {
						if (TimingDialog.this.home.getAppliance(TimingDialog.this.appName).isMultiple()) {
							try {
								TimingDialog.this.home.getAppliance(TimingDialog.this.appName).setQuantity(Integer.parseInt(quantity.getValue()));
							} catch (Exception e) {
								
							}
						}
						TimingDialog.this.home.getAppliance(TimingDialog.this.appName).setHours(hours.getSelectedIndex());
						TimingDialog.this.home.getAppliance(TimingDialog.this.appName).setMinutes(minutes.getSelectedIndex());
						TimingDialog.this.home.getAppliance(TimingDialog.this.appName).setUsesStandby(usesStandby.getValue());
						TimingDialog.this.home.updateResults();
					} else {
						TimingDialog.this.source.setDown(false);
					}
					TimingDialog.this.hide();
				}
			});
		} else if (this.home.getAppliance(appName).getUse() == Appliance.USE_DISTANCE) {
			doneButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					try {
						TimingDialog.this.home.getAppliance(TimingDialog.this.appName).setQuantity(Integer.parseInt(quantity.getValue()));
					} catch (Exception e) {
						
					}
					if (TimingDialog.this.home.getAppliance(TimingDialog.this.appName).getQuantity() > 0) {
						TimingDialog.this.home.updateResults();
					} else {
						TimingDialog.this.source.setDown(false);
					}
					TimingDialog.this.hide();
				}
			});
		}
		
		if (this.home.getAppliance(appName).getStandbyWatts() > 0) {
			hp.add(new HTML("Left on standby?"));
			hp.add(usesStandby);
		}
		
		content.add(hp);
		content.add(doneButton);
		setText("Today's use");
		setWidget(content);
		
		center();
	}

}

	