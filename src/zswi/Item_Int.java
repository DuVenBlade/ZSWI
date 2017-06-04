package zswi;

import java.math.BigInteger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author DDvory
 */
public class Item_Int extends Item {
    public static final Integer[]  lens = new Integer[]{1,2,4,8};
    
    private BigInteger value;
    private Format format;
    private int len;
    //Metric -> bude mozno az posle kolega ty picoviny


    public Item_Int(Format format, Integer len, BigInteger adress,AFlowable parent, ItemManager.DataType type, boolean showUnit) {
        super(adress,parent, type, showUnit);
        init(new BigInteger("0"), format, len);
    }

    public Item_Int(BigInteger value, Format format, int len,  BigInteger adress,AFlowable parent, ItemManager.DataType type, boolean showUnit) {
        super(adress,parent, type, showUnit);
        init(value, format, len);
    }
    public void init(BigInteger value, Format format, int len){
        this.value = value;
        this.format = format;
        this.len = len;
        createView();
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
        setValue(new BigInteger(value));
    }

    public void setValue(BigInteger value) {
        this.value = value;
        updateAll();
    }
    
    public void setFormat(Format format) {
        this.format = format;
    }

    public void setLen(int len) {
        this.len = len;
    }

    @Override
    protected void createView() {
        vItem = ViewItem.createTextFieldView(this);
        super.createView();
    }

    @Override
    public String getStringValue() {
       return value+"";
    }

    @Override
    public Element createElementToSave(Document document) {
        Element element = super.createElementToSave(document);
        element.setAttribute(Constants.len, len+"");
        element.setAttribute(Constants.format, format.toString());
        element.setAttribute(Constants.value, value+"");
        return element;
    }
    
    public enum Format{
        Signed,Unsigned
    }
}
