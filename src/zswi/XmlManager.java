package zswi;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import zswi.LanguageManager.Language;

/**
 *
 * @author DDvory
 */
public class XmlManager {
    
    public static Project ReadXML(String fileName){
        Project prj = null;
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(fileName));
            prj = getProject((Element) doc.getElementsByTagName(Constants.Project).item(0));
        } catch (Exception ex) {
            
        }
        return prj;
    }
    private static Project getProject(Element node)throws WrongArgumentException{
        
        return null;
    }
    private static  List<Language> getLanguages(NodeList nodeList){
        return null;
    }
    private static Window getWindow(Element node)throws WrongArgumentException{
        return null;
    }
    private static Panel getPanel(Element node)throws WrongArgumentException{
        return null;
    }
    private static Table getTable(Element node)throws WrongArgumentException{
        return null;
    }
    
    private static Item getItem(Element node) throws WrongArgumentException{
        return null;
    }
    private static List<Name> getNames(NodeList nodeList){
        return null;
    }
    private static int getID(Element el)throws WrongArgumentException{
        String attribute = el.getAttribute(Constants.id);
        if(attribute.isEmpty())throw new WrongArgumentException("Nebyl, nebo byl špatně, zadán atribut: \"id\"  v elementu: \"" + el.getNodeName()+"\"");
        return Integer.decode(attribute);
    }
    private static String getATR(Element el,String name) throws WrongArgumentException{
        String attribute = el.getAttribute(name);
        if(attribute.isEmpty())throw new WrongArgumentException("Nebyl zadán atribut: \"" + name +"\"  v elementu: \"" + el.getNodeName()+"\"");
        return el.getAttribute(name);
    }
    private static class WrongArgumentException extends Exception{

        public WrongArgumentException(String message) {
            super(message);
        }
     }
}
