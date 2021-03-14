package se.kth.iv1351.view;

import se.kth.iv1351.controller.Controller;
import se.kth.iv1351.model.Instrument;
import java.util.Scanner;

public class Interpreter {
    private final Scanner console = new Scanner(System.in);
    private Controller controller;

    public Interpreter(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        while (true) {
            try {
            	System.out.println("> ");
            	String[] params = console.nextLine().split(" ");
            	Command command;
            	try {
            		command = Command.valueOf(params[0].toUpperCase());
                } catch (Exception e) {
                	System.out.println("Illegal command! Use Help to list all available commands.");
                	continue;
                }
                
                switch (command) {
                	case QUIT: return;
                    case HELP:
                        for (Command c : Command.values()) System.out.println(c);
                        break;
                    case RENT:
                        controller.rentInstrument(params[1], params[2]);
                        break;
                    case END:
                        controller.terminateRental(params[1]);
                        break;
                    case LIST:
                        for (Instrument instrument : controller.listRentalInstruments()) System.out.println(
                        		"Instrument ID: " + instrument.instrumentID + ", " + 
	                            "Instrument Type: " + instrument.instrumentType + ", " +
	                            "Monthly Price: " + instrument.fee + ", " +
	                            "Instrument Brand: " + instrument.instrumentBrand);
                        break;
                }
            } catch (Exception e) {
                System.out.println("Operation failed");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}