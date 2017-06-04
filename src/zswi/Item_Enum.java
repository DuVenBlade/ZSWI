package zswi;

import java.math.BigInteger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author DDvory
 */
public class Item_Enum extends Item{

    private BigInteger value;

    public Item_Enum(BigInteger value, BigInteger adress,AFlowable parent, ItemManager.DataType type, boolean showUnit) {
        super(adress,parent, type, showUnit);
        this.value = value;
        createView();
    }
    public BigInteger getID() {
        return value;
    }


    public void setValue(BigInteger value) {
        this.value = value;
        this.updateAll();
    }

    @Override
    protected void createView() {
        vItem = ViewItem.createBitBoEnView(this, Project.getInstance().getEManager().getSection(value).getEnumValueList().toArray());
        super.createView();
    }

    @Override
    public String getStringValue() {
        if(value==null)return "";
         return Project.getInstance().getEManager().getName(value);
    }

    @Override
    public Element createElementToSave(Document document) {
        Element element = super.createElementToSave(document);
        element.setAttribute(Constants.id, value+"");
        element.setAttribute(Constants.value, value+"");
        return element;
    }
    
    
}
