package zswi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Item for date and time
 *
 * @author DDvory
 */
public class Item_DaTi implements IValuable{
    private Item item;
    private Object value;
    private DateTimeFormatter format;

    public Item_DaTi(Object value) {
        this(value,value instanceof LocalDate?DateTimeFormatter.BASIC_ISO_DATE:DateTimeFormatter.ISO_LOCAL_TIME );
        
    }
    public Item_DaTi(Object value, DateTimeFormatter format) {
        this.value = value;
        this.format = format;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public DateTimeFormatter getFormat() {
        return format;
    }

    public void setFormat(DateTimeFormatter format) {
        this.format = format;
    }

    @Override
    public String toString() {
        String str = "";
        if (value == null); else if (value instanceof LocalDate) {
            str = ((LocalDate) value).format(format);
        } else if (value instanceof LocalTime) {
            ((LocalTime) value).format(format);
        }
        return str;
    }

    public DateTimeFormatter[] getFormatters() {
        DateTimeFormatter[] array=null;
        if (value instanceof LocalDate) {
            array = new DateTimeFormatter[]{
                DateTimeFormatter.BASIC_ISO_DATE,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_LOCAL_DATE,
                DateTimeFormatter.ISO_OFFSET_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
            };
        } else if (value instanceof LocalTime) {
            array = new DateTimeFormatter[]{
                DateTimeFormatter.ISO_LOCAL_TIME,
                DateTimeFormatter.ISO_OFFSET_TIME,
                DateTimeFormatter.ISO_TIME
            };
        }
        return array;
    }

    @Override
    public void setItem(Item item) {
        this.item = item;
    }

}
