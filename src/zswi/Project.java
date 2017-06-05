package zswi;


import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import zswi.LanguageManager.Language;

public class Project extends Window {
    private static Project Instance;
    private EnumManager eManager;
    private ViewProject vProject;
    private int language;
    private String name;
    private List<Language> listLanguages;
    //---------------------------------------------------
    public static Project createProject(int language,String name, List<Language> list) {
        Instance = new Project(language, name, list);
        return Instance;
    }
    public static void close(){
        Instance.vProject.close();
        Instance=null;
    }
    public static Project getInstance() {
        return Instance;
    }
    //----------------------------------------------------------------------------
    private Project(int language,String name,  List<Language> listLanguages) {
        super(null);
        init(language,listLanguages,name);
    }

    private void init(int language, List<Language> listLanguages,String name ) {
        this.name= name;
        this.language = language;
        this.eManager= new EnumManager();
        if(listLanguages==null){
            this.listLanguages = new ArrayList<>();
            this.listLanguages.add(LanguageManager.getListLanguages().get(language));
        }else this.listLanguages = listLanguages;
        vProject = new ViewProject(this);
        vProject.updateLanguage();
    }

    public void seteManager(EnumManager eManager) {
        this.eManager = eManager;
    }
    
    
    public EnumManager getEManager() {
        return eManager;
    }
    
    public int getLanguage() {
        return language;
    }

    public ViewProject getvProject() {
        return vProject;
    }

    public void setLanguage(int language) {
        this.language = language;
    }
    
    @Override
    public String getName() {
        return name;
    }

    public List<Language> getListLanguages() {
        return listLanguages;
    }

    @Override
    public void correction(boolean bool) {
       this.isCorrect = bool;
       this.updateAll();
    }
    
    
    @Override
    public void setName(String name) {
       this.name = name;
       this.getvWindow().notificate();
    }

    @Override
    public void translate(LanguageManager langManager,int from, int to) {
        for (Window window : this.getListWindows()) {
            window.translate(langManager,from, to);
        }
        if(panel!=null)
        panel.translate(langManager, from, to);
    }
    

    public void addLanguage() {
        List<Language> list = new ArrayList(LanguageManager.getListLanguages());
        for (Language language1 : listLanguages) {
            list.remove(language1);
        }
        Language lang =(Language) AlertManager.selectValue(list, "Přidat jazyk: ");
        addLanguage(lang);
    }
    public void addLanguage(Language lang){
        if(lang==null)return;
        listLanguages.add(lang);
        translate(ProjectManager.getLanguageManager(), language, lang.id);
        vProject.updateLanguage();
    }
    public void removeLanguage() {
        if(listLanguages.size()==1){
            AlertManager.info("Nelze odebrat všechny jazyky");
            return;
        }
        Language lang =(Language) AlertManager.selectValue(listLanguages, "Odebrat jazyk: ");
        if(lang!=null){
            listLanguages.remove(lang);
            vProject.updateLanguage();
        }
    }

    @Override
    public void updateAll() {
        vProject.notificate();
        super.updateAll();
    }

    @Override
    public Element createElementToSave(Document document) {
        Element element = super.createElementToSave(document);
        element.setAttribute(Constants.name, name);
        element.setAttribute(Constants.language, language+"");
        for (Language listLanguage : listLanguages) {
            element.appendChild(listLanguage.createElementToSave(document));
        }
        element.appendChild(eManager.createElementToSave(document));
        return element;
    }
    
    
}
