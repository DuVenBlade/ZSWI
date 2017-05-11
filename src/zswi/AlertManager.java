package zswi;

import java.math.BigInteger;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import zswi.EnumManager.EnumItem;
import zswi.EnumManager.OwnEnum;
import zswi.EnumManager.OwnSubEnum;

/**
 *
 * @author DDvory
 */
public class AlertManager {

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
        Alert alert = Main.getAlert(Alert.AlertType.CONFIRMATION, "Založit projekt", "", "", gridPane);

        boolean bool = false;
        String nameData = "";
        int fontData = 0;

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
        return new Project(box.getValue().getId(), nameData, null, null);
    }

    public static String getName(String text) {
        GridPane gridPane = new GridPane();

        TextField name = new TextField();

        Label messageLabel = new Label();
        gridPane.add(new Label(text), 0, 0);
        gridPane.add(name, 1, 0);

        gridPane.add(messageLabel, 0, 4, 2, 1);
        Alert alert = Main.getAlert(Alert.AlertType.CONFIRMATION, text, "", "", gridPane);
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

    public static Item Item() {
        GridPane gridPane = new GridPane();
        Label messageLabel = new Label();
        int position = 0;

        TextField adress = new TextField();
        gridPane.add(new Label("Adresa: "), 0, position);
        gridPane.add(adress, 1, position++);

        ChoiceBox<ItemManager.DataType> box = new ChoiceBox(FXCollections.observableArrayList(ItemManager.DataType.values()));
        gridPane.add(new Label("Datový typ: "), 0, position);
        gridPane.add(box, 1, position++);

        TextField len = new TextField();
        gridPane.add(new Label("Délka v bytech: "), 0, position);
        gridPane.add(len, 1, position++);

        TextField form = new TextField();
        gridPane.add(new Label("Délka v bytech: "), 0, position);
        gridPane.add(form, 1, position++);

        gridPane.add(messageLabel, 0, position, 2, 1);

        Alert alert = Main.getAlert(Alert.AlertType.CONFIRMATION, "Přidat tabulku", "", "", gridPane);
        boolean bool = false;
        Item it = null;
        BigInteger adresa = null;
        ItemManager.DataType dataType = null;
        int dataLen = 0;

        while (!bool) {
            bool = true;
            Optional<ButtonType> bt = alert.showAndWait();
            if (bt.get() == ButtonType.OK) {
                /////////////////////////////////////////////////////////////////////////////////////////////////
                adresa = ItemManager.getINSTANCE().getAdress(adress.getText());
                //
                try {
                    dataLen = Integer.decode(adress.getText());
                    ViewProject.SetUnErrorHandler(len, messageLabel);
                } catch (Exception e) {
                    ViewProject.SetErrorHandler(len, messageLabel, "Délka dat (byt) není číslo");
                    bool = false;

                }
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
        return new Item(adresa, dataType, dataLen, form.getText());
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

        Alert alert = Main.getAlert(Alert.AlertType.INFORMATION, "Time", "", "", gridPane);
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
        Alert alert = Main.getAlert(Alert.AlertType.CONFIRMATION, "Choose", "", "", gridPane);
        Optional<ButtonType> showAndWait = alert.showAndWait();
        if (showAndWait.get() == ButtonType.OK) {
            return h.getValue();
        }
        return null;
    }

    public static EnumItem Enum(EnumItem value) {
        List<OwnEnum> enums = ProjectManager.getINSTANCE().getEnumManager().getEnums();
        if (enums.isEmpty()) {
            Alert alert = Main.getAlert(Alert.AlertType.INFORMATION, "Enum", "Neexistuje žádný enum.", "", null);
            alert.showAndWait();
            return null;
        }
        GridPane gridPane = new GridPane();
        int position = 0;

        ChoiceBox<OwnEnum> ID = new ChoiceBox<OwnEnum>(
                FXCollections.observableArrayList(enums)
        );
        gridPane.add(new Label("Enum: "), 0, position);
        gridPane.add(ID, 1, position++);

        ChoiceBox<OwnSubEnum> subID = new ChoiceBox<OwnSubEnum>();
        gridPane.add(new Label("SubEnum: "), 0, position);
        gridPane.add(subID, 1, position++);

        ID.getSelectionModel().selectedItemProperty().addListener(event -> {
            subID.setItems(FXCollections.observableArrayList(ID.getSelectionModel().getSelectedItem().getSubEnums()));
            subID.getSelectionModel().select(0);
            TestLogger.print(AlertManager.class,ID.getValue()+" "+ subID.getValue());
        });

        if (value == null) {
            ID.getSelectionModel().select(0);
        } else {
            ID.getSelectionModel().select(
                    ProjectManager.getINSTANCE().getEnumManager().getIDEnum(value.getID()));
            subID.getSelectionModel().select(
                    ProjectManager.getINSTANCE().getEnumManager().getSubIDSubEnum(value.getID(), value.getSubId()));
        }

        Alert alert = Main.getAlert(Alert.AlertType.INFORMATION, "Enum", "", "", gridPane);
        alert.showAndWait();

        if (ID.getValue() == null) {
            return null;
        } else {
            return new EnumItem(ID.getValue().getID(), subID.getValue().getID());
        }
    }
}
