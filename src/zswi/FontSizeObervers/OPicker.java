package zswi.FontSizeObervers;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import zswi.Project;
import zswi.ProjectManager;


public class OPicker extends ChoiceBox implements Observer {
    
    public OPicker(Object... array){
        FontSize size = ProjectManager.getFont();
        size.addObserver(this);
        //setFontSize(size.getSize());
        init(array);
    }
    
	private void init(Object[] array){    	
	this.setItems((ObservableList) FXCollections.observableArrayList(array));
        if(this.getSelectionModel().getSelectedItem() == null) {
        	this.getSelectionModel().select(0);
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
    	//
    }
}
