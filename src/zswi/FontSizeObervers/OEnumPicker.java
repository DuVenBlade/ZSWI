package zswi.FontSizeObervers;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import zswi.AlertManager;
import zswi.EnumManager;
import zswi.EnumManager.OwnEnum;
import zswi.EnumManager.OwnSubEnum;
import zswi.ProjectManager;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import zswi.Item_Enum;

public class OEnumPicker extends HBox implements Observer {

    private TextField ID;
    private TextField subID;
    private Button bt;
    private Item_Enum value;

    public OEnumPicker() {
        this("");
    }

    public OEnumPicker(String string) {
        FontSize.getINSTANCE().addObserver(this);
        init();
    }

    private void init() {

        ID = new TextField();
        subID = new TextField();

        ID.setOnKeyReleased(e -> {
            if (value != null) {
                textControl();
            }
        });

        subID.setOnKeyReleased(e -> {
            if (value != null) {
                textControl();
            }
        });

        bt = new Button("enum");
        bt.setOnMouseClicked(e -> {
            Item_Enum enumItem = AlertManager.Enum(value);
            if (enumItem != null) {
                value = enumItem;
                EnumManager temp = ProjectManager.getINSTANCE().getEnumManager();
                ID.setText(temp.getIDName(value.getID()));
                subID.setText(temp.getSubIDName(value.getID(), value.getSubId()));
            }
        });

        this.getChildren().addAll(ID, subID, bt);
    }

    private void textControl() {
        List<OwnEnum> enums = ProjectManager.getINSTANCE().getEnumManager().getEnums();
        List<OwnSubEnum> subEnums;
        OwnEnum tempEnum;
        OwnSubEnum tempSubEnum;
        String tempStringEnum, tempStringSubEnum;

        for (int i = 0; i < enums.size(); i++) {
            tempEnum = enums.get(i);
            tempStringEnum = tempEnum.getName();
            if (tempStringEnum.equals(ID.getText())) {
                subEnums = tempEnum.getSubEnums();
                for (int j = 0; j < subEnums.size(); j++) {
                    tempSubEnum = subEnums.get(j);
                    tempStringSubEnum = tempSubEnum.getName();
                    if (tempStringSubEnum.equals(subID.getText())) {
                        value = new Item_Enum(tempEnum.getID(), tempSubEnum.getID());
                        EnumManager tmp = ProjectManager.getINSTANCE().getEnumManager();
                        ID.setText(tmp.getIDName(value.getID()));
                        subID.setText(tmp.getSubIDName(value.getID(), value.getSubId()));
                    }
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        setFontSize(Integer.decode(arg.toString()));
    }

    private void setFontSize(int size) {

    }

    public Item_Enum getValue() {
        return value;
    }
}
