package at.jojokobi.donatengine.net;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.serialization.BinarySerializable;

public interface ClientPacket extends BinarySerializable {
	
	public void apply (Level level, LevelHandler handler, long client);

}
