package zswi;

import java.math.BigInteger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author DDvory
 */
public abstract class Item extends AFlowable implements IUpdatable{
    private BigInteger adress;
    private ItemManager.DataType  type;
    protected ViewItem vItem;
    private boolean haveUnit;
    ///////////////////////
    private String correctMessage;
    /////////////////////////////

    public Item(BigInteger adress,AFlowable parent, ItemManager.DataType type,  boolean haveUnit) {
        super(type.toString(),parent);
        init(adress, type, haveUnit);
    }

    private void init(BigInteger adress,ItemManager.DataType type, boolean haveUnit){
        this.adress = adress;
        this.type = type;
        this.haveUnit = haveUnit;
    }
    protected void createView(){
        updateAll();
    }
    
    public void correction(boolean bool, String message){
        setIsCorrect(bool);
        correctMessage = message;
        super.correction(bool);
    }

    public BigInteger getAdress() {
        return adress;
    }

    public ItemManager.DataType getType() {
        return type;
    }

    public String getCorrectMessage() {
        return correctMessage;
    }

    public void setAdress(BigInteger adress) {
        this.adress = adress;
    }

    public void setType(ItemManager.DataType type) {
        this.type = type;
    }

    public void updateValue(Object obj){
        
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        vItem.notificate();
    }

    public boolean haveUnit() {
        return haveUnit;
    }

    public ViewItem getvItem() {
        return vItem;
    }

    @Override
    public void translate(LanguageManager langManager, int from, int to) {
        langManager.translate(this, from, to);
    }

    @Override
    public void updateAll() {
        this.vItem.notificate();
    }

    @Override
    public Element createElementToSave(Document document) {
        Element element = super.createElementToSave(document);
        element.setAttribute(Constants.adress, adress+"");
        element.setAttribute(Constants.dataType, type+"");
        element.setAttribute(Constants.haveUnit, haveUnit+"");
        return element;
    }
    
    public abstract String getStringValue();
    
}
