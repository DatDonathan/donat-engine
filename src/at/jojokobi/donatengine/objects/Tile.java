package at.jojokobi.donatengine.objects;


public abstract class Tile extends GameObject{

	private final int tileWidth;
	private final int tileHeight;
	private final int tileLength;
	
//	@Deprecated
//	public Tile(double x, double y, double z, string, int tileWidth, int tileHeight, int tileLength, Image image) {
//		this(x, y, z, tileWidth, tileHeight, tileLength, new Image2DModel(image));
//	}
	
	public Tile(double x, double y, double z, String area, int tileWidth, int tileHeight, int tileLength, String renderTag) {
		super(x, y, z, area, renderTag);
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.tileLength = tileLength;
		setWidth(tileWidth);
		setHeight(tileHeight);
		setLength(tileLength);
		setX(x);
		setY(y);
		setZ(z);
		fetchMoved();
		setCollideSolid(false);
	}
	
	public int getTileX () {
		return (int)getX()/tileWidth;
	}
	
	public int getTileY () {
		return (int)getY()/tileHeight;
	}
	
	public int getTileZ () {
		return (int)getZ()/tileLength;
	}
	
	@Override
	public void setX(double x) {
		super.setX(tileWidth == 0 ? x : ((int) x/tileWidth)*tileWidth);
	}
	
	@Override
	public void setY(double y) {
		super.setY(tileHeight == 0 ? y : ((int) y/tileHeight)*tileHeight);
	}
	
	@Override
	public void setZ(double z) {
		super.setZ(tileLength == 0 ? z : ((int) z/tileLength)*tileLength);
	}
	
	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public int getTileLength() {
		return tileLength;
	}

}
