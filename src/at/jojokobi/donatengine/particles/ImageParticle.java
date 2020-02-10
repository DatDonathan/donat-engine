package at.jojokobi.donatengine.particles;

import java.util.List;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.ImageRenderData;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.util.Position;
import at.jojokobi.donatengine.util.Vector3D;

public class ImageParticle extends Particle{
	
	private String tag;

	public ImageParticle(double x, double y, double z, String area, String tag) {
		super(x, y, z, area);
		this.tag = tag;
	}

	@Override
	public void update(double delta) {
		super.update(delta);
	}

	@Override
	public void render(List<RenderData> data, Camera cam) {
		data.add(new ImageRenderData(new Position(new Vector3D(getX(), getY(), getZ()), getArea()), tag, -1, -1, -1));
	}


}
