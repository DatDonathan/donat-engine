package at.jojokobi.donatengine.rendering;

import java.util.LinkedList;
import java.util.List;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.tiles.TileSystem;

public class RenderScene {
	
	private List<RenderData> data = new LinkedList<RenderData>();
	private Camera camera;
	private TileSystem tileSystem;
	private double animationTimer;
	
		
	public RenderScene() {
		
	}
	
	public List<RenderData> getData() {
		return data;
	}
	public Camera getCamera() {
		return camera;
	}
	public TileSystem getTileSystem() {
		return tileSystem;
	}
	public void setData(List<RenderData> data) {
		this.data = data;
	}
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	public void setTileSystem(TileSystem tileSystem) {
		this.tileSystem = tileSystem;
	}

	public double getAnimationTimer() {
		return animationTimer;
	}

	public void setAnimationTimer(double animationTimer) {
		this.animationTimer = animationTimer;
	}

}
