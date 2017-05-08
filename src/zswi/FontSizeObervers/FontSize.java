package zswi.FontSizeObervers;


import java.util.Observable;

/**
 *
 * @author DDvory
 */
public class FontSize extends Observable{
    private static FontSize INSTANCE;

    private FontSize() {
    }
    
    

    public static FontSize getINSTANCE() {
        if(INSTANCE == null) INSTANCE = new FontSize();
        return INSTANCE;
    }
    
    private static int size = 12;

    public void setSize(int size) {
        FontSize.size = size;
        this.setChanged();
        this.notifyObservers(size);
    }

    public static int getSize() {
        return size;
    }
    
}
