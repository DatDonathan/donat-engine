package at.jojokobi.donatengine.tiles;

public class TilePosition {
	
	private int x;
	private int y;
	private int z;
	private String area;
	
	public TilePosition(int x, int y, int z, String area) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.area = area;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getZ() {
		return z;
	}
	public String getArea() {
		return area;
	}

}
