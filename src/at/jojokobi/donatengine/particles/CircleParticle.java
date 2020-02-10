package at.jojokobi.donatengine.particles;

import java.util.List;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.RectRenderData;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.style.Color;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Position;
import at.jojokobi.donatengine.util.Vector3D;

public class CircleParticle extends Particle{
	
	private Color color;
	private double radius;

	public CircleParticle(double x, double y, double z, String area, double radius, Color color) {
		super(x, y, z, area);
		this.color = color;
		this.radius = radius;
	}
	
	@Override
	public void update(double delta) {
		super.update(delta);
		color = new Color(color.getRed(), color.getGreen(), color.getBlue(), (float) Math.max(1 - getTimer()/getLifetime(), 0));
	}

	@Override
	public void render(List<RenderData> data, Camera cam) {
		data.add(new RectRenderData(new Position(new Vector3D(getX(), getY(), getZ()), getArea()), radius * 2, radius * 2, new FixedStyle().reset().setFill(color)));
	}


}
