package at.jojokobi.donatengine.gui.nodes;

import java.util.List;

import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.rendering.ScreenTextRenderData;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Rect;
import at.jojokobi.donatengine.util.Vector2D;

public class Text extends Node {
	
	private String text;
	private double textWidth;
	private double textHeight;

	public Text(String text) {
		super();
		this.text = text;
	}
	
	@Override
	public void render(double dx, double dy, List<RenderData> data) {
		super.render(dx, dy, data);
//		ctx.setFont(getStyle().getFont());
//		ctx.setFill(getStyle().getFontColor());
//		ctx.setStroke(getStyle().getFontBorder());
//		ctx.setLineWidth(getStyle().getFontBorderStrength());
//		ctx.fillText(text, dx + getX(), dy + getY() + getStyle().getFont().getSize() - 5);
//		ctx.strokeText(text, dx + getX(), dy + getY() + getStyle().getFont().getSize() - 5);
		data.add(new ScreenTextRenderData(new Vector2D(getX(), getY()), text, getStyle()));
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public void updateStyle(double mouseX, double mouseY, Node selected, FixedStyle parent) {
		super.updateStyle(mouseX, mouseY, selected, parent);
		//TODO use Engine Font Calculator
		javafx.scene.text.Text text = new javafx.scene.text.Text(this.text);
		text.setFont(getStyle().getFont());
		textWidth = text.getLayoutBounds().getWidth();
		textHeight = text.getLayoutBounds().getHeight();
	}
	
	@Override
	public Rect getFitBounds() {
		return new Rect(getX(), getY(), textWidth, textHeight);
	}

	@Override
	public void fire() {
		
	}

	@Override
	public void onType(String ch) {
		
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

}
