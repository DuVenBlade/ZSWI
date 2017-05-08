package zswi;


import java.util.List;
import zswi.FontSizeObervers.FontSize;

public class Project extends Window {
    
    private ViewProject vProject;
    private int language;

    //bottom language + pisma
    public Project(int ID, String name, List<Window> window, Panel panel, String language) {
        super(ID, name,null ,window, panel);
        init( language);
    }

    public Project( String language, String name, List<Window> windows, Panel panel) {
        super(name,null, windows, panel);
        init( language);
    }

    private void init( String language) {
        FontSize.getINSTANCE().setSize(12);
        this.language = Integer.decode(language);
        vProject = new ViewProject(this);
    }

    public int getLanguage() {
        return language;
    }

    public ViewProject getvProject() {
        return vProject;
    }
    
}
