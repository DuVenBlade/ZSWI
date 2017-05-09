package zswi;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import zswi.res.ResManager;

/**
 *
 * @author DDvory
 */
public class LanguageManager {
    private static List<Language> listLanguages;
    static{
        List<String> textFile = ResManager.getTextFile("lang.txt");
        listLanguages = new ArrayList<>();
        for (String string : textFile) {
            String[] split = string.split(" ");
            listLanguages.add(new Language(Integer.decode(split[0]),split[1],split[2]));
        }
    }
    
    public static String getIso(int id){
        return listLanguages.get(id).getIso();
    }
    
    public static String getName(int id){
        return listLanguages.get(id).getName();
    }
    
    public static String translate(int from, int to, String tmp){
        return null;
    }

    public static List<Language> getListLanguages() {
        return listLanguages;
    }
    
    public static class Language{
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
        
    }
    
}
