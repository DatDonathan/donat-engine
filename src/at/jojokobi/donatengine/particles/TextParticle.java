package at.jojokobi.donatengine.particles;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TextParticle extends Particle{
	
	private Color color;
	private String text;
	private Font font;
	
	public TextParticle(double x, double y, double z, Color color, String text, Font font) {
		super(x, y, z);
		this.color = color;
		this.text = text;
		this.font = font;
	}

	@Override
	public void update(double delta) {
		super.update(delta);
		setY(getY() + delta * 32);
	}

	@Override
	public void render(GraphicsContext ctx, Camera cam) {
		ctx.setFill(color);
		ctx.setFont(font);
		Vector2D pos = cam.toScreenPosition(new Vector3D(getX(), getY(), getZ()));
		ctx.fillText(text, pos.getX(), pos.getY());
	}


}
