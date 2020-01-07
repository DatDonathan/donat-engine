package at.jojokobi.donatengine.presence;

public interface GamePresencePlatform {
	
	public void init ();
	
	public void updatePresence (GamePresence presence);
	
	public void end ();

}
