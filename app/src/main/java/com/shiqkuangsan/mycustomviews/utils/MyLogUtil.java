package com.shiqkuangsan.mycustomviews.utils;

import android.util.Log;

/**
 * Created by shiqkuangsan on 2016/5/4.
 * <p>
 * ClassName: CLogUtil
 * Author: shiqkuangsan
 * Description: 日志输出的util
 */
public class MyLogUtil {

	/**
	 * 日志输出级别NONE
	 */
	private static final int LEVEL_NONE = 0;
	/**
	 * 日志输出级别E
	 */
	private static final int LEVEL_ERROR = 1;
	/**
	 * 日志输出级别W
	 */
	private static final int LEVEL_WARN = 2;
	/**
	 * 日志输出级别I
	 */
	private static final int LEVEL_INFO = 3;
	/**
	 * 日志输出级别D
	 */
	private static final int LEVEL_DEBUG = 4;
	/**
	 * 日志输出级别V
	 */
	private static final int LEVEL_VERBOSE = 5;

	/**
	 * 日志输出时的TAG
	 */
	private static String mTag = "MyLogUtil";

	/**
	 * 是否允许输出log,该值控制着是否允许打印log
	 */
	private static int mDebuggable = LEVEL_VERBOSE;

	/**
	 * 以级别为 verbose 的形式输出LOG
	 */
	public static void verbose(String msg) {
		if (mDebuggable >= LEVEL_VERBOSE) {
			Log.v(mTag, msg);
		}
	}

	/**
	 * 以级别为 verbose 的形式输出LOG, 自定义TAG
	 */
	public static void verbose(String tag, String msg) {
		if (mDebuggable >= LEVEL_VERBOSE) {
			Log.v(tag, msg);
		}
	}

	/**
	 * 以级别为 debug 的形式输出LOG
	 */
	public static void debug(String msg) {
		if (mDebuggable >= LEVEL_DEBUG) {
			Log.d(mTag, msg);
		}
	}

	/**
	 * 以级别为 debug 的形式输出LOG, 自定义TAG
	 */
	public static void debug(String tag, String msg) {
		if (mDebuggable >= LEVEL_DEBUG) {
			Log.d(tag, msg);
		}
	}

	/**
	 * 以级别为 info 的形式输出LOG
	 */
	public static void info(String msg) {
		if (mDebuggable >= LEVEL_INFO) {
			Log.i(mTag, msg);
		}
	}

	/**
	 * 以级别为 info 的形式输出LOG, 自定义TAG
	 */
	public static void info(String tag, String msg) {
		if (mDebuggable >= LEVEL_INFO) {
			Log.i(tag, msg);
		}
	}

	/**
	 * 以级别为 warn 的形式输出LOG
	 */
	public static void warn(String msg) {
		if (mDebuggable >= LEVEL_WARN) {
			Log.w(mTag, msg);
		}
	}

	/**
	 * 以级别为 warn 的形式输出LOG, 自定义TAG
	 */
	public static void warn(String tag, String msg) {
		if (mDebuggable >= LEVEL_WARN) {
			Log.w(tag, msg);
		}
	}

	/**
	 * 以级别为 warn 的形式输出Throwable
	 */
	public static void warn(Throwable tr) {
		if (mDebuggable >= LEVEL_WARN) {
			Log.w(mTag, "", tr);
		}
	}

	/**
	 * 以级别为 warn 的形式输出LOG信息和Throwable
	 */
	public static void warn(String msg, Throwable tr) {
		if (mDebuggable >= LEVEL_WARN && null != msg) {
			Log.w(mTag, msg, tr);
		}
	}

	/**
	 * 以级别为 error 的形式输出LOG
	 */
	public static void error(String msg) {
		if (mDebuggable >= LEVEL_ERROR) {
			Log.e(mTag, msg);
		}
	}

	/**
	 * 以级别为 error 的形式输出LOG, 自定义TAG
	 */
	public static void error(String tag, String msg) {
		if (mDebuggable >= LEVEL_ERROR) {
			Log.e(tag, msg);
		}
	}

	/**
	 * 以级别为 error 的形式输出Throwable
	 */
	public static void error(Throwable tr) {
		if (mDebuggable >= LEVEL_ERROR) {
			Log.e(mTag, "", tr);
		}
	}

	/**
	 * 以级别为 error 的形式输出LOG信息和Throwable
	 */
	public static void error(String msg, Throwable tr) {
		if (mDebuggable >= LEVEL_ERROR && null != msg) {
			Log.e(mTag, msg, tr);
		}
	}
}
