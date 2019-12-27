package at.jojokobi.donatengine.particles;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ImageParticle extends Particle{
	
	private Image image;

	public ImageParticle(double x, double y, double z, Image image) {
		super(x, y, z);
		this.image = image;
	}

	@Override
	public void update(double delta) {
		super.update(delta);
	}

	@Override
	public void render(GraphicsContext ctx, Camera cam) {
		Vector2D pos = cam.toScreenPosition(new Vector3D(getX(), getY(), getZ()));
		ctx.setGlobalAlpha(1 - getTimer()/getLifetime());
		ctx.drawImage(image, pos.getX() - image.getWidth()/2, pos.getY() - image.getHeight()/2);
		ctx.setGlobalAlpha(1);
	}


}
