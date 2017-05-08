package zswi;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author DDvory
 */
public class ProjectManager {
    //HashTable
    private static ProjectManager INSTANCE;
    private Project project;
    private Map<Integer, AFlowable> mapItems;
    private String fileName;
    
    private ProjectManager(Project project) {
        this.project = project;
        mapItems = new HashMap();
        
    }
    
    public static ProjectManager createProject(){
        
        if(checkProject()){
            INSTANCE = new ProjectManager(AlertManager.Project());
        }
        return INSTANCE;
    }
    public static void closeProject(){
        if(INSTANCE!=null);//ZEPTA SE NA SAVE  A UZAVRE SE
    }
    public static ProjectManager loadProject(){
        boolean bool = false;
        if(checkProject()){
            //Otevre adresar 
        }
        return null;
    }
    private static boolean checkProject(){
        boolean bool = false;
        if(INSTANCE !=null){
            Optional<ButtonType> showAndWait = Main.getAlert(Alert.AlertType.CONFIRMATION, "Project", "Máte již načtený jiný projekt, chcete opravdu tento projekt zavřít?", "", null).showAndWait();
            if(showAndWait.get()==ButtonType.OK){
                closeProject();
                bool = true;
            }
        }else  bool = true;
        return bool;
    }

    public static ProjectManager getINSTANCE() {
        return INSTANCE;
    }
    
    public void add(AFlowable aflow){
        mapItems.put(aflow.getID(), aflow);
    }

    public Project getProject() {
        return project;
    }
    
}
