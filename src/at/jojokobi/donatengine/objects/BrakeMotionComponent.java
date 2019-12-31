package at.jojokobi.donatengine.objects;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import javafx.scene.canvas.GraphicsContext;

public class BrakeMotionComponent implements ObjectComponent {
	
	private double brakeX;
	private double brakeY;
	private double brakeZ;

	public BrakeMotionComponent(double brakeX, double brakeY, double brakeZ) {
		super();
		this.brakeX = brakeX;
		this.brakeY = brakeY;
		this.brakeZ = brakeZ;
	}

	@Override
	public void update(GameObject object, Level level, LevelHandler handler, Camera camera, double delta) {
		double deltaBrakeX = brakeX * delta;
		double deltaBrakeY = brakeY * delta;
		double deltaBrakeZ = brakeZ * delta;
		
		if (object.getxMotion() < -deltaBrakeX) {
			object.setxMotion(object.getxMotion() + deltaBrakeX, false);
		}
		else if (object.getxMotion() > deltaBrakeX) {
			object.setxMotion(object.getxMotion() - deltaBrakeX, false);
		}
		else {
			object.setxMotion(0, false);
		}
		
		if (object.getyMotion() < -deltaBrakeY) {
			object.setyMotion(object.getyMotion() + deltaBrakeY, false);
		}
		else if (object.getyMotion() > deltaBrakeY) {
			object.setyMotion(object.getyMotion() - deltaBrakeY, false);
		}
		else {
			object.setyMotion(0, false);
		}
		
		if (object.getzMotion() < -deltaBrakeZ) {
			object.setzMotion(object.getzMotion() + deltaBrakeZ, false);
		}
		else if (object.getzMotion() > deltaBrakeZ) {
			object.setzMotion(object.getzMotion() - deltaBrakeZ, false);
		}
		else {
			object.setzMotion(0, false);
		}
	}

	@Override
	public void clientUpdate(GameObject object, Level level, LevelHandler handler, Camera camera, double delta) {
		
	}

	@Override
	public void hostUpdate(GameObject object, Level level, LevelHandler handler, Camera camera, double delta) {
		
	}

	@Override
	public void renderBefore(GameObject object, GraphicsContext ctx, Camera cam, Level level) {

	}

	@Override
	public void renderAfter(GameObject object, GraphicsContext ctx, Camera cam, Level level) {
		
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList();
	}

}
