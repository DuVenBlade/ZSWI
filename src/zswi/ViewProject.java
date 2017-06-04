package zswi;

import javafx.collections.FXCollections;
import zswi.FontSizeObervers.OLabel;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import zswi.FontSizeObervers.FontSize;
import zswi.FontSizeObervers.OImageView;
import zswi.Main.Observabler;

/**
 *
 * @author DDvory
 */
public class ViewProject implements Observabler {

    private Project project;
    private TreeView<ViewWindow> treeView;
    private ErrorLabel errorMessage;
    private BorderPane panelPane;
    private BorderPane viewPane;
    private boolean onChange = true;
    ChoiceBox<LanguageManager.Language> langs;

    public ViewProject(Project project) {
        errorMessage = new ErrorLabel();
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
            value.getWindow().setPanel();
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

    public ErrorLabel getErrorMessage() {
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
        pane.setLeft(getSize());
        pane.setCenter(getLanguage());
        pane.setRight(errorMessage);
        return pane;
    }
    private HBox getSize(){
         HBox box = new HBox();
        box.setStyle(Constants.borderStyle5);
        Label size = new Label("Velikost písma: ");
        TextField eSize = new TextField();
        FontSize sizeS = ProjectManager.getFont();
        eSize.setText(sizeS+"");
        eSize.setPrefWidth(50);
        eSize.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    Integer decode = Integer.decode(eSize.getText());
                    sizeS.setSize(decode);
                } catch (Exception ex) {
                }
            }

        });
        box.getChildren().addAll(size, eSize);
        return box;
    }
    
    private HBox getLanguage(){
        HBox box = new HBox();
        box.setStyle(Constants.borderStyle5);
        Label lang = new Label("Jazyk: ");
        Button bt = new Button("");
        langs = new ChoiceBox<>();
        langs.getSelectionModel().selectedItemProperty().addListener(event -> {
            LanguageManager.Language value = langs.getSelectionModel().getSelectedItem();
            if(value!=null){
                project.setLanguage(value.getId());
                project.updateAll();
            }
        });
        bt.setOnMouseClicked(e->{
            showLanguageContextMenu(e);
        });
        ImageView imageView = new ImageView(ProjectManager.edit);
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        bt.setGraphic(imageView);
        box.getChildren().addAll(lang,langs,new Label(" __ "),bt);
        return box;
    }
    public void updateLanguage(){
        langs.getItems().clear();
        langs.getItems().addAll(project.getListLanguages());
        langs.getSelectionModel().select(LanguageManager.getListLanguages().get(project.getLanguage()));
        if(langs.getSelectionModel().getSelectedItem()==null){
            langs.getSelectionModel().selectFirst();
        }
    }
    private void showLanguageContextMenu(MouseEvent eh) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem add = new MenuItem("Přidat jazyk");
        MenuItem remove = new MenuItem("Odebrat jazyk");
        remove.setOnAction(e -> {
            project.removeLanguage();
        });
        add.setOnAction(e -> {
            project.addLanguage();
        });
        contextMenu.getItems().addAll(add, remove);
        contextMenu.setX(eh.getScreenX());
        contextMenu.setY(eh.getScreenY());
        contextMenu.show(Main.getINSTANCE().getStage());
    }

    @Override
    public void notificate() {
        Main instance = Main.getINSTANCE();
        instance.getRoot().setCenter((BorderPane)getView());
        instance.getStage().setTitle(project.getName());
        langs.getSelectionModel().select(LanguageManager.getListLanguages().get(project.getLanguage()));
        if(langs.getSelectionModel().getSelectedItem()==null){
            langs.getSelectionModel().selectFirst();
        }
    }

    @Override
    public Object getView() {
        return viewPane;
    }
    public class ErrorLabel extends HBox{
        ImageView view;
        Label errorMessage;

        public ErrorLabel() {
            errorMessage = new Label();
            view = new ImageView();
            view.setFitWidth(20);
            view.setFitHeight(20);
            this.getChildren().addAll(view, new Label(" "), errorMessage);
            errorMessage.setStyle("-fx-font-weight: 900;-fx-font-size: 15");
            this.setStyle(Constants.borderStyle5);
        }
        public void setMessage(boolean bool, String text){
            if(bool)view.setImage(ProjectManager.error);
            else view.setImage(null);
            errorMessage.setText(text);
        }
    }
}
