
package cn.edu.ncwu.www.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import cn.edu.ncwu.www.MPreferenceActivity;
import cn.edu.ncwu.www.tools.FileUtil;

public class ReStoreStateReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    private SharedPreferences sharedPreference;

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        MPreferenceActivity.restore(arg0,MPreferenceActivity.PROC_GLOVE_MODE,MPreferenceActivity.GLOVE_MODE_KEY) ;
        Log.d("advanceSettings", MPreferenceActivity.PROC_GLOVE_MODE) ;
        MPreferenceActivity.restore(arg0,MPreferenceActivity.PROC_TOUCH_GESTURE,MPreferenceActivity.TOUCH_GESTURE_KEY) ;
        Log.d("advanceSettings", MPreferenceActivity.PROC_TOUCH_GESTURE) ;
  /*      if (arg1.getAction().equals(ACTION)) {
            sharedPreference = arg0.getSharedPreferences(MPreferenceActivity.sharedPreferenceName,
                    Context.MODE_PRIVATE);
            if (sharedPreference.getBoolean("touch_gesture", false))
                FileUtil.writeValue("/proc/touchscreen_gesture_enable", "1");
            else
                FileUtil.writeValue("/proc/touchscreen_gesture_enable", "0");
        }*/

    }

}
