package zswi;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DDvory
 */
public class Table extends AFlowable {
    private ViewTable vTable;
    private List<Item> listItems;

    public Table(int ID,String name, int columnCount, List<Item> listItems) {
        super(ID);
        init(name, listItems);
    }

    public Table(String name, List<Item> listRow) {
        init(name, listRow);
    }
    private void init(String name, List<Item> listRow){
        this.listItems = listRow==null?new ArrayList<>():listRow;
        vTable = new ViewTable(this);
        setName(name);
    }
    public void createItem(){
        addItem(AlertManager.Item());
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
        String s = AlertManager.getName("Přejmenovat Tabulku: ");
        if(s!=null)setName(s);
    }
    
}
