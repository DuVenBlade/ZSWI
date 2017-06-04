package zswi.FontSizeObervers;

import java.util.Observable;
import java.util.Observer;
import javafx.scene.image.ImageView;
import zswi.Project;
import zswi.ProjectManager;

/**
 *
 * @author DDvory
 */
public class OImageView extends ImageView implements Observer{
    
    public OImageView() {
        super();
        FontSize size = ProjectManager.getFont();
        size.addObserver(this);
        setFontSize(size.getSize());
    }

    @Override
    public void update(Observable o, Object arg) {
        setFontSize(Integer.decode(arg+""));
    }

    private void setFontSize(int size) {
        this.setFitWidth(size*1.2);
        this.setFitHeight(size*1.2);
    }

}
