package at.jojokobi.donatengine.input;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import at.jojokobi.donatengine.util.Vector2D;

public class SimpleInput implements Input {

	private Set<String> keys = new HashSet<>();
	private Map<String, Vector2D> axis = new HashMap<>();
	private Set<String> pressed = new HashSet<>();
	private Set<String> released = new HashSet<>();
	
	private Map<String, Boolean> changedButtons = new HashMap<>();
	private Map<String, Vector2D> changedAxis = new HashMap<>();

	@Override
	public boolean getButton(String code) {
		return keys.contains(code);
	}

	@Override
	public Vector2D getAxis(String axis) {
		return this.axis.containsKey (axis) ? this.axis.get(axis).clone() : new Vector2D();
	}

	@Override
	public void reset() {
		for (String string : keys) {
			changedButtons.put(string, false);
		}
		for (String string : axis.keySet()) {
			changedAxis.put(string, null);
		}
		keys.clear();
		axis.clear();
	}

	@Override
	public boolean setButton(String code, boolean pressed) {
		if (pressed) {
			this.keys.add(code);
			this.pressed.add(code);
		}
		else {
			this.keys.remove(code);
			this.released.add(code);
		}
		changedButtons.put(code, pressed);
		return true;
	}

	@Override
	public boolean setAxis(String axis, Vector2D vector) {
		this.axis.put(axis, vector);
		changedAxis.put(axis, vector);
		return true;
	}

	@Override
	public Map<String, Boolean> fetchChangedButtons() {
		var map = new HashMap<>(changedButtons);
		changedButtons.clear ();
		return map;
	}

	@Override
	public Map<String, Vector2D> fetchChangedAxis() {
		var map = new HashMap<>(changedAxis);
		changedAxis.clear();
		return map;
	}

	@Override
	public List<String> getTypedChars() {
		return Arrays.asList();
	}

	@Override
	public void updateBuffers() {
		pressed.clear();
		released.clear();
	}

	@Override
	public boolean isPressed(String button) {
		return pressed.contains(button);
	}
	
	@Override
	public boolean isReleased(String button) {
		return released.contains(button);
	}

}
