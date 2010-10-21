package bzb.gwt.hec.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TimingDialog extends DialogBox {

	private HomeEnergyCalc home;
	private String appName;
	
	public TimingDialog(HomeEnergyCalc home, String appName) {
		this.home = home;
		this.appName = appName;
		
		VerticalPanel content = new VerticalPanel();
		content.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		final Button doneButton = new Button("Done");
		
		hp.add(new HTML("Quantity:"));
		final TextBox quantity = new TextBox();
		hp.add(quantity);
		
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
					try {
						TimingDialog.this.home.getAppliance(TimingDialog.this.appName).setQuantity(Integer.parseInt(quantity.getValue()));
					} catch (Exception e) {
						
					}
					TimingDialog.this.home.getAppliance(TimingDialog.this.appName).setUses(uses.getSelectedIndex());
					TimingDialog.this.home.updateResults();
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
					TimingDialog.this.home.getAppliance(TimingDialog.this.appName).setHours(hours.getSelectedIndex());
					TimingDialog.this.home.getAppliance(TimingDialog.this.appName).setMinutes(minutes.getSelectedIndex());
					TimingDialog.this.home.updateResults();
					TimingDialog.this.hide();
				}
			});
		}
		
		content.add(hp);
		content.add(doneButton);
		setText("Today's use");
		setWidget(content);
		
		center();
	}

}
