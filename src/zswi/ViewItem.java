package zswi;


import java.util.Arrays;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import zswi.FontSizeObervers.*;
import zswi.ViewProject.ErrorLabel;

/**
 *
 * @author DDvory
 * @param <T>
 */
public class ViewItem implements Main.Observabler{
    private Item item;
    private Cell<TextField> name;
    private Cell<Node> data;
    private Cell<TextField> unit;

    private ViewItem(Item cit, Cell<Node> data) {
        this.item = cit;
        this.data = data;
        name = initName();
        unit = initUnit();
        setGeNormal(name,this.data,unit);
    }
    public static ViewItem createBitBoEnView(final Item item,Object... array){
        Cell txn = new Cell<OPicker>(new OPicker(array)){
            @Override
            public void commitEdit() {
                super.commitEdit();
                ItemManager.getINSTANCE().setData(this.getItem().getValue(), item);
            }
        };
        return new ViewItem(item,txn);
    }
    public static ViewItem createTextFieldView(final Item item){
        Cell txn = new Cell<TextField>(new TextField()){
            @Override
            public void commitEdit() {
                super.commitEdit();
                ItemManager.getINSTANCE().setData(this.getItem().getText(), item);
            }
        };
        return new ViewItem(item,txn);
    }
    public static ViewItem createDatePickerView(final Item item){
        Cell txn = new Cell<ODatePicker>(new ODatePicker()){

            @Override
            public void commitEdit() {
                super.commitEdit();
                 ItemManager.getINSTANCE().setData(this.getItem().getValue(),item);
            }
        };
        return new ViewItem(item,txn);
    }
    public static ViewItem createTimePickerView(final Item item){
        Cell txn = new Cell<OTimePicker>(new OTimePicker()){

            @Override
            public void commitEdit() {
                super.commitEdit();
                ItemManager.getINSTANCE().setData(this.getItem().getValue(),item);
            }
        };
        return new ViewItem(item,txn);
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
               
            }

            @Override
            public void startEdit() {
                if(item.haveUnit())
                super.startEdit(); 
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

    public Cell<Node> getData() {
        return data;
    }

    public Cell<TextField> getUnit() {
        return unit;
    }
    public void setGe(boolean bool){
        if(bool)setGeNormal(data);
        else setGeErr(data);
    }
    
    public void setGeErr(HBox ... lb){
        ErrorLabel lab = Project.getInstance().getvProject().getErrorMessage();
        for (HBox label : lb) {
            label.setStyle(Constants.borderStyle6);
            label.setOnMouseEntered(e->{
                lab.setMessage(true,item.getCorrectMessage());
            });
            label.setOnMouseExited(e->{
                lab.setMessage(false,"");
            });
        }
        
    }
    public void setGeNormal(HBox ... lb){
        ErrorLabel lab = Project.getInstance().getvProject().getErrorMessage();
        for (HBox label : lb) {
            label.setStyle(Constants.borderStyle4);
            label.setOnMouseEntered(e->{
                lab.setMessage(false,"");
            });
            label.setOnMouseExited(e->{
                lab.setMessage(false,"");
            });
        }
    }

    @Override
    public void notificate() {
        name.setText(item.toString());
        data.setText(item.getStringValue());
        unit.setText(item.haveUnit()?"":"");
        setGe(item.isCorrect());
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
