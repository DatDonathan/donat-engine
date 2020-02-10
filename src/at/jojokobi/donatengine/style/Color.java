package at.jojokobi.donatengine.style;

public class Color {
	
	public static final Color TRANSPARENT = new Color (0, 0, 0, 0);
	public static final Color BLACK = new Color (0, 0, 0, 1);
	public static final Color WHITE = new Color (255, 255, 255, 1);
	public static final Color RED = new Color (255, 0, 0, 1);
	public static final Color GREEN = new Color (0, 255, 0, 1);
	public static final Color BLUE = new Color (0, 0, 255, 1);
	public static final Color YELLOW = new Color (255, 255, 0, 1);
	public static final Color CYAN = new Color (0, 255, 255, 1);
	public static final Color MAGENTA = new Color (255, 0, 255, 1);
	
	
	private static final int COLOR_MAX = 255;
	
	private int red;
	private int green;
	private int blue;
	private float opacity;
	
	
	public Color(int red, int green, int blue, float opacity) {
		super();
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.opacity = opacity;
		validateRange(red);
		validateRange(green);
		validateRange(blue);
		if (opacity < 0 || opacity > 1) {
			throw new IllegalArgumentException("Opacity must be between 0.0 and 1.0!");
		}
	}

	private void validateRange (int color) {
		if (color < 0 || color > COLOR_MAX) {
			throw new IllegalArgumentException("Values must be between 0 and " + COLOR_MAX + "!");
		}
	}
	
	public int getRed() {
		return red;
	}
	public int getGreen() {
		return green;
	}
	public int getBlue() {
		return blue;
	}
	public float getOpacity() {
		return opacity;
	}	

}
