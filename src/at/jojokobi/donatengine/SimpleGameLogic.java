package at.jojokobi.donatengine;

import java.util.List;
import java.util.function.Consumer;

import at.jojokobi.donatengine.audio.AudioSystem;
import at.jojokobi.donatengine.audio.AudioSystemSupplier;
import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.input.InputHandler;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.presence.GamePresenceHandler;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.ressources.IRessourceHandler;
import at.jojokobi.donatengine.serialization.BinarySerialization;
import at.jojokobi.donatengine.serialization.BinarySerializationWrapper;
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class SimpleGameLogic implements GameLogic{
	
	private Level level;
	private SerializationWrapper serializationWrapper = new BinarySerializationWrapper(BinarySerialization.getInstance().getIdClassFactory());

	public SimpleGameLogic(Level level) {
		super();
		this.level = level;
	}

	@Override
	public void start(InputHandler input, Game game) {
		level.clear();
		level.start(camera, new LevelHandler() {
			
			@Override
			public AudioSystem getAudioSystem(long clientId) {
				return audioSystemSupplier.getAudioSystem(clientId);
			}
			
			@Override
			public Input getInput(long clientId) {
				return input;
			}
			
			@Override
			public void changeLogic(GameLogic logic) {
				logicSwitcher.accept(logic);
			}

			@Override
			public IRessourceHandler getRessourceHandler() {
				return ressourceHandler;
			}
			
			@Override
			public void stop() {
				SimpleGameLogic.this.stop();
			}

			@Override
			public SerializationWrapper getSerialization() {
				return serializationWrapper;
			}
			
			@Override
			public GamePresenceHandler getGamePresenceHandler() {
				return gamePresenceHandler;
			}
		});
	}

	@Override
	public void update(double delta, InputHandler input, Game game) {
		level.update(delta, new LevelHandler() {
			
			@Override
			public AudioSystem getAudioSystem(long clientId) {
				return audioSystemSupplier.getAudioSystem(clientId);
			}
			
			@Override
			public Input getInput(long clientId) {
				return input;
			}
			
			@Override
			public void changeLogic(GameLogic logic) {
				logicSwitcher.accept(logic);
			}

			@Override
			public IRessourceHandler getRessourceHandler() {
				return ressourceHandler;
			}
			
			@Override
			public void stop() {
				SimpleGameLogic.this.stop();
			}

			@Override
			public SerializationWrapper getSerialization() {
				return serializationWrapper;
			}
			
			@Override
			public GamePresenceHandler getGamePresenceHandler() {
				return gamePresenceHandler;
			}
		}, camera);
	}
	
	@Override
	public void stop(InputHandler input, Game game) {
		level.end();
	}
	
	@Override
	public void render(List<RenderData> data) {
		level.render(data, camera, false);
	}
	
}
