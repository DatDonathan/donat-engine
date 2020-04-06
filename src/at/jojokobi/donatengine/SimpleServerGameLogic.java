package at.jojokobi.donatengine;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.jojokobi.donatengine.event.StartEvent;
import at.jojokobi.donatengine.event.StopEvent;
import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.input.InputHandler;
import at.jojokobi.donatengine.input.MapInputHandler;
import at.jojokobi.donatengine.input.SimpleInput;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.net.ClientPacket;
import at.jojokobi.donatengine.rendering.RenderScene;
import at.jojokobi.donatengine.serialization.SerializationWrapper;
import at.jojokobi.donatengine.serialization.binary.BinarySerializable;
import at.jojokobi.netutil.server.Server;

public class SimpleServerGameLogic implements GameLogic {

	private Level level;
	private Server server;
	private Map<Long, Input> inputs = new HashMap<>();

	public SimpleServerGameLogic(Level level, Server server) {
		super();
		this.level = level;
		this.server = server;
	}

	@Override
	public void start(Game game) {
		server.start();

		level.clear();
		level.start(new StartEvent(new MapInputHandler(game.getLocalInput(), inputs), game));
		game.getGameView().bind(level.getTileSystem());
	}

	@Override
	public void update(double delta, Game game) {
		InputHandler input = new MapInputHandler(game.getLocalInput(), inputs);
		level.update(new UpdateEvent(delta, input, game));
		// Update
//		server.update();
//		for (long client : server.fetchRemovedClients()) {
//			level.disconnectPlayer(client);
//		}
		SerializationWrapper serialization = game.getSerialization();
		// New Clients
		List<Long> newClients = server.fetchNewClients();
		//Inputs and players
		for (long client : newClients) {
			inputs.put(client, new SimpleInput());
			level.spawnPlayer(client);
		}
		// Recieve packets
		for (long client : server.getClients()) {
			DataInputStream data = new DataInputStream(server.getInputStream(client));
			try {
				while (data.available() > 0) {
					ClientPacket packet = serialization.deserialize(ClientPacket.class, data);
					packet.apply(level, game, input, client);
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		// Send packets
		try (DataOutputStream out = new DataOutputStream(server.getBroadcastOutputStream())) {
			for (BinarySerializable packet : level.getBehavior().fetchPackets()) {
				serialization.serialize(packet, out);
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
					serialization.serialize(packet, data);
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void stop(Game game) {
		level.stop(new StopEvent(new MapInputHandler(game.getLocalInput(), inputs), game));
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(RenderScene scene) {
		level.render(scene.getData(), false);
		scene.setCamera(level.getCamera());
		scene.setTileSystem(level.getTileSystem());
		scene.setAnimationTimer(level.getTimer());
	}

}
