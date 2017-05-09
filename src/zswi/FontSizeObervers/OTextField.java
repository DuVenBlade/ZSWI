package zswi.FontSizeObervers;


import java.util.Observable;
import java.util.Observer;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

/**
 *
 * @author DDvory
 */
public class OTextField extends TextField implements Observer{

    public OTextField() {
        this("");
    }

    public OTextField(String string) {
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