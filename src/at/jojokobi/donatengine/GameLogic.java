package at.jojokobi.donatengine;

import at.jojokobi.donatengine.rendering.RenderScene;

public interface GameLogic {
	
	public void start (Game game);

	public void update (double delta, Game game);
	
	public void render (RenderScene scene);
	
	public void stop (Game game);
	
}
