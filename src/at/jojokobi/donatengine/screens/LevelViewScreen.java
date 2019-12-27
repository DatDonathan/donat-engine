package at.jojokobi.donatengine.screens;


import at.jojokobi.fxengine.Game;
import at.jojokobi.fxengine.level.Level;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
@Deprecated
public abstract class LevelViewScreen extends Screen{

	private Canvas canvas = new Canvas();
	private Level level;
	private boolean renderInvisible = false;
	
	public LevelViewScreen(Game game) {
		super(game);
	}
	
	protected void initScene () {
		setScene(createScene(canvas));
	}
	
	protected abstract Scene createScene (Canvas canvas);

	@Override
	public void update(double delta) {
		
	}

	@Override
	public synchronized void render () {
		GraphicsContext ctx = canvas.getGraphicsContext2D();
		ctx.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		if (level != null) {
			level.render(canvas.getGraphicsContext2D(), renderInvisible);
		}
	}

	protected Canvas getCanvas() {
		return canvas;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		if (this.level != null) {
			this.level.end();
		}
		this.level = level;
		level.clear();
		level.generate();
		if (level.getCamera() != null) {
			canvas.setWidth(level.getCamera().getViewWidth());
			canvas.setHeight(level.getCamera().getViewHeight());
		}
/*		if (level.getCamera() != null) {
			if (level.getCamera().getPerspective() == RenderPerspecitve.X_Y_TOP_DOWN) {
				canvas.setWidth(level.getCamera().getWidth());
				canvas.setHeight(level.getCamera().getHeight());
			}
			else if (level.getCamera().getPerspective() == RenderPerspecitve.X_Z_TOP_DOWN) {
				canvas.setWidth(level.getCamera().getWidth());
				canvas.setHeight(level.getCamera().getLength());
			}
		}*/
	}

	public boolean isRenderInvisible() {
		return renderInvisible;
	}

	public void setRenderInvisible(boolean renderInvisible) {
		this.renderInvisible = renderInvisible;
	}
	
}
