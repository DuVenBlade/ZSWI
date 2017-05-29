package zswi;


import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{
    private static Main INSTANCE;
    private BorderPane root;
    private Stage stage;
    public static void main(String[] args) {
        
        Main.launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        INSTANCE = this;
        this.stage=stage;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        stage.setScene(new Scene(root = new BorderPane()));
        root.setPrefSize(screenSize.width/2, screenSize.height/2);
        ConMenu.setMenu(root);
        root.setStyle(Constants.borderStyle1);
        stage.show();
    }
    
    public void clearPanel(){
        root.setCenter(null);
    }

    public BorderPane getRoot() {
        return root;
    }

    public Stage getStage() {
        return stage;
    }
    
    public static Main getINSTANCE() {
        return INSTANCE;
    }
    
     public static interface Observabler {
        public void notificate();
        public Object getView();
    }
}
