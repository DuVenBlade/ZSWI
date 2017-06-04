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
    private File fileName;
    
    private static LanguageManager languageManager;
    static{
        languageManager = new LanguageManager();
    }
    static Image[] getArrayImages() {
        return statusImages;
    }


    public static void createProject() {
        if (checkProject()) {
            Project project = AlertManager.Project();
        }
    }

    public static void closeProject() {
        if (Project.getInstance() != null){
            if(AlertManager.confirm("Chete projekt před uzavřením uložit?")){
                saveProejct();
            }
        }
    }
    public static void saveProejct(){
        if (Project.getInstance() != null){
            mod_FileManager.save(Project.getInstance());
        }
    }

    public static void loadProject() {
        if (checkProject()) {
            closeProject();
            mod_XmlManager.ReadXML("C:\\Users\\DDvory\\Desktop\\test.xml");
        }
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
    public static void createFontSize(){
        font = new FontSize(12);
    }

    public static void changeLanguage(int id) {
        Project instance = Project.getInstance();
        instance.setLanguage(id);
        Platform.runLater(()->{
            instance.updateAll();
        });
    }
}
