package zswi;


import java.util.ArrayList;
import java.util.List;



public class Panel {
    private List<Table> listTables;
    private ViewPanel vPanel;
    private boolean status = true;
    public Panel(List<Table> tables) {
        this.listTables = tables==null?new ArrayList<>():tables;
        vPanel = new ViewPanel(this);
    }
    public void createTable(){
        Table tb = AlertManager.Table();
        if(tb==null)return;
        listTables.add(tb);
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
         
}
