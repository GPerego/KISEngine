package engine;

public interface ILogic {
	void init();

	void input(Window window);

	void update(float interval);

	void render(Window window);
}
