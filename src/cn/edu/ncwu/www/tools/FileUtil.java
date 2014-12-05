
package cn.edu.ncwu.www.tools;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
    private static final String TAG = "FileUtils";

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

    public static void releaseFileInAsset(Context context, String fileName, File file)
    {
        // File localFile = new File(this.TMP_SD_DIR + this.ROOT_FILE_NAME);
        try
        {
            InputStream localInputStream = context.getAssets().open(fileName);
            FileOutputStream localFileOutputStream = new FileOutputStream(file);
            byte[] arrayOfByte = new byte[1024];
            while (true)
            {
                int i = localInputStream.read(arrayOfByte);
                if (i == -1)
                {
                    localFileOutputStream.flush();
                    localInputStream.close();
                    localFileOutputStream.close();
                    return;
                }
                localFileOutputStream.write(arrayOfByte, 0, i);
            }
        } catch (IOException localIOException)
        {
            localIOException.printStackTrace();
        }
    }

    private static boolean deleteDir(File dir)
    {
        if(!dir.exists())
            return true;
        String[] files = null;
        if (dir.isDirectory()){
            files = dir.list();
            String tmpDir = dir.getAbsolutePath() ;
            Log.d(TAG, "delete dir:"+ tmpDir);
        for (String name: files)
        {
          
            deleteDir(new File(tmpDir ,name));
               
        }}else{
            dir.delete() ;
        }
        return false;
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

    public static void releaseFilesInAsset(Context context, String path) {
        try {
            String str[] = context.getAssets().list(path);
            // deleteDir(str);
            if (str.length > 0) {// 如果是目录
                File file = new File(context.getFilesDir() + "/" + path);
                deleteDir(file);
                Log.d(TAG, "release dir:" + file.getAbsolutePath());
                
                file.mkdirs();
                for (String string : str) {
                    path = path + "/" + string;
                    // System.out.println("zhoulc:\t" + path);
                    // textView.setText(textView.getText()+"\t"+path+"\t");
                    releaseFilesInAsset(context, path);
                    path = path.substring(0, path.lastIndexOf('/'));
                }
            } else {// 如果是文件
                InputStream is = context.getAssets().open(path);
                FileOutputStream fos = new FileOutputStream(new File(context.getFilesDir()
                        +"/"+ path));
                byte[] buffer = new byte[1024];
                int count = 0;
                while (true) {
                    count++;
                    int len = is.read(buffer);
                    if (len == -1) {
                        break;
                    }
                    fos.write(buffer, 0, len);
                }
                is.close();
                fos.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
