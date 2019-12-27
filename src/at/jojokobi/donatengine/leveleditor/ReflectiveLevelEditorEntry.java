package at.jojokobi.donatengine.leveleditor;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;

public class ReflectiveLevelEditorEntry implements LevelEditorEntry{

	private final Class<? extends GameObject> clazz;
	private final String name;
	private double tileSize;

	public ReflectiveLevelEditorEntry(Class<? extends GameObject> clazz, String name, double tileSize) {
		super();
		this.clazz = clazz;
		this.name = name;
		this.tileSize = tileSize;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public GameObject createInstance(double x, double y, double z, Level level, Map<String, String> attributes) {
		try {
			return clazz.getConstructor(Double.TYPE, Double.TYPE, Double.TYPE, Level.class).newInstance(x/tileSize, y/tileSize, z/tileSize, level);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String toString() {
		return getName();
	}

}
