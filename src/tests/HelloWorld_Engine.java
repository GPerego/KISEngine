package tests;

import org.lwjgl.Version;

import engine.EmptyLogic;
import engine.Engine;
import engine.Window;

public class HelloWorld_Engine{

	public static void main(String[] args) {

		Window w = new Window("Hello World!",300,300);
		w.setClearColor(1f, 0f, 0f);

		EmptyLogic l = new EmptyLogic() {
			@Override
			public void init() { System.out.println("Hello LWJGL " + Version.getVersion() + "!"); }
		};

		new Engine(w, l).start();
	}
}
