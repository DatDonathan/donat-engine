package at.jojokobi.donatengine;

import java.util.List;
import java.util.function.Consumer;

import at.jojokobi.donatengine.audio.AudioSystemSupplier;
import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.presence.GamePresenceHandler;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.ressources.IRessourceHandler;

public interface GameLogic {
	
	public void start (Camera camera, Consumer<GameLogic> logicSwitcher, Input input, AudioSystemSupplier audioSystemSupplier, IRessourceHandler ressourceHandler, GamePresenceHandler gamePresenceHandler);

	public void update (double delta, Camera camera, Consumer<GameLogic> logicSwitcher, Input input, AudioSystemSupplier audioSystemSupplier, IRessourceHandler ressourceHandler, GamePresenceHandler gamePresenceHandler);
	
	public void render (List<RenderData> data, Camera camera, IRessourceHandler ressourceHandler);
	
	public void stop ();
	
	public void onStop ();
	
	public boolean isRunning ();
	
}
