package at.jojokobi.donatengine.level;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.properties.ObservableObjectProperty;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.objects.properties.list.ObservableList;
import at.jojokobi.donatengine.platform.GamePlatform;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.rendering.RenderShape;
import at.jojokobi.donatengine.rendering.RenderText;
import at.jojokobi.donatengine.rendering.ScreenCanvasRenderData;
import at.jojokobi.donatengine.serialization.BinarySerializable;
import at.jojokobi.donatengine.serialization.SerializationWrapper;
import at.jojokobi.donatengine.style.Color;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.style.Font;
import at.jojokobi.donatengine.util.Vector2D;

public class ChatComponent implements LevelComponent {

	private ObservableObjectProperty<ObservableList<Message>> messages = new ObservableObjectProperty<ObservableList<Message>>(
			new ObservableList<>());

	public void postMessage(String message, long duration) {
		messages.get().add(0, new Message(message, System.currentTimeMillis() + duration));
	}

	@Override
	public void renderAfter(List<RenderData> data, Camera cam, Level level) {
		Font font = new Font("Consolas", 16);
		List<RenderShape> shapes = new ArrayList<>();
		int i = 0;
		for (Message string : messages.get()) {
			if (string.getExpirationTime() >= System.currentTimeMillis()) {
				shapes.add(new RenderText(
						new Vector2D(5,
								cam.getViewHeight() - 5
										- (i + 1)
												* GamePlatform.getFontSystem().calculateTextDimensions("Tg", font).getY()),
						string.getMessage(), new FixedStyle().reset().setFont(font).setFontColor(Color.BLACK)));
				i++;
			}
		}
		data.add(new ScreenCanvasRenderData(new Vector2D(0, 0), shapes));
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList(messages);
	}

	public static class Message implements BinarySerializable {

		private String message;
		private long expirationTime;

		public Message() {

		}

		public Message(String message, long expirationTime) {
			super();
			this.message = message;
			this.expirationTime = expirationTime;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public long getExpirationTime() {
			return expirationTime;
		}

		public void setExpirationTime(long expirationTime) {
			this.expirationTime = expirationTime;
		}

		@Override
		public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
			buffer.writeUTF(message);
			buffer.writeLong(expirationTime);
		}

		@Override
		public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
			message = buffer.readUTF();
			expirationTime = buffer.readLong();
		}

	}

}
