package zswi;

import java.math.BigInteger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author DDvory
 * @param <T>
 */
public class Item_DoFl<T> extends Item{
    private T value;
    //Metric

    public Item_DoFl(T value, BigInteger adress,AFlowable parent, ItemManager.DataType type, boolean showUnit) {
        super(adress,parent, type, showUnit);
        init(value);
    }
    private void init(T value){
        this.value = value;
        createView();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        updateAll();
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
        element.setAttribute(Constants.value, value+"");
        return element;
    }
    
}
