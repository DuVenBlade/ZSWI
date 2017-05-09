package zswi.FontSizeObervers;

import java.util.Observable;
import java.util.Observer;

import zswi.Main;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class OEnumPicker extends HBox implements Observer {
	private ChoiceBox<Enum> ID;
	private ChoiceBox<Enum> subID;

    public OEnumPicker() {
        this("");
    }
    
    public OEnumPicker(String string){
        FontSize.getINSTANCE().addObserver(this);
        init();
    }
    
    private void init(){
    	ID = new ChoiceBox<Enum>();
    	subID = new ChoiceBox<Enum>();
    	
    	ID.getSelectionModel().selectedIndexProperty().addListener(event -> {
    		subID.setItems(null);
    		subID.getSelectionModel().select(0);
    	});

    	ID.setItems(null);
    	if(ID.getSelectionModel().getSelectedItem() == null) {
    		ID.getSelectionModel().select(0);
        }
    	
        this.getChildren().addAll(ID, subID);
    }
    
    @Override
    public void update(Observable o, Object arg) {
    	setFontSize(Integer.decode(arg.toString()));
    }
    
    private void setFontSize(int size){
        
    }

	public String getValue() {
		return ID.getSelectionModel().getSelectedItem().name()
				+ " " + subID.getSelectionModel().getSelectedItem().name();
	}
}
