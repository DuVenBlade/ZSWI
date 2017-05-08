package zswi.res;


import javafx.scene.image.Image;

/**
 *
 * @author DDvory
 */
public class ResManager {
    private static String defaultAdress ="/zswi/res/";
    public static Image getImage(String name) {
        Image img = null;
        try {
            img = new Image(ResManager.class.getResourceAsStream(defaultAdress+name+".jpg"));
        } catch (Exception e) {
        }
        return img;
    }
}
