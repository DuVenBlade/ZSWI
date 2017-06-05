package zswi;


import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import zswi.EnumManager.EnumValue;
import zswi.Item_Int.Format;

/**
 *
 * @author DDvory
 */
public class ItemManager {

    private static ItemManager INSTANCE;

    public static ItemManager getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new ItemManager();
        }
        return INSTANCE;
    }

    public void setData(Object data, Item item) {
        item.correction(true, "");
        try {
            Method m = ItemManager.class.getDeclaredMethod("set" + item.getType(), Object.class, zswi.Item.class);
            m.setAccessible(true);
            m.invoke(this, data, item);
        } catch (Exception e) { //(NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public Item createItem(BigInteger adress,ItemManager.DataType type, ViewItemEditManager.ACreateable view,AFlowable parent){
        Object[] data=null;
        if(view !=null)
        data = view.getData();
        switch (type) {
            case STRING:
                return new Item_String(Integer.decode((String)data[0]), adress,parent, type, false);
            case INT:
                return new Item_Int((Format)data[1],(Integer)data[0],adress,parent, type, false);
            case DOUBLE:
                return new Item_DoFl(0.0,adress,parent, type, false);
            case FLOAT:
                return new Item_DoFl(0f,adress,parent, type, false);
            case DATE:
            case TIME:
                return new Item_DaTi((Item_DaTi.Format)data[0],adress, parent,type, false);
            case ENUM:
                return new Item_Enum(((BigInteger)data[0]), adress,parent, type, false);
            case BOOLEAN:
                 return new Item_BiBo<>(false, adress, parent,type, false);
            case BIT:
                return new Item_BiBo<>((byte)0, adress,parent, type, false);
        }
        return null;
    }
    public BigInteger getAdress(String data){
        BigInteger adress =null;
        try {
            adress = new BigInteger(data);
            byte[] arr = adress.toByteArray();
            if(arr.length>3||(arr.length==3&&arr[0]!=0)){
                adress=null;
            }else if(adress.signum()==-1){
                adress=null;
            }
        }catch (Exception e) {}
        return adress;
    }

    private void setSTRING(Object obj, Item item) {
        String STRING = null;
        String string = (String)obj;
        Item_String value = (Item_String)item;
        if (string.getBytes().length > value.getLen()) {
            item.correction(false, "Text je příliš dlouhý, zkraťte ho, nebo nastavte buňku na větší délku");
        }
        STRING = string;
        value.setValue(STRING);
    }

    private void setDOUBLE(Object obj, Item item) {
        Double DOUBLE = null;
        String string = (String)obj;
        Item_DoFl<Double> value = (Item_DoFl)item;
        try {
            DOUBLE = Double.valueOf(string);
        } catch (Exception e) {  
            item.correction(false, "Špatný formát čísla");
        }
        value.setValue(DOUBLE);
    }

    private void setFLOAT(Object obj, Item item) {
        Float FLOAT = null;
        String string = (String)obj;
        Item_DoFl<Float> value = (Item_DoFl)item;
        try {
            FLOAT = Float.valueOf(string);
        } catch (Exception e) {
            item.correction(false, "Špatný formát čísla");
        }
        value.setValue(FLOAT);
    }

    private void setINT(Object obj, Item item) {
        BigInteger INT = null;
        String string = (String)obj;
        Item_Int value = (Item_Int)item;
        try {
            BigInteger integ = new BigInteger(string);
            byte[] byt = integ.toByteArray();
            if (!(byt.length <= value.getLen()||
                  byt.length == value.getLen() + 1 && byt[0] == 0&&value.getFormat() == Format.Unsigned)||
                  integ.signum()<0&&value.getFormat() == Format.Unsigned) {
                throw new IllegalArgumentException();
            }
            INT = integ;
        } catch (Exception e) {
            item.correction(false, "Špatný formát čísla, nebo je číslo příliš veliké");
        }
        value.setValue(INT);
    }

    private void setDATE(Object obj, Item item) {
        LocalDate DATE = (LocalDate)obj;
        Item_DaTi value = (Item_DaTi)item;
        value.setValue(DATE);
    }

    private void setTIME(Object obj, Item item) {
        LocalTime TIME = (LocalTime)obj;
        Item_DaTi value = (Item_DaTi)item;
        value.setValue(TIME);
    }

    private void setENUM(Object obj, Item item) {
        Item_Enum value = (Item_Enum)item;
        EnumValue o = (EnumValue)obj;
        value.setValue(o.getID());
    }
     private void setBOOLEAN(Object obj, Item item) {
        ((Item_BiBo<Boolean>)item).setValue((Boolean)obj);
    }
    private void setBIT(Object obj, Item item) {
        ((Item_BiBo<Byte>)item).setValue((Byte)obj);
    }
    //----------------------------------------------------------------------------------------------------------------------------------------
    public Item createI_BiBo(AFlowable parent, ItemManager.DataType type, String adress, String haveUnit, String value){
        Item item = null;
        switch (type) {
            case BOOLEAN:
                item = new Item_BiBo<Boolean>(Boolean.valueOf(value),new BigInteger(adress),parent, type, Boolean.valueOf(haveUnit));
                break;
            case BIT:
                item = new Item_BiBo<Byte>(Byte.valueOf(value),new BigInteger(adress),parent, type, Boolean.valueOf(haveUnit));
                break;
        }
        return item;
    }
    public Item createI_DoFl(AFlowable parent, ItemManager.DataType type, String adress, String haveUnit, String value){
        Item item = null;
        switch (type) {
            case DOUBLE:
                item = new Item_DoFl<Double>(Double.valueOf(value),new BigInteger(adress),parent, type, Boolean.valueOf(haveUnit));
                break;
            case FLOAT:
                item = new Item_DoFl<Float>(Float.valueOf(value),new BigInteger(adress),parent, type, Boolean.valueOf(haveUnit));
                break;
        }
        return item;
    }
    public Item createI_Int(AFlowable parent, ItemManager.DataType type, String adress, String haveUnit,String format, String len, String value){
        Item item = new Item_Int(new BigInteger(value),Item_Int.Format.valueOf(format),Integer.valueOf(len),new BigInteger(adress),parent, type, Boolean.valueOf(haveUnit));
        return item;
    }
    public Item createI_String(AFlowable parent, ItemManager.DataType type, String adress, String haveUnit,  String len, String value){
        Item item = new Item_String(value,Integer.valueOf(len),new BigInteger(adress),parent, type, Boolean.valueOf(haveUnit));
        return item;
    }
    public Item createI_Enum(AFlowable parent, ItemManager.DataType type, String adress, String haveUnit,  String id, String value){
        Item item = new Item_Enum(new BigInteger(value),new BigInteger(adress),parent, type, Boolean.valueOf(haveUnit));
        return item;
    }
    public Item createI_DaTi(AFlowable parent, ItemManager.DataType type, String adress, String haveUnit,  String format, String value){
        Item item = null;
        switch (type) {
            case DATE:
                item = new Item_DaTi(Item_DaTi.Format.getLocalDate(value),new Item_DaTi.Format(format), new BigInteger(adress), parent, type, false);
                break;
            case TIME:
                item = new Item_DaTi(Item_DaTi.Format.getLocalTime(value),new Item_DaTi.Format(format), new BigInteger(adress), parent, type, false);
                break;
        }
        return item;
    }
    public enum DataType {
        STRING, INT, DOUBLE, FLOAT, DATE, TIME, ENUM,BOOLEAN, BIT;

        public static DataType fromString(String str) {
            DataType[] values = DataType.values();
            for (DataType value : values) {
                if (value.toString().equals(str)) {
                    return value;
                }
            }return null;
        }
    }
}
