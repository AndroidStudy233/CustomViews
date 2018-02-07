package test;

import org.junit.Test;

import java.util.Random;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void test() throws Exception {
        Random random1 = new Random();
        Random random2 = new Random();
        System.out.println(random1.nextInt(8) + 1);
        System.out.println(random2.nextInt(8) + 1);

    }

}