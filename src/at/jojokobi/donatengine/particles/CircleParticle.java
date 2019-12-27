package at.jojokobi.donatengine.particles;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleParticle extends Particle{
	
	private Color color;
	private double radius;

	public CircleParticle(double x, double y, double z, double radius, Color color) {
		super(x, y, z);
		this.color = color;
		this.radius = radius;
	}
	
	@Override
	public void update(double delta) {
		super.update(delta);
		color = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(1 - getTimer()/getLifetime(),0));
	}

	@Override
	public void render(GraphicsContext ctx, Camera cam) {
		ctx.setFill(color);
		Vector2D pos = cam.toScreenPosition(new Vector3D(getX(), getY(), getZ()));
		ctx.fillOval(pos.getX() - radius, pos.getY() - radius, radius*2, radius*2);
	}


}
