package at.jojokobi.donatengine.util;

import java.util.function.Supplier;

public class StringKeySupplier implements Supplier<String>{
	
	private long last = 1;

	@Override
	public String get() {
		return "" + last++;
	}
	
}
