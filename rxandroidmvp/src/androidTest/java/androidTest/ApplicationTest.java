package androidTest;

import android.annotation.SuppressLint;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest {
    @Test
    public void useAppContext() {

        testFlowable(new String[]{"hello", ",", "world"});
    }


    /**
     * RxJava的并行处理
     *
     * @param strList 一个事件的集合
     */
    @SuppressLint("CheckResult")
    public void testFlowable(String[] strList) {
        //把这些事件发射出去
        //这里是把每一个字符串当成一个事件
        Flowable.fromArray(strList)

                //并行执行
                .parallel()

                //为他们设置执行的线程
                //把耗时操作放在别的线程
                .runOn(Schedulers.computation())

                //将每一个事件拆分成更小的事件
                //这里是吧string拆分成单个字符，然后把每个字符当成一个事件
                .flatMap(s -> {
                    Log.i("wtf_wtf_subscribe", Thread.currentThread().getName());
                    return Flowable.fromArray(s.split(""));
                })

                //对每一个事件执行相同的操作
                //这里是把字符都转成大写
                .map(String::toUpperCase)

                //需要并行的操作执行完之后，这里恢复成串行来处理结果
                //是的 使用了这个方法后才能处理结果
                //不保证和之前的顺序相同
                .sequential()

                //观察者在主线程运行
                //处理结果回到主线程来执行
                .observeOn(AndroidSchedulers.mainThread())

                //事件结束 通知观察者
                //事件处理完成，得到结果
                .subscribe(s -> {
                    Log.i("wtf_wtf_subscribe", Thread.currentThread().getName());
                    Log.i("wtf_wtf", s);
                });
    }
}
