package se.kth.iv1351.startup;

import se.kth.iv1351.controller.Controller;
import se.kth.iv1351.view.Interpreter;

public class Main {
    public static void main(String[] args) throws Exception {
        new Interpreter(new Controller()).run();
    }
}
