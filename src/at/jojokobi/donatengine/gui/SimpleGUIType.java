package at.jojokobi.donatengine.gui;

import java.util.function.BiFunction;

public class SimpleGUIType<T> implements GUIType<T>{
	
	private Class<T> clazz;
	private BiFunction<T, Long, GUI> supplier;

	public SimpleGUIType(Class<T> clazz, BiFunction<T, Long, GUI> supplier) {
		super();
		this.clazz = clazz;
		this.supplier = supplier;
	}

	@Override
	public GUI createGUI(T data, long client) {
		return supplier.apply(data, client);
	}

	@Override
	public Class<T> getDataClass() {
		return clazz;
	}

}
