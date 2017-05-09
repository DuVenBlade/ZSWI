package zswi;


import zswi.FontSizeObervers.OLabel;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
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
    BorderPane pane = new BorderPane();
    
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
        Button bt = new Button("");
        ImageView imageView = new ImageView(ProjectManager.edit);
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        bt.setGraphic(imageView);
        
        bt.setOnMouseClicked(e->{
            table.createItem();
        });

        p.setLeft(nameLabel);
        p.setRight(bt);
        pane.setTop(p);
        pane.setStyle(Constants.borderStyle3);
        viewPane.setCenter(pane);
        viewPane.setStyle(Constants.borderStyle5);
        
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
        pane.setCenter(spl);
        
    }

    @Override
    public Node getView() {
        return viewPane;
    }
    
    
}
