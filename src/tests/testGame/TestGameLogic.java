package tests.testGame;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFW;

import engine.EmptyLogic;
import engine.Window;

public class TestGameLogic extends EmptyLogic {
	float[][] vertexes;
	float[][] colors;

	float x, y;
	int w, h;

	float rotationX, rotationY;

	@Override
	public void init() {
		vertexes = new float[][] {
			{-1f, -1f,  1f},
			{-1f,  1f,  1f},
			{ 1f,  1f,  1f},
			{ 1f, -1f,  1f},
			{-1f, -1f, -1f},
			{-1f,  1f, -1f},
			{ 1f,  1f, -1f},
			{ 1f, -1f, -1f}
		};

		colors = new float[][] {
			{0f,0f,0f},
			{1f,0f,0f},
			{1f,1f,0f},
			{0f,1f,0f},
			{0f,0f,1f},
			{1f,0f,1f},
			{1f,1f,1f},
			{0f,1f,1f}
		};

		rotationX = 0;
		rotationY = 0;
		x = 400;
		y = 300;
		w=50;
		h=50;
	}

	@Override
	public void input(Window window) {
		int r = window.getCanvas().getClearColor().getRedInt();
		int g = window.getCanvas().getClearColor().getGreenInt();
		int b = window.getCanvas().getClearColor().getBlueInt();
		
		if(window.isKeyPressed(GLFW.GLFW_KEY_LEFT))
			rotationX+=10;
		if(window.isKeyPressed(GLFW.GLFW_KEY_RIGHT))
			rotationX-=10;

		if(window.isKeyPressed(GLFW.GLFW_KEY_DOWN))
			r--;
		if(window.isKeyPressed(GLFW.GLFW_KEY_UP))
			r++;
		
		window.setClearColor(r,g,b);
	}

	@Override
	public void update(float interval) {}

	@Override
	public void render(Window window) {
		window.getCanvas().clear();
		square(window.getWidth()/2f, window.getHeight()/2f, 50, 50);
	}

	public void square(float x, float y, int w, int h) {
		glPushMatrix();
		glColor3f(1.0f, 0.0f, 0.0f); // red

		glTranslatef(x, y, 0);
		glRotatef(rotationX, 0f, 0f, 1f);
		glScalef(1f, 1f, 1f);
		glTranslatef(-x, -y, 0);

		glBegin(GL_QUADS);
			glVertex2f(x - w/2f, y - h/2f);
			glVertex2f(x + w/2f, y - h/2f);
			glVertex2f(x + w/2f, y + h/2f);
			glVertex2f(x - w/2f, y + h/2f);
		glEnd();            
	glPopMatrix();
	}

	public void quad(int a, int b, int c, int d) {
		glBegin(GL_QUADS);
		glColor3fv(colors[a]);
		glVertex3fv(vertexes[a]);

		glColor3fv(colors[b]);
		glVertex3fv(vertexes[b]);

		glColor3fv(colors[c]);
		glVertex3fv(vertexes[c]);

		glColor3fv(colors[d]);
		glVertex3fv(vertexes[d]);
		glEnd();
	}
}
