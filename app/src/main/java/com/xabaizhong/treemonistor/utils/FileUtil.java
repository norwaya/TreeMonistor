package com.xabaizhong.treemonistor.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 2017/3/2.
 */

public class FileUtil {
    private static String TAG = "file-util";

    private static String basePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "monitor";

    private static String picPath = basePath + File.separator + "pic";


    public static List<String> getPngFiles() {
        List<String> list = new ArrayList<>();
        List<File> files = getFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.getName().equals(".nomedia")) {
                    list.add(FileUtil.bitmapToBase64Str(file));
                }
            }

        }
        return list;
    }

    public static void clearFileDir() {
        File file = new File(picPath);
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
                Log.i("file list", "clearFileDir: " + f.getName());
                if (!f.getName().equals(".nomedia")) {
                    f.delete();
                }
            }
            checkNoMediaFile();
        }
    }

    public static void checkNoMediaFile() {
        File file = new File(picPath, ".nomedia");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<File> getFiles() {
        File file = new File(picPath);
        if (file.exists()) {
            return Arrays.asList(file.listFiles());
        }
        return null;
    }

    public static File createFile(String file) {
        File f = new File(picPath + File.separator + file);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }


    private static void wf(String text) {
        try {
            File f = new File(basePath, "log.txt");
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f, true);
            PrintWriter pw = new PrintWriter(fos);
            pw.write(text);
            pw.close();
        } catch (Exception e) {

        }
    }

    public static String bitmapToBase64Str(File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        String string = Base64.encodeToString(bytes, Base64.DEFAULT);
        wf(string);
        return string;
    }

    public static Bitmap base64ToBitmap(String str) {
        byte[] bytes = Base64.decode(str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
   /* //加密
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
    }*/

}

