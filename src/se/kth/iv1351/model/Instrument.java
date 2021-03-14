package se.kth.iv1351.model;

public class Instrument {
    public String instrumentID;
    public String instrumentName;
    public String instrumentKind;
    public String instrumentBrand;
    public String instrumentSize;
    public String returnDuration;
    public String studentID;
    public int fee;

    /**
     * Creates an instrument with the specified details.
     * @param instrumentID  The ID of the instrument
     * @param instrumentType    The kind of instrument
     * @param returnDate    The date which the instrument can be rented until
     * @param studentID     The ID of the student renting
     * @param fee  The monthly price of renting the instrument
     */
    public Instrument(String instrumentID, String instrumentName, String instrumentKind, String instrumentBrand, String instrumentSize, String returnDuration, String studentID, int fee){
        this.instrumentID = instrumentID;
        this.instrumentName = instrumentName;
        this.instrumentKind = instrumentKind;
        this.instrumentBrand = instrumentBrand;
        this.instrumentSize = instrumentSize;
        this.returnDuration = returnDuration;
        this.studentID = studentID;
        this.fee = fee;
    }
    /**
     * Creates an instrument with the specified details.
     * Used to list all instruments that can be rented.
     * @param instrumentID  The ID of the instrument
     * @param instrumentType    The kind of instrument
     * @param fee  The monthly price of renting the instrument
     */
    public Instrument(String instrumentID, String instrumentType, int fee, String instrumentBrand) {
        this(instrumentID, null, instrumentType, instrumentBrand, null, null, null, fee);
    }
    /**
     * Creates an instrument with the specified details.
     * Used to rent an instrument
     * @param instrumentID  The ID of the instrument
     * @param returnDate    The date which the instrument can be rented until
     * @param studentID     The ID of the student renting
     */
    public Instrument(String instrumentID, String studentID, String returnDuration) {
        this(instrumentID, null, null, null, null, returnDuration, studentID, 0);
    }
}
