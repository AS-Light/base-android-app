package jx.android.staff.utils;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    private static final String TAG = "FileUtils";
    private static final boolean DEBUG = false;
   

    public static boolean fileIsExists(String path) {
        if (path == null || path.trim().length() <= 0) {
            return false;
        }
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        Log.e("TMG",path+"file not exists");
        return true;
    }

    //删除文件内容
    public static boolean deleteFile(String path){
        File out = new File(path);
        boolean delete = false;
        if(out.exists()){
            if(!out.delete()){
                delete= false;
            }else{
                delete= true;
            }
        }
        return delete;
    }

    //删除文件夹及文件内容
    public static void deleteDir(File file){
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile==null||childFile.length==0){
                file.delete();
                return;
            }
            for(File f:childFile){
                deleteDir(f);
            }
            file.delete();
        }
    }

    //删除图片文件夹及文件内容
    public static List<String> getpaths(File file){
        List<String> list = new ArrayList<>();
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile==null||childFile.length==0){
                return list;
            }
            for(File f:childFile){
                list.add(f.getAbsolutePath());
            }
        }
        return list;
    }

    //创建文件夹
    public static boolean createDir(String Dir){
        boolean b;
        File dir = new File(Dir);
        if(!dir.exists()){
           b = dir.mkdirs();//mkdir 父目录不存在 不能创建文件
        }else {
            b = true;
        }
        return b;
    }

    public static String getFileMimeType(String filename) {
        if (TextUtils.isEmpty(filename)) {
            return null;
        }
        int lastDotIndex = filename.lastIndexOf('.');
        String mimetype = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                filename.substring(lastDotIndex + 1).toLowerCase());
        return mimetype;
    }
}