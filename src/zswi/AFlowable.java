package zswi;
//test//
import java.util.Map;

public abstract class AFlowable {
    public static int incrementalId = 0;
    private Map<Integer, String> names;
    private int ID;
    
    public AFlowable() {
        this(getIncrementalIdAdd());
    }

    public AFlowable(int ID) {
        if(ID >= incrementalId)incrementalId = ID+1;
        this.ID = ID;
        if(ProjectManager.getINSTANCE()!=null);
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
        map.
        return m;
    }

    public void setName(String name) {
        this.name = name;
    }
    private class Name{
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
        
        
    }
}
