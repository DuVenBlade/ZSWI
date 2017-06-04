package zswi;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author DDvory
 */
public class mod_FileManager {
    
    public static void save(Project project){
        DirectoryChooser chooser = new DirectoryChooser();
        //File showDialog = chooser.showDialog(Main.getINSTANCE().getStage());
        File file = new File("C:\\Users\\DDvory\\Desktop\\test.xml");
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
    
    public static void load(){
        FileChooser chooser = new FileChooser();
        chooser.showOpenDialog(Main.getINSTANCE().getStage());
                
    }
}
