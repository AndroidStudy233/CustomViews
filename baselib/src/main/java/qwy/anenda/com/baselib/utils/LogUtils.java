package qwy.anenda.com.baselib.utils;

import android.text.TextUtils;
import android.util.Log;


import java.util.List;

import qwy.anenda.com.baselib.BuildConfig;


/**
 * 日志打印控制类
 */
public class LogUtils {

    private static LogUtils sLogUtil;
    /**
     * 日志输出时的TAG
     */
    private static String mTag = "jiading_gov";

    /**
     * 是否允许输出log
     */
    private static boolean mDebuggable = BuildConfig.DEBUG;

    /**
     * 用于记时的变量
     */
    private static long mTimestamp = 0;
    /**
     * 写文件的锁对象
     */
    private static final Object mLogLock = new Object();

    //类的初始化，使用单例模式
    public static LogUtils getInstance() {
        if (sLogUtil == null) {
            synchronized (LogUtils.class) {
                if (sLogUtil == null) {
                    sLogUtil = new LogUtils();
                }
            }
        }
        return sLogUtil;
    }

    /**---------------日志输出,已固定TAG  begin---------------**/
    /**
     * 以级别为 d 的形式输出LOG
     */
    public void v(String msg) {
        if (mDebuggable) {
            Log.v(mTag, msg);
        }
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void d(String msg) {
        if (mDebuggable) {
            Log.d(mTag, msg);
        }
    }

    /**
     * 以级别为 i 的形式输出LOG
     */
    public void i(String msg) {
        if (mDebuggable) {
            Log.i(mTag, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG
     */
    public void w(String msg) {
        if (mDebuggable) {
            Log.w(mTag, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出Throwable
     */
    public void w(Throwable tr) {
        if (mDebuggable) {
            Log.w(mTag, "", tr);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG信息和Throwable
     */
    public void w(String msg, Throwable tr) {
        if (mDebuggable && null != msg) {
            Log.w(mTag, msg, tr);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(String msg) {
        if (mDebuggable) {
            if (msg.length() > 3000) {
                for (int i = 0; i < msg.length(); i += 3000) {
                    if (i + 3000 < msg.length()) {
                        Log.e(mTag, msg.substring(i, i + 3000));
                    } else {
                        Log.e(mTag, msg.substring(i, msg.length()));
                    }
                }
            } else {
                Log.e(mTag, msg);
            }
        }
    }

    /**
     * 以级别为 s 的形式输出LOG,主要是为了System.out.println,稍微格式化了一下
     */
    public void sf(String msg) {
        if (mDebuggable) {
            System.out.println("----------" + msg + "----------");
        }
    }

    /**
     * 以级别为 s 的形式输出LOG,主要是为了System.out.println
     */
    public void s(String msg) {
        if (mDebuggable) {
            System.out.println(msg);
        }
    }

    /**
     * 以级别为 e 的形式输出Throwable
     */
    public void e(Throwable tr) {
        if (mDebuggable) {
            Log.e(mTag, "", tr);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG信息和Throwable
     */
    public void e(String msg, Throwable tr) {
        if (mDebuggable && null != msg) {
            Log.e(mTag, msg, tr);
        }
    }

    /**---------------日志输出,已固定TAG  end---------------**/

    /**---------------日志输出,未固定TAG  begin---------------**/
    /**
     * 以级别为 d 的形式输出LOG
     */
    public void v(String tag, String msg) {
        if (mDebuggable) {
            Log.v(tag, msg);
        }
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    public void d(String tag, String msg) {
        if (mDebuggable) {
            Log.d(tag, msg);
        }
    }

    /**
     * 以级别为 i 的形式输出LOG
     */
    public void i(String tag, String msg) {
        if (mDebuggable) {
            Log.i(tag, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG
     */
    public void w(String tag, String msg) {
        if (mDebuggable) {
            Log.w(tag, msg);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public void e(String tag, String msg) {
        if (mDebuggable) {
            Log.e(tag, msg);
        }
    }

    /**---------------日志输出,未固定TAG  end---------------**/

    /**
     * 把Log存储到文件中
     *
     * @param log
     *            需要存储的日志
     * @param path
     *            存储路径
     */
//	public void log2File(String log, String path) {
//		log2File(log, path, true);
//	}
//
//	public static void log2File(String log, String path, boolean append) {
//		synchronized (mLogLock) {
//			FileUtils.getInstance().writeFile(log + "\r\n", path, append);
//		}
//	}

    /**
     * 以级别为 e 的形式输出msg信息,附带时间戳，用于输出一个时间段起始点
     *
     * @param msg 需要输出的msg
     */
    public void msgStartTime(String msg) {
        mTimestamp = System.currentTimeMillis();
        if (!TextUtils.isEmpty(msg)) {
            e("[Started：" + mTimestamp + "]" + msg);
        }
    }

    /**
     * 以级别为 e 的形式输出msg信息,附带时间戳，用于输出一个时间段结束点* @param msg 需要输出的msg
     */
    public void elapsed(String msg) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - mTimestamp;
        mTimestamp = currentTime;
        e("[Elapsed：" + elapsedTime + "]" + msg);
    }

    public <T> void printList(List<T> list) {
        if (list == null || list.size() < 1) {
            return;
        }
        int size = list.size();
        i("---begin---");
        for (int i = 0; i < size; i++) {
            i(i + ":" + list.get(i).toString());
        }
        i("---end---");
    }

    public <T> void printArray(T[] array) {
        if (array == null || array.length < 1) {
            return;
        }
        int length = array.length;
        i("---begin---");
        for (int i = 0; i < length; i++) {
            i(i + ":" + array[i].toString());
        }
        i("---end---");
    }
}
