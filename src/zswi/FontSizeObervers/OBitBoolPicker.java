package zswi.FontSizeObervers;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;


public class OBitBoolPicker<T> extends ChoiceBox<T> implements Observer {

    public OBitBoolPicker(boolean bool) {
        this(bool, "");
    }
    
    public OBitBoolPicker(boolean bool, String string){
        FontSize.getINSTANCE().addObserver(this);
        init(bool);
    }
    
    @SuppressWarnings("unchecked")
	private void init(boolean bool){    	
    	if(bool) {
	        this.setItems((ObservableList<T>) FXCollections.observableArrayList(
	        		false, true));
    	} else {
	        this.setItems((ObservableList<T>) FXCollections.observableArrayList(
	        	    0, 1));
    	}
        
        if(this.getSelectionModel().getSelectedItem() == null) {
        	this.getSelectionModel().select(0);
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
    	//
    }
}
