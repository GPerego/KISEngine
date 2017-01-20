package engine;

import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import engine.util.Color;

public class Canvas {
	private Color clearColor;
	private boolean initialized;
	
	/** Window's width*/
	private int WIDTH;
	/** Window's height*/
	private int HEIGHT;

	//Constructors

	public Canvas(int wWidth, int wHeight) {
		setInitialized(false);
		clearColor = new Color(0,0,0);
		this.WIDTH = wWidth;
		this.HEIGHT = wHeight;
	}

	//Actual Code

	public void init() {
		if(isInitialized())
			throw new IllegalStateException("Canvas already initialized");

		createCapabilities();
		
		glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WIDTH, 0, HEIGHT, 1, -1);
        glMatrixMode(GL_MODELVIEW);
		
		glClearColor(getClearColor().getRed(), getClearColor().getGreen(), getClearColor().getBlue(), getClearColor().getAlpha());
		
		setInitialized(true);
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void resize(int w, int h) {
		WIDTH = w;
		HEIGHT = h;
		
		if(isInitialized()) {
			glMatrixMode(GL_PROJECTION);
	        glLoadIdentity();
	        glOrtho(0, WIDTH, 0, HEIGHT, 1, -1);
	        glMatrixMode(GL_MODELVIEW);
		}
	}

	// Getters and Setters

	public Color getClearColor() {
		return clearColor;
	}

	public void setClearColor(int r, int g, int b, int a) {
		clearColor.setRed(r);
		clearColor.setGreen(g);
		clearColor.setBlue(b);
		clearColor.setAlpha(a);

		if(isInitialized())
			glClearColor(getClearColor().getRed(), getClearColor().getGreen(), getClearColor().getBlue(), getClearColor().getAlpha());
	}

	public void setClearColor(float r, float g, float b, float a) {
		clearColor.setRed(r);
		clearColor.setGreen(g);
		clearColor.setBlue(b);
		clearColor.setAlpha(a);

		if(isInitialized())
			glClearColor(getClearColor().getRed(), getClearColor().getGreen(), getClearColor().getBlue(), getClearColor().getAlpha());
	}

	public void setClearColor(int r, int g, int b) {
		clearColor.setRed(r);
		clearColor.setGreen(g);
		clearColor.setBlue(b);
		clearColor.setAlpha(0);

		if(isInitialized())
			glClearColor(getClearColor().getRed(), getClearColor().getGreen(), getClearColor().getBlue(), getClearColor().getAlpha());
	}

	public void setClearColor(float r, float g, float b) {
		clearColor.setRed(r);
		clearColor.setGreen(g);
		clearColor.setBlue(b);
		clearColor.setAlpha(0);

		if(isInitialized())
			glClearColor(getClearColor().getRed(), getClearColor().getGreen(), getClearColor().getBlue(), getClearColor().getAlpha());
	}

	public void setClearColor(Color c) {
		clearColor.setRed(c.getRed());
		clearColor.setGreen(c.getGreen());
		clearColor.setBlue(c.getBlue());
		clearColor.setAlpha(c.getAlpha());

		if(isInitialized())
			glClearColor(getClearColor().getRed(), getClearColor().getGreen(), getClearColor().getBlue(), getClearColor().getAlpha());
	}

	public boolean isInitialized() {
		return initialized;
	}

	private void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
}
