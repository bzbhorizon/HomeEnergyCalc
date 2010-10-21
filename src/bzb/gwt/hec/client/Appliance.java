package bzb.gwt.hec.client;

public class Appliance {

	public static final int USE_SINGLE = 0;
	public static final int USE_TIMED = 1;
	public static final int USE_CONSTANT = 2;
	
	private String name;
	private int watts;
	private int use;//per use or per hour
	private int category;
	private int hours = 0;
	private int minutes = 0;
	private int uses = 0;
	private boolean constant = false;
	private boolean multiple;
	private int quantity = 0;
	private int standbyWatts;
	private boolean usesStandby;
	
	public Appliance (String name, int watts, int use, int category, boolean multiple, int standbyWatts) {
		setName(name);
		setUse(use);
		setWatts(watts);
		setCategory(category);
		setMultiple(multiple);
		setStandbyWatts(standbyWatts);
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param watts the watts to set
	 */
	public void setWatts(int watts) {
		this.watts = watts;
	}

	/**
	 * @return the watts
	 */
	public int getWatts() {
		return watts;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(int category) {
		this.category = category;
	}

	/**
	 * @return the category
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * @param hours the hours to set
	 */
	public void setHours(int hours) {
		this.hours = hours;
	}

	/**
	 * @return the hours
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * @param minutes the minutes to set
	 */
	public void setMinutes(int minutes) {
		if (this.getHours() < 24) {
			this.minutes = minutes;
		} else {
			this.minutes = 0;
		}
	}

	/**
	 * @return the minutes
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * @param singleUse the singleUse to set
	 */
	public void setUse(int use) {
		this.use = use;
	}

	/**
	 * @return the singleUse
	 */
	public int getUse() {
		return use;
	}

	/**
	 * @param uses the uses to set
	 */
	public void setUses(int uses) {
		this.uses = uses;
	}

	/**
	 * @return the uses
	 */
	public int getUses() {
		return uses;
	}

	/**
	 * @param constant the constant to set
	 */
	public void setConstant(boolean constant) {
		this.constant = constant;
	}

	/**
	 * @return the constant
	 */
	public boolean isConstant() {
		return constant;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param multiple the multiple to set
	 */
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	/**
	 * @return the multiple
	 */
	public boolean isMultiple() {
		return multiple;
	}

	/**
	 * @param standbyCapable the standbyCapable to set
	 */
	public void setStandbyWatts(int standbyWatts) {
		this.standbyWatts = standbyWatts;
	}

	/**
	 * @return the standbyCapable
	 */
	public int getStandbyWatts() {
		return standbyWatts;
	}

	/**
	 * @param usesStandby the usesStandby to set
	 */
	public void setUsesStandby(boolean usesStandby) {
		this.usesStandby = usesStandby;
	}

	/**
	 * @return the usesStandby
	 */
	public boolean isUsesStandby() {
		return usesStandby;
	}

	
}
