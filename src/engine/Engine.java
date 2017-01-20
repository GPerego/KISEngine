package engine;

import engine.util.Timer;

public class Engine implements Runnable {

	private int FPS; // Frames per Second
	private int UPS; // Updates per Second
	private Window w;
	private final Timer t;
	private ILogic l;
	private final Thread loopThread;
	
	private float elapsedTime;
	private boolean running = true;

	public Engine() {
		FPS = 60;
		UPS = 30;
		w = new Window();
		t = new Timer();
		l = new ILogic() {
				public void init() {}
				public void input(Window window) {}
				public void update(float interval) {}
				public void render(Window window) {window.getCanvas().clear();}
			};
		loopThread = new Thread(this, "ENGINE_THREAD");
	}
	
	public Engine(String title, int width, int height, boolean vSync, ILogic gameLogic) {
		FPS = 60;
		UPS = 30;
		w = new Window(title, width, height, vSync);
		t = new Timer();
		l = gameLogic;
		loopThread = new Thread(this, "ENGINE_THREAD");
	}
	
	public Engine(String title, int width, int height, ILogic gameLogic) {
		FPS = 60;
		UPS = 30;
		w = new Window(title, width, height);
		t = new Timer();
		l = gameLogic;
		loopThread = new Thread(this, "ENGINE_THREAD");
	}
	
	public Engine(Window window, ILogic gameLogic) {
		FPS = 60;
		UPS = 30;
		w = window;
		t = new Timer();
		l = gameLogic;
		loopThread = new Thread(this, "ENGINE_THREAD");
	}

	public void start() {
		String osName = System.getProperty("os.name");
		if ( osName.contains("Mac") ) {
			loopThread.run();
		} else {
			loopThread.start();
		}
	}

	public void run() {
		init();
		loop();

		w.close();
	}

	protected void init() {
		w.init();
		w.setvSync(false);
		t.init();
		l.init();
	}

	protected void loop() {
		float accumulator = 0f;

		while (running && !w.shouldClose()) {
			elapsedTime = t.getElapsedTime();
			accumulator += elapsedTime;

			input();

			while (accumulator >= (1f / UPS)) {
				update((1f / UPS));
				accumulator -= (1f / UPS);
			}

			render();

			if (!w.isvSync()) {
				sync();
			}
		}

	}

	protected void input() {
		l.input(w);
	}

	protected void update(float interval) {
		l.update(interval);
	}
	
	protected void render() {
		l.render(w);
		w.update();
	}
	
	private void sync() {
        float loopSlot = 1f / FPS;
        double endTime = t.getLastTime() + loopSlot;
        while (t.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
            }
        }
}
}
