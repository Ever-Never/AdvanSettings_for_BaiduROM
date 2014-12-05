
package cn.edu.ncwu.www.tools;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import android.util.Log;

public class RootCmd {
    private static final String TAG = "RootCmd";

    public static boolean isRoot()
    {
        return execute("su", "id").contains("uid=0(root) gid=0(root)");
    }

    public static int RunRootCmd(String paramString) {

        try {
            Log.i(TAG, paramString);
            Process localProcess = Runtime.getRuntime().exec("su");
            DataOutputStream localDataOutputStream = new DataOutputStream(
                    localProcess.getOutputStream());
            String str = paramString + "\n";
            localDataOutputStream.writeBytes(str);
            localDataOutputStream.flush();
            localDataOutputStream.writeBytes("exit\n");
            localDataOutputStream.flush();
            localProcess.waitFor();
            return localProcess.exitValue();
        } catch (Exception localException) {
            localException.printStackTrace();
        }

        return 1;
    }

    public static String execute(String shell, String cmd)
    {
        String localObject = "";
        Log.d(TAG, cmd) ;
        try
        {
            Process localProcess = Runtime.getRuntime().exec(shell);
            if (localProcess != null)
            {
                DataOutputStream localDataOutputStream = new DataOutputStream(
                        localProcess.getOutputStream());
                DataInputStream localDataInputStream = new DataInputStream(
                        localProcess.getInputStream());
                localDataOutputStream.writeBytes(cmd + "\n");
                localDataOutputStream.flush();
                localDataOutputStream.writeBytes("exit\n");
                localDataOutputStream.flush();
                localProcess.waitFor();
                while (true)
                {
                    String str1 = localDataInputStream.readLine();
                    if (str1 == null) {
                        Log.d(TAG, localObject) ;
                        return localObject;
                    }
                    localObject = localObject + str1;
                    String str2 = localObject + "\n";
                    localObject = str2;
                }
            }
        } catch (IOException localIOException)
        {
            localIOException.printStackTrace();
            return localObject;
        } catch (InterruptedException localInterruptedException)
        {
            localInterruptedException.printStackTrace();
        } finally {
            ;
        }

        return localObject;
    }

}
