package at.jojokobi.donatengine.leveleditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import at.jojokobi.fxengine.Game;
import at.jojokobi.fxengine.audio.AudioSystem;
import at.jojokobi.fxengine.input.ClickInformation;
import at.jojokobi.fxengine.input.Input;
import at.jojokobi.fxengine.level.Level;
import at.jojokobi.fxengine.level.LevelHandler;
import at.jojokobi.fxengine.objects.GameObject;
import at.jojokobi.fxengine.rendering.Perspective;
import at.jojokobi.fxengine.rendering.StretchYZPerspective;
import at.jojokobi.fxengine.rendering.ThreeDimensionalPerspective;
import at.jojokobi.fxengine.rendering.TwoDimensionalPerspective;
import at.jojokobi.fxengine.screens.LevelViewScreen;
import at.jojokobi.fxengine.util.Vector2D;
import at.jojokobi.fxengine.util.Vector3D;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
@Deprecated
public class LevelEditorScreen extends LevelViewScreen{

	private double placeCooldown = 0;
	private boolean playing = false;
	
	private List<LevelEditorEntry> entries = new ArrayList<>();
	private ListView<LevelEditorEntry> menu;
	
	private File saveFile = new File("level.xml");
	private MapLoader loader = new XMLMapLoader();
	
	public LevelEditorScreen(Game game, List<LevelEditorEntry> entries) {
		super(game);
		this.entries.addAll(entries);
		initScene();
		setRenderInvisible(true);
	}
	
	@Override
	public void update(double delta) {
		super.update(delta);
		if (getLevel() != null) {
			//Cam control
			if (getInput().getKey(KeyCode.PLUS)) {
				getLevel().getCamera().setRotationX(Math.min(getLevel().getCamera().getRotationX() + 60 * delta, 90));
			}
			else if (getInput().getKey(KeyCode.MINUS)) {
				getLevel().getCamera().setRotationX(Math.max(getLevel().getCamera().getRotationX() - 60 * delta, 0));
			}
			
			//Place
			//Click
			if (placeCooldown <= 0) {
				Vector2D pos = new Vector2D(getInput().getMouseX(), getInput().getMouseY());
				//Remove
				if (getInput().getMouseButton(MouseButton.SECONDARY)) {
					ClickInformation click = getLevel().getCamera().getClickedObject(pos, getLevel().getObjects());
					if (click != null) {
						click.getGameObject().delete(getLevel());
					}
					placeCooldown = 0.5;
				}
				//Place
				else if (getInput().getMouseButton(MouseButton.PRIMARY)) {
					ClickInformation click = getLevel().getCamera().getClickedObject(pos, getLevel().getObjects());
					if (click != null) {
						Vector3D place = new Vector3D(click.getGameObject().getX(), click.getGameObject().getY(), click.getGameObject().getZ());
						GameObject obj = menu.getSelectionModel().getSelectedItem().createInstance(0, 0, 0, getLevel());
						switch (click.getDirection()) {
						case FRONT:
							place.add(0, 0, click.getGameObject().getLength());
							break;
						case LEFT:
							place.add(-obj.getWidth(), 0, 0);
							break;
						case RIGHT:
							place.add(click.getGameObject().getWidth(), 0, 0);
							break;
						case TOP:
							place.add(0, click.getGameObject().getHeight(), 0);
							break;
						case BOTTOM:
							place.add(0, -obj.getHeight(), 0);
							break;
						case BACK:
							place.add(0, 0, -obj.getLength());
							break;			
						}
						obj.setX(place.getX());
						obj.setY(place.getY());
						obj.setZ(place.getZ());
						getLevel().spawn(obj);
						placeCooldown = 0.25;
					}
				}
			}
			
			if (playing) {
				getLevel().update(delta, new LevelHandler() {
					
					@Override
					public AudioSystem getAudioSystem(long clientId) {
						return getGame().getAudioSystem();
					}
					
					@Override
					public Input getInput(long clientId) {
						return LevelEditorScreen.this.getInput();
					}
					
					@Override
					public void changeLevel(Level level) {
						setLevel(level);
					}
				});
			}
			//Movement
			else {
				//Left - Right
				if (getInput().getKey(KeyCode.A)) {
					getLevel().getCamera().setX(getLevel().getCamera().getX() - 500 * delta);
				}
				else if (getInput().getKey(KeyCode.D)) {
					getLevel().getCamera().setX(getLevel().getCamera().getX() + 500 * delta);
				}
				
				//Forwards - Backwards
				if (getInput().getKey(KeyCode.W)) {
					getLevel().getCamera().setZ(getLevel().getCamera().getZ() - 500 * delta);
				}
				else if (getInput().getKey(KeyCode.S)) {
					getLevel().getCamera().setZ(getLevel().getCamera().getZ() + 500 * delta);
				}
				
				//Up - Down
				if (getInput().getKey(KeyCode.SHIFT)) {
					getLevel().getCamera().setY(getLevel().getCamera().getY() - 500 * delta);
				}
				else if (getInput().getKey(KeyCode.SPACE)) {
					getLevel().getCamera().setY(getLevel().getCamera().getY() + 500 * delta);
				}
			}
			
			placeCooldown -= delta;
			if (placeCooldown < 0) {
				placeCooldown = 0;
			}
		}
	}

	@Override
	protected Scene createScene(Canvas canvas) {
		BorderPane pane = new BorderPane(canvas);
		menu = new ListView<>(FXCollections.observableArrayList(entries));
		menu.setOrientation(Orientation.HORIZONTAL);
		menu.getSelectionModel().selectFirst();
		menu.setPrefHeight(50);
		
		Button play = new Button("Play");
		play.setPrefHeight(50);
		play.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				playing = !playing;
				if (playing) {
					save();
					getLevel().start();
					play.setText("Stop");
				}
				else {
					load();
					play.setText("Play");
				}
				canvas.requestFocus();
			}
		});
		
		ComboBox<Perspective> perspective = new ComboBox<>();
		perspective.setPrefHeight(50);
		perspective.getItems().addAll(new StretchYZPerspective(), new ThreeDimensionalPerspective(), new TwoDimensionalPerspective());
		perspective.getSelectionModel().selectFirst();
		perspective.valueProperty().addListener(new ChangeListener<Perspective>() {
			@Override
			public void changed(ObservableValue<? extends Perspective> value, Perspective oldValue, Perspective newValue) {
				getLevel().getCamera().setPerspective(newValue);
			}
		});
		
		Button save = new Button("Save");
		save.setPrefHeight(50);
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!playing) {
					//Choose file
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Where do you want to save you level?");
					fileChooser.setInitialDirectory(saveFile.getParentFile());
					fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter ("XML Level Files", "*.xml"));
					File temp = fileChooser.showSaveDialog(getGame().getStage());
					//Load
					if (temp != null) {
						saveFile = temp;
						save();
					}
				}
			}
		});
		
		Button load = new Button("Load");
		load.setPrefHeight(50);
		load.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!playing) {
					//Choose file
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Select the level to load!");
					fileChooser.setInitialDirectory(saveFile.getParentFile());
					fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter ("XML Level Files", "*.xml"));
					File temp = fileChooser.showOpenDialog(getGame().getStage());
					//Load
					if (temp != null) {
						saveFile = temp;
						load();
					}
				}
			}
		});
		
		HBox box = new HBox(play, menu, perspective, save, load);
		pane.setBottom(box);
		
		canvas.requestFocus();
		return new Scene(pane);
	}
	
	private void save () {
		try {
			loader.save(new FileOutputStream(saveFile), getLevel());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void load () {
		try {
			getLevel().clear();
			loader.load(new FileInputStream(saveFile), getLevel());
		} catch (FileNotFoundException | InvalidLevelFileException e) {
			e.printStackTrace();
		}
	}

}
