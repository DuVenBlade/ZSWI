package zswi;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import zswi.ItemManager.DataType;

/**
 * Item for date and time
 *
 * @author DDvory
 */
public class Item_DaTi extends Item {

    private Object value;
    private Format format;


    public Item_DaTi(Format format, BigInteger adress,AFlowable parent, ItemManager.DataType type, boolean showUnit) {
        super(adress,parent, type, showUnit);
        init(format, this.getType()==DataType.DATE?LocalDate.now():LocalTime.now());
    }


    public Item_DaTi(Object value, Format format, BigInteger adress,AFlowable parent, ItemManager.DataType type, boolean showUnit) {
        super(adress,parent, type, showUnit);
        init(format, value);
    }
    public  void init(Format format, Object value){
        this.value = value;
        this.format = format;
        createView();
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
        this.getvItem().notificate();
    }

    public SimpleDateFormat getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public static Format[] getFormatters(DataType type) {
        Format[] array = null;
        if (type== DataType.DATE) {
            array = new Format[]{
                new Format("ddMMyyyy"),
                new Format("dd-MM-yyyy"),
                new Format("dd.MM.yyyy"),
                new Format("MM-dd-yyyy"),
            };
        } else if (type== DataType.TIME) {
            array = new Format[]{
                new Format("HHmmSS"),
                new Format("hh:mm:SS a"),
                new Format("HH:mm:SS"),
            };
        }
        return array;
    }

    @Override
    protected void createView() {
        switch (this.getType()) {
            case DATE:
                vItem = ViewItem.createDatePickerView(this);
                break;
            case TIME:
                vItem = ViewItem.createTimePickerView(this);
                break;
        }
        super.createView();
    }

    @Override
    public String getStringValue() {
        String str = "null";
        if (value == null); else if (value instanceof LocalDate) {
            str = format.format(Date.valueOf((LocalDate) value));
        } else if (value instanceof LocalTime) {
            str = format.format(Time.valueOf((LocalTime) value));
        }
        return str;
    }

    @Override
    public Element createElementToSave(Document document) {
        Element element = super.createElementToSave(document);
        element.setAttribute(Constants.format, format.toPattern());
        switch (this.getType()) {
            case DATE:
                element.setAttribute(Constants.value, new SimpleDateFormat("ddMMyyyy").format(Date.valueOf((LocalDate) value)));
                break;
            case TIME:
                element.setAttribute(Constants.value, new SimpleDateFormat("HHmmSS").format(Time.valueOf((LocalTime) value)));
                break;
        }
        return element;
    }
    public static class Format extends SimpleDateFormat{

        public Format(String pattern) {
            super(pattern);
        }
        public static LocalTime getLocalTime(String args) {
            String h = args.substring(0,2);
            String m = args.substring(2,4);
            String s = args.substring(4);
            return LocalTime.of(Integer.decode(h), Integer.decode(m), Integer.decode(s));
        }
        //
        public static LocalDate getLocalDate(String args) {
            String d = args.substring(0,2);
            String m = args.substring(2,4);
            String y = args.substring(4);
            return LocalDate.of(Integer.decode(y), Integer.decode(m), Integer.decode(d));
        }
        @Override
        public String toString() {
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            String str = "";
            str+=this.format(Date.valueOf(date));
            str+=" ";
            str+=this.format(Time.valueOf(time));
            return str;
        }
        
    }
}
