package zswi;


import java.util.List;
import zswi.FontSizeObervers.FontSize;

public class Project extends Window {
    
    private ViewProject vProject;
    private int language;
    private String name;

    //bottom language + pisma
    public Project(int ID, String name, List<Window> window, Panel panel, String language) {
        super(ID, name,null ,window, panel);
        init( Integer.decode(language));
    }

    public Project(int language, String name, List<Window> windows, Panel panel) {
        super(name,null, windows, panel);
        init(language);
    }

    private void init(int language) {
        FontSize.getINSTANCE().setSize(12);
        this.language = language;
        vProject = new ViewProject(this);
    }

    public int getLanguage() {
        return language;
    }

    public ViewProject getvProject() {
        return vProject;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
       this.name = name;
       this.getvWindow().notificate();
    }
    
}
