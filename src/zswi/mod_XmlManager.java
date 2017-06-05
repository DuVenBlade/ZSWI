package zswi;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import zswi.EnumManager.EnumSection;
import zswi.EnumManager.EnumValue;
import zswi.ItemManager.DataType;
import zswi.LanguageManager.Language;

/**
 *
 * @author DDvory
 */
public class mod_XmlManager {
    
    public static Project ReadXML(File fileName){
        Project prj = null;
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileName);
            prj = getProject((Node)doc.getChildNodes().item(0));
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace();
        }
        return prj;
    }
    
    private static Project getProject(Node node){
        Integer decode = Integer.decode(getATR(node, Constants.language));
        String name = getATR(node, Constants.name);
        List<Node> childsByName = getChildsByName(node, Constants.Language);
        Node get = getChildsByName(node, Constants.EnumManager).get(0);
        List<Language> list = getLanguages(childsByName);
        Project prj = Project.createProject(decode, name, list);
        prj.seteManager(getEnumManager(get));
        Project proeject =(Project) getWindow((Window)prj,node,null);
        return proeject;
    }
    
    private static  List<Language> getLanguages(List<Node> childsByName){
        List<Language> list = new ArrayList(); 
        List<Language> listLanguages = LanguageManager.getListLanguages();
        for (Node element : childsByName) {
            list.add(listLanguages.get(Integer.decode(getATR(element, Constants.id))));
        }
        return list;
    }
    
    private static Window getWindow(Window window,Node node,AFlowable parent){
        if(window==null){
            window = new Window(parent);
            window.setListNames(getNames(getChildsByName(node, Constants.Name)));
        }
        List<Node> panels = getChildsByName(node, Constants.Panel);
        if(panels.size()>0)window.setPanel(getPanel(panels.get(0),window));
        List<Window> list = new ArrayList<>();
        List<Node> childsByName = getChildsByName(node, Constants.Window);
        for (Node element : childsByName) {
            list.add(getWindow(null,element, window));
        }
        window.setListWindow(list);
        return window;
    }
    
    private static Panel getPanel(Node node,AFlowable parent){
        Panel p = new Panel(parent);
        List<Node> childsByName = getChildsByName(node, Constants.Table);
         List<Table> list = new ArrayList<>();
        for (Node element : childsByName) {
            list.add(getTable(element,p));
        }
        p.setListTables(list);
        return p;
    }
    
    private static Table getTable(Node node,AFlowable parent){
        Table table = new Table("", parent);
        table.setListNames(getNames(getChildsByName(node, Constants.Name)));
        List<Node> childsByName = getChildsByName(node, Constants.Item);
        List<Item> listItems = new ArrayList();
        for (Node listItem : childsByName) {
             if(listItem!=null)listItems.add(getItem(listItem,table));
        }
        table.setListItems(listItems);
        return table;
    }
    
    private static Item getItem(Node node,AFlowable parent){
        String adress = getATR(node, Constants.adress);
        String haveUnit = getATR(node, Constants.haveUnit);
        DataType dataType = DataType.valueOf(getATR(node, Constants.dataType));
        Item item = null;
        switch (dataType) {
            case STRING:
                item = ItemManager.getINSTANCE().createI_String(parent, dataType, adress, haveUnit,
                        getATR(node, Constants.len), getATR(node, Constants.value));
                break;
            case INT:
                item = ItemManager.getINSTANCE().createI_Int(parent, dataType, adress, haveUnit, 
                        getATR(node, Constants.format),getATR(node, Constants.len), getATR(node, Constants.value));
                break;
            case DOUBLE:
            case FLOAT:
                item = ItemManager.getINSTANCE().createI_DoFl(parent, dataType, adress, haveUnit, 
                         getATR(node, Constants.value));
                break;
            case DATE:
            case TIME:
                item = ItemManager.getINSTANCE().createI_DaTi(parent, dataType, adress, haveUnit, 
                         getATR(node, Constants.format),getATR(node, Constants.value));
                break;
            case ENUM:
                item = ItemManager.getINSTANCE().createI_Enum(parent, dataType, adress, haveUnit, 
                         getATR(node, Constants.id),getATR(node, Constants.value));
                break;
            case BOOLEAN:
            case BIT:
                item = ItemManager.getINSTANCE().createI_BiBo(parent, dataType, adress, haveUnit, 
                         getATR(node, Constants.value));
                break;
            
        }
        if(item!=null)
        item.setListNames(getNames(getChildsByName(node, Constants.Name)));
        return item;
    }
    
    private static List<Name> getNames(List<Node> childsByName){
        List<Name> list = new ArrayList<>();
        for (Node el : childsByName) {
            int id = Integer.decode(getATR(el, Constants.id));
            String value = getATR(el, Constants.value);
            list.add(new Name(id, value));
        }
        return list;
    }
    private static  EnumManager getEnumManager(Node node){
        EnumManager eManager = new EnumManager();
        List<Node> childsByName = getChildsByName(node, Constants.EnumSection);
        for (Node node1 : childsByName) {
            eManager.addEnumSection(getEnumSection(eManager,node1));
        }
        return eManager;
    }
    private static EnumSection getEnumSection(EnumManager manager,Node node){
        String atr = getATR(node, Constants.id);
        EnumSection section = new EnumSection(manager,atr);
        section.setListNames(getNames(getChildsByName(node, Constants.Name)));
        List<Node> childsByName = getChildsByName(node, Constants.EnumValue);
        for (Node node1 : childsByName) {
            section.addEnumValue(getEnumValue(node1,section));
        }
        return section;
    }
    private static EnumValue getEnumValue(Node node, AFlowable parent){
        String atr = getATR(node, Constants.id);
        EnumValue enumValue = new EnumValue(atr, parent);
        enumValue.setListNames(getNames(getChildsByName(node, Constants.Name)));
        return enumValue;
    }
    private static String getATR(Node el,String name){
        return el.getAttributes().getNamedItem(name).getNodeValue();
    }
    private static List<Node> getChildsByName(Node element, String name){
        List<Node> list = new ArrayList<>();
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node el = (Node)childNodes.item(i);
            if(el.getNodeName().equals(name))list.add(el);
        }
        return list;
    }
}
