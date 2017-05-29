package zswi;

import java.util.Hashtable;
import java.util.Map;

public abstract class AFlowable {
    private boolean isCorrect;
    private Map<Integer, Name> mapNames = null;
 
    public AFlowable() {
        this("");
    }
    public AFlowable(Name ... names){
         mapNames = new Hashtable<>();
        for (Name name : names) {
            mapNames.put(name.getLanguageId(), name);
        }
    }
    public AFlowable(String name){
        isCorrect = true;
        if(ProjectManager.getINSTANCE()!=null){
            mapNames = new Hashtable<>();
            this.setNm(name);
        }
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getName() {
        int id = ProjectManager.getINSTANCE().getProject().getLanguage();
        return getName(id);
    }

    public void setName(String name) {
        this.setNm(name);
    }
    private void setNm(String name){
        int language = ProjectManager.getINSTANCE().getProject().getLanguage();
        setName(new Name(language,name));
    }
    public void setName(Name name){
        Name get = mapNames.get(name.getLanguageId());
        if(get!=null) get.setName(name.getName());
        else mapNames.put(name.getLanguageId(), name);
    }
    public String getName(int id){
        Name get = mapNames.get(id);
        if(get==null)return "";
        else return get.getName();
    }
    @Override
    public String toString() {
        return getName();
    }
    public abstract void translate(LanguageManager langManager,int from, int to);
}
