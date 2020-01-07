package at.jojokobi.donatengine.presence;

public class GamePresence {
	
	private String state = null;
	private String details = "";
	/**
	 * Milliseconds
	 */
	private long startTimestamp;
	/**
	 * Milliseconds
	 */
	private long endTimestamp;
	private String largeImageKey;
	private String largeImageText;
	private String smallImageKey;
	private String smallImageText;
	private String partyId;
	private int partySize;
	private int partyMax;
	private String matchSecret;
	private String spectateSecret;
	private String joinSecret;
	private boolean instance;
	public String getState() {
		return state;
	}
	public String getDetails() {
		return details;
	}
	public long getStartTimestamp() {
		return startTimestamp;
	}
	public long getEndTimestamp() {
		return endTimestamp;
	}
	public String getLargeImageKey() {
		return largeImageKey;
	}
	public String getLargeImageText() {
		return largeImageText;
	}
	public String getSmallImageKey() {
		return smallImageKey;
	}
	public String getSmallImageText() {
		return smallImageText;
	}
	public String getPartyId() {
		return partyId;
	}
	public int getPartySize() {
		return partySize;
	}
	public int getPartyMax() {
		return partyMax;
	}
	public String getMatchSecret() {
		return matchSecret;
	}
	public String getSpectateSecret() {
		return spectateSecret;
	}
	public String getJoinSecret() {
		return joinSecret;
	}
	public boolean isInstance() {
		return instance;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public void setStartTimestamp(long startTimestamp) {
		this.startTimestamp = startTimestamp;
	}
	public void setEndTimestamp(long endTimestamp) {
		this.endTimestamp = endTimestamp;
	}
	public void setLargeImageKey(String largeImageKey) {
		this.largeImageKey = largeImageKey;
	}
	public void setLargeImageText(String largeImageText) {
		this.largeImageText = largeImageText;
	}
	public void setSmallImageKey(String smallImageKey) {
		this.smallImageKey = smallImageKey;
	}
	public void setSmallImageText(String smallImageText) {
		this.smallImageText = smallImageText;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public void setPartySize(int partySize) {
		this.partySize = partySize;
	}
	public void setPartyMax(int partyMax) {
		this.partyMax = partyMax;
	}
	public void setMatchSecret(String matchSecret) {
		this.matchSecret = matchSecret;
	}
	public void setSpectateSecret(String spectateSecret) {
		this.spectateSecret = spectateSecret;
	}
	public void setJoinSecret(String joinSecret) {
		this.joinSecret = joinSecret;
	}
	public void setInstance(boolean instance) {
		this.instance = instance;
	}

}
