package zswi;


import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import zswi.FontSizeObervers.FontSize;
import zswi.LanguageManager.Language;

public class Project extends Window {
    
    private ViewProject vProject;
    private int language;
    private String name;
    private List<Language> listLanguages;

    //bottom language + pisma
    public Project(String name, List<Window> window, Panel panel, String language, List<Language> listLanguages) {
        super(name,null ,window, panel);
        init( Integer.decode(language),listLanguages);
    }

    public Project(int language, String name, List<Window> windows, Panel panel, List<Language> listLanguages) {
        super(name,null, windows, panel);
        init(language,listLanguages);
    }

    private void init(int language, List<Language> listLanguages) {
        FontSize.getINSTANCE().setSize(12);
        this.language = language;
        if(listLanguages==null){
            this.listLanguages = new ArrayList<>();
            this.listLanguages.add(LanguageManager.getListLanguages().get(language));
        }else this.listLanguages = listLanguages;
        vProject = new ViewProject(this);
        vProject.updateLanguage();
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
    public void setName(String name) {
       this.name = name;
       this.getvWindow().notificate();
    }

    @Override
    public void translate(LanguageManager langManager,int from, int to) {
        for (Window window : this.getListWindows()) {
            window.translate(langManager,from, to);
        }
        Panel panel = this.getPanel();
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
        translate(ProjectManager.getINSTANCE().getLanguageManager(), language, lang.id);
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
    
}
