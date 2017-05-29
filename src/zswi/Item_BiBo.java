package zswi;

/**
 *
 * @author DDvory
 */
public class Item_BiBo<T> implements IValuable{
    private Item item;
    private T value;

    public Item_BiBo(T value) {
        this.value = value;
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
