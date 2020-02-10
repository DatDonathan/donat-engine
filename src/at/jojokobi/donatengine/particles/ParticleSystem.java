package at.jojokobi.donatengine.particles;

import java.util.ArrayList;
import java.util.List;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.RenderData;

public class ParticleSystem {

	private List<Particle> particles = new ArrayList<>();
	
	public ParticleSystem() {
		
	}
	
	public void update (double delta) {
		for (Particle particle : new ArrayList<>(particles)) {
			particle.update(delta);
			if (particle.getTimer() > particle.getLifetime()) {
				removeParticle(particle);
			}
		}
	}
	
	public void render (List<RenderData> data, Camera camera) {
		for (Particle particle : particles) {
//			if (camera.isColliding(particle.getX(), particle.getY(), 0, 0)) {
				particle.render(data, camera);
//			}
		}
	}
	
	public void addParticle (Particle particle) {
		particles.add(particle);
	}
	
	public void removeParticle (Particle particle) {
		particles.remove(particle);
	}

}
