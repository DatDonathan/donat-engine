package at.jojokobi.donatengine.gui.nodes;

import java.util.List;

import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.rendering.RenderLine;
import at.jojokobi.donatengine.rendering.RenderShape;
import at.jojokobi.donatengine.style.Color;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Vector2D;

public class TextField extends Parent{
	
	private Text text;
	private double timer = 0;
	
	public TextField() {
		this.text = new Text("");
		addChild(this.text);
		addStyle(s -> true, new FixedStyle().setFill(Color.WHITE).setBorder(Color.BLACK).setBorderStrength(1.0).setBorderRadius(5.0).setPadding(10.0));
	}
	
	@Override
	public void update(Input input, double delta) {
		super.update(input, delta);
		timer += delta * 5;
	}
	
	@Override
	public void render(double dx, double dy, List<RenderShape> shapes) {
		super.render(dx, dy, shapes);
		if (getState().isSelected() && ((int) timer) % 2 == 0) {
//			ctx.setStroke(Color.BLACK);
//			ctx.setLineWidth(1);
//			ctx.strokeLine(dx + getX() + text.getX() + text.getWidth(), dy + getY() + text.getY(), dx + getX() + text.getX() + text.getWidth(), dy + getY() + text.getY() + text.getHeight());
			shapes.add(new RenderLine(new Vector2D(dx + getX() + text.getX() + text.getWidth(), dy + getY() + text.getY()), new Vector2D(dx + getX() + text.getX() + text.getWidth(), dy + getY() + text.getY() + text.getHeight()), new FixedStyle().reset().setBorder(Color.BLACK).setBorderStrength(1.0)));
		}
	}
	
	public String getText() {
		return text.getText();
	}

	public void setText(String text) {
		this.text.setText(text);
	}

	@Override
	public boolean isSelectable() {
		return true;
	}

	@Override
	public void fire() {
		
	}

	@Override
	public void onType(String ch) {
		if (ch.equals("\b")) {
			if (getText().length() > 0) {
				setText(getText().substring(0, getText().length() - 1));
			}
		}
		else {
			setText(getText() + ch);
		}
	}

}
