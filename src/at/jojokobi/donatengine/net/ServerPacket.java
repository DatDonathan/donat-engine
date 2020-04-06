package at.jojokobi.donatengine.net;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.serialization.SerializationWrapper;
import at.jojokobi.donatengine.serialization.binary.BinarySerializable;

/**
 * 
 * A packet sent from the server to the client
 * 
 * @author jojokobi
 *
 */
public interface ServerPacket extends BinarySerializable{
	
	public void apply (Level level, Game game, SerializationWrapper serialization);

}
