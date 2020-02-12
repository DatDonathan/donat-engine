package at.jojokobi.donatengine.engine;

import at.jojokobi.donatengine.style.Font;
import at.jojokobi.donatengine.util.Vector2D;

public interface FontSystem {
	
	public Vector2D calculateTextDimensions (String text, Font font);

}
