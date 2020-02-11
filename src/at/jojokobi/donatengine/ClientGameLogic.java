package at.jojokobi.donatengine;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

import at.jojokobi.donatengine.audio.AudioSystem;
import at.jojokobi.donatengine.audio.AudioSystemSupplier;
import at.jojokobi.donatengine.event.StartEvent;
import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.input.InputHandler;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.net.ServerPacket;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.presence.GamePresenceHandler;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.ressources.IRessourceHandler;
import at.jojokobi.donatengine.serialization.BinarySerializable;
import at.jojokobi.donatengine.serialization.BinarySerialization;
import at.jojokobi.donatengine.serialization.BinarySerializationWrapper;
import at.jojokobi.donatengine.serialization.SerializationWrapper;
import at.jojokobi.netutil.client.Client;
import at.jojokobi.netutil.client.ClientController;

public class ClientGameLogic implements GameLogic{
	
	private Level level;
	private Client client;
	
	private SerializationWrapper serialization;
	private DataInputStream data;
	private DataOutputStream dataOut;

	public ClientGameLogic(Level level, Client client) {
		super();
		this.level = level;
		this.client = client;
		serialization = new BinarySerializationWrapper(BinarySerialization.getInstance().getIdClassFactory());
	}

	@Override
	public void start(Game game) {
		client.setController(new ClientController() {
			@Override
			public void listenTo(InputStream in) throws IOException {
//				DataInputStream data = new DataInputStream(in);
//				while (true) {
//					if (data.available() > 0) {
//						ServerPacket packet = BinarySerialization.getInstance().deserialize(ServerPacket.class, data);
//						synchronized (ClientGameLogic.this) {
//							serverPackets.add(packet);
//						}
//					}
//					else {
//						try {
//							Thread.sleep(30);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
//				}
			}
		});
LevelHandler handler = new LevelHandler() {
			
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
			public SerializationWrapper getSerialization() {
				return serialization;
			}
			
			@Override
			public GamePresenceHandler getGamePresenceHandler() {
				return gamePresenceHandler;
			}

			@Override
			public void stop() {
				ClientGameLogic.this.stop();
			}
		};
		client.start ();
		data = new DataInputStream(client.getInputStream());
		dataOut = new DataOutputStream(client.getOutputStream());
		
		level.setClientId(client.getClientId());
		level.clear();
		level.start(camera, handler);
	}

	@Override
	public void update(double delta, Game game) {
		LevelHandler handler = new LevelHandler() {
			
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
			public SerializationWrapper getSerialization() {
				return serialization;
			}
			
			@Override
			public GamePresenceHandler getGamePresenceHandler() {
				return gamePresenceHandler;
			}

			@Override
			public void stop() {
				ClientGameLogic.this.stop();
			}
		};
		level.update(delta, handler, camera);
		
		try {
			while (data.available() > 0) {
				ServerPacket packet = serialization.deserialize(ServerPacket.class, data);
				synchronized (ClientGameLogic.this) {
					packet.apply(level, handler, serialization);
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
		level.end();
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(List<RenderData> data) {
		level.render(data, camera, false);
	}

}
