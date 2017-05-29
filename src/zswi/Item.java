package zswi;

import java.math.BigInteger;

/**
 *
 * @author DDvory
 */
public class Item extends AFlowable implements IUpdatable{
    private BigInteger adress;
    private ItemManager.DataType  type;
    private ViewItem vItem;
    private boolean showUnit;
    ///////////////////////
    private String correctMessage;
    /////////////////////////////
    private IValuable value;
    private String unit;

    public Item(String adress, String type, String showUnit, IValuable value) {
        init(adress, type, showUnit,value);
    }

    public Item(BigInteger adress, ItemManager.DataType type,  boolean showUnit, IValuable value) {
        init(adress, type, showUnit, value);
    }
    
    private void init(String adress, String type, String showUnit, IValuable value){
        init(ItemManager.getINSTANCE().getAdress(adress),ItemManager.DataType.fromString(type), Boolean.valueOf(showUnit), value);
    }
    private void init(BigInteger adress,ItemManager.DataType type, boolean showUnit, IValuable value){
        this.adress = adress;
        this.type = type;
        this.showUnit = showUnit;
        super.setName(type.toString());
        value.setItem(this);
        createView();
    }
    private void createView(){
        switch(this.getType()){
            case DOUBLE:
            case FLOAT:     
            case INT:
            case STRING:
                vItem = ViewItem.createTextFieldView(this);
                break;
            case ENUM:
            	vItem = ViewItem.createEnumView(this);
                break;
            case TIME:
                 vItem = ViewItem.createTimePickerView(this);
                break;
            case DATE:
              vItem = ViewItem.createDatePickerView(this);
              	break;
            case BOOLEAN:
            	vItem = ViewItem.createBitBoolView(this, true);
              	break;
            case BIT:
            	vItem = ViewItem.createBitBoolView(this, false);
            	break;
        }
        if(this.getType()==null){
            vItem = ViewItem.createTextFieldView(this);
            vItem.notificate();
        }
    }
    
    public void correction(boolean bool, String message){
        setIsCorrect(bool);
        correctMessage = message;
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

    public Object getData() {
        return value;
    }
    public void updateValue(Object obj){
        
    }

    public IValuable getValue() {
        return value;
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        vItem.notificate();
    }

    public boolean isShowUnit() {
        return showUnit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
        vItem.notificate();
    }

    public void setControlData(String str){
        ItemManager.getINSTANCE().setData(str, this);
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
    
}
