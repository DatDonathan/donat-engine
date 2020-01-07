package at.jojokobi.donatengine.level;

import at.jojokobi.donatengine.GameLogic;
import at.jojokobi.donatengine.audio.AudioSystemSupplier;
import at.jojokobi.donatengine.input.InputSupplier;
import at.jojokobi.donatengine.presence.GamePresenceHandler;
import at.jojokobi.donatengine.ressources.IRessourceHandler;
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public interface LevelHandler extends InputSupplier, AudioSystemSupplier{

	public void changeLogic (GameLogic level);
	
	public void stop ();
	
	public IRessourceHandler getRessourceHandler ();
	
	public SerializationWrapper getSerialization ();
	
	public GamePresenceHandler getGamePresenceHandler ();
	
}
