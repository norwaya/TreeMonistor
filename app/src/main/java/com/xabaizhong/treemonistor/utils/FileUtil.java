package com.xabaizhong.treemonistor.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
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
            if(!nomedia.exists()){
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
    public static File createFile(String file){
        File f = new File(path+File.separator+file);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }
}
