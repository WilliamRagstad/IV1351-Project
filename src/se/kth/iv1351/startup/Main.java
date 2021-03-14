package se.kth.iv1351.startup;

import se.kth.iv1351.controller.Controller;
import se.kth.iv1351.integration.SoundGoodDBException;
import se.kth.iv1351.view.Interpreter;

public class Main {
    public static void main(String[] args) throws SoundGoodDBException {
        new Interpreter(new Controller()).run();
    }
}
