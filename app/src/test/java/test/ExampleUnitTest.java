package test;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void test() throws Exception {
        int a = 10;
        int b = 10;
        method(a, b);
        System.out.println("a=" + a);
        System.out.println("b=" + b);
    }

    private void method(int a, int b) {
        a = 100;
        b = 200;
        System.out.println("a=" + a);
        System.out.println("b=" + b);
        System.exit(0);
    }
}