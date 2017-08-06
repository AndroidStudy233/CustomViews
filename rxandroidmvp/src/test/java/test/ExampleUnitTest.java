package test;

import com.shiqkuangsan.rxandroidmvp.review2.Test2;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.x;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

        String parentPath = "";

        File origin = new File(parentPath);
        File[] files = origin.listFiles();
        for (int i = 0; i < files.length; i++) {
            String originName = files[i].getName();

            String newName = originName.replace(" - 副本", "");

            File newFile = new File(parentPath, newName);
            boolean result = files[i].renameTo(newFile);
            System.out.println(result);
        }
    }
}