package at.jojokobi.donatengine.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import at.jojokobi.donatengine.event.StartEvent;
import at.jojokobi.donatengine.event.StopEvent;
import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.gui.DynamicGUIFactory;
import at.jojokobi.donatengine.gui.GUI;
import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.gui.SimpleGUISystem;
import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.net.MultiplayerBehavior;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.Collidable;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.ObjectComponent;
import at.jojokobi.donatengine.objects.PlayerComponent;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.particles.ParticleSystem;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.tiles.MapTileSystem;
import at.jojokobi.donatengine.tiles.Tile;
import at.jojokobi.donatengine.tiles.TileSystem;
import at.jojokobi.donatengine.util.KeyedContainer;
import at.jojokobi.donatengine.util.KeyedHashContainer;
import at.jojokobi.donatengine.util.LongKeySupplier;
import at.jojokobi.donatengine.util.Pair;
import at.jojokobi.donatengine.util.StringKeySupplier;
import at.jojokobi.donatengine.util.Vector3D;


public abstract class Level {
//	
//	public static final Comparator<GameObject> ZY_COMPARATOR = new Comparator<GameObject>() {
//		@Override
//		public int compare(GameObject o1, GameObject o2) {
//			int compare = 0;
//			if (o1.getZ() + o1.getLength() < o2.getZ() + o2.getLength()) {
//				compare = -1;
//			}
//			else if (o1.getZ() + o1.getLength() > o2.getZ() + o2.getLength()) {
//				compare = 1;
//			}
//			else if (o1.getY() + o1.getHeight() < o2.getY() + o2.getHeight()) {
//				compare = -1;
//			}
//			else if (o1.getY() + o1.getHeight() > o2.getY() + o2.getHeight()) {
//				compare = 1;
//			}
//			return compare;
//		}
//	};
//	
//	public static final Comparator<GameObject> YZ_COMPARATOR = new Comparator<GameObject>() {
//		@Override
//		public int compare(GameObject o1, GameObject o2) {
//			int compare = 0;
//			if (o1.getY() < o2.getY()) {
//				compare = -1;
//			}
//			else if (o1.getY() > o2.getY()) {
//				compare = 1;
//			}
//			else if (o1.getZ() + o1.getLength() < o2.getZ() + o2.getLength()) {
//				compare = -1;
//			}
//			else if (o1.getZ() + o1.getLength() > o2.getZ() + o2.getLength()) {
//				compare = 1;
//			}
//			return compare;
//		}
//	};

	private KeyedContainer<Long, GameObject> objects = new KeyedHashContainer<>(new LongKeySupplier());
	private KeyedContainer<String, LevelArea> areas = new KeyedHashContainer<>(new StringKeySupplier());
	private List<LevelComponent> components = new ArrayList<>();
	private boolean updateHidden = true;
	private ParticleSystem particleSystem = new ParticleSystem();
	private GUISystem guiSystem;
	private MultiplayerBehavior behavior;
	private long clientId = 0;
	private Camera camera = new Camera(0, 0, 0, 1280, 768);
	
	private TileSystem tileSystem;

//	private long nextId = 1;

	public Level(MultiplayerBehavior behavior) {
		this.behavior = behavior;
	}

	protected void addComponent(LevelComponent component) {
		components.add(component);
	}

	public <T extends LevelComponent> T getComponent(Class<T> clazz) {
		for (LevelComponent component : components) {
			if (clazz.isInstance(component)) {
				return clazz.cast(component);
			}
		}
		return null;
	}

	public void update(UpdateEvent event) {
//		if (camera.hasMoved()) {
//			recalcObjectsInView();
//		}
//		camera.update(delta);
		camera.update(event, this);
		particleSystem.update(event.getDelta());
		getBehavior().update(this, event);
		if (getBehavior().isHost()) {
			components.forEach(c -> c.hostUpdate(this, event));
		}
		components.forEach(c -> c.update(this, event));
		if (getBehavior().isClient()) {
			components.forEach(c -> c.clientUpdate(this, event));
		}
		for (long id : objects.keySet()) {
			GameObject gameObject = objects.get(id);
			if (updateHidden || gameObject.isAlwaysUpdate() || gameObject.isNeedsUpdate()) {
				behavior.onUpdate(this, gameObject, id, event);
				if (behavior.isHost()) {
					gameObject.hostUpdate(this, event);
				}
				gameObject.update(this, event);
				if (behavior.isClient()) {
					gameObject.clientUpdate(this, event);
				}
			}
		}
		for (Pair<Long, GUIAction> action : guiSystem.update(this, camera.getViewWidth(), camera.getViewHeight(), event)) {
			behavior.processGUIAction(this, event.getGame(), action.getKey(), action.getValue());
		}
	}

	public void render(List<RenderData> data, boolean renderInvisible) {
		LevelArea area = getArea(camera.getArea());
		if (area != null) {
			area.render(this, data, camera);
		}
		for (LevelComponent comp : components) {
			comp.renderBefore(data, camera, this);
		}
		List<GameObject> objects = getObjects();
		// Render objects
		for (GameObject obj : objects) {
			if (obj.isVisible() || renderInvisible) {
				obj.render(data, camera, this);
			}
		}
		// Render Particles
		particleSystem.render(data, camera);
		// Render object GUI
		for (GameObject obj : objects) {
			obj.renderGUI(data, camera, this);
		}

		// GUI System
		guiSystem.render(clientId, data, camera.getViewWidth(), camera.getViewHeight());
		for (LevelComponent comp : components) {
			comp.renderAfter(data, camera, this);
		}
	}

	public List<LevelArea> getAreas() {
		return areas.asList();
	}

	public void start(StartEvent event) {
//		recalcObjectsInView();
		if (guiSystem == null) {
			initGuiSystem(new SimpleGUISystem(new DynamicGUIFactory()));
		}
		if (tileSystem == null) {
			initTileSystem(new MapTileSystem(32));
		}
		if (getBehavior().isHost()) {
			generate(camera);
			components.forEach(c -> c.init(this, event));
			if (getBehavior().isClient()) {
				spawnPlayer(0);
			}
		}
	}

	public abstract void generate(Camera camera);

	public void spawnPlayer(long client) {
		components.forEach(c -> c.onConnectPlayer(this, client));
	}

	public void parseTilemap(int[][][] tilemap, TileMapParser parser, String area) {
		parseTilemap(tilemap, parser, 1, 1, 1, area);
	}

	public void parseTilemap(int[][][] tilemap, TileMapParser parser, double tileWidth, double tileHeight,
			double tileLength, String area) {
		for (int y = 0; y < tilemap.length; y++) {
			for (int z = 0; z < tilemap[y].length; z++) {
				for (int x = 0; x < tilemap[y][z].length; x++) {
					for (GameObject tile : parser.parse(tilemap[y][z][x], x * tileWidth,
							(tilemap.length - 1 - y) * tileHeight, z * tileLength)) {
						if (tile != null) {
							tile.setArea(area);
							spawn(tile);
						}
					}
				}
			}
		}
	}

//	protected void recalcObjectsInView () {
//		renderObjects.clear();
//		for (GameObject gameObject : getObjects()) {
//			if (gameObject.isAlwaysUpdate() || gameObject.canRender(camera)) {
//				renderObjects.add(gameObject);
//			}
//		}
//	}

//	public synchronized List<GameObject> getObjectsInArea (double x, double y, double width, double height, long area) {
//		List<GameObject> objects = new ArrayList<>();
//		for (GameObject object : this.objects) {
//			if (object.isColliding(x, y, width, height)) {
//				objects.add(object);
//			}
//		}
//		return objects;
//	}
	
	public <T extends GameObject> List<T> getObjectsInArea(double x, double y, double z, double width, double height, double length,
			String area, Class<T> clazz) {
		List<T> objects = new ArrayList<>();
		for (T object : getInstances(clazz)) {
			if (object.isColliding(x, y, z, width, height, length, area)) {
				objects.add(object);
			}
		}
		return objects;
	}

	public List<GameObject> getObjectsInArea(double x, double y, double z, double width, double height, double length,
			String area) {
		List<GameObject> objects = new ArrayList<>();
		for (GameObject object : getObjects()) {
			if (object.isColliding(x, y, z, width, height, length, area)) {
				objects.add(object);
			}
		}
		return objects;
	}
	
	public List<Collidable> getCollidablesInArea(double x, double y, double z, double width, double height, double length,
			String area) {
		List<Collidable> collidables = new ArrayList<>();
		collidables.addAll(getObjectsInArea(x, y, z, width, height, length, area));
		collidables.addAll(tileSystem.getTilesInAbsoluteArea(x, y, z, width, height, length, area));
		return collidables;
	}

//	public List<GameObject> getSolidInArea (double x, double y, double width, double height, long area) {
//		List<GameObject> objects = getObjectsInArea(x, y, width, height, area);
//		for (Iterator<GameObject> iter = objects.iterator(); iter.hasNext();) {
//			if (!iter.next().isSolid()) {
//				iter.remove();
//			}
//		}
//		return objects;
//	}

	public List<Collidable> getSolidInArea(double x, double y, double z, double width, double height, double length,
			String area) {
		return getCollidablesInArea(x, y, z, width, height, length, area).stream().filter(c -> c.isSolid()).collect(Collectors.toList());
	}

	public <T> List<T> getInstances(Class<T> clazz) {
		List<T> objs = new ArrayList<>();
		for (GameObject obj : getObjects()) {
			if (clazz.isInstance(obj)) {
				objs.add(clazz.cast(obj));
			}
		}
		return objs;
	}

	public <T extends ObjectComponent> List<GameObject> getObjectsWithComponent(Class<T> clazz) {
		List<GameObject> objs = new ArrayList<>();
		for (GameObject obj : getObjects()) {
			if (obj.getComponent(clazz) != null) {
				objs.add(obj);
			}
		}
		return objs;
	}

	public <T> T getInstance(Class<T> clazz) {
		List<T> objs = getInstances(clazz);
		return objs.isEmpty() ? null : objs.get(0);
	}

	public Vector3D calcMotion(double x, double y, double z, String area, double width, double height, double length,
			double xMotion, double yMotion, double zMotion, boolean slide, List<Object> ignore) {
		Vector3D pos = new Vector3D();
		boolean blocked = false;
		// Y
		List<Collidable> yObjs = null;
		if (yMotion < 0) {
			yObjs = getSolidInArea(x, y + yMotion, z, width, height - yMotion, length, area);
		} else {
			yObjs = getSolidInArea(x, y, z, width, height + yMotion, length, area);
		}
		yObjs.removeAll(ignore);
		if (yObjs.isEmpty()) {
			pos.setY(y + yMotion);
		}
		// Positive
		else if (yMotion > 0) {
			Collidable yObj = yObjs.get(0);
			for (int i = 1; i < yObjs.size(); i++) {
				if (yObj.getY() > yObjs.get(i).getY()) {
					yObj = yObjs.get(i);
				}
			}
			pos.setY(yObj.getY() - height);
			blocked = true;
		}
		// Negative
		else if (yMotion < 0) {
			Collidable yObj = yObjs.get(0);
			for (int i = 1; i < yObjs.size(); i++) {
				if (yObj.getY() + yObj.getHeight() < yObjs.get(i).getY() + yObjs.get(i).getHeight()) {
					yObj = yObjs.get(i);
				}
			}
			pos.setY(yObj.getY() + yObj.getHeight());
			blocked = true;
		}
		
		// X
		if (slide || !blocked) {
			List<Collidable> xObjs = null;
			if (xMotion < 0) {
				xObjs = getSolidInArea(x + xMotion, y, z, width - xMotion, height, length,area);
			} else {
				xObjs = getSolidInArea(x, y, z, width + xMotion, height, length, area);
			}
			xObjs.removeAll(ignore);
			if (xObjs.isEmpty()) {
				pos.setX(x + xMotion);
			}
			// Positive
			else if (xMotion > 0) {
				Collidable xObj = xObjs.get(0);
				for (int i = 1; i < xObjs.size(); i++) {
					if (xObj.getX() > xObjs.get(i).getX()) {
						xObj = xObjs.get(i);
					}
				}
				pos.setX(xObj.getX() - width);
				blocked = true;
			}
			// Negative
			else if (xMotion < 0) {
				Collidable xObj = xObjs.get(0);
				for (int i = 1; i < xObjs.size(); i++) {
					if (xObj.getX() + xObj.getWidth() < xObjs.get(i).getX() + xObjs.get(i).getWidth()) {
						xObj = xObjs.get(i);
					}
				}
				pos.setX(xObj.getX() + xObj.getWidth());
				blocked = true;
			}
		}

		// Z
		if (slide || !blocked) {
			List<Collidable> zObjs = null;
			if (zMotion < 0) {
				zObjs = getSolidInArea(x, y, z + zMotion, width, height, length - zMotion,area);
			} else {
				zObjs = getSolidInArea(x, y, z, width, height, length + zMotion, area);
			}
			zObjs.removeAll(ignore);
			if (zObjs.isEmpty()) {
				pos.setZ(z + zMotion);
			}
			// Positive
			else if (zMotion > 0) {
				Collidable zObj = zObjs.get(0);
				for (int i = 1; i < zObjs.size(); i++) {
					if (zObj.getZ() > zObjs.get(i).getZ()) {
						zObj = zObjs.get(i);
					}
				}
				pos.setZ(zObj.getZ() - length);
				blocked = true;
			}
			// Negative
			else if (zMotion < 0) {
				Collidable zObj = zObjs.get(0);
				for (int i = 1; i < zObjs.size(); i++) {
					if (zObj.getZ() + zObj.getLength() < zObjs.get(i).getZ() + zObjs.get(i).getLength()) {
						zObj = zObjs.get(i);
					}
				}
				pos.setZ(zObj.getZ() + zObj.getLength());
				blocked = true;
			}
		}
		return pos;
	}

	/**
	 * 
	 * WARNING: Do not use this method. For internal use only.
	 * 
	 * @param obj
	 * @param id
	 */
	public void spawn(GameObject obj, long id) {
		if (obj != null && getId(obj) < 0) {
			objects.add(obj, id);
			obj.onSpawn(this);
			behavior.onSpawn(this, obj, id);
		}
	}

	public long spawn(GameObject obj) {
		long id = -1;
		if (obj != null && getId(obj) < 0) {
			id = objects.add(obj);
			obj.onSpawn(this);
			behavior.onSpawn(this, obj, id);
		}
		return id;
	}

	public long getId(GameObject obj) {
//		for (long id : objects.keySet()) {
//			if (objects.get(id) == obj) {
//				return id;
//			}
//		}
		return objects.containsValue(obj) ? objects.getKey(obj) : -1;
	}

	public String getId(LevelArea area) {
//		for (long id : objects.keySet()) {
//			if (objects.get(id) == obj) {
//				return id;
//			}
//		}
		return areas.containsValue(area) ? areas.getKey(area) : "-1";
	}

	public String addArea(String id, LevelArea area) {
		areas.add(area, id);
		behavior.onAddArea(this, area, id);
		return id;
	}

	public GameObject getObjectById(long id) {
		return objects.get(id);
	}

	public LevelArea getArea(String id) {
		return areas.get(id);
	}

	public void remove(GameObject obj) {
		long id = getId(obj);
		if (objects.remove(id) != null) {
			behavior.onDelete(this, obj, id);
		}
//		renderObjects.remove(obj);
	}

	public void clear() {
		objects.forEach(o -> o.delete(this));
	}

	public List<GameObject> getObjects() {
//		ArrayList<GameObject> objects = new ArrayList<>();
//		this.objects.entrySet().stream().forEach(o -> {
//			objects.add(o.getValue());
//		});
		return objects.asList();
	}

	public boolean isUpdateHidden() {
		return updateHidden;
	}

	protected void setUpdateHidden(boolean updateHidden) {
		this.updateHidden = updateHidden;
	}

//	public AudioSystem getAudioSystem () {
//		return getScreen().getGame().getAudioSystem();
//	}

	public void disconnectPlayer(long client) {
		GameObject player = getPlayer(client);
		if (player != null) {
			player.delete(this);
		}
	}

	public GameObject getPlayer(long client) {
		GameObject player = null;
		for (GameObject obj : getObjects()) {
			if (obj.getComponent(PlayerComponent.class) != null
					&& obj.getComponent(PlayerComponent.class).getClient() == client) {
				player = obj;
			}
		}
		return player;
	}

	public ParticleSystem getParticleSystem() {
		return particleSystem;
	}

	public void stop(StopEvent event) {

	}

	public Map<Long, GUI> getGUIs() {
		return guiSystem.getGUIs();
	}

	public MultiplayerBehavior getBehavior() {
		return behavior;
	}

	public long getClientId() {
		return clientId;
	}

	/**
	 * 
	 * WARNING: Do not use this function. For internal use only.
	 * 
	 * @param clientId
	 */
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public GUISystem getGuiSystem() {
		return guiSystem;
	}

	public void initGuiSystem(GUISystem guiSystem) {
		if (this.guiSystem == null) {
			this.guiSystem = guiSystem;
			guiSystem.addListener(new GUISystem.Listener() {

				@Override
				public void onRemoveGUI(GUISystem guiSystem, GUI gui, long id) {
					getBehavior().onRemoveGUI(guiSystem, gui, id);
				}

				@Override
				public void onAddGUI(GUISystem guiSystem, GUI gui, long id) {
					getBehavior().onAddGUI(guiSystem, gui, id);
				}
			});
		} else {
			throw new IllegalStateException("GUISystem already initialized");
		}
	}
	
	public void initTileSystem (TileSystem tileSystem) {
		if (this.tileSystem == null) {
			this.tileSystem = tileSystem;
			tileSystem.addListener(new TileSystem.Listener() {
				
				@Override
				public void onRemove(int tileX, int tileY, int tileZ, String area) {
					getBehavior().onRemove(tileX, tileY, tileZ, area);
				}
				
				@Override
				public void onPlace(Tile tile, int tileX, int tileY, int tileZ, String area) {
					getBehavior().onPlace(tile, tileX, tileY, tileZ, area);
				}
			});
		} else {
			throw new IllegalStateException("GUISystem already initialized");
		}
	}

	public List<ObservableProperty<?>> observableProperties() {
		List<ObservableProperty<?>> properties = new ArrayList<>();
		for (LevelComponent comp : components) {
			properties.addAll(comp.observableProperties());
		}
		return properties;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public TileSystem getTileSystem() {
		return tileSystem;
	}

}
