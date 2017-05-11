package zswi.FontSizeObervers;

import zswi.Main;
import java.time.LocalTime;
import java.util.Observable;
import java.util.Observer;
import javafx.beans.InvalidationListener;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import zswi.AlertManager;
import zswi.ProjectManager;
import zswi.res.ResManager;

/**
 *
 * @author DDvory
 */
public class OTimePicker extends BorderPane implements Observer{
    private static Image time = ResManager.getImage("time");
    private TextField editor;
    private LocalTime value;
    private Button bt;

    public OTimePicker() {
        this(0,0,0);
    }
    
    public OTimePicker(int h, int min, int sec){
        this(LocalTime.of(h, min,sec));
    }
    

    public OTimePicker(LocalTime time) {
        this.value = time;
        init();
        
    }

    public LocalTime getValue() {
        return value;
    }

    private void init(){
        editor = new TextField();
        bt = new Button("");
        
        ImageView imageView = new ImageView(time);
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        bt.setGraphic(imageView);
        
        bt.setOnMouseClicked(e->{
            LocalTime time = AlertManager.Time(value);
            if(time!=null)value = time;
            editor.setText(value.toString());
        });
        editor.setOnKeyReleased(e->{
            String text = editor.getText();
            try {
                String[] split = text.split(":");
                int[] arr = new int[split.length];
                for (int i = 0; i < split.length; i++) {
                    arr[i]=Integer.decode(split[i]);
                }
                LocalTime time;
                switch(arr.length){
                    case 4:
                        time = LocalTime.of(arr[0], arr[1], arr[2], arr[3]);
                        break;
                    case 3:
                        time = LocalTime.of(arr[0], arr[1], arr[2]);
                        break;
                    case 2:
                        time = LocalTime.of(arr[0], arr[1]);
                        break;
                    case 1:
                        time = LocalTime.of(arr[0],0);
                        break;
                    default:
                        throw new IllegalArgumentException();
                        
                }value = time;
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
        });
        this.setCenter(editor);
        this.setRight(bt);
    }
    @Override
    public void update(Observable o, Object arg) {
        setFont(Integer.decode(arg.toString()));
    }
    private void setFont(int size){
        this.editor.setFont(Font.font((double)size));
    }


}
