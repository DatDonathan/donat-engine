package at.jojokobi.donatengine.input;

import at.jojokobi.donatengine.util.Vector2D;

public interface Axis {
	
	public Vector2D getAxis (Input input, double mouseX, double mouseY);
	
	public boolean fetchChanged (Input input, double mouseX, double mouseY);

}
