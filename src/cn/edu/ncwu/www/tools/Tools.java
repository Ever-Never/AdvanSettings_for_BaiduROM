
package cn.edu.ncwu.www.tools;

import android.content.Context;
import android.widget.Toast;

import java.io.File;

public class Tools {

    public static boolean installRecovery(Context context, File file, final String fstabPath)
    {
        final Context _context = context;
        // if (file.exists())
        if (!file.exists())
            return false;

        final String reoveryimage = file.getAbsolutePath();
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                String cmd = "dd if=" + reoveryimage + " of=" + fstabPath;
                // RootCmd.execute("su", cmd);
                Toast.makeText(_context, "安装recovery成功", Toast.LENGTH_LONG).show();

            }
        }).start();

        return true;

    }

}
