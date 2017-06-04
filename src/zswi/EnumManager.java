package zswi;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class EnumManager extends ASaveable {

    private EnumView vEnum;
    private Increment enumIncrement;
    private List<EnumSection> list;
    private Map<BigInteger, EnumValues> mapValues;

    public EnumManager() {
        mapValues = (Map<BigInteger, EnumValues>) new Hashtable();
        vEnum = new EnumView(this);
        enumIncrement = new Increment();
    }

    public List<EnumSection> getNotEmptyEnums() {
        List<EnumSection> temp = new ArrayList<>();
        for (EnumSection enumSection : list) {
            if(!enumSection.getEnumValueList().isEmpty())
                temp.add(enumSection);
        }
        return temp;
    }
    
    public List<EnumSection> getEnumList() {
        return list;
    }

    public Map<BigInteger, EnumValues> getEnumMap() {
        return mapValues;
    }

    public void addEnumSection(EnumSection ownEnum) {
        list.add(ownEnum);
    }

    public void removeEnumSection(EnumSection ownEnum) {
        list.remove(ownEnum);
    }

    public String getName(BigInteger id) {
        return mapValues.get(id).getName();
    }

    public EnumSection getSection(BigInteger id) {
        return (EnumSection)mapValues.get(id).getParent();
    }
    public Increment getIncrement() {
        return enumIncrement;
    }

    public EnumView getvEnum() {
        return vEnum;
    }
    

    public static class EnumSection extends EnumValues {

        private List<EnumValues> EnumValues;
        public EnumSection(String id, String name) {
            super(id, name,null);
        }

        public EnumSection(int id,String name) {
            this(id+"",name);
        }

        public List<EnumValues> getEnumValueList() {
            return EnumValues;
        }
        

        public void addEnumValue(EnumValues enumValue) {
            EnumValues.add(enumValue);
        }

        public void removeEnumValue(EnumValues ownSubEnum) {
            EnumValues.remove(ownSubEnum);
        }
        @Override
        public void translate(LanguageManager langManager, int from, int to) {
            langManager.translate(this, from, to);
        }
    }

    public static class EnumValues  extends AFlowable{

        private BigInteger id;

        public EnumValues(String id, String name, AFlowable parent) {
            this(new BigInteger(id), name,parent);
        }

        public EnumValues(BigInteger id, String name, AFlowable parent) {
            super(name,parent);
            this.id = id;
        }

        public BigInteger getID() {
            return id;
        }

        @Override
        public void translate(LanguageManager langManager, int from, int to) {
            langManager.translate(this, from, to);
        }
        
    }
}
