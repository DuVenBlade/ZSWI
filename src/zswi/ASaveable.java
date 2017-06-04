package zswi;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author DDvory
 */
public class ASaveable {
    public Element createElementToSave(Document document){
       String string = this.getClass().getSimpleName();
       if(string.startsWith(Constants.Item))string = Constants.Item;
       return document.createElement(string);
    }
}
