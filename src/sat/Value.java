package sat;

/**
 * A class describing a Value, a type of Data where the measure is a double.
 */
public class Value extends Data{
    /**The measure of this type of Data, which is a double. */
    double measure;

    /**
     * Constructor, with a double.
     * It creates a data by using the current time to create a Date and by using the double it takes in parameter.
     * @param measureCollected The value that a component measured after a T/M.
     */
    public Value(double measureCollected){
        super();
        measure = measureCollected;

    }
}
