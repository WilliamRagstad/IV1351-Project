package se.kth.iv1351.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;
import se.kth.iv1351.model.Instrument;

/**
 * The Data Access Object handles all the database calls.
 */
public class SoundGoodDAO {
    private static final String RENTAL_INSTRUMENT_PK_COLUMN_NAME = "instrument_id";
    private static final String RENTAL_INSTRUMENT_FEE_COLUMN_NAME = "fee";

    private Connection connection;
    private PreparedStatement listRentalInstrumentStmt;
    private PreparedStatement rentInstrumentStmt;
    private PreparedStatement terminateRentalStmt;
    private PreparedStatement getStudentRentalInstrumentsStmt;

    /**
     * Creates a new DAO object and connects it to the database.
     * @throws SoundGoodDBException If could not connect to the database.
     */
    public SoundGoodDAO() throws SoundGoodDBException {
        try {
            connectToSoundGood();
        } catch(ClassNotFoundException | SQLException exception){
            throw new SoundGoodDBException("Could not connect to datasource.", exception);
        }

    }
    private void connectToSoundGood() throws ClassNotFoundException, SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soundgood",
                "postgres", "example");
        connection.setAutoCommit(false);
    }

    /**
     * Terminates a rental of an instrument
     * @param instrumentID The instrument which will have its rental terminated
     * @throws InstrumentException If unable to terminate the rental.
     */
    public void terminateRental(String instrumentID) throws SoundGoodDBException {

        try {
            terminateRentalStmt.setString(1, instrumentID);
            int updatedRows = terminateRentalStmt.executeUpdate();
            if (updatedRows != 1) {
                handleException("Could not terminate the rental", null);
            }
            connection.commit();
        } catch(SQLException sqle){
            handleException("Could not terminate the rental", sqle);
        }
    }
    /**
     * Lists all the instruments
     * @return Returns the instruments that was retrieved from the database
     * @throws InstrumentException If unable to list the instruments
     */
    public List<Instrument> listRentalInstruments() throws SoundGoodDBException {
        List<Instrument> instruments = new ArrayList<>();
        try(ResultSet result = listRentalInstrumentStmt.executeQuery()){
            while(result.next()){
                instruments.add(new Instrument(
                        result.getString(RENTAL_INSTRUMENT_PK_COLUMN_NAME),
                        result.getString("type"),
                        result.getString("returned"),
                        result.getString("student_id"),
                        result.getInt(RENTAL_INSTRUMENT_FEE_COLUMN_NAME),
                        result.getString("brand")
                        ));
            }
            connection.commit();
        } catch(SQLException sqle){
            handleException("Could not list any instruments.", sqle);
        }
        return instruments;
    }
    /**
     * Rents an instrument by updating the database with student id and instrument id.
     * It checks first if the renting student has 2 or more current rentals and if not successfully rents.
     * @param instrument Information about the instrument and student is kept here.
     * @throws InstrumentException If could not rent.
     */
    public void rentInstrument(Instrument instrument) throws SoundGoodDBException {
        try{
            rentInstrumentStmt.setTimestamp(1, Timestamp.valueOf(instrument.returnDate + " 00:00:00.00"));
            rentInstrumentStmt.setString(2, instrument.studentID);
            rentInstrumentStmt.setString(3, instrument.instrumentID);
            int updatedRows = rentInstrumentStmt.executeUpdate();
            if (updatedRows != 1) {
                handleException("Could not rent the instrument: " + instrument, null);
            }
            connection.commit();
        } catch(SQLException sqle){
            handleException("Could not rent the instrument: " + instrument, sqle);
        }

    }

    /**
     * Retrieves the amount of instruments a student has rented. Used to see if an student is eligible to rent.
     * @param instrument Information about the student is brought from here.
     * @return Returns the quantity of rented instruments
     * @throws SoundGoodDBException If could not retrieve instruments
     */
    public int getStudentAmountRentalInstruments(Instrument instrument) throws SoundGoodDBException {
        try {
            getStudentRentalInstrumentsStmt.setString(1, instrument.studentID);
        } catch(SQLException sqle){
            handleException("Could not retrieve instruments", sqle);
        }
        int i = 0;
        try(ResultSet result = getStudentRentalInstrumentsStmt.executeQuery()){
                while(result.next()){
                    i++;
            }
            connection.commit();
        } catch(SQLException sqle){
            handleException("Could not retrieve instruments", sqle);
        }
        return i;
    }

    private void handleException(String failureMsg, Exception cause) throws SoundGoodDBException {
        try {
            connection.rollback();
        } catch (SQLException rollbackExc) {
        	failureMsg = failureMsg + ". Also failed to rollback transaction because of: " + rollbackExc.getMessage();
        }
        if (cause != null) {
            throw new SoundGoodDBException(failureMsg, cause);
        } else {
            throw new SoundGoodDBException(failureMsg);
        }
    }
}
