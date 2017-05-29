package zswi;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EnumManager {

    private Map<BigInteger, OwnEnum> enums;
    private EnumView vEnum;
    private Increment increment;
    public EnumManager() {
        enums = (Map<BigInteger, OwnEnum>) new Hashtable();
        vEnum = new EnumView(this);
        increment = new Increment();
    }

    public List<OwnEnum> getEnums() {
        List<OwnEnum> temp = new ArrayList<>();
        Set<BigInteger> set = enums.keySet();
        Iterator<BigInteger> iterator = set.iterator();
        BigInteger tmp;
        while (iterator.hasNext()) {
            tmp = iterator.next();
            if (!enums.get(tmp).getSubEnums().isEmpty()) {
                temp.add(enums.get(tmp));
            }
        }
        return temp;
    }
    
    public List<OwnEnum> getAllEnums() {
        List<OwnEnum> temp = new ArrayList<>();
        Set<BigInteger> set = enums.keySet();
        Iterator<BigInteger> iterator = set.iterator();
        BigInteger tmp;
        while (iterator.hasNext()) {
            tmp = iterator.next();
            temp.add(enums.get(tmp));
        }
        return temp;
    }

    public Map<BigInteger, OwnEnum> getEnumMap() {
        return enums;
    }

    public void addEnum(OwnEnum ownEnum) {
        enums.put(ownEnum.getID(), ownEnum);
    }

    public void removeEnum(OwnEnum ownEnum) {
        enums.remove(ownEnum.getID());
    }

    public String getIDName(BigInteger id) {
        return enums.get(id).getName();
    }

    public String getSubIDName(BigInteger id, BigInteger subId) {
        return enums.get(id).getSubEnumMap().get(subId).getName();
    }

    public OwnEnum getIDEnum(BigInteger id) {
        return enums.get(id);
    }

    public OwnSubEnum getSubIDSubEnum(BigInteger id, BigInteger subId) {
        return enums.get(id).getSubEnumMap().get(subId);
    }

    /*
	 * 8 byte nebo 8 bit?
     */
    public static BigInteger getAdress(String data) {
        BigInteger adress = null;
        try {
            adress = new BigInteger(data);
            byte[] arr = adress.toByteArray();
            if (arr.length > 2 || (arr.length == 2 && arr[0] != 0)) {
                adress = null;
            } else if (adress.signum() == -1) {
                adress = null;
            }
        } catch (Exception e) {
        }
        return adress;
    }

    public Increment getIncrement() {
        return increment;
    }

    public EnumView getvEnum() {
        return vEnum;
    }
    

    public static class OwnEnum extends OwnSubEnum {

        private Map<BigInteger, OwnSubEnum> subEnums;
        private Increment increment;
        public OwnEnum(String id, Name name) {
            super(id, name);
            subEnums = (Map<BigInteger, OwnSubEnum>) new Hashtable();
            increment = new Increment();
        }

        public OwnEnum(Increment increment,Name name) {
            this(increment.increment()+"",name);
        }

        public Increment getIncrement() {
            return increment;
        }
        public List<OwnSubEnum> getSubEnums() {
            List<OwnSubEnum> temp = new ArrayList<OwnSubEnum>();
            Set<BigInteger> set = subEnums.keySet();
            Iterator<BigInteger> iterator = set.iterator();
            BigInteger tmp;
            while (iterator.hasNext()) {
                tmp = iterator.next();
                temp.add(subEnums.get(tmp));
            }
            return temp;
        }
        public OwnSubEnum getSubEnum(BigInteger integer){
            return subEnums.get(integer);
        }

        public Map<BigInteger, OwnSubEnum> getSubEnumMap() {
            return subEnums;
        }

        public void addSubEnum(OwnSubEnum ownSubEnum) {
            subEnums.put(ownSubEnum.getID(), ownSubEnum);
        }

        public void removeSubEnum(OwnSubEnum ownSubEnum) {
            subEnums.remove(ownSubEnum.getID());
        }

    }

    public static class OwnSubEnum  extends AFlowable{

        private BigInteger id;

        public OwnSubEnum(String id, Name name) {
            this(new BigInteger(id), name);
        }

        public OwnSubEnum(BigInteger id, Name name) {
            this.id = id;
            this.setName(name);
        }

        public OwnSubEnum(Increment incr,Name name) {
            this(incr.increment()+"",name);
        }

        public BigInteger getID() {
            return id;
        }

        @Override
        public void translate(LanguageManager langManager, int from, int to) {
            ProjectManager.getINSTANCE().getLanguageManager().translate(this, from, to);
        }
        
    }
}
