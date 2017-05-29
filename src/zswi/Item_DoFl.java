package zswi;

/**
 *
 * @author DDvory
 */
public class Item_DoFl<T> implements IValuable{
    private T value;
    private Item item;
    //Metric

    public Item_DoFl(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public void setItem(Item item) {
        this.item = item;
    }
    
}
