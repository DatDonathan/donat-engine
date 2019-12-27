package at.jojokobi.donatengine.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

@Deprecated
public class LegacyLabel implements LegacySimpleGUIComponent {
	
	private Color fillColor;
	private Color strokeColor;
	private Font font;
	private String text;
	private LegacyDynamicRect rect;

	public LegacyLabel(Color fillColor, Color strokeColor, Font font, String text, LegacyDynamicRect rect) {
		super();
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
		this.font = font;
		this.text = text;
		this.rect = rect;
	}

	@Override
	public boolean onClick(double x, double y) {
		return false;
	}

	@Override
	public boolean onKeyPress(boolean selected, KeyCode key) {
		return false;
	}

	@Override
	public LegacyDynamicRect getRect() {
		return rect;
	}

	@Override
	public void render(GraphicsContext ctx, boolean selected, boolean hovered, boolean clicked, double totalWidth,
			double totalHeight) {
		Text textNode = new Text(text);
		textNode.setFont(font);
		double textWidth = textNode.getLayoutBounds().getWidth();
		double textHeight = textNode.getLayoutBounds().getHeight();
		double textX = rect.getLeft(totalWidth, totalHeight) + rect.getWidth(totalWidth, totalHeight)/2 - textWidth/2;
		double textY = rect.getTop(totalWidth, totalHeight) + rect.getHeight(totalWidth, totalHeight)/2 + textHeight/2;
		
		ctx.setFont(font);
		ctx.setFill(fillColor);
		ctx.fillText(text, textX, textY);
		ctx.setStroke(strokeColor);
		ctx.strokeText(text, textX, textY);
	}

	@Override
	public String getValue() {
		return null;
	}

}
