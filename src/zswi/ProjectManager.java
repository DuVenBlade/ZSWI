package zswi;

import java.io.File;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import zswi.FontSizeObervers.FontSize;
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
    

    private static FontSize font;
    private static File fileName;
    private static boolean isEdited = false;
    
    private static LanguageManager languageManager;
    static{
        languageManager = new LanguageManager();
    }
    static Image[] getArrayImages() {
        return statusImages;
    }


    public static void createProject() {
        if (checkProject()) {
            isEdited=true;
            font = new FontSize(12);
            AlertManager.Project();
        }
    }

    public static void closeProject() {
        if (Project.getInstance() != null){
            if(isEdited&&AlertManager.confirm("Chete projekt před uzavřením uložit?")){
                saveProejct();
            }
            Project.close();
            ProjectManager.font=null;
        }fileName = null;
    }
    public static void saveProejct(){
        if (Project.getInstance() != null){
            if(fileName==null){
                saveAs();
            }else{
                mod_FileManager.save(Project.getInstance(),fileName);
            }
            isEdited = false;
        }
    }
    public static void saveAs(){
        File directory = mod_FileManager.getDirectory();
        if(directory==null)return;
        mod_FileManager.save(Project.getInstance(),directory);
        isEdited = false;
    }

    public static void loadProject() {
        if (checkProject()) {
            closeProject();
        }
        font = new FontSize(12);
        loadProject(mod_FileManager.getFile());
    }
    
    private static void loadProject(File file){
        if(file==null)return;
        fileName=file.getParentFile();
        Project ReadXML = mod_XmlManager.ReadXML(file);
        ReadXML.updateAll();
    }
    
    private static boolean checkProject() {
        if (Project.getInstance() != null) {
            if (AlertManager.confirm("Máte již načtený jiný projekt, chcete opravdu tento projekt zavřít?")) {
                closeProject();
                return true;
            }else return false;
        } 
        return true;
    }

    public static LanguageManager getLanguageManager() {
        return languageManager;
    }
    
    
    public static FontSize getFont() {
        return font;
    }

    public static void setEdited(){
        isEdited = true;
    }
    public static boolean isEdited(){
        return isEdited;
    }

    public static void changeLanguage(int id) {
        Project instance = Project.getInstance();
        instance.setLanguage(id);
        Platform.runLater(()->{
            instance.updateAll();
        });
    }
}
