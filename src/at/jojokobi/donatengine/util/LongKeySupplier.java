package at.jojokobi.donatengine.util;

import java.util.function.Supplier;

public class LongKeySupplier implements Supplier<Long>{
	
	private long last = 1;

	@Override
	public Long get() {
		return last++;
	}
	
}
