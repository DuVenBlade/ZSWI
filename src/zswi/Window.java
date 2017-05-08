package zswi;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;


public class Window extends AFlowable {
        private ViewWindow vWindow;
        private Window parrent;
	private List<Window> listWindows;
	private Panel panel;

    public Window(int ID,String name,Window parrent, List<Window> windows, Panel panel) {
        super(ID);
        init(name,parrent, windows, panel);
    }

    public Window(String name, Window parrent,List<Window> windows, Panel panel) {
        super();
        init(name,parrent, windows, panel);
        
    }
    private void init(String name, Window parrent,List<Window> windows, Panel panel){
        super.setName(name);
        this.listWindows = windows==null?new ArrayList<>():windows;
        this.panel = panel;
        vWindow = new ViewWindow(this);
        this.parrent = parrent;
    }
    public void createWindow(){
        Window wind = AlertManager.Window(this);
        if(wind!=null)this.addWindow(wind);
    }
    public void createPanel(){
        panel = new Panel(null);
        vWindow.notificate();
    }
    private void addWindow(Window wind){
        listWindows.add(wind);
        vWindow.add(wind.getvWindow());
        vWindow.notificate();
    }

    public List<Window> getListWindows() {
        return listWindows;
    }

    public Panel getPanel() {
        return panel;
    }
    
    public void removeWindow(){
        if (parrent == null) {
            Main.getAlert(Alert.AlertType.ERROR, "ERROR", "Tato položka nemůže být odstraněna", "", null).show();
            return;
        }
        parrent.getListWindows().remove(this);
        vWindow.removeWindow();
        vWindow.notificate();
    }

    public void setName(String name) {
        super.setName(name);
        vWindow.notificate();
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
        vWindow.notificate();
    } 

    public ViewWindow getvWindow() {
        return vWindow;
    }

    void removePanel() {
        panel = null;
        vWindow.notificate();
    }
    
}
