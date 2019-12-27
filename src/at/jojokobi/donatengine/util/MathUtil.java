package at.jojokobi.donatengine.util;

public final class MathUtil {

	private MathUtil() {
		
	}
	
	public static double interpolate (double prog, double val1, double val2) {
		return val1 * (1 - prog) + val2 * prog;
	}

}
