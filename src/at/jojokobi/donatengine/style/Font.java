package at.jojokobi.donatengine.style;

public class Font {
	
	private double size;
	private String family;
	
	public Font(double size, String family) {
		super();
		this.size = size;
		this.family = family;
	}

	public double getSize() {
		return size;
	}

	public String getFamily() {
		return family;
	}

}
