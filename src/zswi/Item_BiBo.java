package zswi;

import java.math.BigInteger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author DDvory
 */
public class Item_BiBo<T> extends Item{
    private T value;


    public Item_BiBo(T value, BigInteger adress,AFlowable parent, ItemManager.DataType type, boolean showUnit) {
        super(adress,parent, type, showUnit);
        this.value = value;
        createView();
    }


    public void setValue(T value) {
        this.value = value;
        updateAll();
    }
    

    @Override
    protected void createView() {
        switch (this.getType()) {
            case  BIT:
                 this.vItem = ViewItem.createBitBoEnView(this,(byte)0,(byte)1);
                break;
            case  BOOLEAN:
                this.vItem = ViewItem.createBitBoEnView(this,true,false);
                break;
        }
        super.createView();
    }

    @Override
    public String getStringValue() {
        return value+"";
    }

    @Override
    public Element createElementToSave(Document document) {
        Element element = super.createElementToSave(document); 
        element.setAttribute(Constants.value, value+"");
        return element;
    }
    
}
