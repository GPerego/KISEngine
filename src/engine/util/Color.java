package engine.util;

public class Color {
	private final int bitmask = 0xff;
	public byte r, g, b, a;

	public Color() {
		setRed(0);
		setGreen(0);
		setBlue(0);
		setAlpha(0);
	}
	
	public Color(int r, int g, int b, int a) {
		setRed(r);
		setGreen(g);
		setBlue(b);
		setAlpha(a);
	}
	
	public Color(float r, float g, float b, float a) {
		setRed(r);
		setGreen(g);
		setBlue(b);
		setAlpha(a);
	}
	
	public Color(int r, int g, int b) {
		setRed(r);
		setGreen(g);
		setBlue(b);
		setAlpha(0);
	}
	
	public Color(float r, float g, float b) {
		setRed(r);
		setGreen(g);
		setBlue(b);
		setAlpha(0);
	}

	//Red
	public int getRedInt() {
		return (r & bitmask);
	}

	public float getRed() {
		return (r & bitmask)/255f;
	}

	public void setRed(int r) {
		this.r = (byte) (r & bitmask);
	}

	public void setRed(float r) {
		setRed(Math.round(r*255));
	}

	//Green
	public int getGreenInt() {
		return (g & bitmask);
	}

	public float getGreen() {
		return (g & bitmask)/255f;
	}

	public void setGreen(int g) {
		this.g = (byte) (g & bitmask);
	}

	public void setGreen(float g) {
		setGreen(Math.round(g*255));
	}

	//Blue
	public int getBlueInt() {
		return (b & bitmask);
	}

	public float getBlue() {
		return (b & bitmask)/255f;
	}

	public void setBlue(int b) {
		this.b = (byte) (b & bitmask);
	}

	public void setBlue(float b) {
		setBlue(Math.round(b*255));
	}

	//Alpha
	public int getAlphaInt() {
		return (a & bitmask);
	}

	public float getAlpha() {
		return (a & bitmask)/255f;
	}

	public void setAlpha(int a) {
		this.a = (byte) (a & bitmask);
	}

	public void setAlpha(float a) {
		setAlpha(Math.round(a*255));
	}
}