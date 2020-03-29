package at.jojokobi.donatengine.tiles;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.serialization.BinarySerializable;
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public abstract class Tile implements BinarySerializable{
	
	private String model;
	
	public Tile(String model) {
		super();
		this.model = model;
	}

	public void onPlace (Level level) {
		
	}
	
	public void onRemove (Level level) {
		
	}
	
	public boolean isSolid () {
		return true;
	}
	
	public String getModel () {
		return model;
	}
	
	protected void setModel(String model) {
		this.model = model;
	}

	public void update (TileSystem system, int tileX, int tileY, int tileZ, String area) {
		
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		
	}
	
	public List<ObservableProperty<?>> observableProperties () {
		return Arrays.asList();
	}
	
}
