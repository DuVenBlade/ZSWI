package zswi;


import zswi.FontSizeObervers.OLabel;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author DDvory
 */
public class ViewPanel implements Main.Observabler {

    private Panel panel;
    private Label panelLabel;
    private ScrollPane view;
    private BorderPane pane;

    public ViewPanel(Panel panel) {
        this.panel = panel;
        view = new ScrollPane();
        view.setFitToWidth(true);
        pane = new BorderPane();
        panelLabel = new OLabel();
        init();
        notificate();
    }

    private void init() {
        BorderPane topPanel = new BorderPane();
        pane.setTop(topPanel);
        topPanel.setLeft(panelLabel);
        //Button pro vytvoreni nove tabulky
        Button bt = new Button("");
        ImageView imageView = new ImageView(ProjectManager.edit);
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        bt.setGraphic(imageView);
        bt.setOnAction(e -> {
            panel.createTable();
        });
        topPanel.setRight(bt);
        pane.setCenter(view);
        pane.setStyle(Constants.borderStyle2);
    }

    public Panel getPanel() {
        return panel;
    }

    @Override
    public void notificate() {
        VBox vBox = new VBox();
        for (Table table : panel.getListTables()) {
            vBox.getChildren().add(table.getvTable().getView());
        }view.setContent(vBox);
    }

    @Override
    public BorderPane getView() {
        return pane;
    }

    void setName(String name) {
        panelLabel.setText(name);
    }

}
