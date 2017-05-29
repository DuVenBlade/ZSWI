package zswi;

import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import zswi.res.ResManager;

/**
 *
 * @author DDvory
 */
public class ProjectManager {

    public final static Image edit = ResManager.getImage("edit");
    public final static Image error = ResManager.getImage("error");
    public final static Image menu = ResManager.getImage("menu");
    private static final Image[] statusImages;
    static {
        statusImages = new Image[]{
            ResManager.getImage("red"),
            ResManager.getImage("orange"),
            ResManager.getImage("green")
        };

    }
    
    private static ProjectManager INSTANCE;

    private Project project;
    private EnumManager emanager;
    private String fileName;
    private LanguageManager languageManager;
    static Image[] getArrayImages() {
        return statusImages;
    }
    
    private ProjectManager(Project project) {
        languageManager = new LanguageManager();
        this.project = project;
        emanager = new EnumManager();
        this.project.getvProject().notificate();
        
    }
    public void setTitle(String text){
        Main.getINSTANCE().getStage().setTitle(text);
    }

    public static ProjectManager createProject() {

        if (checkProject()) {
            Project project = AlertManager.Project();
            if (project != null) {
                INSTANCE = new ProjectManager(project);
            }
        }
        return INSTANCE;
    }

    public static void closeProject() {
        if (INSTANCE != null);//ZEPTA SE NA SAVE  A UZAVRE SE
    }

    public static ProjectManager loadProject() {
        boolean bool = false;
        if (checkProject()) {
            //Otevre adresar 
        }
        return null;
    }
    
    private static boolean checkProject() {
        if (INSTANCE != null) {
            if (AlertManager.confirm("Máte již načtený jiný projekt, chcete opravdu tento projekt zavřít?")) {
                closeProject();
                return true;
            }else return false;
        } 
        return true;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }
    
    public EnumManager getEnumManager() {
        return emanager;
    }
    public static ProjectManager getINSTANCE() {
        return INSTANCE;
    }

    public Project getProject() {
        return project;
    }

    void changeLanguage(int id) {
        project.setLanguage(id);
        Platform.runLater(()->{
            project.updateAll();
        });
    }

}
