package com.shiqkuangsan.rxandroidmvp.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.shiqkuangsan.rxandroidmvp.MyLogUtil;
import com.shiqkuangsan.rxandroidmvp.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;

/*************************************************
 * <p>类描述：rxjava操作符</p>
 * <p>创建人：余志伟</p>
 * <p>创建时间 : 2017/4/26</p>
 * <p>修改人：       </p>
 * <p>修改时间：   </p>
 * <p>修改备注：   </p>
 *
 * @version V3.1
 *********************************/
public class RxJavaOperatorActivity extends AppCompatActivity {

    @BindView(R.id.btn_rxoperator_timer)
    Button btn_timer;
    @BindView(R.id.btn_rxoperator_interval)
    Button btn_interval;
    @BindView(R.id.btn_rxoperator_range)
    Button btn_range;
    @BindView(R.id.btn_rxoperator_concat)
    Button btn_concat;
    @BindView(R.id.btn_rxoperator_startWith)
    Button btn_startWith;
    @BindView(R.id.btn_rxoperator_zip)
    Button btn_zip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjavaoperator);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_rxoperator_timer, R.id.btn_rxoperator_interval, R.id.btn_rxoperator_range,
            R.id.btn_rxoperator_concat, R.id.btn_rxoperator_startWith, R.id.btn_rxoperator_zip})
    public void bindEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_rxoperator_timer:
                timer();
                break;

            case R.id.btn_rxoperator_interval:
                interval();
                break;

            case R.id.btn_rxoperator_range:
                range();
                break;

            case R.id.btn_rxoperator_concat:
                concat();
                break;

            case R.id.btn_rxoperator_startWith:
                startWith();
                break;

            case R.id.btn_rxoperator_zip:
                zip();
                break;

        }
    }

    /**
     * @return
     * @desc: timer： 创建一个在给定的延时之后发射数据项为0的Observable<Long>,内部通过OnSubscribeTimerOnce工作
     * @author: yzw
     * @modify: 2017/4/26 15:45
     * @param:
     **/
    public void timer() {
        Observable.timer(1000, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("JG", aLong.toString()); // 0
            }
        });
    }

    /**
     * @return
     * @desc: interval： 创建一个按照给定的时间间隔发射从0开始的整数序列的Observable<Long>，内部通过OnSubscribeTimerPeriodically工作。
     * @author: yzw
     * @modify: 2017/4/26 15:48
     * @param: interval:间隔时间
     * period:周期
     **/
    public void interval() {
        Observable.interval(2000, 10000, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("JG", aLong.toString());
            }
        });
    }

    /**
     * @return
     * @desc: range： 创建一个发射指定范围的整数序列的Observable<Integer>
     * @author: yzw
     * @modify: 2017/4/26 15:56
     * @param:
     **/
    public void range() {
        Observable.range(2, 5).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("JG", integer.toString());// 2,3,4,5,6 从2开始发射5个数据
            }
        });
    }

    /**
     * @return
     * @desc: concat： 按顺序连接多个Observables。需要注意的是Observable.concat(a,b)等价于a.concatWith(b)。
     * @author: yzw
     * @modify: 2017/4/26 16:08
     * @param:
     **/
    public void concat() {
        Observable<Integer> observable = Observable.just(1, 2, 3);
        Observable<Integer> observable1 = Observable.just(4, 5, 6);
        Observable.concat(observable, observable1).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("JG", integer.toString());// 1,2,3,4,5,6 
            }
        });
    }

    /**
     * @return
     * @desc: startWith： 在数据序列的开头增加一项数据。startWith的内部也是调用了concat
     * @author: yzw
     * @modify: 2017/4/26 16:13
     * @param:
     **/
    public void startWith() {
        Observable<Integer> observable = Observable.just(1, 2, 3);
        observable.startWith(4, 5, 6).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("JG", integer.toString());// 4,5,6,1,2,3
            }
        });
    }

    /**
     * @return
     * @desc: 使用一个函数组合多个Observable发射的数据集合，然后再发射这个结果。如果多个Observable发射的数据量不一样
     * ，则以最少的Observable为标准进行压合。内部通过OperatorZip进行压合。
     * @author: yzw
     * @modify: 2017/4/26 16:27
     * @param:
     **/
    public void zip() {
        Observable<Integer> observable = Observable.just(1, 2, 3);
        Observable<Integer> observable1 = Observable.just(4, 5, 6, 7);
        Observable.zip(observable, observable1, new Func2<Integer, Integer, String>() {
            @Override
            public String call(Integer integer, Integer integer2) {
                return integer + " >> " + integer2;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                MyLogUtil.d("zip: " + s);
            }
        });
    }
}
