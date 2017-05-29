package zswi;

import zswi.EnumManager.OwnEnum;
import zswi.EnumManager.OwnSubEnum;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class EnumView implements Main.Observabler {
    
    private EnumManager eManager;
    private BorderPane viewPane;
    private ListView<OwnEnum> listEnumView;
    private ListView<OwnSubEnum> listSubEnumView;

    public EnumView(EnumManager eManager) {
        viewPane = new BorderPane();
        this.eManager = eManager;
        init();
    }

    private void init() {
        SplitPane spl = new SplitPane();
        spl.getItems().addAll(createEnumPane(), createSubEnumPane());
        viewPane.setCenter(spl);
    }

    //------------------------------------------ENUM-----------------------------------------------
    private Node createEnumPane() {
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(createEnumListView());
        borderPane.setBottom(createEnumBot());
        return borderPane;
    }

    private Node createEnumListView() {
        listEnumView = new ListView<>();

        listEnumView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OwnEnum>() {

            @Override
            public void changed(ObservableValue<? extends OwnEnum> observable, OwnEnum oldValue, OwnEnum newValue) {
                changeSubEnumList(newValue);
            }
        });

        listEnumView.setCellFactory(lv -> {
            ListCell<OwnEnum> cell = new ListCell<OwnEnum>();
            ContextMenu contextMenu = createEnumContextMenu(cell);

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                    cell.textProperty().bind(Bindings.format(""));
                } else {
                    cell.setContextMenu(contextMenu);
                    cell.textProperty().bind(Bindings.format("%s", cell.itemProperty()));
                }
            });
            return cell;
        });

        listEnumView.setStyle(Constants.borderStyle8);

        return listEnumView;
    }

    private void changeSubEnumList(OwnEnum ownEnum) {
        OwnEnum temp = listEnumView.getSelectionModel().getSelectedItem();
        if (temp != null) {
            listSubEnumView.getItems().clear();
            listSubEnumView.getItems().addAll(
                    ProjectManager.getINSTANCE().getEnumManager().getIDEnum(
                            temp.getID()).getSubEnums());
        } else {
            listSubEnumView.getItems().clear();
        }
    }

    private ContextMenu createEnumContextMenu(ListCell<OwnEnum> cell) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem addItem = new MenuItem("Přidat SubEnum");
        addItem.setOnAction(event -> addSubEnum(AlertManager.getName("Název: ", "")));
        MenuItem editItem = new MenuItem("Upravit");
        editItem.setOnAction(event -> editEnum(cell));
        MenuItem deleteItem = new MenuItem("Smazat");
        deleteItem.setOnAction(event -> deleteEnum(cell.getItem()));
        contextMenu.getItems().addAll(addItem, editItem, deleteItem);

        return contextMenu;
    }

    private void addEnum(String name) {
        if (name.isEmpty()) {
            AlertManager.info("Název výčtového typu je příliš krátký.");
        } else {
             ProjectManager temp = ProjectManager.getINSTANCE();
            OwnEnum ownEnum = new OwnEnum(eManager.getIncrement(), new Name(temp.getProject().getLanguage(), name));
            eManager.addEnum(ownEnum);
            listEnumView.getItems().add(ownEnum);
        }
    }

    private void editEnum(ListCell<OwnEnum> cell) {
        OwnEnum item = cell.getItem();
        item.setName(AlertManager.getName("Nový název: ", item.getName()));
        cell.textProperty().bind(Bindings.format("%s", cell.itemProperty()));
    }

    private void deleteEnum(OwnEnum item) {
        if (AlertManager.confirm("Opravdu chcete smazat tento item? " + item.toString())) {
            ProjectManager.getINSTANCE().getEnumManager().removeEnum(item);
            listEnumView.getItems().remove(item);
        }
    }

    private Node createEnumBot() {
        HBox hBox = new HBox();
        TextField textField = new TextField();
        //textField
        Button button = new Button("Přidat Enum");
        button.setOnAction(event -> addEnum(textField.getText()));

        hBox.getChildren().addAll(textField);
        hBox.getChildren().addAll(button);

        hBox.setStyle(Constants.borderStyle7);
        return hBox;
    }

    //-----------------------------------------SUBENUM---------------------------------------------
    private Node createSubEnumPane() {
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(createSubEnumListView());
        borderPane.setBottom(createSubEnumBot());
        return borderPane;
    }

    private Node createSubEnumListView() {
        listSubEnumView = new ListView<>();

        listSubEnumView.setCellFactory(lv -> {
            ListCell<OwnSubEnum> cell = new ListCell<OwnSubEnum>();
            ContextMenu contextMenu = createSubEnumContextMenu(cell);

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                    cell.textProperty().bind(Bindings.format(""));
                } else {
                    cell.setContextMenu(contextMenu);
                    cell.textProperty().bind(Bindings.format("%s", cell.itemProperty()));
                }
            });
            return cell;
        });

        listSubEnumView.setStyle(Constants.borderStyle8);

        return listSubEnumView;
    }

    private ContextMenu createSubEnumContextMenu(ListCell<OwnSubEnum> cell) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem editItem = new MenuItem("Upravit");
        editItem.setOnAction(event -> editSubEnum(cell));
        MenuItem deleteItem = new MenuItem("Smazat");
        deleteItem.setOnAction(event -> deleteSubEnum(cell.getItem()));
        contextMenu.getItems().addAll(editItem, deleteItem);

        return contextMenu;
    }

    private void addSubEnum(String name) {
        OwnEnum tmp = listEnumView.getSelectionModel().getSelectedItem();
        if (tmp == null) {
            AlertManager.info("Nebyl vybrán žádný Výčtový typ.");
        } else if (name.isEmpty()) {
            AlertManager.info("Název výčtového typu je příliš krátký.");
        } else {
            ProjectManager temp = ProjectManager.getINSTANCE();
            OwnSubEnum ownSubEnum = new OwnSubEnum(tmp.getIncrement(), new Name(temp.getProject().getLanguage(), name));

            temp.getEnumManager().getIDEnum(
                    listEnumView.getSelectionModel().getSelectedItem().getID())
                    .addSubEnum(ownSubEnum);
            listSubEnumView.getItems().add(ownSubEnum);
        }
    }

    private void editSubEnum(ListCell<OwnSubEnum> cell) {
        OwnSubEnum item = cell.getItem();
        item.setName(AlertManager.getName("Nový název: ", item.getName()));
        cell.textProperty().bind(Bindings.format("%s", cell.itemProperty()));
    }

    private void deleteSubEnum(OwnSubEnum item) {
        if (AlertManager.confirm("Opravdu chcete smazat tento item? " + item.toString())) {
            ProjectManager.getINSTANCE().getEnumManager().getIDEnum(
            listEnumView.getSelectionModel().getSelectedItem().getID()).removeSubEnum(item);
            listSubEnumView.getItems().remove(item);
        }
    }

    private Node createSubEnumBot() {
        HBox hBox = new HBox();
        TextField textField = new TextField();
        Button button = new Button("Přidat SubEnum");
        button.setOnAction(event -> addSubEnum(textField.getText()));

        hBox.getChildren().addAll(textField);
        hBox.getChildren().addAll(button);

        hBox.setStyle(Constants.borderStyle7);
        return hBox;
    }

    //-------------------------------------------------------------------------------------------------
    @Override
    public void notificate() {
        if (ProjectManager.getINSTANCE() != null) {
            listEnumView.getItems().clear();
            listEnumView.getItems().addAll(ProjectManager.getINSTANCE().getEnumManager().getAllEnums());
        }
    }

    @Override
    public Object getView() {
        return viewPane;
    }
}
