package at.jojokobi.donatengine.tiles;

import at.jojokobi.donatengine.objects.Collidable;

public class TileInstance implements Collidable {
	
	private TileSystem tileSystem;
	private TilePosition position;
	private Tile tile;

	public TileInstance(TileSystem tileSystem, TilePosition position, Tile tile) {
		super();
		this.tileSystem = tileSystem;
		this.position = position;
		this.tile = tile;
	}

	public TilePosition getTilePosition() {
		return position;
	}

	public Tile getTile() {
		return tile;
	}

	@Override
	public double getX() {
		return tileSystem.getTileSize() * position.getX();
	}

	@Override
	public double getY() {
		return tileSystem.getTileSize() * position.getY();
	}

	@Override
	public double getZ() {
		return tileSystem.getTileSize() * position.getZ();
	}

	@Override
	public double getWidth() {
		return tileSystem.getTileSize();
	}

	@Override
	public double getHeight() {
		return tileSystem.getTileSize();
	}

	@Override
	public double getLength() {
		return tileSystem.getTileSize();
	}

	@Override
	public String getArea() {
		return position.getArea();
	}
}
