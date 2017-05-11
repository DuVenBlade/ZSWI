package zswi;


import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import zswi.FontSizeObervers.*;

/**
 *
 * @author DDvory
 * @param <T>
 */
public class ViewItem<T extends Node> implements Main.Observabler{
    private Item item;
    private Cell<TextField> name;
    private Cell<T> data;
    private Cell<TextField> unit;

    private ViewItem(Item cit, Cell<T> data) {
        this.item = cit;
        this.data = data;
        name = initName();
        unit = initUnit();
        setGraphics(name,this.data,unit);
    }
    public static ViewItem createBitBoolView(final Item item, boolean bool){
        Cell<OBitBoolPicker> txn = new Cell<OBitBoolPicker>(new OBitBoolPicker(bool)){
            @Override
            public void commitEdit() {
                super.commitEdit();
                item.setData(this.getItem().getValue());
            }
        };
        return new ViewItem<OBitBoolPicker>(item,txn);
    }
    public static ViewItem createEnumView(final Item item){
        Cell<OEnumPicker> txn = new Cell<OEnumPicker>(new OEnumPicker()){
            @Override
            public void commitEdit() {
                super.commitEdit();
                item.setData(this.getItem().getValue());
            }
        };
        return new ViewItem<OEnumPicker>(item,txn);
    }
    public static ViewItem createTextFieldView(final Item item){
        Cell<TextField> txn = new Cell<TextField>(new TextField()){
            @Override
            public void commitEdit() {
                super.commitEdit();
                item.setControlData(this.getItem().getText());
            }
        };
        return new ViewItem<TextField>(item,txn);
    }
    public static ViewItem createDatePickerView(final Item item){
        Cell<ODatePicker> txn = new Cell<ODatePicker>(new ODatePicker()){

            @Override
            public void commitEdit() {
                super.commitEdit();
                item.setData(this.getItem().getValue());
            }
        };
        return new ViewItem<ODatePicker>(item,txn);
    }
    public static ViewItem createTimePickerView(final Item item){
        Cell<OTimePicker> txn = new Cell<OTimePicker>(new OTimePicker()){

            @Override
            public void commitEdit() {
                super.commitEdit();
                item.setData(this.getItem().getValue());
            }
        };
        return new ViewItem<OTimePicker>(item,txn);
    }
    private Cell<TextField> initName(){
        Cell<TextField> txn = new Cell<TextField>(new TextField()){

            @Override
            public void commitEdit() {
                super.commitEdit();
                item.setName(this.getItem().getText());
            }
        };
        return txn;
    }
    private Cell<TextField> initUnit(){
        Cell<TextField> txn = new Cell<TextField>(new TextField()){

            @Override
            public void commitEdit() {
                super.commitEdit();
                item.setUnit(this.getItem().getText());
            }
        };
        return txn;
    }
    public void setName(String name) {
       this.name.setText(name);
    }

    public void setData(String data) {
        this.data.setText(data);
    }

    public void setUnit(String unit) {
        this.unit.setText(unit);
    }

    public Cell<TextField> getName() {
        return name;
    }

    public Cell<T> getData() {
        return data;
    }

    public Cell<TextField> getUnit() {
        return unit;
    }

    
    public void setErrGraphics(HBox ... lb){
        for (HBox label : lb) {
            label.setStyle("-fx-control-inner-background: red;"
                            );
            
        }
        
    }
    public void setGraphics(HBox ... lb){
        for (HBox label : lb) {
            label.setStyle(Constants.borderStyle4);
        }
    }

    @Override
    public void notificate() {
        name.setText(item.getName() == null?item.getType().toString():item.getName());
        data.setText(item.getData()==null?"":item.getData().toString());
        unit.setText(item.getUnit()==null?"":item.getUnit());
    }

    @Override
    public ViewItem getView() {
        return this;
    }
    private static  class Cell<S extends Node> extends HBox{
        private S eItem;
        private Label lb;
        private String cashText;
        private boolean isInEdit = false;

        public Cell(S item) {
            this.eItem = item;
            this.setOnMouseClicked(e->{
                if(e.getClickCount()==2){
                    isInEdit = true;
                    startEdit();
                }
            });
            
            this.lb= new OLabel();
            lb.setOnKeyReleased(e->{
                 if(e.getCode() == KeyCode.ENTER&&isInEdit){
                        commitEdit();
                    }else if(e.getCode() == KeyCode.ESCAPE&&isInEdit){
                        cancleEdit();
                    }
            });
            this.getChildren().add(this.lb);
        }
        public void setText(String text){
            cashText = text;
            lb.setText(cashText);
        }
        
        public void startEdit(){
            this.setText("");
            lb.setGraphic(eItem);
        }
        public void cancleEdit(){
            this.setText(cashText);
            lb.setGraphic(null);
        }
        public void commitEdit(){
            this.setText(cashText);
            lb.setGraphic(null);
        }

        public S getItem() {
            return eItem;
        }
        
    }
    
    
}
