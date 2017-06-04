package zswi;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import zswi.res.ResManager;

/**
 *
 * @author DDvory
 */
public class LanguageManager extends WebWorker {
    private static List<Language> listLanguages;
    private static final String ID_NAME = "result_box";
    private static final String ADRESS = "https://translate.google.com";
    private List<ToTranslate> listToTranslate;
    private TranslateThread thread;

    static {
        List<String> textFile = ResManager.getTextFile("lang.txt");
        listLanguages = new ArrayList<>();
        for (String string : textFile) {
            String[] split = string.split(" ");
            listLanguages.add(new Language(Integer.decode(split[0]), split[1], split[2]));
        }
    }

    public LanguageManager() {
        listToTranslate = new ArrayList();
        thread = new TranslateThread(this);
    }

    public static String getIso(int id) {
        return listLanguages.get(id).getIso();
    }

    public static String getName(int id) {
        return listLanguages.get(id).getName();
    }

    public static List<Language> getListLanguages() {
        return listLanguages;
    }

    public void translate(AFlowable aFlowable, int langFrom, int langTo) {
        translate(aFlowable, listLanguages.get(langFrom), listLanguages.get(langTo));
    }

    public void translate(AFlowable aFlowable, Language from, Language to) {
        listToTranslate.add(new ToTranslate(aFlowable, from, to));
        thread.start();

    }

    @Override
    public void onLoadedPage(Document document) {
        Element element = document.getElementById("dfgjhnj");
        if (element == null) {
            String str = document.getElementById(ID_NAME).getTextContent().trim();
            if (str.isEmpty() && this.getLoadCount() < 3) {
                this.reload();
                return;
            }
            ToTranslate get = listToTranslate.get(0);
            get.getaFlowable1().setName(new Name(get.getTo().getId(), str));
        }
        thread.update();
    }

    @Override
    public void onLoadingPage(int data) {
    }

    public static class Language extends ASaveable {
        final int id;
        final String iso;
        final String name;

        public Language(int id, String iso, String name) {
            this.id = id;
            this.iso = iso;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getIso() {
            return iso;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public Element createElementToSave(Document document) {
            Element element = super.createElementToSave(document);
            element.setAttribute(Constants.id, id+"");
            return element;
        }
        
    }

    private class ToTranslate {

        private final AFlowable aFlowable1;
        private final Language from;
        private final Language to;

        public ToTranslate(AFlowable aFlowable1, Language from, Language to) {
            this.aFlowable1 = aFlowable1;
            this.from = from;
            this.to = to;
        }

        public AFlowable getaFlowable1() {
            return aFlowable1;
        }

        public Language getFrom() {
            return from;
        }

        public Language getTo() {
            return to;
        }
    }

    private static class TranslateThread implements Runnable {

        private LanguageManager lang;
        boolean isRunning;

        public TranslateThread(LanguageManager lang) {
            this.lang = lang;
            isRunning = false;
        }

        @Override
        public void run() {
            ToTranslate get = null;
            while (!lang.listToTranslate.isEmpty()) {
                get = lang.listToTranslate.get(0);
                String name = get.getaFlowable1().getName(get.getFrom().getId());
                if (!name.trim().isEmpty()) {
                    String adres = ADRESS + "/#" + get.getFrom().getIso() + "/" + get.getTo().getIso() + "/" + name;
                    lang.sendAdress(adres);
                    pause();
                }
                lang.listToTranslate.remove(0);
                lang.loadContent("<span id=\"dfgjhnj\"></span>");
                pause();
            }
            if (get != null) {
                ProjectManager.changeLanguage(get.to.id);
            }
            isRunning = false;
        }

        private synchronized void pause() {
            try {
                this.wait();
            } catch (InterruptedException ex) {
            }
        }

        public synchronized void update() {
            notify();
        }

        public void start() {
            if (!isRunning) {
                isRunning = true;
                Thread t = new Thread(this);
                t.setDaemon(true);
                t.start();
            }
        }
    }
}
