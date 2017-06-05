package zswi;


import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
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
        MenuItem close = new MenuItem("_Zavřít");
        MenuItem openItem = new MenuItem("_Otevřít");
        MenuItem saveItem = new MenuItem("_Uložit");
        MenuItem saveAsItem = new MenuItem("Uložit _Jako");
        CheckMenuItem enumItem = new CheckMenuItem("Nastavit Enu_my");
        enumItem.setSelected(true);
        //------------------------------------------------------------
        newItem.setOnAction(event -> ProjectManager.createProject());
        openItem.setOnAction(e->ProjectManager.loadProject());
        saveItem.setOnAction(e->ProjectManager.saveProejct());
        saveAsItem.setOnAction(e->ProjectManager.saveAs());
        close.setOnAction(e->ProjectManager.closeProject());
        enumItem.setOnAction(event -> {
            if(Project.getInstance()!=null)
            changeEnumButton(enumItem);
            else enumItem.setSelected(true);
                });
        //-----------------------------------------------------------
        fileMenu.getItems().addAll(
                newItem, openItem,close, new SeparatorMenuItem(),
                saveItem, saveAsItem, new SeparatorMenuItem(),
                enumItem);
        return fileMenu;
    }
    private static void changeEnumButton(CheckMenuItem enumItem) {
		if(enumItem.isSelected()) {
			enumItem.setText("Nastavit Enu_my");
			Main.getINSTANCE().getRoot().setCenter(
					(Node)Project.getInstance().getvProject().getView());
		} else {
			enumItem.setText("Nastavit _Projekt");
			EnumView temp =Project.getInstance().getEManager().getvEnum();
			Main.getINSTANCE().getRoot().setCenter(
					(Node) temp.getView());
			temp.notificate();
		}
	}

}
