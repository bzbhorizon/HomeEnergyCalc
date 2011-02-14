package bzb.gwt.hec.server;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Response {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "false")
	private String key;
	
	@Persistent
	private String units;
	
	@Persistent
	private String response;
	
	@Persistent
	private long duration;
	
	@Persistent
	private long timestamp = System.currentTimeMillis();
	
	public Response () {}
	
	public Response (String units, String response, long duration) {
		setUnits(units);
		setResponse(response);
		setDuration(duration);
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getUnits() {
		return units;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getResponse() {
		return response;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getKey() {
		return key;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getDuration() {
		return duration;
	}

}