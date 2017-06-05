package zswi;

import zswi.EnumManager.EnumSection;
import zswi.EnumManager.EnumValue;
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
    private ListView<EnumValue> listSubEnumView;
    TextField textFieldEnumSection;
    TextField textFieldEnumValue;

    public EnumView(EnumManager eManager) {
        viewPane = new BorderPane();
        this.eManager = eManager;
        init();
    }

    private void init() {
        textFieldEnumValue = new TextField();
        textFieldEnumSection = new TextField();
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

        MenuItem addItem = new MenuItem("Přidat hodnotu");
        addItem.setOnAction(event -> addEnumValue(AlertManager.getName("Název: ", "")));
        MenuItem editItem = new MenuItem("Přejmenovat");
        editItem.setOnAction(event -> editEnum(cell));
        MenuItem deleteItem = new MenuItem("Smazat");
        deleteItem.setOnAction(event -> deleteEnum(cell.getItem()));
        contextMenu.getItems().addAll(addItem, editItem, deleteItem);

        return contextMenu;
    }

    private void addEnumValue(String name) {
        if (name.isEmpty()) {
            AlertManager.info("Název je pžíliš krátký");
            return;
        }
        EnumSection selectedItem = listEnumView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            AlertManager.info("Nebyla vybrána sekce výčtového typu");
            return;
        }
        eManager.addEnumValue(selectedItem, name);
    }

    private void addEnumSection(String name) {
        if (name.isEmpty()) {
            AlertManager.info("Název výčtového typu je příliš krátký.");
        } else {
            eManager.addEnumSection(name);
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
        //textField
        Button button = new Button("Přidat Výčtový typ");
        button.setOnAction(event -> addEnumSection(textFieldEnumSection.getText()));

        hBox.getChildren().addAll(textFieldEnumSection);
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
            ListCell<EnumValue> cell = new ListCell<EnumValue>();
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

    private ContextMenu createSubEnumContextMenu(ListCell<EnumValue> cell) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem editItem = new MenuItem("Upravit");
        editItem.setOnAction(event -> editSubEnum(cell));
        MenuItem deleteItem = new MenuItem("Smazat");
        deleteItem.setOnAction(event -> deleteSubEnum(cell.getItem()));
        contextMenu.getItems().addAll(editItem, deleteItem);

        return contextMenu;
    }

    private void editSubEnum(ListCell<EnumValue> cell) {
        EnumValue item = cell.getItem();
        String name = AlertManager.getName("Nový název: ", item.getName());
        if (name != null) {
            item.setName(name);
            cell.textProperty().bind(Bindings.format("%s", cell.itemProperty()));
        }
    }

    private void deleteSubEnum(EnumValue item) {
        if (AlertManager.confirm("Opravdu chcete smazat tento item? " + item.toString())) {
            eManager.removeEnumValue(item);
        }
    }

    private Node createSubEnumBot() {
        HBox hBox = new HBox();
        Button button = new Button("Přidat Hodnotu výčtového typu");
        button.setOnAction(event -> addEnumValue(textFieldEnumValue.getText()));

        hBox.getChildren().addAll(textFieldEnumValue);
        hBox.getChildren().addAll(button);

        hBox.setStyle(Constants.borderStyle7);
        return hBox;
    }

    //-------------------------------------------------------------------------------------------------
    @Override
    public void notificate() {
        EnumSection selectedItem = listEnumView.getSelectionModel().getSelectedItem();
        listEnumView.getItems().clear();
        listEnumView.getItems().addAll(eManager.getEnumList());
        textFieldEnumValue.setText("");
        textFieldEnumSection.setText("");
        if (selectedItem != null) {
            changeSubEnumList(selectedItem);
        }
    }

    @Override
    public Object getView() {
        return viewPane;
    }
}
