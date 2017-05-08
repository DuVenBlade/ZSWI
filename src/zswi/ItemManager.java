package zswi;


import java.lang.reflect.Method;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;

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

    public void setData(String data, Item item) {
        item.correction(true, "");
        try {
            Method m = ItemManager.class.getDeclaredMethod("set" + item.getType(), String.class, zswi.Item.class);
            m.setAccessible(true);
            m.invoke(this, data, item);

        } catch (Exception e) { //(NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private void setSTRING(String string, Item<String> item) {
        String STRING = null;
        if (string.getBytes().length > item.getDataLen()) {
            item.correction(false, "Text je příliš dlouhý, zkraťte ho, nebo nastavte buňku na větší dýlku");
        }
        STRING = string;
        item.setData(STRING);
    }

    private void setDOUBLE(String string, Item<Double> item) {
        double DOUBLE = 0;
        try {
            DOUBLE = Double.valueOf(string);
        } catch (Exception e) {
            item.correction(false, "Špatný formát čísla");
        }
        item.setData(DOUBLE);
    }

    private void setFLOAT(String string, Item<Float> item) {
        float FLOAT = 0;
        try {
            FLOAT = Float.valueOf(string);
        } catch (Exception e) {
            item.correction(false, "Špatný formát čísla");
        }
        item.setData(FLOAT);
    }

    private void setINT(String string, Item<BigInteger> item) {
        BigInteger INT = null;
        try {
            BigInteger integ = new BigInteger(string);
            byte[] byt = integ.toByteArray();
            if (!(byt.length <= item.getDataLen() || byt.length == item.getDataLen() + 1 && byt[0] == 0)) {
                throw new IllegalArgumentException();
            }
            INT = integ;
        } catch (Exception e) {
            item.correction(false, "Špatný formát čísla, nebo je číslo příliš veliké");
        }
        item.setData(INT);
    }

    private void setDATE(String string, Item<LocalDate> item) {
        LocalDate DATE = null;
        try {
            String[] split = string.split(";");
            int a = Integer.decode(split[0]);
            int b = Integer.decode(split[1]);
            int c = Integer.decode(split[2]);
            DATE = LocalDate.of(a, b, c);
        } catch (Exception e) {
            item.correction(false, "Špatná data pro datum.");
        }
        item.setData(DATE);
    }

    private void setTIME(String string, Item<LocalTime> item) {
        LocalTime TIME = null;
        try {
            String[] split = string.split(":");
            int a = Integer.decode(split[0]);
            int b = Integer.decode(split[1]);
            int c = Integer.decode(split[2]);
            TIME = LocalTime.of(a, b, c);
        } catch (Exception e) {
            item.correction(false, "Špatná data pro čas.");
        }
        item.setData(TIME);
    }

    private void setENUM(String string, Item<EnumItem> item) {
        EnumItem ENUM = null;
        try {
            String[] split = string.split(";");
            int a = Integer.decode(split[0]);
            int b = Integer.decode(split[1]);
            ENUM = new EnumItem(a, b);
        } catch (Exception e) {
            item.correction(false, "Špatná data pro výčtový typ.");
        }
        item.setData(ENUM);
    }

    public static class EnumItem {

        private final int id;
        private int subId;

        public EnumItem(int id, int subId) {
            this.id = id;
            this.subId = subId;
        }

    }

    public enum DataType {
        STRING, INT, DOUBLE, FLOAT, DATE, TIME, ENUM,;

        public static DataType fromString(String str) {
            DataType[] values = DataType.values();
            for (DataType value : values) {
                if (value.toString().equals(str)) {
                    return value;
                }
            }
            return null;
        }
    }
}
