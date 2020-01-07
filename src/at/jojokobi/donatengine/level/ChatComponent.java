package at.jojokobi.donatengine.level;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.properties.ObservableList;
import at.jojokobi.donatengine.objects.properties.ObservableObjectProperty;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ChatComponent implements LevelComponent{
	
	private ObservableObjectProperty<ObservableList<String>> messages = new ObservableObjectProperty<ObservableList<String>>(new ObservableList<>());

	
	public void postMessage (String message) {
		messages.get().add(0, message);
	}

	@Override
	public void renderBefore(GraphicsContext ctx, Camera cam, Level level) {
		
	}

	@Override
	public void renderAfter(GraphicsContext ctx, Camera cam, Level level) {
		ctx.setFont(new Font("Consolas", 12));
		ctx.setFill(Color.BLACK);
		int i = 0;
		for (String string : messages.get()) {
			ctx.fillText(string, 5, cam.getViewHeight() - 5 - i * 16);
			i++;
		}
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList(messages);
	}

	@Override
	public void init(Level level) {
		
	}

	@Override
	public void hostUpdate(Level level, LevelHandler handler, Camera cam, double delta) {
		
	}

	@Override
	public void update(Level level, LevelHandler handler, Camera cam, double delta) {
		
	}

	@Override
	public void clientUpdate(Level level, LevelHandler handler, Camera cam, double delta) {
		
	}

}
