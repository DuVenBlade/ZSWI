package zswi;

import java.math.BigInteger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author DDvory
 */
public class Item_String extends Item {
    private String value;
    private int len;

    public Item_String(String value, int len, BigInteger adress,AFlowable parent, ItemManager.DataType type, boolean showUnit) {
        super(adress,parent, type, showUnit);
        init(value, len);
    }

    public Item_String(int len, BigInteger adress,AFlowable parent, ItemManager.DataType type, boolean showUnit) {
        super(adress,parent, type, showUnit);
        init("", len);
    }
    
    private void init(String value, int len){
        this.value = value;
        this.len = len;
        createView();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        updateAll();
    }

    public int getLen() {
        return len;
    }
    public void setLen(String len){
        try {
            setLen(Integer.decode(len));
        } catch (Exception e) {
        }
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
      return value;
    }

    @Override
    public Element createElementToSave(Document document) {
        Element element = super.createElementToSave(document);
        element.setAttribute(Constants.len, len+"");
        element.setAttribute(Constants.value, value);
        return element;
    }
    
}
