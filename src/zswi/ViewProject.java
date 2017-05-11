package zswi;

import javafx.event.EventType;
import zswi.res.*;
import zswi.FontSizeObervers.OLabel;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import zswi.FontSizeObervers.FontSize;

/**
 *
 * @author DDvory
 */
public class ViewProject {

    private Project project;
    private TreeView<ViewWindow> treeView;
    private Label errorMessage;
    private BorderPane panelPane;
    private BorderPane viewPane;

    public ViewProject(Project project) {
        errorMessage = new OLabel("");
        this.project = project;
        panelPane = new BorderPane();
        viewPane = new BorderPane();
        init();
    }

    private void init() {
        ViewWindow.linkTree(project);
        treeView = new TreeView<>(project.getvWindow().getWindows());
        setTVHandler();
        SplitPane spl = new SplitPane();
        spl.getItems().addAll(treeView, panelPane);
        viewPane.setCenter(spl);
        viewPane.setBottom(getBottom());
    }

    private void setTVHandler() {
        treeView.setOnMousePressed(e -> {
            if (e.isSecondaryButtonDown()) {
                TreeItem<ViewWindow> selectedItem = treeView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    showContextMenu(e);
                }
            }
        });
        treeView.getSelectionModel().selectedItemProperty().addListener(cl -> {
            selectItem();
        });
    }

    private void selectItem() {
        TreeItem<ViewWindow> item = treeView.getSelectionModel().getSelectedItem();
        if (item == null) {
            this.setViewPanel(this.project.getvWindow());
        } else {
            while (item.getValue().getWindow().getPanel() == null && item.getParent() != null) {
                item = item.getParent();
            }
            this.setViewPanel(item.getValue());
        }

    }

    public BorderPane getViewPane() {
        return viewPane;
    }
    
    private void showContextMenu(MouseEvent eh) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem add = new MenuItem("Přidat větev");
        MenuItem remove = new MenuItem("Odebrat větev");
        MenuItem rename = new MenuItem("Přejmenovat větev");
        MenuItem addPanel = new MenuItem("Přidat panel");
        MenuItem removePanel = new MenuItem("Odebrat panel");
        ViewWindow value = treeView.getSelectionModel().getSelectedItem().getValue();
        remove.setOnAction(e -> {
            value.getWindow().removeWindow();
        });
        add.setOnAction(e -> {
            value.getWindow().createWindow();
        });
        rename.setOnAction(e->{
            value.getWindow().rename();
        });
        addPanel.setOnAction(e -> {
            value.getWindow().createPanel();
            this.selectItem();
        });
        removePanel.setOnAction(e -> {
            value.getWindow().removePanel();
            this.selectItem();
        });
        contextMenu.getItems().addAll(add, remove, rename,addPanel, removePanel);
        contextMenu.setX(eh.getScreenX());
        contextMenu.setY(eh.getScreenY());
        contextMenu.show(Main.getINSTANCE().getStage());
    }

    public static Image getStatusImage(int i) {
        try {
            return ProjectManager.getArrayImages()[i];
        } catch (Exception e) {
            return null;
        }

    }

    public Label getErrorMessage() {
        return errorMessage;
    }

    public void setViewPanel(ViewWindow cw) {
        this.panelPane.setCenter(cw.getView());
    }

    public static void SetErrorHandler(Node node, Label messageLabel, String text) {
        node.setStyle("-fx-control-inner-background: red");
        node.setOnMouseEntered(e -> {
            messageLabel.setText("!!! -> " + text);
        });
        node.setOnMouseExited(e -> {
            messageLabel.setText("");
        });
    }

    public static void SetUnErrorHandler(Node node, Label messageLabel) {
        node.setStyle("-fx-control-inner-background: white");
        messageLabel.setText("");
        node.setOnMouseEntered(e -> {
        });
        node.setOnMouseExited(e -> {
        });
    }

    private BorderPane getBottom() {
        BorderPane pane = new BorderPane();
        HBox box = new HBox();
        Label size = new Label("Velikost písma: ");
        TextField eSize = new TextField();
        eSize.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    Integer decode = Integer.decode(eSize.getText());
                    decode = decode >50?50:decode<10?10:decode;
                    FontSize.getINSTANCE().setSize(decode);
                } catch (Exception ex) {
                     FontSize.getINSTANCE().setSize(12);
                }
            }

        });
        box.getChildren().addAll(size, eSize);
        pane.setLeft(box);
        return pane;
    }

}
