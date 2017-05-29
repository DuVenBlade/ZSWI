package zswi;


import java.util.ArrayList;
import java.util.List;



public class Panel implements IUpdatable{
    private List<Table> listTables;
    private ViewPanel vPanel;
    private boolean status = true;
    public Panel(List<Table> tables) {
        this.listTables = tables==null?new ArrayList<>():tables;
        vPanel = new ViewPanel(this);
    }
    public void createTable(){
        String tb = AlertManager.getName("Vytvořit tabulku: ","");
        if(tb==null)return;
        listTables.add(new Table(tb, null));
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

    public ViewPanel getvPanel() {
        return vPanel;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public void removeTable(){
        Table selectValue =(Table) AlertManager.selectValue(listTables, "Vyber tabulku:");
        removeTable(selectValue);

    }
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
}
