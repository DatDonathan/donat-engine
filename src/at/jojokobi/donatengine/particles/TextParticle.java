package at.jojokobi.donatengine.particles;

import java.util.List;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.rendering.TextRenderData;
import at.jojokobi.donatengine.style.Color;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.style.Font;
import at.jojokobi.donatengine.util.Position;
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
		setY(getY() + delta * 32);
	}

	@Override
	public void render(List<RenderData> data, Camera cam) {
		data.add(new TextRenderData(new Position(new Vector3D(getX(), getY(), getZ()), getArea()), new FixedStyle().reset().setFont(font).setFontColor(color), text));
	}


}
