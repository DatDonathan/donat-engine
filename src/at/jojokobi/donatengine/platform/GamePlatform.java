package at.jojokobi.donatengine.platform;

public class GamePlatform {
	
	private static IGamePlatform instance;
	
	public static void initialize (IGamePlatform engine) {
		if (instance == null) {
			instance = engine;
		}
	}
	
	public static FontSystem getFontSystem () {
		return instance.getFontSystem();
	}

}
