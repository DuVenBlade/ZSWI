package zswi;

import java.util.List;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author DDvory
 */
public class Name extends ASaveable {

    private final int languageId;
    private String name;

    public Name(int languageId) {
        this(languageId, "");
    }

    public Name(int languageId, String name) {
        this.languageId = languageId;
        this.name = name;
    }
    
    public int getLanguageId() {
        return languageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Element createElementToSave(Document document) {
        Element saveDocument = super.createElementToSave(document);
        saveDocument.setAttribute(Constants.id, languageId+"");
        saveDocument.setAttribute(Constants.value, name);
        return saveDocument;
    }
    
}
