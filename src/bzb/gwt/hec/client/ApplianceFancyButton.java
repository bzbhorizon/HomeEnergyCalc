package bzb.gwt.hec.client;

import bzb.gwt.hec.client.appliances.Appliance;
import bzb.gwt.hec.client.appliances.ConstantAppliance;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ApplianceFancyButton extends VerticalPanel {

	private String appName;
	private Button add;
	private Button edit;
	private Button remove;

	public ApplianceFancyButton (final Appliance app) {
		setAppName(app.getName());
		addStyleName("applianceFancyButton");
		setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		HTML face;
		if (app.getIconURL() != null) {
			face = new HTML(app.getName() + "<br /><img src='" + app.getIconURL() + "' />");
		} else {
			face = new HTML(app.getName());
		}
		face.setStyleName("applianceFancyButtonText");
		add(face);
		
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		add = new Button("Add");
		add.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				app.setQuantity(1);
				if (app.getClass().equals(ConstantAppliance.class)) {
					add.setEnabled(false);
					remove.setEnabled(true);
					app.setConstant(true);
					ResultsPanel.order.add(app.getName());
					WorkingPanel.updateResults();
				} else {
					setAddedButtons();
					AppliancePanels.setTd(new DetailsDialog(app, ApplianceFancyButton.this));
				}
			}
		});
		add.setStyleName("applianceFancyButtonButton");
		buttonPanel.add(add);
		
		edit = new Button("Edit");
		edit.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ResultsPanel.order.remove(app.getName());
				AppliancePanels.setTd(new DetailsDialog(app, ApplianceFancyButton.this));
			}
		});
		edit.setStyleName("applianceFancyButtonButton");
		buttonPanel.add(edit);
		edit.setEnabled(false);
		edit.setVisible(false);
		
		remove = new Button("Remove");
		remove.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				setRemovedButtons();
				app.reset();
		    	ResultsPanel.order.remove(app.getName());
		    	WorkingPanel.updateResults();
			}
		});
		remove.setStyleName("applianceFancyButtonButton");
		buttonPanel.add(remove);
		add(buttonPanel);
		remove.setEnabled(false);
	}
	
	public void setAddedButtons () {
		add.setEnabled(false);
		add.setVisible(false);
		edit.setEnabled(true);
		edit.setVisible(true);
		remove.setEnabled(true);
		addStyleName("applianceFancyButtonPressed");
	}
	
	public void setRemovedButtons () {
		add.setEnabled(true);
		add.setVisible(true);
		edit.setEnabled(false);
		edit.setVisible(false);
		remove.setEnabled(false);
		removeStyleName("applianceFancyButtonPressed");
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppName() {
		return appName;
	}
	
	public Button getAdd() {
		return add;
	}

	public Button getRemove() {
		return remove;
	}
	
}
