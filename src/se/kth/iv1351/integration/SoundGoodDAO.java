package se.kth.iv1351.integration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.List;
import java.util.Properties;
import java.util.ArrayList;
import java.lang.Exception;

import se.kth.iv1351.model.Instrument;

/**
 * The Data Access Object handles all the database calls.
 */
public class SoundGoodDAO {
    private static final String INSTRUMENT_TABLE_NAME = "instrument";
    private static final String INSTRUMENT_PK_COLUMN_NAME = "instrument_id";
    private static final String INSTRUMENT_NAME_COLUMN_NAME = "name";
    private static final String INSTRUMENT_BRAND_COLUMN_NAME = "brand";
    private static final String INSTRUMENT_KIND_COLUMN_NAME = "kind";
    private static final String INSTRUMENT_SIZE_COLUMN_NAME = "size";
    private static final String INSTRUMENT_FEE_COLUMN_NAME = "fee";
    
    private static final String RENTAL_TABLE_NAME = "rental";
    private static final String RENTAL_INSTRUMENT_FK_COLUMN_NAME = "instrument_id";
    private static final String RENTAL_STUDENT_FK_COLUMN_NAME = "student_id";
    private static final String RENTAL_DURATION_COLUMN_NAME = "days";
    private static final String RENTAL_RETURNED_COLUMN_NAME = "returned";

    private Connection connection;
    private PreparedStatement listRentalInstrumentStmt;
    private PreparedStatement rentInstrumentStmt;
    private PreparedStatement terminateRentalStmt;
    private PreparedStatement getStudentRentalInstrumentsStmt;

    /**
     * Creates a new DAO object and connects it to the database.
     * @throws SoundGoodDBException If could not connect to the database.
     */
    public SoundGoodDAO() throws Exception {
        connectToDatabase();
        prepareStatements();
    }
    
    private void connectToDatabase() throws ClassNotFoundException, SQLException {
    	connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soundgood", "postgres", "example"); // PostgreSQL is default running on 5432.
    	connection.setAutoCommit(false);
    }
    
    private void prepareStatements() throws SQLException {
        listRentalInstrumentStmt = connection.prepareStatement(
                "SELECT " + INSTRUMENT_TABLE_NAME + "." + INSTRUMENT_PK_COLUMN_NAME
                		+ ", " + INSTRUMENT_KIND_COLUMN_NAME
                        + ", " + INSTRUMENT_FEE_COLUMN_NAME
                        + ", " + INSTRUMENT_BRAND_COLUMN_NAME
                + " FROM " + INSTRUMENT_TABLE_NAME
                + "LEFT JOIN " + RENTAL_TABLE_NAME + " ON " + RENTAL_TABLE_NAME + "." + RENTAL_INSTRUMENT_FK_COLUMN_NAME + " = " + INSTRUMENT_TABLE_NAME + "." + INSTRUMENT_PK_COLUMN_NAME
                + " WHERE " + RENTAL_RETURNED_COLUMN_NAME + " = TRUE");
        rentInstrumentStmt = connection.prepareStatement(
                "UPDATE " + RENTAL_TABLE_NAME + " SET " +
                        RENTAL_RETURNED_COLUMN_NAME + " = ?, " +
                        RENTAL_STUDENT_FK_COLUMN_NAME + " = ? WHERE " +
                        RENTAL_INSTRUMENT_FK_COLUMN_NAME + " = ?");

        getStudentRentalInstrumentsStmt = connection.prepareStatement(
                "SELECT " + INSTRUMENT_TABLE_NAME + "." + INSTRUMENT_PK_COLUMN_NAME
                		+ " FROM " + INSTRUMENT_TABLE_NAME
                		+ "LEFT JOIN " + RENTAL_TABLE_NAME + " ON " + RENTAL_TABLE_NAME + "." + RENTAL_INSTRUMENT_FK_COLUMN_NAME + " = " + INSTRUMENT_TABLE_NAME + "." + INSTRUMENT_PK_COLUMN_NAME
                		+ " WHERE " + RENTAL_STUDENT_FK_COLUMN_NAME + " = ? AND " + RENTAL_RETURNED_COLUMN_NAME + " = FALSE"
        );

        terminateRentalStmt = connection.prepareStatement(
                "UPDATE " + RENTAL_TABLE_NAME + " SET " +
                        RENTAL_RETURNED_COLUMN_NAME + " = TRUE WHERE " +
                        RENTAL_INSTRUMENT_FK_COLUMN_NAME + " = ?");

    }

    /**
     * Terminates a rental of an instrument
     * @param instrumentID The instrument which will have its rental terminated
     * @throws InstrumentException If unable to terminate the rental.
     */
    public void terminateRental(String instrumentID) throws Exception {

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
    public List<Instrument> listRentalInstruments() throws Exception {
        List<Instrument> instruments = new ArrayList<>();
        try(ResultSet result = listRentalInstrumentStmt.executeQuery()){
            while(result.next()){
                instruments.add(new Instrument(
                        result.getString(RENTAL_INSTRUMENT_FK_COLUMN_NAME),
                        result.getString("name"),
                        result.getString("kind"),
                        result.getString("brand"),
                        result.getString("size"),
                        result.getString("returned"),
                        result.getString("student_id"),
                        result.getInt("fee")
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
    public void rentInstrument(Instrument instrument) throws Exception {
        try{
            rentInstrumentStmt.setTimestamp(1, Timestamp.valueOf(instrument.returnDuration + " 00:00:00.00"));
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
    public int getStudentAmountRentalInstruments(Instrument instrument) throws Exception {
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

    private void handleException(String failureMsg, Exception cause) throws Exception {
        try {
            connection.rollback();
        } catch (SQLException rollbackExc) {
        	failureMsg = failureMsg + ". Also failed to rollback transaction because of: " + rollbackExc.getMessage();
        }
        if (cause != null) {
            throw new Exception(failureMsg, cause);
        } else {
            throw new Exception(failureMsg);
        }
    }
}
