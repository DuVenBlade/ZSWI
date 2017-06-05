package zswi;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Window extends AFlowable implements IUpdatable {

    private ViewWindow vWindow;
    private Window parent;
    private List<Window> listWindows;
    protected Panel panel;

    public Window(AFlowable parent) {
        super(parent);
        init();
    }
    
    public Window(String name, AFlowable parent) {
        super(name,parent);
        init();
    }

    private void init() {
        this.listWindows = new ArrayList<>();
        vWindow = new ViewWindow(this);
        this.panel = null;
    }

    public void setPanel() {
        setPanel(new Panel(this));
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
        }addWindow(new Window(s, this));
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
        if (parent == null) {
            AlertManager.info("Tato položka nemůže být odstraněna");
            return;
        }
        parent.getListWindows().remove(this);
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

    @Override
    public Element createElementToSave(Document document) {
        Element element = super.createElementToSave(document);
        if(panel!=null)
        element.appendChild(panel.createElementToSave(document));
        for (Window listWindow : listWindows) {
            element.appendChild(listWindow.createElementToSave(document));
        }
        return element;
    }

    void setListWindow(List<Window> list) {
        this.listWindows = list;
        vWindow.setListWindows(list);
        this.vWindow.notificate();
    }
    
}
