package at.jojokobi.donatengine.objects;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShadowComponent implements ObjectComponent{

	@Override
	public void hostUpdate(GameObject object, Level level, Game game, double delta) {
		
	}

	@Override
	public void renderBefore(GameObject object, GraphicsContext ctx, Camera cam, Level level) {
		
		double y = 0;
		for (GameObject obj : level.getObjectsInArea(object.getX(), 0, object.getZ(), object.getWidth(), object.getY(), object.getLength(), object.getArea ())) {
			if (y < obj.getY() + obj.getHeight() && obj != object && obj.isSolid()) {
				y = obj.getY() + obj.getHeight();
			}
		}
		
		//Render
		Vector3D startPos = new Vector3D(object.getX(), y, object.getZ());
		Vector3D endPos = new Vector3D(object.getX() + object.getWidth(), y, object.getZ() + object.getLength());
		
		Vector2D screenStartPos = cam.toScreenPosition(startPos);
		Vector2D screenEndPos = cam.toScreenPosition(endPos);
		
		ctx.setFill(new Color(0.5, 0.5, 0.5, 0.5));
		ctx.fillOval(screenStartPos.getX(), screenStartPos.getY(), screenEndPos.getX() - screenStartPos.getX(), screenEndPos.getY() - screenStartPos.getY());
	}

	@Override
	public void renderAfter(GameObject object, GraphicsContext ctx, Camera cam, Level level) {
		
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList();
	}

	@Override
	public void update(GameObject object, Level level, Game game, double delta) {
		
	}

	@Override
	public void clientUpdate(GameObject object, Level level, Game game, double delta) {
		
	}

}
