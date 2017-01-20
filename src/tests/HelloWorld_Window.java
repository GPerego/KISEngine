package tests;
import org.lwjgl.Version;

import engine.Window;

public class HelloWorld_Window {

	private Window w = new Window("Hello World!",300,300);

	public void run() {
		init();
		loop();

		w.close();
	}

	private void init() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		w.init();
		w.getCanvas().setClearColor(1f, 0, 0);
	}

	private void loop() {
		while ( !w.shouldClose() ) {
			w.getCanvas().clear();
			w.update();
		}
	}

	public static void main(String[] args) {
		new HelloWorld_Window().run();
	}

}