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
	private String email;
	
	@Persistent
	private String units;
	
	@Persistent
	private String response;
	
	@Persistent
	private long duration;
	
	@Persistent
	private long timestamp = System.currentTimeMillis();
	
	@Persistent
	private double target;
	
	@Persistent
	private String ip;
	
	@Persistent
	private String feedback;
	
	public Response () {}
	
	public Response (String email, String units, String response, long duration, double target, String ip, String feedback) {
		setEmail(email);
		setUnits(units);
		setResponse(response);
		setDuration(duration);
		setTarget(target);
		setIp(ip);
		setFeedback(feedback);
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getUnits() {
		return units;
	}

	public void setResponse(String response) {
		response = response.trim().replace("\n", "; ");
		response = response.trim().replace("\r", "; ");
		if (response.length() > 500) {
			System.out.println("Response larger than can handle: " + response);
			response = response.substring(0, 499);
		}
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

	public void setTarget(double target) {
		this.target = target;
	}

	public double getTarget() {
		return target;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public void setFeedback(String feedback) {
		feedback = feedback.trim().replace("\n", "; ");
		feedback = feedback.trim().replace("\r", "; ");
		if (feedback.length() > 500) {
			System.out.println("Feedback larger than can handle: " + feedback);
			feedback = feedback.substring(0, 499);
		}
		this.feedback = feedback;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setEmail(String email) {
		email = email.trim();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

}
