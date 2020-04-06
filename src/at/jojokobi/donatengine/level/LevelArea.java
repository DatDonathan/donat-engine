package at.jojokobi.donatengine.level;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.BackgroundRenderData;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.serialization.SerializationWrapper;
import at.jojokobi.donatengine.serialization.binary.BinarySerializable;

public class LevelArea implements BinarySerializable{
	
	private String background = "";
	
	public LevelArea() {
		
	}
	
	public LevelArea(String background) {
		super();
		this.background = background;
	}

	public void render (Level level, List<RenderData> data, Camera camera) {
		data.add(new BackgroundRenderData(background));
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeUTF(background);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		background = buffer.readUTF();
	}

}
