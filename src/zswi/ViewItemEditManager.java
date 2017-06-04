package zswi;

import java.text.SimpleDateFormat;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import zswi.EnumManager.EnumSection;
import zswi.ItemManager.DataType;

/**
 *
 * @author DDvory
 */
public class ViewItemEditManager {
 
    public static ACreateable getEdit(DataType type){
        switch (type) {
            case STRING:
                return new ViewItem_String();
            case INT:
                return new ViewItem_Int();
            case DOUBLE:
                return new ViewItem_DoFl();
            case FLOAT:
                return new ViewItem_DoFl();
            case DATE:
            case TIME:
                return new ViewItem_DaTi(type);
            case ENUM:
                return new ViewItem_Enum();
            case BOOLEAN:
            case BIT:
                return null; 
        }
        return null;
    }

    public static abstract class ACreateable extends GridPane {
        protected abstract void init();
        public abstract Object[] getData();
    }

    //---------------------------------------------------------
    public static class ViewItem_DaTi extends ACreateable {
        private ChoiceBox box;
        public ViewItem_DaTi(DataType type) {
            box = new ChoiceBox(FXCollections.observableArrayList(Item_DaTi.getFormatters(type)));
            box.getSelectionModel().selectFirst();
            init();
        }

        @Override
        protected void init() {
            int position = 0;
            this.add(new Label("Formát: "), 0, position);
            this.add(box, 1, position++);

        }

        @Override
        public Object[] getData() {
            return new Object[]{(SimpleDateFormat) box.getValue()};
        }
    }
//-----------------------------------------------------------

    public static class ViewItem_String extends ACreateable {


        private TextField len;

        public ViewItem_String() {
            this.len = new TextField("0");
            init();
        }

        @Override
        protected void init() {
            int position = 0;
            this.add(new Label("Délka textu(Byte): "), 0, position);
            this.add(len, 1, position++);
        }

        @Override
        public Object[] getData() {
            return new Object[]{len.getText()};
        }
    }
//------------------------------------------------------------

    public static class ViewItem_Int extends ACreateable {


        private ChoiceBox<Integer> len;
        private ChoiceBox<Item_Int.Format> format;

        //Metric
        public ViewItem_Int() {
            len = new ChoiceBox(FXCollections.observableArrayList(Item_Int.lens));
            len.getSelectionModel().selectFirst();

            format = new ChoiceBox(FXCollections.observableArrayList(Item_Int.Format.values()));
            format.getSelectionModel().selectFirst();
            init();
        }

        @Override
        protected void init() {
            int position = 0;
            this.add(new Label("Délka(byte): "), 0, position);
            this.add(len, 1, position++);
            this.add(new Label("Formát: "), 0, position);
            this.add(format, 1, position++);
        }

        @Override
        public Object[] getData() {
            return new Object[]{len.getValue(),format.getValue()};
        }
    }
    //--------------------------------------------------------------------------------
    public static class ViewItem_DoFl extends ACreateable {
        
        @Override
        protected void init() {
        }

        @Override
        public Object[] getData() {
            return new Object[]{};
        }
    }
    //--------------------------------------------------------
    public static class ViewItem_Enum extends ACreateable{
        private ChoiceBox<EnumSection> value;

        public ViewItem_Enum() {
            List<EnumSection> en = Project.getInstance().getEManager().getNotEmptyEnums();
            value = new ChoiceBox<>(FXCollections.observableArrayList(en));
            if(!en.isEmpty())value.getSelectionModel().selectFirst();
            init();
        }
        
        @Override
        protected void init() {
            int position = 0;
            this.add(new Label("Výběr výčtového typu: "), 0, position);
            this.add(value, 1, position++);
        }

        @Override
        public Object[] getData() {
            return new Object[]{value.getValue().getID()};
        }
    }
}
