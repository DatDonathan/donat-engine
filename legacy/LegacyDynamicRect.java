package at.jojokobi.donatengine.gui;

@Deprecated
public interface LegacyDynamicRect {
	
	public double getLeft (double width, double height);
	
	public double getRight (double width, double height);
	
	public double getTop (double width, double height);
	
	public double getBottom (double width, double height);
	
	public default double getWidth (double width, double height) {
		return getRight(width, height) - getLeft(width, height);
	}
	
	public default double getHeight (double width, double height) {
		return getBottom(width, height) - getTop(width, height);
	}
	
	public default boolean collides (double screenWidth, double screenHeight, double x2, double y2, double width2, double height2) {
		double x1 = getLeft(screenWidth, screenHeight);
		double y1 = getTop(screenWidth, screenHeight);
		double width1 = getWidth(screenWidth, screenHeight);
		double height1 = getHeight(screenWidth, screenHeight);
		
		return x1 + width1 >= x2 && x2 + width2 >= x1 && y1 + height1 >= y2 && y2 + height2 >= y1;
	}

}
