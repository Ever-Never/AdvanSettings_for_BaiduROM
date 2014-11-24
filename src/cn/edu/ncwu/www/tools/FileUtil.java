
package cn.edu.ncwu.www.tools;

import android.content.Context;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    public static boolean fileExists(String path)
    {
        return new File(path).exists();
    }

    public static boolean fileWritable(String file)
    {
        return (fileExists(file)) && (new File(file).canWrite());
    }

    public static boolean isFileExists(String file)
    {
        return new File(file).exists();
    }

    private static boolean deleteDir(File dir)
    {
        String[] arrayOfString = null;
        if (dir.isDirectory())
            arrayOfString = dir.list();
        for (int i = 0;; i++)
        {
            if (i >= arrayOfString.length)
                return dir.delete();
            if (!deleteDir(new File(dir, arrayOfString[i])))
                return false;
        }
    }

    public static String execute(String shell, String cmd)
    {
        String localObject = "";
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
                    if (str1 == null)
                        return localObject;
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
        }
        return localObject;
    }

    public static void writeValue(String file, String value)
    {
        try
        {
            FileOutputStream localFileOutputStream = new FileOutputStream(new File(file));
            localFileOutputStream.write(value.getBytes());
            localFileOutputStream.flush();
            localFileOutputStream.close();
            return;
        } catch (FileNotFoundException localFileNotFoundException)
        {
            localFileNotFoundException.printStackTrace();
            return;
        } catch (IOException localIOException)
        {
            localIOException.printStackTrace();
        }
    }

    public static String getFileValue(String file, String defaultString)
    {
        String str = readLine(file);
        if (str != null)
            return str;
        return defaultString;
    }
    

    public static boolean getFileValueAsBoolean(String file, boolean bool)
    {
        String str = readLine(file);
        if (str != null)
            return !str.equals("0");
        return bool;
    }

    public static String readLine(String name) {
        String tmp = null;
        BufferedReader bf = null;

        try {
            bf = new BufferedReader(new FileReader(name));
            tmp = bf.readLine();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (bf != null)
                try {
                    bf.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return tmp;
    }
}
