package zswi;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DDvory
 */
public class Table extends AFlowable implements IUpdatable {
    private ViewTable vTable;
    private List<Item> listItems;

    public Table(String name, List<Item> listRow) {
        init(name, listRow);
    }
    private void init(String name, List<Item> listRow){
        this.listItems = listRow==null?new ArrayList<>():listRow;
        vTable = new ViewTable(this);
        setName(name);
    }
    public void createItem(){
        addItem(AlertManager.Item(null));
    }
    public void addItem(Item item){
        if(item!=null)listItems.add(item);
        vTable.notificate();
    }
    public void removeItem(Item item){
        if(item==null)return;
        listItems.remove(item);
        vTable.notificate();
    }

    public List<Item> getListItems() {
        return listItems;
    }
    
    @Override
    public void setName(String name) {
        super.setName(name);
        vTable.notificate();
    } 

    public ViewTable getvTable() {
        return vTable;
    }
    public void removeItem(){
        Item selectValue =(Item) AlertManager.selectValue(listItems, "Vyberte řádek:");
        removeItem(selectValue);
    }
    public void rename(){
        String s = AlertManager.getName("Přejmenovat Tabulku: ",this.getName());
        if(s!=null)setName(s);
    }

    @Override
    public void translate(LanguageManager langManager, int from, int to) {
        for (Item listItem : listItems) {
            listItem.translate(langManager, from, to);
        }langManager.translate(this, from, to);
    }

    @Override
    public void updateAll() {
        this.vTable.notificate();
        for (Item listItem : listItems) {
         listItem.updateAll();
        }
    }
}
