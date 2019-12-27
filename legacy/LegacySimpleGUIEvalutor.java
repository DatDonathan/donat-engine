package at.jojokobi.donatengine.gui;

import java.util.Map;

import at.jojokobi.fxengine.level.LevelHandler;

@Deprecated
@FunctionalInterface
public interface LegacySimpleGUIEvalutor {

	public GUI evalute (GUI gui, String cause, Map<String, String> values, LevelHandler handler);
	
}
