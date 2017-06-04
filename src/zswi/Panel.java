package zswi;


import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



public class Panel extends AFlowable implements IUpdatable{
    private List<Table> listTables;
    private ViewPanel vPanel;
    private boolean status = true;
    public Panel(AFlowable parent) {
        super(parent);
        this.listTables = new ArrayList<>();
        vPanel = new ViewPanel(this);
    }
    public void createTable(){
        String tb = AlertManager.getName("Vytvořit tabulku: ","");
        if(tb==null)return;
        listTables.add(new Table(tb, this));
        vPanel.notificate();
    }
    public void removeTable(Table tb){
        if(tb==null)return;
        listTables.remove(tb);
        vPanel.notificate();
    }
    public List<Table> getListTables() {
        return listTables;
    }

    @Override
    public String getName() {
        return parent.getName(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setName(String name) {
        vPanel.notificate();
    }
    
    public ViewPanel getvPanel() {
        return vPanel;
    }

    public void removeTable(){
        Table selectValue =(Table) AlertManager.selectValue(listTables, "Vyber tabulku:");
        removeTable(selectValue);

    }
    @Override
    public void translate(LanguageManager langManager, int from, int to) {
        for (Table listTable : listTables) {
            listTable.translate(langManager, from, to);
        }
    }
    @Override
    public void updateAll(){
        this.vPanel.notificate();
        for (Table listTable : listTables) {
            listTable.updateAll();
        }
    }

    @Override
    public Element createElementToSave(Document document) {
        Element element = super.createElementToSave(document);
        for (Table table : listTables) {
            element.appendChild(table.createElementToSave(document));
        }
        return element;
    }

    void setListTables(List<Table> list) {
        this.listTables = list;
        this.vPanel.notificate();
    }
    
}
