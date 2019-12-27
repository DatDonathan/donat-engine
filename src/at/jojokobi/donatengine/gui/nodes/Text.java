package at.jojokobi.donatengine.gui.nodes;

import at.jojokobi.donatengine.gui.style.FixedStyle;
import at.jojokobi.donatengine.util.Rect;
import javafx.scene.canvas.GraphicsContext;

public class Text extends Node {
	
	private String text;
	private double textWidth;
	private double textHeight;

	public Text(String text) {
		super();
		this.text = text;
	}
	
	@Override
	public void render(double dx, double dy, GraphicsContext ctx) {
		super.render(dx, dy, ctx);
		ctx.setFont(getStyle().getFont());
		ctx.setFill(getStyle().getFontColor());
		ctx.setStroke(getStyle().getFontBorder());
		ctx.setLineWidth(getStyle().getFontBorderStrength());
		ctx.fillText(text, dx + getX(), dy + getY() + getStyle().getFont().getSize() - 5);
		ctx.strokeText(text, dx + getX(), dy + getY() + getStyle().getFont().getSize() - 5);
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
