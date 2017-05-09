package zswi;

import java.util.Hashtable;
import java.util.Map;

public abstract class AFlowable {
    public static int incrementalId = 0;
    private Map<Integer, Name> mapNames = null;
    private int ID;
    
    public AFlowable() {
        this(getIncrementalIdAdd());
    }

    public AFlowable(int id) {
        this(id,"");
    }
    public AFlowable(int id,String name){
        if(id >= incrementalId)incrementalId = id+1;
        this.setID(id);
        if(ProjectManager.getINSTANCE()!=null){
            mapNames = new Hashtable<>();
            this.setNm(name);
        }
    }
    
    public static void clearIncrement(){
        incrementalId = 0;
    }

    private static int getIncrementalIdAdd() {
        return incrementalId++;
    }
    

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        int language = ProjectManager.getINSTANCE().getProject().getLanguage();
        Name get = mapNames.get(language);
        if(get==null)return "";
        else return get.getName();
    }

    public void setName(String name) {
        this.setNm(name);
    }
    private void setNm(String name){
        int language = ProjectManager.getINSTANCE().getProject().getLanguage();
        Name get = mapNames.get(language);
        if(get!=null) get.setName(name);
        else mapNames.put(language, new Name(language,name));
    }
    
    public class Name{
        private final int languageId;
        private String name;

        public Name(int languageId) {
            this(languageId, "");
        }

        public Name(int languageId, String name) {
            this.languageId = languageId;
            this.name = name;
        }

        public int getLanguageId() {
            return languageId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
