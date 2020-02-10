package at.jojokobi.donatengine.gui.nodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.style.Color;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Vector2D;

public class Button extends Parent {
	
	private Text text;
	private Supplier<GUIAction> onAction;

	public Button(String text) {
		this.text = new Text(text);
		addChild(this.text);
		addStyle(s -> true, new FixedStyle().setFill(Color.WHITE).setBorder(Color.BLACK).setBorderStrength(1.0).setBorderRadius(5.0).setPadding(10.0));
	}

	@Override
	public boolean isSelectable() {
		return true;
	}

	@Override
	public void fire() {
		if (onAction != null) {
			addAction(onAction.get());
		}
	}

	@Override
	public void onType(String ch) {
		
	}
	
	public String getText() {
		return text.getText();
	}

	public void setText(String text) {
		this.text.setText(text);
	}
	
	public Supplier<GUIAction> getOnAction() {
		return onAction;
	}

	public void setOnAction(Supplier<GUIAction> onAction) {
		this.onAction = onAction;
	}

	@Override
	public Map<Node, Vector2D> calcPositions(List<Node> nodes) {
		Map<Node, Vector2D> positions = new HashMap<>();
		for (Node node : nodes) {
			Vector2D pos = new Vector2D(getWidth()/2 - node.getWidth()/2, getHeight()/2 - node.getHeight()/2);
			positions.put(node, pos);
		}
		return positions;
	}

}
