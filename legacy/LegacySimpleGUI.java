package at.jojokobi.donatengine.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.jojokobi.fxengine.gui.actions.GUIAction;
import at.jojokobi.fxengine.input.Input;
import javafx.scene.canvas.GraphicsContext;

@Deprecated
public class LegacySimpleGUI implements GUI {
	
	private LegacySimpleGUIEvalutor evaluator;
	
	private Map<String, LegacySimpleGUIComponent> components = new HashMap<>();
	private String selected = null;

	public LegacySimpleGUI(LegacySimpleGUIEvalutor evaluator) {
		super();
		this.evaluator = evaluator;
	}

	@Override
	public void render(GraphicsContext ctx, double width, double height) {
		for (String id : components.keySet()) {
			ctx.save();
			components.get(id).render(ctx, id.equals(selected), false, false, width, height);
			ctx.restore();
		}
	}

	@Override
	public void update(GUISystem system, Input input, double width, double height, double delta) {
		for (String id : components.keySet()) {
			LegacySimpleGUIComponent comp = components.get(id);
			//Mouse
				if (input.getButton(Input.PRIMARY_BUTTON) && comp.getRect().collides(width, height, input.getCursorX(), input.getCursorY(), 0, 0)) {
					selected = id;
					if (comp.onClick(input.getCursorX() - comp.getRect().getLeft(width, height), input.getCursorY() - comp.getRect().getTop(width, height))) {
						GUI next = evaluator.evalute(this, id, getComponentValues(), null);
						if (next == null) {
							system.removeGUI(system.getID(this));
						}
						else if (next != this) {
							system.removeGUI(system.getID(this));
							system.addGUI(next);
						}
					}
				}
		}
	}
	
	private Map<String, String> getComponentValues () {
		Map<String, String> map = new HashMap<>();
		for (String id : components.keySet()) {
			if (components.get(id).getValue() != null) {
				map.put(id, components.get(id).getValue());
			}
		}
		return map;
	}
	
	public void addComponent (String identifier, LegacySimpleGUIComponent comp) {
		components.put(identifier, comp);
	}

	@Override
	public List<GUIAction> fetchActions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
