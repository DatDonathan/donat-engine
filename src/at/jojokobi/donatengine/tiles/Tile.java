package at.jojokobi.donatengine.tiles;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.rendering.TileModel;
import at.jojokobi.donatengine.serialization.BinarySerializable;
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public abstract class Tile implements BinarySerializable{
	
	private TileModel model;
	
	public void onPlace (Level level) {
		
	}
	
	public void onRemove (Level level) {
		
	}
	
	public boolean isSolid () {
		return true;
	}
	
	public TileModel getModel () {
		return model;
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
