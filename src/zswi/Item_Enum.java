package zswi;

import java.math.BigInteger;

/**
 *
 * @author DDvory
 */
public class Item_Enum implements IValuable{

    private final BigInteger id;
    private BigInteger subId;
    private Item item;

    public Item_Enum() {
        this("0", "0");
    }

    public Item_Enum(String id, String subId) {
        this(new BigInteger(id), new BigInteger(subId));
    }

    public Item_Enum(BigInteger id, BigInteger subId) {
        this.id = id;
        this.subId = subId;
    }

    public BigInteger getID() {
        return id;
    }

    public BigInteger getSubId() {
        return subId;
    }

    public void setSubId(BigInteger subId) {
        this.subId = subId;
    }

    @Override
    public String toString() {
        return ProjectManager.getINSTANCE().getEnumManager().getSubIDName(id, subId);
    }

    @Override
    public void setItem(Item item) {
        this.item = item;
    }
}
