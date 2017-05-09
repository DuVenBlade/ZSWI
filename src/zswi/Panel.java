package zswi;


import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.VBox;



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
        if(tb!=null)
        listTables.add(tb);
        vPanel.notificate();
    }
    public void removeTable(Table tb){
        if(tb!=null);
        listTables.remove(tb);
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
