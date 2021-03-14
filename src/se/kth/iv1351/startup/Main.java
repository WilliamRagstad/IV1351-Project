package se.kth.iv1351.startup;

import se.kth.iv1351.controller.Controller;
import se.kth.iv1351.integration.SoundGoodDAO;
import se.kth.iv1351.view.Interpreter;

public class Main {
	private static SoundGoodDAO soundgood;
	
    public static void main(String[] args) throws Exception {
    	soundgood = new SoundGoodDAO(); // Exception prone
        new Interpreter(new Controller(soundgood)).run(); // No exceptions
    }
}
