package engine;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import engine.util.Color;

public class Window {
	/** Handle to this Window, used by some functions of GLFW*/
	private long windowHandle;

	/** Whether GLFW is initialized or not*/
	private boolean initialized;
	/** Whether window is created or not*/
	private boolean created;

	/** Window's title*/
	private String title;
	/** Window's width*/
	private int width;
	/** Window's height*/
	private int height;
	/** Whether window is resizable or not*/
	private final boolean resizable;

	/** Window's Error Callback. 
	 * @see GLFWErrorCallback
	 */
	private GLFWErrorCallback errorCallback;
	/** Window's Key Callback
	 * @see GLFWKeyCallback
	 */
	private GLFWKeyCallback keyCallback;
	/** Window's Size Callback
	 * @see GLFWWindowSizeCallback
	 */
	private GLFWWindowSizeCallback windowSizeCallback;

	/** Window's current resized status*/
	private boolean resized;
	/** Window's current vSync status*/
	private boolean vSync;

	private Canvas canvas;

	/********************
	 *** Constructors ***
	 ********************/

	public Window(String title, int width, int height, boolean resizable, GLFWErrorCallback errorCallback, GLFWKeyCallback keyCallback, GLFWWindowSizeCallback windowSizeCallback, boolean vSync) {
		this.setInitialized(false);
		this.setCreated(false);
		this.setResized(false);

		this.setTitle(title);
		this.setWidth(width);
		this.setHeight(height);

		this.resizable = resizable;

		this.setErrorCallback(errorCallback);
		this.setKeyCallback(keyCallback);
		this.setWindowSizeCallback(windowSizeCallback);

		this.setvSync(vSync);

		this.setCanvas(new Canvas(width, height));
	}

	public Window(String title, int width, int height, boolean resizable, boolean vSync) {
		this.setInitialized(false);
		this.setCreated(false);
		this.setResized(false);

		this.setTitle(title);
		this.setWidth(width);
		this.setHeight(height);

		this.resizable = resizable;

		this.setvSync(vSync);

		this.setCanvas(new Canvas(width, height));
	}

	public Window(String title, int width, int height, boolean vSync) {
		this.setInitialized(false);
		this.setCreated(false);
		this.setResized(false);

		this.setTitle(title);
		this.setWidth(width);
		this.setHeight(height);

		this.resizable = false;

		this.setvSync(vSync);

		this.setCanvas(new Canvas(width, height));
	}

	public Window(String title, int width, int height) {
		this.setInitialized(false);
		this.setCreated(false);
		this.setResized(false);

		this.setTitle(title);
		this.setWidth(width);
		this.setHeight(height);

		this.resizable = false;

		this.setvSync(true);

		this.setCanvas(new Canvas(width, height));
	}

	public Window(int width, int height) {
		this.setInitialized(false);
		this.setCreated(false);
		this.setResized(false);

		this.setTitle("");
		this.setWidth(width);
		this.setHeight(height);

		this.resizable = false;

		this.setvSync(true);

		this.setCanvas(new Canvas(width, height));
	}

	public Window(String title) {
		this.setInitialized(false);
		this.setCreated(false);
		this.setResized(false);

		this.setTitle(title);
		this.setWidth(800);
		this.setHeight(600);

		this.resizable = false;

		this.setvSync(true);

		this.setCanvas(new Canvas(800,600));
	}

	public Window() {
		this.setInitialized(false);
		this.setCreated(false);
		this.setResized(false);

		this.setTitle("");
		this.setWidth(800);
		this.setHeight(600);

		this.resizable = false;

		this.setvSync(true);

		this.setCanvas(new Canvas(800,600));
	}

	/*******************
	 *** Actual Code ***
	 *******************/

	//Big mother****** of a function. This does the magic for us.
	/** Initializes this Window*/
	public void init(){
		if(isInitialized())
			throw new IllegalStateException("Window already initialized");

		if (isCreated())
			throw new IllegalStateException("Window already created");

		// Setup a default error callback, which will print the error message in System.err.
		if(getErrorCallback() == null)
			setErrorCallback(GLFWErrorCallback.createPrint(System.err));

		glfwSetErrorCallback(getErrorCallback());

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		setInitialized(true);

		glfwDefaultWindowHints();									// optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);						// the window will stay hidden after creation
		if(isResizable()) glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);	// the window will be resizable
		else glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);				// the window won't be resizable
		// Mac Compatibility
//		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
//		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
//		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
//		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

		// Create the window
		windowHandle = glfwCreateWindow(getWidth(), getHeight(), getTitle(), NULL, NULL);
		if (windowHandle == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a default resize callback
		if(getWindowSizeCallback() == null)
			setWindowSizeCallback(new GLFWWindowSizeCallback() {
				@Override
				public void invoke(long window, int width, int height) {
					Window.this.setWidth(width);
					Window.this.setHeight(height);
					Window.this.setResized(true);
				}
			});

		glfwSetWindowSizeCallback(windowHandle, getWindowSizeCallback());

		// Setup a default key callback. It will be called every time a key is pressed, repeated or released.
		if(getKeyCallback() == null)
			setKeyCallback(new GLFWKeyCallback() {
				@Override
				public void invoke(long window, int key, int scancode, int action, int mods) {
					if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
						glfwSetWindowShouldClose(window, true);
					}
				}
			});

		glfwSetKeyCallback(windowHandle, getKeyCallback());

		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// Center our window
		glfwSetWindowPos(
				windowHandle,
				(vidmode.width() - getWidth()) / 2,
				(vidmode.height() - getHeight()) / 2
				);

		// Make the OpenGL context current
		glfwMakeContextCurrent(windowHandle);

		// Enable v-sync
		if (isvSync())
			glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(windowHandle);

		getCanvas().init();

		setCreated(true);
	}

	/** Swap the video buffers, then Poll the events (All the callbacks)*/
	public void update() {
		glfwSwapBuffers(windowHandle);
		glfwPollEvents();
	}

	/** Dispose this Window*/
	public void close() {
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(windowHandle);
		glfwDestroyWindow(windowHandle);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public boolean isKeyPressed(int keyCode) {
		return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
	}

	public boolean isKeyReleased(int keyCode) {
		return glfwGetKey(windowHandle, keyCode) == GLFW_RELEASE;
	}

	public void setClearColor(Color c) {
		getCanvas().setClearColor(c);
	}

	public void setClearColor(float r, float g, float b) {
		getCanvas().setClearColor(r, g, b);
	}

	public void setClearColor(int r, int g, int b) {
		getCanvas().setClearColor(r, g, b);
	}

	// Setters and getters

	public boolean shouldClose() {
		return glfwWindowShouldClose(windowHandle);
	}

	public void setShouldClose(boolean value){
		glfwSetWindowShouldClose(windowHandle, value);
	}

	public boolean isInitialized() {
		return initialized;
	}

	// This should NOT be public. Ever.
	private void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public boolean isCreated() {
		return created;
	}

	// This should NOT be public. Ever.
	private void setCreated(boolean created) {
		this.created = created;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		if(isCreated())
			glfwSetWindowTitle(windowHandle, title);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		if(isCreated()) {
			GLFW.glfwSetWindowSize(windowHandle, width, this.getHeight());
			getCanvas().resize(getWidth(), getHeight());
		}
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		if(isCreated()) {
			GLFW.glfwSetWindowSize(windowHandle, this.getWidth(), height);
			getCanvas().resize(getWidth(), getHeight());
		}
	}

	public boolean isResizable() {
		return resizable;
	}

	public GLFWErrorCallback getErrorCallback() {
		return errorCallback;
	}

	public void setErrorCallback(GLFWErrorCallback errorCallback) {
		if(isInitialized())
			throw new IllegalStateException("You can't set the error callback once GLFW already initialized");

		this.errorCallback = errorCallback;
	}

	public GLFWKeyCallback getKeyCallback() {
		return keyCallback;
	}

	public void setKeyCallback(GLFWKeyCallback keyCallback) {
		this.keyCallback = keyCallback;
		glfwSetKeyCallback(windowHandle, getKeyCallback());
	}

	public GLFWWindowSizeCallback getWindowSizeCallback() {
		return windowSizeCallback;
	}

	public void setWindowSizeCallback(GLFWWindowSizeCallback windowSizeCallback) {
		this.windowSizeCallback = windowSizeCallback;
		glfwSetWindowSizeCallback(windowHandle, getWindowSizeCallback());
	}

	public boolean isResized() {
		return resized;
	}

	private void setResized(boolean resized) {
		this.resized = resized;
	}

	public boolean isvSync() {
		return vSync;
	}

	public void setvSync(boolean vSync) {
		this.vSync = vSync;
		if(isInitialized()) {
			if(isvSync())
				glfwSwapInterval(1);
			else
				glfwSwapInterval(0);
		}
	}

	public Canvas getCanvas() {
		return canvas;
	}

	private void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

}
