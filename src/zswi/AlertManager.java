package zswi;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import zswi.EnumManager.OwnEnum;
import zswi.EnumManager.OwnSubEnum;
import zswi.ItemManager.DataType;
import zswi.ViewItemEditManager.*;

/**
 *
 * @author DDvory
 */
public class AlertManager {

    private static Alert getAlert(AlertType type, String title, String headerText, String content, Node graphics) {
        Alert tmp = new Alert(type);
        tmp.setTitle(title);
        tmp.setHeaderText(headerText);
        tmp.setContentText(content);
        tmp.getDialogPane().setContent(graphics);
        return tmp;
    }

    public static boolean confirm(String text) {
        Alert alert = getAlert(Alert.AlertType.CONFIRMATION, "CONFIRM", text, "", null);
        Optional<ButtonType> showAndWait = alert.showAndWait();
        return showAndWait.get() == ButtonType.OK;
    }

    public static void info(String text) {
        getAlert(Alert.AlertType.INFORMATION, "INFO", text, "", null).show();
    }

    public static Project Project() {
        GridPane gridPane = new GridPane();
        TextField name;
        int position = 0;
        Label messageLabel = new Label();
        gridPane.add(new Label("Název projektu: "), 0, position);
        gridPane.add(name = new TextField(), 1, position++);

        ChoiceBox<LanguageManager.Language> box = new ChoiceBox(FXCollections.observableArrayList(LanguageManager.getListLanguages()));
        gridPane.add(new Label("Jazyk: "), 0, position);
        gridPane.add(box, 1, position++);
        gridPane.add(messageLabel, 0, 4, 2, 1);
        Alert alert = getAlert(Alert.AlertType.CONFIRMATION, "Založit projekt", "", "", gridPane);

        boolean bool = false;
        String nameData = "";

        while (!bool) {
            bool = true;
            Optional<ButtonType> bt = alert.showAndWait();
            if (bt.get() == ButtonType.OK) {
                /////////////////////////////////////////////////////////////////////////////////////////////////
                nameData = name.getText().trim();
                if (nameData.isEmpty()) {
                    ViewProject.SetErrorHandler(name, messageLabel, "Název projektu je příliš krátký");
                    bool = false;
                } else {
                    ViewProject.SetUnErrorHandler(name, messageLabel);
                }
                if (box.getValue() == null) {
                    ViewProject.SetErrorHandler(box, messageLabel, "Nevybral jsi jazyk");
                    bool = false;
                } else {
                    ViewProject.SetUnErrorHandler(box, messageLabel);
                }
                /////////////////////////////////////////////////////////////////////////////////////////////////
            } else {
                return null;
            }
        }
        return new Project(box.getValue().getId(), nameData, null, null, null);
    }

    public static String getName(String text, String def) {
        GridPane gridPane = new GridPane();

        TextField name = new TextField();
        name.setText(def);

        Label messageLabel = new Label();
        gridPane.add(new Label(text), 0, 0);
        gridPane.add(name, 1, 0);

        gridPane.add(messageLabel, 0, 4, 2, 1);
        Alert alert = getAlert(Alert.AlertType.CONFIRMATION, text, "", "", gridPane);
        alert.setResizable(true);
        boolean bool = false;
        String nameData = "";

        while (!bool) {
            bool = true;
            Optional<ButtonType> bt = alert.showAndWait();
            if (bt.get() == ButtonType.OK) {
                /////////////////////////////////////////////////////////////////////////////////////////////////
                nameData = name.getText().trim();

                if (nameData.isEmpty()) {
                    ViewProject.SetErrorHandler(name, messageLabel, "Název je příliš krátký");
                    bool = false;
                } else {
                    ViewProject.SetUnErrorHandler(name, messageLabel);
                }
                /////////////////////////////////////////////////////////////////////////////////////////////////
            } else {
                return null;
            }
        }
        return nameData;
    }

    public static Item Item(Item item) {
        GridPane gridPane = new GridPane();
        Label messageLabel = new Label();
        int position = 0;

        TextField adress = new TextField();
        gridPane.add(new Label("Adresa: "), 0, position);
        gridPane.add(adress, 1, position++);

        ChoiceBox<ItemManager.DataType> box = new ChoiceBox(FXCollections.observableArrayList(ItemManager.DataType.values()));
        gridPane.add(new Label("Datový typ: "), 0, position);
        gridPane.add(box, 1, position++);

        CheckBox cb = new CheckBox();
        gridPane.add(new Label("Zobrazit jednotky: "), 0, position);
        gridPane.add(cb, 1, position++);

        ScrollPane contentPane = new ScrollPane();
        contentPane.setPrefHeight(100);
        gridPane.add(contentPane, 0, position++, 2, 1);

        gridPane.add(messageLabel, 0, position, 2, 3);

        Alert alert = getAlert(Alert.AlertType.CONFIRMATION, "Přidat tabulku", "", "", gridPane);
        Object value = null;
        box.getSelectionModel().selectedItemProperty().addListener(cl -> {
            contentPane.setContent(getValue(box.getSelectionModel().getSelectedItem(), value));
        });
        boolean bool = false;
        BigInteger adresa = null;
        ItemManager.DataType dataType = null;
        while (!bool) {
            bool = true;
            Optional<ButtonType> bt = alert.showAndWait();
            if (bt.get() == ButtonType.OK) {
                /////////////////////////////////////////////////////////////////////////////////////////////////
                adresa = ItemManager.getINSTANCE().getAdress(adress.getText());
                //
                if ((dataType = box.getValue()) != null) {
                    ViewProject.SetUnErrorHandler(box, messageLabel);
                } else {
                    ViewProject.SetErrorHandler(box, messageLabel, "Délka dat (byt) není číslo");
                    bool = false;
                }
                if (adresa == null) {
                    ViewProject.SetErrorHandler(adress, messageLabel, "Adresa nebyla nastavena, \n musí být číslo\n větší než 0 a menší než");
                    bool = false;
                } else {
                    ViewProject.SetUnErrorHandler(adress, messageLabel);
                }
                /////////////////////////////////////////////////////////////////////////////////////////////////
            } else {
                return null;
            }
        }
        return new Item(adresa, dataType, cb.isSelected(), value);
    }

    public static LocalTime Time(LocalTime time) {
        GridPane gridPane = new GridPane();
        int position = 0;

        ChoiceBox<Integer> h = new ChoiceBox(FXCollections.observableArrayList(getIntArry(24)));
        h.setValue(time.getHour());
        gridPane.add(new Label("Hodiny: "), 0, position);
        gridPane.add(h, 1, position++);

        ChoiceBox<Integer> m = new ChoiceBox(FXCollections.observableArrayList(getIntArry(60)));
        m.setValue(time.getMinute());
        gridPane.add(new Label("Minuty: "), 0, position);
        gridPane.add(m, 1, position++);

        ChoiceBox<Integer> s = new ChoiceBox(FXCollections.observableArrayList(getIntArry(60)));
        s.setValue(time.getSecond());
        gridPane.add(new Label("Sekundy: "), 0, position);
        gridPane.add(s, 1, position++);

        Alert alert = getAlert(Alert.AlertType.INFORMATION, "Time", "", "", gridPane);
        alert.showAndWait();
        return LocalTime.of(h.getValue(), m.getValue(), s.getValue());
    }

    private static Integer[] getIntArry(int size) {
        Integer[] vh = new Integer[size + 1];
        for (int i = 0; i <= size; i++) {
            vh[i] = i;
        }
        return vh;
    }

    public static Object selectValue(List list, String text) {
        GridPane gridPane = new GridPane();
        int position = 0;
        ChoiceBox h = new ChoiceBox(FXCollections.observableArrayList(list));
        gridPane.add(new Label(text), 0, position);
        gridPane.add(h, 1, position++);
        Alert alert = getAlert(Alert.AlertType.CONFIRMATION, "Choose", "", "", gridPane);
        Optional<ButtonType> showAndWait = alert.showAndWait();
        if (showAndWait.get() == ButtonType.OK) {
            return h.getValue();
        }
        return null;
    }

    public static Item_Enum Enum(Item_Enum value) {
        EnumManager enumManager = ProjectManager.getINSTANCE().getEnumManager();
        List<OwnEnum> enums = enumManager.getEnums();
        if (enums.isEmpty()) {
            Alert alert = getAlert(Alert.AlertType.INFORMATION, "Enum", "Neexistuje žádný enum.", "", null);
            alert.showAndWait();
            return null;
        }
        GridPane gridPane = new GridPane();
        int position = 0;

        ChoiceBox<OwnEnum> ID = new ChoiceBox<>(
                FXCollections.observableArrayList(enums)
        );
        gridPane.add(new Label("Enum: "), 0, position);
        gridPane.add(ID, 1, position++);

        ChoiceBox<OwnSubEnum> subID = new ChoiceBox<>();
        gridPane.add(new Label("SubEnum: "), 0, position);
        gridPane.add(subID, 1, position++);

        ID.getSelectionModel().selectedItemProperty().addListener(event -> {
            subID.setItems(FXCollections.observableArrayList(ID.getSelectionModel().getSelectedItem().getSubEnums()));
            subID.getSelectionModel().select(0);
        });

        if (value == null) {
            ID.getSelectionModel().select(0);
        } else {
            ID.getSelectionModel().select(
                    enumManager.getIDEnum(value.getID()));
            subID.getSelectionModel().select(
                    enumManager.getSubIDSubEnum(value.getID(), value.getSubId()));
        }

        Alert alert = getAlert(Alert.AlertType.INFORMATION, "Enum", "", "", gridPane);
        alert.showAndWait();

        if (ID.getValue() == null) {
            return null;
        } else {
            return new Item_Enum(ID.getValue().getID(), subID.getValue().getID());
        }
    }

    private static ACreateable getValue(DataType type, Object obj) {
        ACreateable tmp = null;
        switch (type) {
            case BIT:
                obj = new Item_BiBo<Byte>((byte) 0);
                break;
            case BOOLEAN:
                obj = new Item_BiBo<Boolean>(false);
                break;
            case ENUM:
                obj = new Item_Enum();
                break;
            case DATE:
                obj = new Item_DaTi(LocalDate.now());
                tmp = new ViewItem_DaTi((Item_DaTi) obj);
                break;
            case DOUBLE:
                obj = new Item_DoFl<Double>(0.0);
                tmp = new ViewItem_DoFl((Item_DoFl) obj);
                break;
            case FLOAT:
                obj = new Item_DoFl<Float>(0.0f);
                tmp = new ViewItem_DoFl((Item_DoFl) obj);
                break;
            case INT:
                obj = new Item_Int();
                tmp = new ViewItem_Int((Item_Int) obj);
                break;
            case STRING:
                obj = new Item_String();
                tmp = new ViewItem_String((Item_String) obj);
                break;
            case TIME:
                obj = new Item_DaTi(LocalTime.now());
                tmp = new ViewItem_DaTi((Item_DaTi) obj);
                break;
        }
        return tmp;
    }
}
