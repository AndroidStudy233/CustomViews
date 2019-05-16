package qwy.anenda.com.baselib.utils;
 /* 
 -----------------------------------------------------------------
 * Author: yzw
 * Create: 2019/3/21
 * Descride:
 * 
 *-----------------------------------------------------------------
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

public class CommonUtils {
    @SuppressLint("MissingPermission")
    public static String getImeiOrMeid(Context ctx) {
        TelephonyManager manager = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (manager != null) {
            return manager.getDeviceId();
        }

        return null;
    }
}
