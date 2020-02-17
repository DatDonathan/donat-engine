package at.jojokobi.donatengine.level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.properties.ObservableObjectProperty;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.objects.properties.list.ObservableList;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.rendering.RenderShape;
import at.jojokobi.donatengine.rendering.RenderText;
import at.jojokobi.donatengine.rendering.ScreenCanvasRenderData;
import at.jojokobi.donatengine.style.Color;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.style.Font;
import at.jojokobi.donatengine.util.Vector2D;

public class ChatComponent implements LevelComponent{
	
	private ObservableObjectProperty<ObservableList<String>> messages = new ObservableObjectProperty<ObservableList<String>>(new ObservableList<>());

	
	public void postMessage (String message) {
		messages.get().add(0, message);
	}

	@Override
	public void renderAfter(List<RenderData> data, Camera cam, Level level) {
		Font font = new Font("Consolas", 16);
		List<RenderShape> shapes = new ArrayList<>();
		int i = 0;
		for (String string : messages.get()) {
			shapes.add(new RenderText(new Vector2D(5, cam.getViewHeight() - 5 - i * 16), string,  new FixedStyle().reset().setFont(font).setFontColor(Color.BLACK)));
			i++;
		}
		data.add(new ScreenCanvasRenderData(new Vector2D(0, 0), shapes));
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList(messages);
	}

}
