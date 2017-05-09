package zswi;


import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author DDvory
 */
public class ConMenu {

    static void setMenu(BorderPane root) {
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(getFileMenu());//, getEditMenu());
        root.setTop(menuBar);
    }

    private static Menu getFileMenu() {
        ImageView imageView = new ImageView(ProjectManager.menu);
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        Menu fileMenu = new Menu("_Soubor");
        fileMenu.setGraphic(imageView);
        MenuItem newItem = new MenuItem("_Nový");
        MenuItem openItem = new MenuItem("_Otevřít");
        MenuItem saveItem = new MenuItem("_Uložit");
        MenuItem saveAsItem = new MenuItem("Uložit _Jako");
        MenuItem importItem = new MenuItem("_Importovat Jazyk");
        MenuItem exportItem = new MenuItem("_Exportovat Jazyk");

        newItem.setOnAction(event -> ProjectManager.createProject());
//        openItem.setOnAction(event -> loadProject());
        //saveItem.setOnAction(event -> );
        //saveAsItem.setOnAction(event -> );
        //importItem.setOnAction(event -> );
        //exportItem.setOnAction(event -> );

        fileMenu.getItems().addAll(
                newItem, openItem, new SeparatorMenuItem(),
                saveItem, saveAsItem, new SeparatorMenuItem(),
                importItem, exportItem);
        return fileMenu;
    }


}
