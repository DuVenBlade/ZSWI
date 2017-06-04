package zswi.FontSizeObervers;


import java.util.Observable;

/**
 *
 * @author DDvory
 */
public class FontSize extends Observable{
    private  int size;
    private FontSize() {
        this(12);
    }
    public  FontSize(int size){
        this.size =size;
    }

    public void setSize(int size) {
        if(size>50||size<10)return;
        this.size = size;
        this.setChanged();
        this.notifyObservers(size);
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return size+"";
    }
    
    
}
