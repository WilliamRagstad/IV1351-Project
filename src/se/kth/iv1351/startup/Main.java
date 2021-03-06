package se.kth.iv1351.startup;

import se.kth.iv1351.controller.Controller;
import se.kth.iv1351.integration.SoundGoodDAO;
import se.kth.iv1351.view.Interpreter;

public class Main {
	private static SoundGoodDAO soundgood;
	
    public static void main(String[] args) throws Exception {
    	try {
    		soundgood = new SoundGoodDAO();
    	}
    	catch (Exception e) {
    		System.out.println("Could not connect to the Soundgood database: " + e.getMessage());
    		return;
    	}
        new Interpreter(new Controller(soundgood)).run();
    }
}
