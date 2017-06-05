package zswi;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class EnumManager extends ASaveable {

    private EnumView vEnum;
    private Increment enumIncrement;
    private Increment enumValueIncrement;
    private List<EnumSection> list;
    private Map<BigInteger, EnumValue> mapValues;

    public EnumManager() {
        mapValues = (Map<BigInteger, EnumValue>) new Hashtable();
        vEnum = new EnumView(this);
        enumIncrement = new Increment();
        enumValueIncrement = new Increment();
        list = new ArrayList<>();
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

    public Map<BigInteger, EnumValue> getEnumMap() {
        return mapValues;
    }

    public void addEnumSection(EnumSection ownEnum) {
        list.add(ownEnum);
        vEnum.notificate();
    }
    public void addEnumSection(String name){
        String increment = enumIncrement.toStringIncrement();
        addEnumSection(new EnumSection(this,increment, name));
        
    }
    public void addEnumValue(EnumSection section,String name){
        addEnumValue(section, new EnumValue(enumValueIncrement.toStringIncrement(), name, section));
    }
    
    public void addEnumValue(EnumSection section,EnumValue value){
        section.addEnumValue(value);
        vEnum.notificate();
    }
    
    public void removeEnumSection(EnumSection ownEnum) {
        list.remove(ownEnum);
        vEnum.notificate();
    }

    public String getName(BigInteger id) {
        return mapValues.get(id).getName();
    }

    public EnumSection getSection(BigInteger id) {
        return (EnumSection)mapValues.get(id).getParent();
    }

    public EnumView getvEnum() {
        return vEnum;
    }

    @Override
    public Element createElementToSave(Document document) {
        Element element = super.createElementToSave(document);
        for (EnumSection enumSection : list) {
            element.appendChild(enumSection.createElementToSave(document));
        }
        return element;
    }

    void removeEnumValue(EnumValue item) {
       EnumSection section =(EnumSection)item.getParent();
       section.removeEnumValue(item);
       vEnum.notificate();
    }
    

    public static class EnumSection extends EnumValue {

        private List<EnumValue> EnumValues;
        private EnumManager manager;
        public EnumSection(EnumManager manager,String id) {
            super(id, Project.getInstance());
            init(manager);
        }
        
        public EnumSection(EnumManager manager,int id,String name) {
            this(manager,id+"",name);
        }
        
        public EnumSection(EnumManager manager,String id, String name) {
            super(id, name,Project.getInstance());
            init(manager);
        }

        public List<EnumValue> getEnumValueList() {
            return EnumValues;
        }
        
        private void init(EnumManager manager){
            EnumValues = new ArrayList<>();
            this.manager = manager;
        }

        public void addEnumValue(EnumValue enumValue) {
            EnumValues.add(enumValue);
            manager.getEnumMap().put(enumValue.getID(),enumValue);
        }

        public void removeEnumValue(EnumValue ownSubEnum) {
            EnumValues.remove(ownSubEnum);
            manager.getEnumList().remove(ownSubEnum.getID());
        }
        @Override
        public void translate(LanguageManager langManager, int from, int to) {
            langManager.translate(this, from, to);
            for (EnumValue EnumValue1 : EnumValues) {
                EnumValue1.translate(langManager, from, to);
            }
        }

        @Override
        public Element createElementToSave(Document document) {
            Element element = super.createElementToSave(document); 
            for (EnumValue EnumValue1 : EnumValues) {
                element.appendChild(EnumValue1.createElementToSave(document));
            }
            return element;
        }
        
    }

    public static class EnumValue  extends AFlowable{

        private BigInteger id;

        public EnumValue(String id, AFlowable parent) {
            super(parent);
            this.id = new BigInteger(id);
        }
        

        public EnumValue(String id, String name, AFlowable parent) {
            this(new BigInteger(id), name,parent);
        }

        public EnumValue(BigInteger id, String name, AFlowable parent) {
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

        @Override
        public Element createElementToSave(Document document) {
            Element element = super.createElementToSave(document);
            element.setAttribute(Constants.id, id+"");
            return element;
        }
        
        
    }
}
