package at.jojokobi.donatengine;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import at.jojokobi.donatengine.event.StartEvent;
import at.jojokobi.donatengine.event.StopEvent;
import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.net.ServerPacket;
import at.jojokobi.donatengine.rendering.RenderScene;
import at.jojokobi.donatengine.serialization.BinarySerializable;
import at.jojokobi.donatengine.serialization.SerializationWrapper;
import at.jojokobi.netutil.client.Client;

public class ClientGameLogic implements GameLogic{
	
	private Level level;
	private Client client;
	
	private DataInputStream data;
	private DataOutputStream dataOut;

	public ClientGameLogic(Level level, Client client) {
		super();
		this.level = level;
		this.client = client;
	}

	@Override
	public void start(Game game) {
		client.start ();
		data = new DataInputStream(client.getInputStream());
		dataOut = new DataOutputStream(client.getOutputStream());
		
		level.setClientId(client.getClientId());
		level.clear();
		level.start(new StartEvent(c -> game.getLocalInput(), game));
		game.getGameView().bind(level.getTileSystem());
	}

	@Override
	public void update(double delta, Game game) {
		level.update(new UpdateEvent(delta, c -> game.getLocalInput(), game));
		
		//Read Data
		SerializationWrapper serialization = game.getSerialization();
		try {
			while (data.available() > 0) {
				ServerPacket packet = serialization.deserialize(ServerPacket.class, data);
				synchronized (ClientGameLogic.this) {
					packet.apply(level, game, serialization);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		//Send packets
		for (BinarySerializable packet : level.getBehavior().fetchPackets()) {
			try {
				serialization.serialize(packet, dataOut);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		try {
			dataOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void stop(Game game) {
		level.stop(new StopEvent(c -> game.getLocalInput(), game));
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(RenderScene scene) {
		level.render(scene.getData(), false);
		scene.setCamera(level.getCamera());
		scene.setTileSystem(scene.getTileSystem());
	}

}
