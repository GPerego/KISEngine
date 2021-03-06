package engine.util;

public class Timer {

    private double lastTime;
    
    public void init() {
        lastTime = getTime();
    }

    public double getTime() {
        return System.nanoTime() / 1000_000_000.0;
    }

    public float getElapsedTime() {
        double time = getTime();
        float elapsedTime = (float) (time - lastTime);
        lastTime = time;
        return elapsedTime;
    }

    public double getLastTime() {
        return lastTime;
    }
}