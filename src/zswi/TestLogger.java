package zswi;

/**
 *
 * @author DDvory
 */
public class TestLogger {
    public static void print(Class<?> classs, String message) {
        System.out.println(classs.getCanonicalName()+ "_"+classs.getEnclosingMethod()+" : " + message);
    }
}
