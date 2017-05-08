package zswi;

/**
 *
 * @author DDvory
 */
public enum Meter {
    none(0),
    m_per_s(1);
    
    private final double konstant;

    private Meter(double konstant) {
        this.konstant = konstant;
    }
    public static Meter getMeter(String str) throws NullPointerException {
        Meter[] loads = Meter.values();
        for (Meter load : loads) {
            if (load.toString().equals(str)) {
                return load;
            }
        }
        throw new NullPointerException();
    }
    
}
