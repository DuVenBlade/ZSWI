package zswi;

import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author DDvory
 */
public class ViewItemEditManager {

    public abstract class ACreateable extends GridPane {

        protected abstract void init();

        public abstract void match();
    }

    //---------------------------------------------------------
    public static class ViewItem_DaTi extends ACreateable {

        private ChoiceBox box;
        private Item_DaTi dati;

        public ViewItem_DaTi(Item_DaTi dati) {
            this.dati = dati;
            box = new ChoiceBox(FXCollections.observableArrayList(dati.getFormatters()));
            box.getSelectionModel().select(dati.getFormat());
            init();
        }

        @Override
        protected void init() {
            int position = 0;
            this.add(new Label("Formát: "), 0, position);
            this.add(box, 1, position++);

        }

        @Override
        public void match() {
            dati.setFormat((DateTimeFormatter) box.getValue());
        }
    }
//-----------------------------------------------------------

    public static class ViewItem_String extends ACreateable {

        private Item_String str;

        private TextField len;

        public ViewItem_String(Item_String str) {
            this.str = str;
            this.len = new TextField(str.getValue());
            init();
        }

        @Override
        protected void init() {
            int position = 0;
            this.add(new Label("Formát: "), 0, position);
            this.add(len, 1, position++);
        }

        @Override
        public void match() {
            str.setLen(len.getText());
        }
    }
//------------------------------------------------------------

    public static class ViewItem_Int extends ACreateable {

        private Item_Int integer;

        private ChoiceBox<Integer> len;
        private ChoiceBox<Item_Int.Format> format;

        //Metric
        public ViewItem_Int(Item_Int integer) {
            this.integer = integer;
            len = new ChoiceBox(FXCollections.observableArrayList(Item_Int.lens));
            len.getSelectionModel().select((Integer) integer.getLen());

            format = new ChoiceBox(FXCollections.observableArrayList(Item_Int.Format.values()));
            format.getSelectionModel().select(integer.getFormat());
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
        public void match() {
            integer.setLen(len.getValue());
            integer.setFormat(format.getValue());
        }
    }

    //--------------------------------------------------------------------------------
    public static class ViewItem_DoFl extends ACreateable {

        Item_DoFl dofl;

        //Metric
        public ViewItem_DoFl(Item_DoFl dofl) {
            this.dofl = dofl;
        }

        @Override
        protected void init() {
        }

        @Override
        public void match() {
        }
    }
}
