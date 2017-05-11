package zswi;

/**
 *
 * @author DDvory
 */
public class Name {

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
}
