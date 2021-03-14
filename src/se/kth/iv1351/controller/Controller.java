package se.kth.iv1351.controller;

import se.kth.iv1351.integration.SoundGoodDAO;
import se.kth.iv1351.model.Instrument;

import java.time.*;
import java.util.List;

/**
 * This is the controller of the application. It is responsible to guide all calls to the model
 * and handles all functions of the SoundGoodDAO.
 */
public class Controller {
    private final SoundGoodDAO soundGood;

    /**
     * Creates a new instance of the DAO, which connects to the database.
     * @throws SoundGoodDBException If unable to connect to the database.
     */
    public Controller() throws Exception {
        soundGood = new SoundGoodDAO();
    }

    /**
     * Lists all the instruments
     * @return Returns the instruments that was retrieved from the database
     * @throws Exception If unable to list the instruments
     */
    public List<Instrument> listRentalInstruments() throws Exception {
        return soundGood.listRentalInstruments();
    }

    /**
     * Rents an instrument by updating the database with student id and instrument id.
     * It checks first if the renting student has 2 or more current rentals and if not successfully rents.
     * @param instrumentID The ID of the instrument being rented
     * @param studentID The ID of the student renting
     * @throws InstrumentException If could not rent.
     */
    public void rentInstrument(String instrumentID, String studentID) throws Exception  {
        try {
            Instrument instrument  = new Instrument(instrumentID, studentID, getReturnDate());
            if(soundGood.getStudentAmountRentalInstruments(instrument)<2) {
                soundGood.rentInstrument(instrument);
                System.out.println("Instrument " + instrumentID + " is now being rented to student " + studentID);
            } else System.out.println("User already has 2 instruments");
        } catch (Exception e) {
            throw new Exception("Could not rent instrument " + instrumentID + " to " + studentID, e);
        }
    }

    /**
     * Terminates a rental of an instrument
     * @param instrumentID The instrument which will have its rental terminated
     * @throws InstrumentException If unable to terminate the rental.
     */
    public void terminateRental(String instrumentID) throws Exception {
        try {
            soundGood.terminateRental(instrumentID);
            System.out.println("Rental for instrument " + instrumentID + " is now terminated.");
        } catch (Exception e) {
            throw new Exception("Could not terminate the rental", e);
        }
    }
    private String getReturnDate(){
        LocalDate year = LocalDate.now();
        LocalDate nextYear = year.plusYears(1);
        return nextYear.toString();
    }
}
