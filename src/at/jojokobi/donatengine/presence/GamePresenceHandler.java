package at.jojokobi.donatengine.presence;

import java.util.ArrayList;
import java.util.List;

public class GamePresenceHandler {
	
	private List<GamePresencePlatform> platforms = new ArrayList<>();

	
	public void addPlatform (GamePresencePlatform platform) {
		platforms.add(platform);
	}
	
	public void init () {
		platforms.forEach(p -> p.init());
	}
	
	public void updatePresence (GamePresence presence) {
		platforms.forEach(p -> p.updatePresence(presence));
	}
	
	public void end () {
		platforms.forEach(p -> p.end());
	}
	
}
