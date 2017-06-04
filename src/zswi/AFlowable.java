package zswi;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AFlowable extends ASaveable {

    protected boolean isCorrect;
    private Map<Integer, Name> mapNames = null;
    protected AFlowable parent;

    public AFlowable(AFlowable parent) {
        init(parent);
    }

    public AFlowable(String name, AFlowable parent) {
        init(parent);
        this.setNm(name);
    }
    private void init(AFlowable parent){
        isCorrect = true;
        this.parent = parent;
        mapNames = new Hashtable<>();
    }

    private List<Name> getListNames() {
        List<Name> list = new ArrayList();
        if (mapNames != null) {
            Set<Integer> keySet = mapNames.keySet();
            for (Integer integer : keySet) {
                list.add(mapNames.get(integer));
            }
        }
        return list;
    }

    public void setListNames(List<Name> list) {
        if (list == null) {
            return;
        }
        if (this.mapNames == null) {
            mapNames = new Hashtable();
        }
        for (Name name : list) {
            mapNames.put(name.getLanguageId(), name);
        }
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getName() {
        int id = this.getProject().getLanguage();
        return getName(id);
    }

    public AFlowable getParent() {
        return parent;
    }

    public Project getProject() {
        if (parent == null) {
            return (Project) this;
        }
        return parent.getProject();
    }

    public void setName(String name) {
        this.setNm(name);
    }

    private void setNm(String name) {
        int language = this.getProject().getLanguage();
        setName(new Name(language, name));
    }

    public void setName(Name name) {
        Name get = mapNames.get(name.getLanguageId());
        if (get != null) {
            get.setName(name.getName());
        } else {
            mapNames.put(name.getLanguageId(), name);
        }
    }

    public String getName(int id) {
        Name get = mapNames.get(id);
        if (get == null) {
            return "";
        } else {
            return get.getName();
        }
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public Element createElementToSave(Document document) {
        Element saveDocument = super.createElementToSave(document);
        List<Name> listNames = getListNames();
        for (Name listName : listNames) {
            saveDocument.appendChild(listName.createElementToSave(document));
        }
        return saveDocument;
    }

    public void correction(boolean bool) {
        this.isCorrect = bool;
        parent.correction(bool);
    }

    public abstract void translate(LanguageManager langManager, int from, int to);
}
