package com.xabaizhong.treemonistor.utils;

import android.os.Environment;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 2017/3/2.
 */

public class FileUtil {
    private static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/monitor/pic";


    public static void clearFileDir() {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
            File nomedia = new File(".nomedia");
            if (!nomedia.exists()) {
                try {
                    nomedia.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (File f : file.listFiles()
                    ) {
                if (!f.getName().equals(".nomedia")) {
                    f.delete();
                }
            }
        }
    }

    public static void checkNoMediaFile() {
        File file = new File(path, ".nomedia");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<File> getFiles() {
        File file = new File(path);
        if (file.exists()) {
            return Arrays.asList(file.listFiles());
        }
        return null;
    }

    public static File createFile(String file) {
        File f = new File(path + File.separator + file);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }



    //文件转字符串
    public static String file2String(File file) {
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String temp;
            while ((temp = buffer.readLine()) != null) {
                sb.append(temp);

            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //加密
    public static String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = Base64.encodeToString(b, Base64.DEFAULT);
        }
        return s;

    }

    //解密
    public static String getFromBase64(String str) {
        byte[] b = null;
        String result = null;
        if (str != null) {
            try {
                b = Base64.decode(str, Base64.DEFAULT);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}

