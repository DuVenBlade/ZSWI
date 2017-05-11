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

    public EnumManager() {
        enums = (Map<BigInteger, OwnEnum>) new Hashtable();

        OwnEnum ownEnum = new OwnEnum("2", new Name(0, "hodnota 2"));
        OwnEnum ownEnum2 = new OwnEnum("4", new Name(0, "hodnota 4"));
        OwnEnum ownEnum3 = new OwnEnum("5", new Name(0, "hodnota 5"));
        OwnSubEnum subEnum1 = new OwnSubEnum("6", new Name(0, "hodnota 6"));
        OwnSubEnum subEnum2 = new OwnSubEnum("7", new Name(0, "hodnota 7"));
        OwnSubEnum subEnum3 = new OwnSubEnum("8", new Name(0, "hodnota 8"));
        ownEnum.addSubEnum(subEnum1);
        ownEnum.addSubEnum(subEnum2);
        ownEnum2.addSubEnum(subEnum3);
        this.addEnum(ownEnum);
        this.addEnum(ownEnum2);
        this.addEnum(ownEnum3);
    }

    public List<OwnEnum> getEnums() {
        List<OwnEnum> temp = new ArrayList<>();
        Set<BigInteger> set = enums.keySet();
        Iterator<BigInteger> iterator = set.iterator();
        BigInteger tmp;
        while (iterator.hasNext()) {
            tmp = iterator.next();
            TestLogger.print(this.getClass(), tmp.toString());
            if (!enums.get(tmp).getSubEnums().isEmpty()) {
                temp.add(enums.get(tmp));
                TestLogger.print(this.getClass(), "added");
            }
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
        int temp = ProjectManager.getINSTANCE().getProject().getLanguage();
        return enums.get(id).getMap().get(temp).getName();
    }

    public String getSubIDName(BigInteger id, BigInteger subId) {
        int temp = ProjectManager.getINSTANCE().getProject().getLanguage();
        return enums.get(id).getSubEnumMap().get(subId).getMap().get(temp).getName();
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

    public static class EnumItem {

        private final BigInteger id;
        private BigInteger subId;

        public EnumItem() {
            this("0", "0");
        }

        public EnumItem(String id, String subId) {
            this(new BigInteger(id), new BigInteger(subId));
        }

        public EnumItem(BigInteger id, BigInteger subId) {
            this.id = id;
            this.subId = subId;
        }

        public BigInteger getID() {
            return id;
        }

        public BigInteger getSubId() {
            return subId;
        }

        @Override
        public String toString() {
            return ProjectManager.getINSTANCE().getEnumManager().getSubIDName(id, subId);
        }
    }

    public static class OwnEnum extends OwnSubEnum {

        private Map<BigInteger, OwnSubEnum> subEnums;

        public OwnEnum(String id, Name name) {
            super(id, name);
            subEnums = (Map<BigInteger, OwnSubEnum>) new Hashtable();
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

    public static class OwnSubEnum {

        private Map<Integer, Name> map;
        private BigInteger id;

        public OwnSubEnum(String id, Name name) {
            this(new BigInteger(id), name);
        }

        public OwnSubEnum(BigInteger id, Name name) {
            this.id = id;
            map = (Map<Integer, Name>) new Hashtable();
            map.put(name.getLanguageId(), name);
        }

        public BigInteger getID() {
            return id;
        }

        public Map<Integer, Name> getMap() {
            return map;
        }

        @Override
        public String toString() {
            int temp = ProjectManager.getINSTANCE().getProject().getLanguage();
            Name name = map.get(temp);
            return name == null ? "" : name.getName();
        }

    }
}
