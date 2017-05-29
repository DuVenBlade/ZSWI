/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zswi;

import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.web.WebEngine;
import org.w3c.dom.Document;

/**
 *
 * @author User
 */
public abstract class WebWorker {

    private WebEngine engine;
    private int loadCount;

    public WebWorker() {
        loadCount=0;
        if (!Platform.isFxApplicationThread()) {
            new JFXPanel();
        }
        Platform.runLater(() -> {
            init(engine = new WebEngine());
        });
    }

    private void init(WebEngine engine) {
        engine.getLoadWorker().workDoneProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, final Number newValue) -> {
            if (newValue.intValue() == 100) {
                onLoadedPage(engine.getDocument());
            } else {
                onLoadingPage(newValue.intValue());
            }
        });

    }

    public void sendAdress(String str) {
        if (str != null) {
            str = getUrl(str);
            if (str == null) {
                return;
            }
        } else {
            return;
        }
        final String s = str;
        loadCount=0;
        Platform.runLater(() -> {
            engine.load(s);
        });
    }

    public int getLoadCount() {
        return loadCount;
    }
    
    private String getUrl(String text) {
        if (Pattern.matches("[a-zA-Z]+:.+", text)) {
            return text ;
        } else return "http://"+text;
    }

    public void stop() {
        com.sun.javafx.application.PlatformImpl.tkExit();
    }
    public void loadContent(String con){
        Platform.runLater(()->engine.loadContent(con));
    }
    public void reload(){
        loadCount++;
        Platform.runLater(()->engine.reload());
    }

    public abstract void onLoadedPage(Document document);

    public abstract void onLoadingPage(int data);
}
