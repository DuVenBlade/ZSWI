package zswi.res;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import zswi.TestLogger;

/**
 *
 * @author DDvory
 */
public class ResManager {

    private static String defaultAdress = "/zswi/res/";

    public static Image getImage(String name) {
        Image img = null;
        try {
            img = new Image(ResManager.class.getResourceAsStream(defaultAdress + name + ".png"));
        } catch (Exception e) {
        }
        return img;
    }

    public static List<String> getTextFile(String name) {
        List<String> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(ResManager.class.getResourceAsStream(defaultAdress + name)));
            String read = "";
            while ((read = reader.readLine()) != null) {
                list.add(read);
            }
        } catch (Exception e) {
        }
        return list;
    }
}
