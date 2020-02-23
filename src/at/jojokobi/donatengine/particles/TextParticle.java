package at.jojokobi.donatengine.particles;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.platform.GamePlatform;
import at.jojokobi.donatengine.rendering.CanvasRenderData;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.rendering.RenderText;
import at.jojokobi.donatengine.style.Color;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.style.Font;
import at.jojokobi.donatengine.util.Position;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;

public class TextParticle extends Particle{
	
	private Color color;
	private String text;
	private Font font;
	
	public TextParticle(double x, double y, double z, String area, Color color, String text, Font font) {
		super(x, y, z, area);
		this.color = color;
		this.text = text;
		this.font = font;
	}

	@Override
	public void update(double delta) {
		super.update(delta);
		setY(getY() + delta);
	}

	@Override
	public void render(List<RenderData> data, Camera cam) {
		FixedStyle style = new FixedStyle().reset().setFont(font).setFontColor(color);
		Vector2D dimensions = GamePlatform.getFontSystem().calculateTextDimensions(text, font);
		data.add(new CanvasRenderData(new Position(new Vector3D(getX(), getY(), getZ()), getArea()), Arrays.asList(new RenderText(new Vector2D(-dimensions.getX()/2, -dimensions.getY()/2), text, style))));
	}

}
