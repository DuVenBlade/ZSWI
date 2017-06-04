package zswi;

import zswi.EnumManager.EnumSection;
import zswi.EnumManager.EnumValues;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
    private ListView<EnumSection> listEnumView;
    private ListView<EnumValues> listSubEnumView;

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

        listEnumView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EnumSection>() {

            @Override
            public void changed(ObservableValue<? extends EnumSection> observable, EnumSection oldValue, EnumSection newValue) {
                changeSubEnumList(newValue);
            }
        });

        listEnumView.setCellFactory(lv -> {
            ListCell<EnumSection> cell = new ListCell<EnumSection>();
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

    private void changeSubEnumList(EnumSection ownEnum) {
        if (ownEnum != null) {
            listSubEnumView.getItems().clear();
            listSubEnumView.getItems().addAll(FXCollections.observableArrayList(ownEnum.getEnumValueList()));
        } else {
            listSubEnumView.getItems().clear();
        }
    }

    private ContextMenu createEnumContextMenu(ListCell<EnumSection> cell) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem addItem = new MenuItem("Přidat SubEnum");
        //addItem.setOnAction(event -> addSubEnum(AlertManager.getName("Název: ", ""),listEnumView.getSelectionModel().getSelectedItem()));
        MenuItem editItem = new MenuItem("Upravit");
        editItem.setOnAction(event -> editEnum(cell));
        MenuItem deleteItem = new MenuItem("Smazat");
        deleteItem.setOnAction(event -> deleteEnum(cell.getItem()));
        contextMenu.getItems().addAll(addItem, editItem, deleteItem);

        return contextMenu;
    }

    private void addEnumValue(String name) {
        if(name.isEmpty()){
            AlertManager.info("Název je pžíliš krátký");
            return;
        }
        EnumSection selectedItem = listEnumView.getSelectionModel().getSelectedItem();
        if(selectedItem==null){
            AlertManager.info("Nebyla vybrána sekce výčtového typu");
            return;
        }
        EnumSection ownEnum = new EnumSection(eManager.getIncrement().increment(), name);
        selectedItem.addEnumValue(ownEnum);
        listEnumView.getItems().add(ownEnum);
    }
    private void addEnumSection(String name){
        EnumSection tmp = listEnumView.getSelectionModel().getSelectedItem();
        if (tmp == null) {
            AlertManager.info("Nebyl vybrán žádný Výčtový typ.");
        } else if (name.isEmpty()) {
            AlertManager.info("Název výčtového typu je příliš krátký.");
        } else {
           //eManager.addEnumSection(new EnumSection());
        }
    }
    private void editEnum(ListCell<EnumSection> cell) {
        EnumSection item = cell.getItem();
        item.setName(AlertManager.getName("Nový název: ", item.getName()));
        cell.textProperty().bind(Bindings.format("%s", cell.itemProperty()));
    }

    private void deleteEnum(EnumSection item) {
        if (AlertManager.confirm("Opravdu chcete smazat tento item? " + item.toString())) {
            eManager.removeEnumSection(item);
            listEnumView.getItems().remove(item);
        }
    }

    private Node createEnumBot() {
        HBox hBox = new HBox();
        TextField textField = new TextField();
        //textField
        Button button = new Button("Přidat Enum");
        //button.setOnAction(event -> addEnum(textField.getText()));

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
            ListCell<EnumValues> cell = new ListCell<EnumValues>();
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

    private ContextMenu createSubEnumContextMenu(ListCell<EnumValues> cell) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem editItem = new MenuItem("Upravit");
        editItem.setOnAction(event -> editSubEnum(cell));
        MenuItem deleteItem = new MenuItem("Smazat");
        deleteItem.setOnAction(event -> deleteSubEnum(cell.getItem()));
        contextMenu.getItems().addAll(editItem, deleteItem);

        return contextMenu;
    }

    private void editSubEnum(ListCell<EnumValues> cell) {
        EnumValues item = cell.getItem();
        item.setName(AlertManager.getName("Nový název: ", item.getName()));
        cell.textProperty().bind(Bindings.format("%s", cell.itemProperty()));
    }

    private void deleteSubEnum(EnumValues item) {
        if (AlertManager.confirm("Opravdu chcete smazat tento item? " + item.toString())) {
            ((EnumSection) item.getParent()).getEnumValueList().remove(item);
        }
    }

    private Node createSubEnumBot() {
        HBox hBox = new HBox();
        TextField textField = new TextField();
        Button button = new Button("Přidat SubEnum");
        //button.setOnAction(event -> addSubEnum(textField.getText()));

        hBox.getChildren().addAll(textField);
        hBox.getChildren().addAll(button);

        hBox.setStyle(Constants.borderStyle7);
        return hBox;
    }

    //-------------------------------------------------------------------------------------------------
    @Override
    public void notificate() {
        if (Project.getInstance() != null) {
            listEnumView.getItems().clear();
            listEnumView.getItems().addAll(Project.getInstance().getEManager().getEnumList());
        }
    }

    @Override
    public Object getView() {
        return viewPane;
    }
}
