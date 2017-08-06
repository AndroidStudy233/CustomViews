package test;

import org.junit.Test;

import java.io.File;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void test() throws Exception {
        File origin = new File("D:\\Game\\地下城与勇士\\SoungdPacks");
        File[] files = origin.listFiles();
        for (int i = 0; i < files.length; i++) {
            String originName = files[i].getName();
            String newName = originName.replace(" - 副本", "");
            File newFile = new File("D:\\Game\\地下城与勇士\\SoungdPacks", newName);
            boolean result = files[i].renameTo(newFile);
            System.out.println(result);
        }
    }

}