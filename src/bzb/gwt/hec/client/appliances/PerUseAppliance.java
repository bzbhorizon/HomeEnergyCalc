package bzb.gwt.hec.client.appliances;

public class PerUseAppliance extends Appliance {
	
	private int uses = 0;

	public PerUseAppliance(String name, String iconURL, int watts,
			int category, boolean multiple, int standbyWatts) {
		super(name, iconURL, watts, category, multiple, standbyWatts);
	}

	public void setUses(int uses) {
		this.uses = uses;
	}

	public int getUses() {
		return uses;
	}
	
	public void reset () {
		super.reset();
		setUses(0);
	}

}
