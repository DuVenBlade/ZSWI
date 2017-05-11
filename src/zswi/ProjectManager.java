package zswi;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import zswi.res.ResManager;

/**
 *
 * @author DDvory
 */
public class ProjectManager {

    //HashTable
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
    
    static Image[] getArrayImages() {
        return statusImages;
    }
    
    private ProjectManager(Project project) {
        this.project = project;
        emanager = new EnumManager();
        Main.getINSTANCE().getRoot().setCenter(project.getvProject().getViewPane());
    }

    public static ProjectManager createProject() {

        if (checkProject()) {
            Project project = AlertManager.Project();
            if (project != null) {
                AFlowable.clearIncrement();
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
        boolean bool = false;
        if (INSTANCE != null) {
            Optional<ButtonType> showAndWait = Main.getAlert(Alert.AlertType.CONFIRMATION, "Project", "Máte již načtený jiný projekt, chcete opravdu tento projekt zavřít?", "", null).showAndWait();
            if (showAndWait.get() == ButtonType.OK) {
                closeProject();
                bool = true;
            }
        } else {
            bool = true;
        }
        return bool;
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

}
