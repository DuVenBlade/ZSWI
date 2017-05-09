package zswi;


import zswi.FontSizeObervers.OLabel;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author DDvory
 */
public class ViewWindow implements Main.Observabler {

    private TreeItem<ViewWindow> windows;
    private Window window;
    private Label treeLabel;
    private ImageView statusCircle;

    public  ViewWindow(Window window) {
        this.window = window;

        treeLabel = new OLabel();
        statusCircle = new ImageView();
        statusCircle.setFitWidth(15);
        statusCircle.setFitHeight(15);
        windows = new TreeItem<>(this,new HBox(statusCircle,treeLabel) );
        updateStatusImage();
        notificate();
    }


    public Window getWindow() {
        return window;
    }

    public void updateStatusImage() {
        int i = 2;
        if (window == null||window.getPanel()==null) {
            i = 1;
        } else if (!window.getPanel().isStatus()) {
            i = 0;
        }
        statusCircle.setImage(ViewProject.getStatusImage(i));
    }

    public TreeItem<ViewWindow> getWindows() {
        return windows;
    }

    public ImageView getStatusCircle() {
        return statusCircle;
    }

    @Override
    public BorderPane getView() {
        if(window.getPanel()==null)return null;
        return window.getPanel().getvPanel().getView();
    }

    public void removeWindow() {
        TreeItem<ViewWindow> parent = windows.getParent();
        parent.getChildren().remove(windows);
    }

    public static void linkTree(Project project){
        linkTree((Window)project);
    }
    private static void linkTree(Window window){
        List<Window> listWindows = window.getListWindows();
        for (Window listWindow : listWindows) {
            linkTree(listWindow);
            window.getvWindow().getWindows().getChildren().add(listWindow.getvWindow().getWindows());
        }
    }

    public Label getTreeLabel() {
        return treeLabel;
    }

    @Override
    public void notificate() {
        treeLabel.setText(window.getName());
        Panel panel = window.getPanel();
        if(panel!=null)
        panel.getvPanel().setName(window.getName());
        this.updateStatusImage();
    }

    public void add(ViewWindow wind) {
        windows.getChildren().add(wind.getWindows());
        windows.setExpanded(true);
    }

    @Override
    public String toString() {
        return "";
    }
}
