package at.jojokobi.donatengine.gui;

import java.util.function.Function;

public class SimpleGUIType<T> implements GUIType<T>{
	
	private Class<T> clazz;
	private Function<T, GUI> supplier;

	public SimpleGUIType(Class<T> clazz, Function<T, GUI> supplier) {
		super();
		this.clazz = clazz;
		this.supplier = supplier;
	}

	@Override
	public GUI createGUI(T data) {
		return supplier.apply(data);
	}

	@Override
	public Class<T> getDataClass() {
		return clazz;
	}

}
