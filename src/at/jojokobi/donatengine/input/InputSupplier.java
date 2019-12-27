package at.jojokobi.donatengine.input;

import at.jojokobi.netutil.server.Server;

@FunctionalInterface
public interface InputSupplier {
	
	public static final long SCENE_INPUT = Server.BROADCAST_CLIENT_ID;

	public Input getInput(long clientId);
	
	public default Input getInput () {
		return getInput(SCENE_INPUT);
	}
	
}
