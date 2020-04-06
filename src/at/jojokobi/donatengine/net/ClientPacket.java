package at.jojokobi.donatengine.net;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.input.InputHandler;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.serialization.binary.BinarySerializable;

public interface ClientPacket extends BinarySerializable {
	
	public void apply (Level level, Game game, InputHandler input, long client);

}
