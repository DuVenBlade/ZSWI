package zswi;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

public class Window extends AFlowable {

    private ViewWindow vWindow;
    private Window parrent;
    private List<Window> listWindows;
    private Panel panel;

    public Window(int ID, String name, Window parrent, List<Window> windows, Panel panel) {
        super(ID);
        init(name, parrent, windows, panel);
    }

    public Window(String name, Window parrent, List<Window> windows, Panel panel) {
        init(name, parrent, windows, panel);

    }

    private void init(String name, Window parrent, List<Window> windows, Panel panel) {
        this.listWindows = windows == null ? new ArrayList<>() : windows;
        vWindow = new ViewWindow(this);
        this.panel = panel;
        this.parrent = parrent;
        setName(name);
    }

    public void createPanel() {
        setPanel(new Panel(null));
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
        vWindow.notificate();
    }
    

    public void createWindow() {
        String s = AlertManager.getName("Vytvořit Větev: ");
        if(s==null)return;
        addWindow(new Window(s,this,null,null));
    }

    private void addWindow(Window wind) {
        if (wind != null) {
            listWindows.add(wind);
            vWindow.add(wind.getvWindow());
            vWindow.notificate();
        }
    }

    public List<Window> getListWindows() {
        return listWindows;
    }

    public Panel getPanel() {
        return panel;
    }

    public void removeWindow() {
        if (parrent == null) {
            Main.getAlert(Alert.AlertType.ERROR, "ERROR", "Tato položka nemůže být odstraněna", "", null).show();
            return;
        }
        parrent.getListWindows().remove(this);
        vWindow.removeWindow();
        vWindow.notificate();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        vWindow.notificate();
    }

    public ViewWindow getvWindow() {
        return vWindow;
    }

    public void removePanel() {
        panel = null;
        vWindow.notificate();
    }
    public void rename(){
        String s = AlertManager.getName("Přejmenovat větev");
        if(s!=null)setName(s);
    }

}
