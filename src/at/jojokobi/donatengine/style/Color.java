package at.jojokobi.donatengine.style;

public class Color {
	
	public static final Color TRANSPARENT = new Color (0, 0, 0, 0);
	public static final Color BLACK = new Color (0, 0, 0, 1);
	public static final Color WHITE = new Color (1, 1, 1, 1);
	public static final Color RED = new Color (1, 0, 0, 1);
	public static final Color GREEN = new Color (0, 1, 0, 1);
	public static final Color BLUE = new Color (0, 0, 1, 1);
	public static final Color YELLOW = new Color (1, 1, 0, 1);
	public static final Color CYAN = new Color (0, 1, 1, 1);
	public static final Color MAGENTA = new Color (1, 0, 1, 1);
	
	
	public static final int COLOR_MAX = 255;
	
	private double red;
	private double green;
	private double blue;
	private double opacity;
	

	public Color(double red, double green, double blue, double opacity) {
		super();
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.opacity = opacity;
		validateRange(red);
		validateRange(green);
		validateRange(blue);
		validateRange(opacity);
	}

	private void validateRange (double color) {
		if (color < 0 || color > 1.0) {
			throw new IllegalArgumentException("Values must be between 0.0 and 1.0!");
		}
	}

	public double getRed() {
		return red;
	}

	public double getGreen() {
		return green;
	}

	public double getBlue() {
		return blue;
	}

	public double getOpacity() {
		return opacity;
	}

}
