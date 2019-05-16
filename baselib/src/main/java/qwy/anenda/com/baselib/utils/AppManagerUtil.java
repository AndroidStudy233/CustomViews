package qwy.anenda.com.baselib.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Activity管理类：用于管理Activity和退出程序
 */
public class AppManagerUtil {

    /**
     * 堆栈
     */
    private Stack<Activity> activityStack;
    /**
     * Activity管理类对象
     */
    private static AppManagerUtil instance;

    private AppManagerUtil() {
    }

    /**
     * 单一实例：获取Activity管理类：用于管理Activity和退出程序
     *
     * @return Activity管理类对象
     */
    public static AppManagerUtil getAppManager() {
        if (instance == null) {
            synchronized (AppManagerUtil.class){
                    if(instance == null){
                        instance = new AppManagerUtil();
                    }
                }
            }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     *
     * @param activity Activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 移除堆栈Activity
     */
    public void removeActivity(Class<?> cls) {
        try {
            if (null != activityStack && activityStack.size() >= 1) {
                for (Activity activity : activityStack) {
                    if (activity.getClass().equals(cls)) {
                        activityStack.remove(activity);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定类名 Activity 是否存在棧内
     *
     * @param cls 类名
     */
    public boolean isExist(Class<?> cls) {
        Stack<Activity> activityStack1 = (Stack<Activity>) activityStack.clone();
        for (Activity activity : activityStack1) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     *
     * @return 当前Activity
     */
    public Activity currentActivity() {
        if(activityStack != null){
            return activityStack.lastElement();
        }
        return null;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        finishActivity(currentActivity());
    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls 类名
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }


    /**
     * 结束 除指定类名外的 所有Activity
     *
     * @param cls 类名
     */
    public void finishOtherActivity(Class<?> cls) {
        try {
            Stack<Activity> activityStack1 = (Stack<Activity>) activityStack.clone();
            for (Activity activity : activityStack1) {
                if (!activity.getClass().equals(cls)) {
                    finishActivity(activity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束 除指定类名外的 所有Activity
     */
    public void finishOtherActivity() {
        Activity currentActivity = currentActivity();
        finishOtherActivity(currentActivity.getClass());
    }

    //    /**
    //     * 退出应用程序
    //     * 
    //     */
    //    public void exitApp(Context context) {
    //        try {
    //            finishAllActivity();
    //            ActivityManager activityMgr = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
    //            activityMgr.killBackgroundProcesses(context.getPackageName());
    //            System.exit(0);
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        Stack<Activity> activityStack1 = (Stack<Activity>) activityStack.clone();
        for (int i = 0; i < activityStack1.size(); i++) {
            if (null != activityStack1.get(i)) {
                activityStack1.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束指定的Activity
     *
     * @param activity 指定的Activity
     */
    private void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            //IntentUtil.finish(activity);
            activity.finish();
        }
    }

    /**
     * 退出应用程序
     */
    public void exitApp(Context context) {
        // 获取Activity管理器
        ActivityManager activityManger = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        // 从窗口管理器中获取正在运行的Service
        List<ActivityManager.RunningServiceInfo> serviceList = activityManger.getRunningServices(100);
        // 当前app所有服务的类名
        ArrayList<String> service = getServicesName(serviceList);
        for (String serviceName : service) {
            LogUtils.e(serviceName);
            try {
                context.stopService(new Intent(context, Class.forName(serviceName)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        finishAllActivity();
        // 退出应用程序
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 获取当前app所有服务的名称
     *
     * @param list List<ActivityManager.RunningServiceInfo>
     * @return 当前app所有服务的名称
     */
    private ArrayList<String> getServicesName(List<ActivityManager.RunningServiceInfo> list) {//
        ArrayList<String> service = new ArrayList<>();
        for (ActivityManager.RunningServiceInfo runServiceInfo : list) {
            // 获得Service所在的进程的信息
            // service所在的进程ID号
            int pid = runServiceInfo.pid;
            if (pid == android.os.Process.myPid()) {
                service.add(runServiceInfo.service.getClassName());
                //                int uid = runServiceInfo.uid; // 用户ID 类似于Linux的权限不同，ID也就不同 比如 root等
                //                // 进程名，默认是包名或者由属性android：process指定
                //                String processName = runServiceInfo.process;
                //                // 该Service启动时的时间值
                //                long activeSince = runServiceInfo.activeSince;
                //                // 如果该Service是通过Bind方法方式连接，则clientCount代表了service连接客户端的数目
                //                int clientCount = runServiceInfo.clientCount;
                //                // 获得该Service的组件信息 可能是pkgname/servicename
                //                ComponentName serviceCMP = runServiceInfo.service;
                //                // service 的类名
                //                String serviceName = serviceCMP.getShortClassName();
                //                // 包名
                //                String pkgName = serviceCMP.getPackageName();
                //if (pkgName.equals(TApplication.getInstance().getPackageName())) {
                //service.add(runServiceInfo.service.getClassName());
                //LogUtil.i(runServiceInfo.service.getClassName() + "...." + pkgName + "..." + TApplication.getInstance().getPackageName());
                //}
            }
        }
        return service;
    }

    /**
     * 获取launcher activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @return launcher activity
     */
    public static String getLauncherActivity(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo info : infos) {
            if (info.activityInfo.packageName.equals(packageName)) {
                return info.activityInfo.name;
            }
        }
        return "no " + packageName;
    }

}