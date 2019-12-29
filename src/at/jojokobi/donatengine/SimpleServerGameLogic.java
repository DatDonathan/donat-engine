package at.jojokobi.donatengine;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import at.jojokobi.donatengine.audio.AudioSystem;
import at.jojokobi.donatengine.audio.AudioSystemSupplier;
import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.input.InputSupplier;
import at.jojokobi.donatengine.input.SimpleInput;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.net.ClientPacket;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.ressources.IRessourceHandler;
import at.jojokobi.donatengine.serialization.BinarySerializable;
import at.jojokobi.donatengine.serialization.BinarySerialization;
import at.jojokobi.netutil.server.Server;
import at.jojokobi.netutil.server.ServerController;
import javafx.scene.canvas.GraphicsContext;

public class SimpleServerGameLogic implements GameLogic {

	private Level level;
	private boolean running = true;
	private Server server;
	private Map<Long, Input> inputs = new HashMap<>();

	public SimpleServerGameLogic(Level level, Server server) {
		super();
		this.level = level;
		this.server = server;
	}

	@Override
	public void start(Camera camera) {
		server.setController(new ServerController() {
			@Override
			public void onConnect(long client, OutputStream out) throws IOException {
//				List<BinarySerializable> packets = level.getBehavior().recreateLevelPackets(level);
//				DataOutputStream data = new DataOutputStream(out);
//				for (BinarySerializable packet : packets) {
//					BinarySerialization.getInstance().serialize(packet, data);
//				}
//				data.flush();
			}

			@Override
			public void listenTo(long client, InputStream in) {

			}
		});
		server.start();

		level.clear();
		level.start(camera);
	}

	@Override
	public void update(double delta, Camera camera, Consumer<GameLogic> logicSwitcher, Input input,
			AudioSystemSupplier audioSystemSupplier, IRessourceHandler ressourceHandler) {
		LevelHandler handler = new LevelHandler() {

			@Override
			public AudioSystem getAudioSystem(long clientId) {
				return audioSystemSupplier.getAudioSystem(clientId);
			}

			@Override
			public Input getInput(long clientId) {
				return clientId == InputSupplier.SCENE_INPUT ? input : inputs.get(clientId);
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
				SimpleServerGameLogic.this.stop();
			}

		};
		level.update(delta, handler, camera);
		// Update
//		server.update();
//		for (long client : server.fetchRemovedClients()) {
//			level.disconnectPlayer(client);
//		}
		// New Clients
		List<Long> newClients = server.fetchNewClients();
		//Inputs and players
		for (long client : newClients) {
			inputs.put(client, new SimpleInput());
			level.spawnPlayer(client, camera);
		}
		// Recieve packets
		for (long client : server.getClients()) {
			DataInputStream data = new DataInputStream(server.getInputStream(client));
			try {
				while (data.available() > 0) {
					ClientPacket packet = BinarySerialization.getInstance().deserialize(ClientPacket.class, data);
					packet.apply(level, handler, client);
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		// Send packets
		try (DataOutputStream out = new DataOutputStream(server.getBroadcastOutputStream())) {
			for (BinarySerializable packet : level.getBehavior().fetchPackets()) {
				BinarySerialization.getInstance().serialize(packet, out);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		// New clients
		for (long client : newClients) {
			List<BinarySerializable> packets = level.getBehavior().recreateLevelPackets(level);
			try (DataOutputStream data = new DataOutputStream(server.getOutputStream(client))) {
				for (BinarySerializable packet : packets) {
					System.out.println(packet);
					BinarySerialization.getInstance().serialize(packet, data);
				}
				System.out.println("Sent " + packets.size() + " in total!");
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void onStop() {
		level.end();
		running = false;
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		running = false;
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public void render(GraphicsContext ctx, Camera camera, IRessourceHandler ressourceHandler) {
		level.render(ctx, camera, ressourceHandler, false);
	}

}
