package at.jojokobi.donatengine.presence;

public class GameJoinRequest {
	
	private String username;
	private String platform;
	
	
	public GameJoinRequest(String username, String platform) {
		super();
		this.username = username;
		this.platform = platform;
	}
	public String getUsername() {
		return username;
	}
	public String getPlatform() {
		return platform;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}

}
