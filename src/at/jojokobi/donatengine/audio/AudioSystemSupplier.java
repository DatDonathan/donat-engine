package at.jojokobi.donatengine.audio;

import at.jojokobi.netutil.server.Server;

public interface AudioSystemSupplier {

	public static final long LOCAL_AUDIO_SYSTEM = Server.BROADCAST_CLIENT_ID;

	public AudioSystem getAudioSystem(long clientId);
	
	public default AudioSystem getAudioSystem () {
		return getAudioSystem(LOCAL_AUDIO_SYSTEM);
	}
	
}
