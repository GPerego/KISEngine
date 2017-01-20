package engine;

public class EmptyLogic implements ILogic {
	public void init() {}
	public void input(Window window) {}
	public void update(float interval) {}
	public void render(Window window) { window.getCanvas().clear(); }
}
