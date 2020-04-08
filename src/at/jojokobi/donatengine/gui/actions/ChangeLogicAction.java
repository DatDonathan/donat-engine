package at.jojokobi.donatengine.gui.actions;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.function.Supplier;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.GameLogic;
import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;

public class ChangeLogicAction implements GUIAction {
	
	private Supplier<GameLogic> logicSupplier;

	public ChangeLogicAction(Supplier<GameLogic> logicSupplier) {
		super();
		this.logicSupplier = logicSupplier;
	}
	
	public ChangeLogicAction() {

	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public void perform(Level level, Game game, long id, GUISystem system) {
		game.changeLogic(logicSupplier.get());
	}

	@Override
	public boolean executeOnClient() {
		return true;
	}

}
