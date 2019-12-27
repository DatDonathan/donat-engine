package at.jojokobi.donatengine.level;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.ressources.IRessourceHandler;
import at.jojokobi.donatengine.serialization.BinarySerializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class LevelArea implements BinarySerializable{
	
	private String background = "";
	
	public LevelArea() {
		
	}
	
	public LevelArea(String background) {
		super();
		this.background = background;
	}

	public void render (Level level, GraphicsContext ctx, IRessourceHandler ressourceHandler, Camera camera) {
		Image img = ressourceHandler.getImage(background);
		if (img != null) {
			ctx.drawImage(img, 0, 0);
		}
	}

	@Override
	public void serialize(DataOutput buffer) throws IOException {
		buffer.writeUTF(background);
	}

	@Override
	public void deserialize(DataInput buffer) throws IOException {
		background = buffer.readUTF();
	}

}
