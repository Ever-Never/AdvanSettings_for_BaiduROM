
package cn.edu.ncwu.www;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.UserHandle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.view.MenuItem;
import android.widget.Toast;

import cn.edu.ncwu.www.tools.FileUtil;
import cn.edu.ncwu.www.tools.RootCmd;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class MPreferenceActivity extends PreferenceActivity {

    private PreferenceScreen recovery;
    private PreferenceScreen replaceRecovery;
    private CheckBoxPreference touchGesture;
    private CheckBoxPreference gloveMode;
    private PreferenceScreen flashOta;
    private static final int OPEN_FILE = 0;
    private static final int FLASH_OTA = 1;
    private File file;
    private SharedPreferences sp;
    private Editor editor;
    public static String GLOVE_MODE_KEY = "glove_mode";
    public static String TOUCH_GESTURE_KEY = "touch_gesture";
    public static String PROC_TOUCH_GESTURE = "/proc/touchscreen_gesture_enable";
    public static String PROC_GLOVE_MODE = "/proc/gloved_finger_switch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
     
//        getActionBar().setDisplayShowTitleEnabled(false);
//        getActionBar().setDisplayShowHomeEnabled(false);
        addPreferencesFromResource(R.xml.preferences);
        flashOta = (PreferenceScreen) findPreference("flashOta");
        recovery = (PreferenceScreen) findPreference("recovery");
        replaceRecovery = (PreferenceScreen) findPreference("replaceRecovery");
        touchGesture = (CheckBoxPreference) findPreference("touch_gesture");
        gloveMode = (CheckBoxPreference) findPreference("glovemode");
             
        gloveMode.setOnPreferenceClickListener(new OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference arg0) {
                // TODO Auto-generated method stub
                if (((CheckBoxPreference) arg0).isChecked()) {
                    FileUtil.writeValue("/proc/gloved_finger_switch", "1");
                    editor.putBoolean(GLOVE_MODE_KEY, true);
                }
                else
                {
                    FileUtil.writeValue("/proc/gloved_finger_switch", "0");
                    editor.putBoolean(GLOVE_MODE_KEY, false);
                }
                editor.commit();
                return false;
            }
        });

        touchGesture.setOnPreferenceClickListener(new OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference arg0) {
                // TODO Auto-generated method stub
                if (((CheckBoxPreference) arg0).isChecked()) {
                    FileUtil.writeValue("/proc/touchscreen_gesture_enable", "1");
                    editor.putBoolean(TOUCH_GESTURE_KEY, true);
                }
                else
                {
                    FileUtil.writeValue("/proc/touchscreen_gesture_enable", "0");
                    editor.putBoolean(TOUCH_GESTURE_KEY, false);
                }
                editor.commit();
                return false;
            }
        });

    }

    public static boolean isEnabled(Context paramContext, String key)
    {

        return PreferenceManager.getDefaultSharedPreferences(paramContext).getBoolean(
                key, false);
    }

    public static void restore(Context context, String file, String key)
    {
        if (!isSupported(file))
            return;
        if (isEnabled(context, key))

            FileUtil.writeValue(file, "1");
        else
            FileUtil.writeValue(file, "0");
    }

    public static boolean isSupported(String file)
    {
        return FileUtil.fileWritable(file);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;

    }

    @Override
    @Deprecated
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
            Preference preference) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        if (preference == recovery) {
            ab.setTitle("重启到recovery").setMessage("是否重启到recovery")
                    .setNegativeButton("否", new OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub
                            arg0.dismiss();
                        }
                    }).setPositiveButton("是", new OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
//                            RootCmd.RunRootCmd("reboot recovery");
                            ((PowerManager)getSystemService(Context.POWER_SERVICE)).reboot("recovery");

                        }
                    }).show();
        } else if (preference == replaceRecovery) {

            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            try {
                startActivityForResult(
                        Intent.createChooser(intent, "请选择recovery.img"),
                        OPEN_FILE);
            } catch (android.content.ActivityNotFoundException ex) {

            }

        } else if (preference == flashOta)
        {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            try {
                startActivityForResult(
                        Intent.createChooser(intent, "请选择刷机包"),
                        FLASH_OTA);
            } catch (android.content.ActivityNotFoundException ex) {

            }
        }

        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case OPEN_FILE:
                if (resultCode == RESULT_OK) {
                    if (data.getData() != null) {
                        file = new File(data.getData().getPath());
                        if (file.getName().endsWith(".img")) {
                            RootCmd.RunRootCmd("dd if=" + file.getAbsolutePath()
                                    + " of=/dev/block/platform/msm_sdcc.1/by-name/recovery");
                            Toast.makeText(this, "更换成功",
                                    Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(this, "请选择镜像文件，（.img结尾）",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                }

                break;
            case FLASH_OTA:
                if (resultCode == RESULT_OK) {
                    OutputStream localOutputStream = null;

                    if (data.getData() != null) {
                        file = new File(data.getData().getPath());
                        if (file.getName().endsWith(".zip")) {

                            try {
                                localOutputStream = Runtime.getRuntime().exec("su")
                                        .getOutputStream();

                                localOutputStream.write("mkdir -p /cache/recovery/\n".getBytes());
                                localOutputStream
                                        .write("echo 'boot-recovery' >/cache/recovery/command\n"
                                                .getBytes());
                                localOutputStream
                                        .write(("echo '--update_package=" + file.getAbsolutePath() + "' >> /cache/recovery/command\n")
                                                .getBytes());
                                localOutputStream
                                .write(("reboot recovery\n")
                                        .getBytes());
                                localOutputStream.flush();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } finally
                            {
                                if (localOutputStream != null)
                                    try {
                                        localOutputStream.close();
                                    } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                            }

                        }
                        else
                        {
                            Toast.makeText(this, "请选择刷机包，（.zip结尾）",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                }

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
