package zswi;

import java.io.File;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

/**
 * @author DDvory
 */
public class mod_FileManager {
    
    public static void save(Project project,File file){
        String absolutePath = file.getAbsolutePath();
        try {
            file = new File(absolutePath+"\\"+project.getName()+".flow");
            if(!file.exists())if(!file.createNewFile())AlertManager.info("Uložení se nezdařilo, nepodařilo se vytvořit složku v adresáři");
        } catch (Exception e) {
            AlertManager.info("Uložení se nezdařilo, nepodařilo se vytvořit složku v adresáři");
        }
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            document.appendChild(project.createElementToSave(document));
            
            TransformerFactory transformerFactory =
         TransformerFactory.newInstance();
         Transformer transformer =
         transformerFactory.newTransformer();
         DOMSource source = new DOMSource(document);
         StreamResult result =
         new StreamResult(file);
         transformer.transform(source, result);
         // Output to console for testing
         StreamResult consoleResult =
         new StreamResult(System.out);
         transformer.transform(source, consoleResult);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static File getFile(){
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("FLOW","*.flow"));
        File showOpenDialog = chooser.showOpenDialog(Main.getINSTANCE().getStage());
        return showOpenDialog;
    }
    public static File getDirectory(){
        DirectoryChooser chooser = new DirectoryChooser();
        return chooser.showDialog(Main.getINSTANCE().getStage());
    }
}
