package zswi;

/**
 *
 * @author DDvory
 */
public class Item_String implements IValuable {
    private String value;
    private int len;
    private Item item;

    public Item_String(String value, int len) {
        this.value = value;
        this.len = len;
    }

    public Item_String(int len) {
        this("",len);
    }

    Item_String() {
        this(0);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
    public void setItem(Item item) {
        this.item = item;
    }
}
