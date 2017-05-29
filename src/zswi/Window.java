package zswi;

import java.util.ArrayList;
import java.util.List;

public class Window extends AFlowable implements IUpdatable {

    private ViewWindow vWindow;
    private Window parrent;
    private List<Window> listWindows;
    private Panel panel;

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

    public void setPanel() {
        setPanel(new Panel(null));
    }

    public void setPanel(Panel panel) {
        if (this.panel != null) {
            if (!AlertManager.confirm("V tomto okně je již panel přidán, opravdu chcete přidat nový?")) {
                return;
            }
        }
        this.panel = panel;
        vWindow.notificate();

    }

    public void createWindow() {
        String s = AlertManager.getName("Vytvořit Větev: ", "");
        if (s == null) {
            return;
        }
        addWindow(new Window(s, this, null, null));
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
            AlertManager.info("Tato položka nemůže být odstraněna");
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

    public void rename() {
        String s = AlertManager.getName("Přejmenovat větev", this.getName());
        if (s != null) {
            setName(s);
        }
    }

    @Override
    public void translate(LanguageManager langManager, int from, int to) {
        for (Window listWindow : listWindows) {
            listWindow.translate(langManager, from, to);
        }
        langManager.translate(this, from, to);
        if (panel != null) {
            panel.translate(langManager, from, to);
        }
    }

    @Override
    public void updateAll() {
        vWindow.notificate();
        for (Window listWindow : listWindows) {
            listWindow.updateAll();
        }
        if (panel != null) {
            panel.updateAll();
        }
    }
}
