package zswi;


import zswi.FontSizeObervers.OLabel;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author DDvory
 */
public class ViewTable implements Main.Observabler{
    //rok mesic den ->  local date
    
    private Table table;
    private Label nameLabel;
    private BorderPane viewPane;
    
    public  ViewTable(Table table) {
        this.table =table;
        viewPane = new BorderPane();
        nameLabel = new OLabel();
        init();
        notificate();
    }
    
    public static ViewTable createTable(Table table){
        if(table==null)return null;
        ViewTable tab = new ViewTable(table);
        return tab;
    }
    public static ViewTable createTable(){
        return createTable(AlertManager.Table());
    }
    private void init(){

        BorderPane p = new BorderPane();
        //Button pro upravu tabulky -> spusti alert
        Button bt = new Button("Upravit");
        bt.setOnMouseClicked(e->{
            table.createItem();
        });

        p.setLeft(nameLabel);
        p.setRight(bt);
        viewPane.setTop(p);
    }
    public void addItem(Item it){
        if(it==null)return;
        table.getListItems().add(it);
        notificate();
    }

    @Override
    public void notificate() {
        nameLabel.setText(table.getName());
        boolean show3th = false;
        SplitPane spl = new SplitPane();
        VBox name = new VBox();
        VBox data = new VBox();
        VBox unit = new VBox();
        for (Item item : table.getListItems()) {
            //if(item.isSet3Row())show3th = true;
            ViewItem vItem = item.getvItem().getView();
            name.getChildren().add(vItem.getName());
            data.getChildren().add(vItem.getData());
            unit.getChildren().add(vItem.getUnit());
        }
        spl.getItems().addAll(name,data);
        if(show3th)spl.getItems().add(unit);
        viewPane.setCenter(spl);
        
    }

    @Override
    public Node getView() {
        return viewPane;
    }
    
    
}
