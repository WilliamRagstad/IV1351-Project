package se.kth.iv1351.model;

public class Instrument {
    public String instrumentID;
    public String instrumentType;
    public String returnDate;
    public String studentID;
    public int fee;
    public String instrumentBrand;

    /**
     * Creates an instrument with the specified details.
     * @param instrumentID  The ID of the instrument
     * @param instrumentType    The kind of instrument
     * @param returnDate    The date which the instrument can be rented until
     * @param studentID     The ID of the student renting
     * @param fee  The monthly price of renting the instrument
     */
    public Instrument(String instrumentID, String instrumentType, String returnDate, String studentID, int fee,String instrumentBrand){
        this.instrumentID = instrumentID;
        this.instrumentType = instrumentType;
        this.returnDate = returnDate;
        this.studentID = studentID;
        this.fee = fee;
        this.instrumentBrand = instrumentBrand;
    }
    /**
     * Creates an instrument with the specified details.
     * Used to list all instruments that can be rented.
     * @param instrumentID  The ID of the instrument
     * @param instrumentType    The kind of instrument
     * @param fee  The monthly price of renting the instrument
     */
    public Instrument(String instrumentID, String instrumentType, int fee, String instrumentBrand){
        this(instrumentID, instrumentType, null, null, fee, instrumentBrand);
    }
    /**
     * Creates an instrument with the specified details.
     * Used to rent an instrument
     * @param instrumentID  The ID of the instrument
     * @param returnDate    The date which the instrument can be rented until
     * @param studentID     The ID of the student renting
     */
    public Instrument(String instrumentID, String studentID, String returnDate){
        this(instrumentID, null, returnDate, studentID, 0, null);
    }
}
