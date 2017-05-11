package zswi.FontSizeObervers;

import java.util.Observable;
import java.util.Observer;
import javafx.scene.image.ImageView;

/**
 *
 * @author DDvory
 */
public class OImageView extends ImageView implements Observer{
    
    public OImageView() {
        super();
        FontSize.getINSTANCE().addObserver(this);
        setFontSize(FontSize.getSize());
    }

    @Override
    public void update(Observable o, Object arg) {
        setFontSize(Integer.decode(arg+""));
    }

    private void setFontSize(int size) {
        this.setFitWidth(size);
        this.setFitHeight(size);
    }

}
