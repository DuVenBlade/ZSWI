package zswi;

/**
 *
 * @author DDvory
 */
public class Item<T> extends AFlowable {
    private short adress;
    private ItemManager.DataType  type;
    private int dataLen;
    private String format;
    private ViewItem vItem;
    ///////////////////////
    private boolean correct;
    private String correctMessage;
    /////////////////////////////
    private T data;
    private String name;
    private String unit;
    

    public Item(int id, String adress, String type, String dataLen, String format) {
        super(id);
        init(adress, type, dataLen, format);
    }

    public Item(String adress, String type, String dataLen, String format) {
        super();
        init(adress, type, dataLen, format);
    }

    public Item(short adress, ItemManager.DataType type, int dataLen, String format) {
        super();
        init(adress, type, dataLen, format);
    }
    
    private void init(String adress, String type, String dataLen, String format){
        init(Short.decode(adress),ItemManager.DataType.fromString(type),Integer.decode(dataLen),format);
    }
    private void init(short adress,ItemManager.DataType type, int dataLen, String format){
        this.adress = adress;
        this.type = type;
        this.dataLen = dataLen;
        this.format = format;
        createView();
    }
    private void createView(){
        
        switch(this.getType()){
            case DOUBLE:
            case ENUM:
            	vItem = ViewItem.createEnumView(this);
                break;
            case FLOAT:     
            case INT:
            case STRING:
              vItem = ViewItem.createTextFieldView(this);
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
        if(this.getType()==null)vItem = ViewItem.createTextFieldView(this);
    }
    
    public void correction(boolean bool, String message){
        correct = bool;
        correctMessage = message;
    }

    public short getAdress() {
        return adress;
    }

    public ItemManager.DataType getType() {
        return type;
    }

    public int getDataLen() {
        return dataLen;
    }

    public String getFormat() {
        return format;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getCorrectMessage() {
        return correctMessage;
    }

    public void setAdress(short adress) {
        this.adress = adress;
    }

    public void setType(ItemManager.DataType type) {
        this.type = type;
    }

    public void setDataLen(int dataLen) {
        this.dataLen = dataLen;
    }

    public void setFormat(String format) {
        this.format = format;
        vItem.notificate();
    }
    

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        vItem.notificate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        vItem.notificate();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
        vItem.notificate();
    }

    @Override
    public String toString() {
        return data.toString();
    }
    public void setControlData(String str){
        ItemManager.getINSTANCE().setData(str, this);
    }

    public ViewItem getvItem() {
        return vItem;
    }
}
