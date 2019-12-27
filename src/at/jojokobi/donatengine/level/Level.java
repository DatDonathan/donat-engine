package at.jojokobi.donatengine.level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import at.jojokobi.donatengine.gui.DynamicGUIFactory;
import at.jojokobi.donatengine.gui.GUI;
import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.gui.SimpleGUISystem;
import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.net.MultiplayerBehavior;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.Hitbox;
import at.jojokobi.donatengine.objects.ObjectComponent;
import at.jojokobi.donatengine.objects.PlayerComponent;
import at.jojokobi.donatengine.objects.Tile;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.particles.ParticleSystem;
import at.jojokobi.donatengine.ressources.IRessourceHandler;
import at.jojokobi.donatengine.util.KeyedContainer;
import at.jojokobi.donatengine.util.KeyedHashContainer;
import at.jojokobi.donatengine.util.LongKeySupplier;
import at.jojokobi.donatengine.util.Pair;
import at.jojokobi.donatengine.util.StringKeySupplier;
import javafx.scene.canvas.GraphicsContext;


public abstract class Level extends Hitbox{
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
	
//	private long nextId = 1;
	
	
	public Level(MultiplayerBehavior behavior, double width, double height, double length) {
		super(0, 0);
		this.behavior = behavior;
		setWidth(width);
		setHeight(height);
		setLength(length);
	}
	
	protected void addComponent (LevelComponent component) {
		components.add(component);
	}
	
	public <T extends LevelComponent> T getComponent (Class<T> clazz) {
		for (LevelComponent component : components) {
			if (clazz.isInstance(component)) {
				return clazz.cast(component);
			}
		}
		return null;
	}
	
	public void update (double delta, LevelHandler handler, Camera camera) {
//		if (camera.hasMoved()) {
//			recalcObjectsInView();
//		}
//		camera.update(delta);
		camera.update(delta, this);
		particleSystem.update(delta);
		getBehavior().update(this, handler);
		if (getBehavior().isHost()) {
			components.forEach(c -> c.update(this, delta));
		}
		for (long id : objects.keySet()) {
			GameObject gameObject = objects.get(id);
			if (camera.canSee(gameObject) || updateHidden) {
				behavior.onUpdate(this, gameObject, id, handler);
				if (behavior.isHost()) {
					gameObject.hostUpdate(this, handler, camera, delta);
				}
				gameObject.update(this, handler, camera, delta);
				if (behavior.isClient()) {
					gameObject.clientUpdate(this, handler, camera, delta);
				}
			}
		}
		for (Pair<Long, GUIAction> action : guiSystem.update(this, camera.getViewWidth(), camera.getViewHeight(), handler, camera, delta)) {
			behavior.processGUIAction(this, handler, camera, action.getKey(), action.getValue());
		}
	}
	
	public void render (GraphicsContext ctx, Camera camera, IRessourceHandler ressourceHandler, boolean renderInvisible) {
		ctx.clearRect(0, 0, camera.getViewWidth(), camera.getViewHeight());
		LevelArea area = getArea(camera.getArea());
		if (area != null) {
			area.render(this, ctx, ressourceHandler, camera);
		}
		for (LevelComponent comp : components) {
			comp.renderBefore(ctx, camera, this);
		}
		List<GameObject> objects = getObjects();
		objects.sort(camera.getComparator());
		//Render objects
		for (GameObject obj : objects) {
			if (camera.canSee(obj) && (obj.isVisible() || renderInvisible)) {
				ctx.save();
				obj.render(ctx, camera, this);
				ctx.restore();
			}
		}
		//Render Particles
		particleSystem.render(ctx, camera);
		//Render object GUI
		for (GameObject obj : objects) {
			ctx.save();
			obj.renderGUI(ctx, camera, this);
			ctx.restore();
		}
		
		//GUI System
		guiSystem.render(ctx, camera.getViewWidth(), camera.getViewHeight());
		for (LevelComponent comp : components) {
			comp.renderAfter(ctx, camera, this);
		}
	}
	
	public List<LevelArea> getAreas () {
		return areas.asList();
	}
	
	public void start (Camera camera) {
//		recalcObjectsInView();
		if (guiSystem == null) {
			initGuiSystem(new SimpleGUISystem(new DynamicGUIFactory()));
		}
		if (getBehavior().isHost()) {
			generate(camera);
			components.forEach(c -> c.init(this));
			if (getBehavior().isClient()) {
				spawnPlayer(0, camera);
			}
		}
	}
	
	public abstract void generate (Camera camera);
	
	public void spawnPlayer (long client, Camera camera) {
		components.forEach(c -> c.onConnectPlayer(camera, this, client));
	}
	
	public void parseTilemap (int[][][] tilemap, TileMapParser parser, String area) {
		for (int y = 0; y < tilemap.length; y++) {
			for (int z = 0; z < tilemap[y].length; z++) {
				for (int x = 0; x < tilemap[y][z].length; x++) {
					GameObject tile = parser.parse(tilemap[y][z][x], x, tilemap.length - 1 - y, z, this);
					if (tile != null) {
						tile.setArea(area);
						spawn(tile);
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
	
	public List<GameObject> getObjectsInArea (double x, double y, double z, double width, double height, double length, String area) {
		List<GameObject> objects = new ArrayList<>();
		for (GameObject object : this.objects) {
			if (object.isColliding(x, y, z, width, height, length, area)) {
				objects.add(object);
			}
		}
		return objects;
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
	
	public List<GameObject> getSolidInArea (double x, double y, double z, double width, double height, double length, String area) {
//		List<GameObject> objects = new ArrayList<>();
//		for (GameObject object : getSolidInArea(x, y, width, height, area)) {
//			if (object.isColliding(x, y, z, width, height, length)) {
//				objects.add(object);
//			}
//		}
		List<GameObject> objects = getObjectsInArea(x, y, z, width, height, length, area);
		for (Iterator<GameObject> iter = objects.iterator(); iter.hasNext();) {
			if (!iter.next().isSolid()) {
				iter.remove();
			}
		}
		return objects;
	}
	
	public <T> List<T> getInstances (Class<T> clazz) {
		List<T> objs = new ArrayList<>();
		for (GameObject obj : objects) {
			if (clazz.isInstance(obj)) {
				objs.add(clazz.cast(obj));
			}
		}
		return objs;
	}
	
	public <T extends ObjectComponent> List<GameObject> getObjectsWithComponent (Class<T> clazz) {
		List<GameObject> objs = new ArrayList<>();
		for (GameObject obj : objects) {
			if (obj.getComponent(clazz) != null) {
				objs.add(obj);
			}
		}
		return objs;
	}
	
	public <T> T getInstance (Class<T> clazz) {
		List<T> objs = getInstances(clazz);
		return objs.isEmpty() ? null : objs.get(0);
	}
	
	public Tile getTileAt (int tileX, int tileY, String area) {
		return getTileAt(tileX, tileY, 0, area);
	}
	
	public Tile getTileAt (int tileX, int tileY, int tileZ, String area) {
		Tile tile = null;
		for (Tile t : getInstances(Tile.class)) {
			if (t.getArea().equals(area) && t.getTileX() == tileX && t.getTileY() == tileY && t.getTileZ() == tileZ) {
				tile = t;
			}
		}
		return tile;
	}
	
	/**
	 * 
	 * WARNING: Do not use this method. For internal use only.
	 * 
	 * @param obj
	 * @param id
	 */
	public void spawn (GameObject obj, long id) {
		if (getId(obj) < 0) {
//			if (!(obj instanceof Tile) || (getTileAt(((Tile) obj).getTileX(), ((Tile) obj).getTileY(), ((Tile) obj).getTileZ(), obj.getArea()) == null)) {
//				objects.put(nextId++, obj);
				objects.add(obj, id);
				obj.onSpawn(this);
				behavior.onSpawn(this, obj, id);
//			}
		}
	}
	
	public long spawn (GameObject obj) {
		long id = -1;
		if (getId(obj) < 0) {
//			if (!(obj instanceof Tile) || (getTileAt(((Tile) obj).getTileX(), ((Tile) obj).getTileY(), ((Tile) obj).getTileZ(), obj.getArea()) == null)) {
//				objects.put(nextId++, obj);
				id = objects.add(obj);
				obj.onSpawn(this);
				behavior.onSpawn(this, obj, id);
//			} 
		}
		return id;
	}
	
	public long getId (GameObject obj) {
//		for (long id : objects.keySet()) {
//			if (objects.get(id) == obj) {
//				return id;
//			}
//		}
		return objects.containsValue(obj) ? objects.getKey(obj) : -1;
	}
	
	public String getId (LevelArea area) {
//		for (long id : objects.keySet()) {
//			if (objects.get(id) == obj) {
//				return id;
//			}
//		}
		return areas.containsValue(area) ? areas.getKey(area) : "-1";
	}
	
	public String addArea (String id, LevelArea area) {
		areas.add(area, id);
		behavior.onAddArea(this, area, id);
		return id;
	}
	
	public GameObject getObjectById (long id) {
		return objects.get(id);
	}
	
	public LevelArea getArea (String id) {
		return areas.get(id);
	}
	
	public void remove (GameObject obj) {
		long id = getId(obj);
		objects.remove(id);
		behavior.onDelete(this, obj, id);
//		renderObjects.remove(obj);
	}
	
	public void clear () {
		objects.forEach(o -> o.delete(this));
	}

	public List<GameObject> getObjects() {
//		ArrayList<GameObject> objects = new ArrayList<>();
//		this.objects.entrySet().stream().forEach(o -> {
//			objects.add(o.getValue());
//		});
		return objects.asList();
	}

	@Override
	public final void setX(double x) {
		
	}
	
	@Override
	public final void setY(double y) {
		
	}
	
	@Override
	public final void setZ(double z) {
		
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
	
	public void disconnectPlayer (long client) {
		GameObject player = getPlayer(client);
		if (player != null) {
			player.delete(this);
		}
	}
	
	public GameObject getPlayer (long client) {
		GameObject player = null;
		for (GameObject obj : objects) {
			if (obj.getComponent(PlayerComponent.class) !=  null && obj.getComponent(PlayerComponent.class).getClient() == client) {
				player = obj;
			}
		}
		return player;
	}

	public ParticleSystem getParticleSystem() {
		return particleSystem;
	}

	public void end() {
		
	}

	public Map<Long, GUI> getGUIs(){
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
		}
		else {
			throw new IllegalStateException("GUISystem already initialized");
		}
	}

	public List<ObservableProperty<?>> observableProperties () {
		List<ObservableProperty<?>> properties = new ArrayList<>();
		for (LevelComponent comp : components) {
			properties.addAll(comp.observableProperties());
		}
		return properties;
	}

}
