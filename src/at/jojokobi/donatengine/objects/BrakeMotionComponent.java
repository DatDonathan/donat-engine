package at.jojokobi.donatengine.objects;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.rendering.RenderData;

public class BrakeMotionComponent implements ObjectComponent {
	
	private double brakeX;
	private double brakeY;
	private double brakeZ;
	
	private double brakeXLimit;
	private double brakeYLimit;
	private double brakeZLimit;

	
	public BrakeMotionComponent(double brakeX, double brakeY, double brakeZ, double brakeXLimit, double brakeYLimit,
			double brakeZLimit) {
		super();
		this.brakeX = brakeX;
		this.brakeY = brakeY;
		this.brakeZ = brakeZ;
		this.brakeXLimit = brakeXLimit;
		this.brakeYLimit = brakeYLimit;
		this.brakeZLimit = brakeZLimit;
	}

	@Override
	public void update(GameObject object, Level level, LevelHandler handler, Camera camera, double delta) {
		double deltaBrakeX = brakeX * delta;
		double deltaBrakeY = brakeY * delta;
		double deltaBrakeZ = brakeZ * delta;
		
		if (object.getxMotion() < -deltaBrakeX - brakeXLimit) {
			object.setxMotion(object.getxMotion() + deltaBrakeX, false);
		}
		else if (object.getxMotion() > deltaBrakeX + brakeXLimit) {
			object.setxMotion(object.getxMotion() - deltaBrakeX, false);
		}
		else if (object.getxMotion() < -brakeXLimit) {
			object.setxMotion(-brakeXLimit, false);
		}
		else if (object.getxMotion() > brakeXLimit) {
			object.setxMotion(brakeXLimit, false);
		}
		
		if (object.getyMotion() < -deltaBrakeY - brakeYLimit) {
			object.setyMotion(object.getyMotion() + deltaBrakeY, false);
		}
		else if (object.getyMotion() > deltaBrakeY + brakeYLimit) {
			object.setyMotion(object.getyMotion() - deltaBrakeY, false);
		}
		else if (object.getyMotion() < -brakeYLimit) {
			object.setyMotion(-brakeYLimit, false);
		}
		else if (object.getyMotion() > brakeYLimit) {
			object.setyMotion(brakeYLimit, false);
		}
		
		if (object.getzMotion() < -deltaBrakeZ - brakeZLimit) {
			object.setzMotion(object.getzMotion() + deltaBrakeZ, false);
		}
		else if (object.getzMotion() > deltaBrakeZ + brakeZLimit) {
			object.setzMotion(object.getzMotion() - deltaBrakeZ, false);
		}
		else if (object.getzMotion() < -brakeZLimit) {
			object.setzMotion(-brakeZLimit, false);
		}
		else if (object.getzMotion() > brakeZLimit) {
			object.setzMotion(brakeZLimit, false);
		}
	}

	@Override
	public void clientUpdate(GameObject object, Level level, LevelHandler handler, Camera camera, double delta) {
		
	}

	@Override
	public void hostUpdate(GameObject object, Level level, LevelHandler handler, Camera camera, double delta) {
		
	}

	public double getBrakeX() {
		return brakeX;
	}

	public void setBrakeX(double brakeX) {
		this.brakeX = brakeX;
	}

	public double getBrakeY() {
		return brakeY;
	}

	public void setBrakeY(double brakeY) {
		this.brakeY = brakeY;
	}

	public double getBrakeZ() {
		return brakeZ;
	}

	public void setBrakeZ(double brakeZ) {
		this.brakeZ = brakeZ;
	}

	public double getBrakeXLimit() {
		return brakeXLimit;
	}

	public void setBrakeXLimit(double brakeXLimit) {
		this.brakeXLimit = brakeXLimit;
	}

	public double getBrakeYLimit() {
		return brakeYLimit;
	}

	public void setBrakeYLimit(double brakeYLimit) {
		this.brakeYLimit = brakeYLimit;
	}

	public double getBrakeZLimit() {
		return brakeZLimit;
	}

	public void setBrakeZLimit(double brakeZLimit) {
		this.brakeZLimit = brakeZLimit;
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList();
	}

	@Override
	public void renderBefore(GameObject object, List<RenderData> data, Camera cam, Level level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderAfter(GameObject object, List<RenderData> data, Camera cam, Level level) {
		// TODO Auto-generated method stub
		
	}

}
