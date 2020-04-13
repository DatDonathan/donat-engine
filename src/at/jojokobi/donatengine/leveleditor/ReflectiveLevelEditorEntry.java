package at.jojokobi.donatengine.leveleditor;

import java.lang.reflect.InvocationTargetException;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;

public class ReflectiveLevelEditorEntry implements LevelEditorEntry{

	private final Class<? extends GameObject> clazz;
	private final String name;

	public ReflectiveLevelEditorEntry(Class<? extends GameObject> clazz, String name) {
		super();
		this.clazz = clazz;
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	@Override
	public void place(double x, double y, double z, String area, Level level) {
		try {
			GameObject obj = clazz.getConstructor().newInstance();
			obj.setX(x);
			obj.setY(y);
			obj.setZ(z);
			obj.setArea(area);
			level.spawn(obj);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
