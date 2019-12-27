package at.jojokobi.donatengine.objects;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TopLightComponent implements ObjectComponent {


	@Override
	public void hostUpdate(GameObject object, Level level, LevelHandler handler, Camera camera, double delta) {
		
	}
	
	@Override
	public void renderAfter(GameObject object, GraphicsContext ctx, Camera cam, Level level) {
		
		Vector3D pos = object.getPosition();
		
		Vector2D ul = cam.toScreenPosition(pos.clone().add(0, object.getRenderModel().getHeight(), 0)).round();
		Vector2D ur = cam.toScreenPosition(pos.clone().add(object.getRenderModel().getWidth(), object.getRenderModel().getHeight(), 0)).round();
		Vector2D lr = cam.toScreenPosition(pos.clone().add(object.getRenderModel().getWidth(), object.getRenderModel().getHeight(), object.getRenderModel().getLength())).round();
		Vector2D ll = cam.toScreenPosition(pos.clone().add(0, object.getRenderModel().getHeight(), object.getRenderModel().getLength())).round();
		
		ctx.setFill(new Color (0, 0, 0, (level.getHeight() - object.getY())/level.getHeight() * 0.1));
		ctx.fillPolygon(new double[] {ul.getX(), ur.getX(), lr.getX(), ll.getX()}, new double[] {ul.getY(), ur.getY(), lr.getY(), ll.getY()}, 4);
		
	}

	@Override
	public void renderBefore(GameObject object, GraphicsContext ctx, Camera cam, Level level) {
		
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList();
	}

	@Override
	public void update(GameObject object, Level level, LevelHandler handler, Camera camera, double delta) {
		
	}

	@Override
	public void clientUpdate(GameObject object, Level level, LevelHandler handler, Camera camera, double delta) {
		
	}

}
