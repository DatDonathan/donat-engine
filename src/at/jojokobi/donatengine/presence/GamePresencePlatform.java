package at.jojokobi.donatengine.presence;

public interface GamePresencePlatform {
	
	public void init ();
	
	public void updatePresence (GamePresence presence);
	
	public void setListeners (JoinListener join, JoinRequestListener joinRequest);
	
	public void end ();

}
