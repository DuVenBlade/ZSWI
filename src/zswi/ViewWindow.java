package zswi;


import zswi.FontSizeObervers.OLabel;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import zswi.FontSizeObervers.OImageView;

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
        statusCircle = new OImageView();
        windows = new TreeItem<>(this,new HBox(statusCircle,treeLabel) );
        updateStatusImage();
        notificate();
    }


    public Window getWindow() {
        return window;
    }

    public void updateStatusImage() {
        int i = 2;
        if(!window.isCorrect()){
            i = 0;
        }else if (window == null||window.getPanel()==null) {
            i = 1;
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
        panel.setName(window.getName());
        this.updateStatusImage();
    }

    public void add(ViewWindow wind) {
        windows.getChildren().add(wind.getWindows());
        windows.setExpanded(true);
    }
    public void setListWindows(List<Window> list){
        ObservableList<TreeItem<ViewWindow>> children = windows.getChildren();
        children.clear();
        for (Window window1 : list) {
            children.add(window1.getvWindow().windows);
        }
    }

    @Override
    public String toString() {
        return "";
    }
}
