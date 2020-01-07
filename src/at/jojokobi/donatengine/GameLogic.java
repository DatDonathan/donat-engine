package at.jojokobi.donatengine;

import java.util.function.Consumer;

import at.jojokobi.donatengine.audio.AudioSystemSupplier;
import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.presence.GamePresenceHandler;
import at.jojokobi.donatengine.ressources.IRessourceHandler;
import javafx.scene.canvas.GraphicsContext;

public interface GameLogic {
	
	public void start (Camera camera, Consumer<GameLogic> logicSwitcher, Input input, AudioSystemSupplier audioSystemSupplier, IRessourceHandler ressourceHandler, GamePresenceHandler gamePresenceHandler);

	public void update (double delta, Camera camera, Consumer<GameLogic> logicSwitcher, Input input, AudioSystemSupplier audioSystemSupplier, IRessourceHandler ressourceHandler, GamePresenceHandler gamePresenceHandler);
	
	public void render (GraphicsContext ctx, Camera camera, IRessourceHandler ressourceHandler);
	
	public void stop ();
	
	public void onStop ();
	
	public boolean isRunning ();
	
}
