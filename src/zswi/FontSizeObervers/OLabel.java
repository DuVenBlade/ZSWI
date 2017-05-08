package zswi.FontSizeObervers;


import java.util.Observable;
import java.util.Observer;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

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
        FontSize.getINSTANCE().addObserver(this);
        setFontSize(FontSize.getSize());
    }
    
    @Override
    public void update(Observable o, Object arg) {
        this.setFontSize(Integer.decode(arg.toString()));
    }
    private void setFontSize(int size){
        this.setFont(Font.font((double)size));
    }
}
