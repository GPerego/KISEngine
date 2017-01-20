package tests.testGame;

import engine.Engine;

public class TestGame {
	
	public static void main(String[] args){
		new Engine("Teste", 800, 600, new TestGameLogic()).start();
	}
	
}