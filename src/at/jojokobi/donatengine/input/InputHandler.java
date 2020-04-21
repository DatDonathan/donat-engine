package at.jojokobi.donatengine.input;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.util.Pair;
import at.jojokobi.netutil.server.Server;

@FunctionalInterface
public interface InputHandler {
	
	public static final long SCENE_INPUT = Server.BROADCAST_CLIENT_ID;

	public Input getInput(long clientId);
	
	public default List<Pair<Long, Input>> getInputs () {
		return Arrays.asList(new Pair<>(SCENE_INPUT, getInput(SCENE_INPUT)));
	}
	
	public default Input getInput () {
		return getInput(SCENE_INPUT);
	}
	
}
