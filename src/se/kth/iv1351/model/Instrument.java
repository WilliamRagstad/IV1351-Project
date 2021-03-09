package se.kth.iv1351.model;

public class Instrument implements InstrumentDTO {
    private String instrumentID;
    private String instrumentType;
    private String returnDate;
    private String studentID;
    private int fee;
    private String instrumentBrand;

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

    /**
     * @param returnDate Sets the return date.
     */
    public void setReturnDate(String returnDate){
        this.returnDate = returnDate;
    }

    /**
     * @param studentID Sets the student ID.
     */
    public void setStudentID(String studentID){
        this.studentID = studentID;
    }

    /**
     * @return The Instrument ID
     */
    public String getInstrumentID(){
        return instrumentID;
    }
    /**
     * @return The Instrument Type
     */
    public String getInstrumentType(){
        return instrumentType;
    }
    /**
     * @return The Return Date
     */
    public String getReturnDate(){
        return returnDate;
    }
    /**
     * @return The Student ID
     */
    public String getStudentID(){
        return studentID;
    }
    /**
     * @return The Instruments Monthly Price
     */
    public int getInstrumentFee(){
        return fee;
    }
    /**
     * @return The Instruments Brand
     */
    public String getInstrumentBrand(){
        return instrumentBrand;
    }

}
