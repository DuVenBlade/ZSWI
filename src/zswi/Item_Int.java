package zswi;

import java.math.BigInteger;

/**
 *
 * @author DDvory
 */
public class Item_Int implements IValuable {
    public static final Integer[]  lens = new Integer[]{1,2,4,8};
    
    private BigInteger value;
    private Format format;
    private int len;
    //Metric -> bude mozno az posle kolega ty picoviny
    private Item item;

    public Item_Int(String value, Format format, int len) {
        setValue(value);
        this.format = format;
        this.len = len;
    }

    public Item_Int(Format format, int len) {
        this("0",format,len);
    }

    public Item_Int() {
        this(Format.Signed,1);
    }

    public BigInteger getValue() {
        return value;
    }

    public Format getFormat() {
        return format;
    }

    public int getLen() {
        return len;
    }

    public void setValue(String value) {
        this.value = new BigInteger(value);
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }
    
    public void setFormat(Format format) {
        this.format = format;
    }

    public void setLen(int len) {
        this.len = len;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public void setItem(Item item) {
        this.item = item;
    }
    
    
    public enum Format{
        Signed,Unsigned
    }
}
