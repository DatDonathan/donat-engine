package at.jojokobi.donatengine.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

@Deprecated
public class LegacyButton implements LegacySimpleGUIComponent {
	
	private Color border;
	private Color fill;
	private Font font;
	private String text;
	private LegacyDynamicRect rect;

	public LegacyButton(Color border, Color fill, Font font, String text, LegacyDynamicRect rect) {
		super();
		this.border = border;
		this.fill = fill;
		this.font = font;
		this.text = text;
		this.rect = rect;
	}
	
	public LegacyButton (String text, LegacyDynamicRect rect) {
		this(Color.BLACK, Color.LIGHTGRAY, new Font(11), text, rect);
	}

	@Override
	public boolean onClick(double x, double y) {
		return true;
	}

	@Override
	public boolean onKeyPress(boolean selected, KeyCode key) {
		return selected && key == KeyCode.ENTER;
	}

	@Override
	public LegacyDynamicRect getRect() {
		return rect;
	}

	@Override
	public void render(GraphicsContext ctx, boolean selected, boolean hovered, boolean clicked, double totalWidth,
			double totalHeight) {
		ctx.setFill(fill);
		ctx.setStroke(border);
		ctx.fillRoundRect(rect.getLeft(totalWidth, totalHeight), rect.getTop(totalWidth, totalHeight), rect.getWidth(totalWidth, totalHeight), rect.getHeight(totalWidth, totalHeight), 5, 5);
		ctx.setLineWidth(selected ?  4 : 2);
		ctx.strokeRoundRect(rect.getLeft(totalWidth, totalHeight), rect.getTop(totalWidth, totalHeight), rect.getWidth(totalWidth, totalHeight), rect.getHeight(totalWidth, totalHeight), 5, 5);
		Text textNode = new Text(text);
		textNode.setFont(font);
		double textWidth = textNode.getLayoutBounds().getWidth();
		double textHeight = textNode.getLayoutBounds().getHeight();
		double textX = rect.getLeft(totalWidth, totalHeight) + rect.getWidth(totalWidth, totalHeight)/2 - textWidth/2;
		double textY = rect.getTop(totalWidth, totalHeight) + rect.getHeight(totalWidth, totalHeight)/2 + textHeight/2;
		
		ctx.setFont(font);
		ctx.setFill(Color.BLACK);
		ctx.fillText(text, textX, textY);
	}

	@Override
	public String getValue() {
		return null;
	}
	
}
