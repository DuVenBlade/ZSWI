package zswi.FontSizeObervers;


import java.util.Observable;
import java.util.Observer;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import zswi.Project;
import zswi.ProjectManager;

/**
 *
 * @author DDvory
 */
public class OLabel extends Label implements Observer{

    public OLabel() {
        this("");
    }

    public OLabel(String string) {
        super(string);
        FontSize size = ProjectManager.getFont();
        size.addObserver(this);
        setFontSize(size.getSize());
    }
    
    @Override
    public void update(Observable o, Object arg) {
        this.setFontSize(Integer.decode(arg.toString()));
    }
    private void setFontSize(int size){
        this.setFont(Font.font((double)size));
    }
}
