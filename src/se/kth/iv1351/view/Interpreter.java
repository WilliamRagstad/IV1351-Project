package se.kth.iv1351.view;
import se.kth.iv1351.controller.Controller;
import se.kth.iv1351.model.InstrumentDTO;

import java.util.List;
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
                	command = Command.ILLEGAL_COMMAND;
                }
                
                switch (command) {
                    case HELP:
                        for (Command c : Command.values()) {
                            if (c == Command.ILLEGAL_COMMAND) {
                                continue;
                            }
                            System.out.println(c);
                        }
                        break;
                    case QUIT: return;
                    case RENT:
                        controller.rentInstrument(params[1], params[2]);
                        break;
                    case END:
                        controller.terminateRental(params[1]);
                    case LIST:
                        List<? extends InstrumentDTO> instruments = null;
                        instruments = controller.listRentalInstruments();
                        if(params[1].equals("")) {
                            for (InstrumentDTO instrument : instruments) {
                                System.out.println("Instrument ID " + instrument.getInstrumentID() + ", "
                                        + "Instrument Type: " + instrument.getInstrumentType() + ", "
                                        + "Monthly Price: " + instrument.getInstrumentFee() + ", "
                                        + "Instrument Brand: " + instrument.getInstrumentBrand());
                            }
                        } else{
                            for (InstrumentDTO instrument : instruments) {
                                if(params[1].equals(instrument.getInstrumentType())) {
                                    System.out.println("Instrument ID " + instrument.getInstrumentID() + ", "
                                            + "Instrument Type: " + instrument.getInstrumentType() + ", "
                                            + "Monthly Price: " + instrument.getInstrumentFee() + ", "
                                            + "Instrument Brand: " + instrument.getInstrumentBrand());
                                }
                            }
                        }

                        break;
                    default:
                        System.out.println("illegal command");
                }
            } catch (Exception e) {
                System.out.println("Operation failed");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}