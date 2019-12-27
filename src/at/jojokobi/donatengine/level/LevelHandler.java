package at.jojokobi.donatengine.level;

import at.jojokobi.donatengine.GameLogic;
import at.jojokobi.donatengine.audio.AudioSystemSupplier;
import at.jojokobi.donatengine.input.InputSupplier;
import at.jojokobi.donatengine.ressources.IRessourceHandler;

public interface LevelHandler extends InputSupplier, AudioSystemSupplier{

	public void changeLogic (GameLogic level);
	
	public void stop ();
	
	public IRessourceHandler getRessourceHandler ();
	
}
