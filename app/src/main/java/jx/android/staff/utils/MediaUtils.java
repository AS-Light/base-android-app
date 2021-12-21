package jx.android.staff.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.Locale;

public class MediaUtils {
  
  private static final String LOGTAG = "MediaUtils";

  /**
  * Scan a media file by sending a broadcast.This is the easiest way.
  * 对方成功接收广播并处理条件  文件必须存在，文件路径必须以Environment.getExternalStorageDirectory().getPath() 的返回值开头
  */
  public static void sendScanFileBroadcast(Context context, String filePath) {
          File file = new File(filePath);
          Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file));
          context.sendBroadcast(intent);
  }
  /**
  * 
  * @param context
  * @param paths File paths to scan 
  * @param mimeTypes mimeTypes in array;it could be null;then 
  * @param callback
  */
  public static void scanFiles(Context context, String[] paths, String[] mimeTypes, MediaScannerConnection.OnScanCompletedListener callback) {
      if (null != paths && paths.length != 0) {
          MediaScannerConnection.scanFile(context, paths, mimeTypes, callback);
      } else {
          Log.i(LOGTAG, "scanFiles paths = null or paths.length=0 paths=" + paths);
      }
  }
  
  public static void scanFiles(Context context, String[] paths, String[] mimeTypes) {
      scanFiles(context, paths, mimeTypes, null);
  }
  
  public static void scanFiles(Context context, String[] paths) {
      scanFiles(context, paths, null);
  }
  
  public static int removeImageFromLib(Context context, String filePath) {
      ContentResolver resolver = context.getContentResolver();
      return resolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA + "=?", new String[]{filePath});
  }
  
  public static int removeAudioFromLib(Context context, String filePath) {
      return context.getContentResolver().delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
              MediaStore.Audio.Media.DATA + "=?", new String[] {filePath});
  }
  
  public static int removeVideoFromLib(Context context, String filePath) {
      return context.getContentResolver().delete(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
              MediaStore.Video.Media.DATA + "=?", new String[] {filePath});
      
  }
  
  public static int removeMediaFromLib(Context context, String filePath) {
      String mimeType = FileUtils.getFileMimeType(filePath);
      int affectedRows = 0;
      if (null != mimeType) {
          mimeType = mimeType.toLowerCase(Locale.US);
          if (isImage(mimeType)) {
              affectedRows = removeImageFromLib(context, filePath);
          } else if (isAudio(mimeType)) {
              affectedRows = removeAudioFromLib(context ,filePath);
          } else if (isVideo(mimeType)) {
              affectedRows = removeVideoFromLib(context, filePath);
          }
      }
      return affectedRows;
  }
  
  public static boolean isAudio(String mimeType) {
      return mimeType.startsWith("audio");
  }
  
  public static boolean isImage(String mimeType) {
      return mimeType.startsWith("image");
  }
  
  public static boolean isVideo(String mimeType) {
      return mimeType.startsWith("video");
  }
  
  
  public static boolean isMediaFile(String filePath) {
      String mimeType = FileUtils.getFileMimeType(filePath);
      return isMediaType(mimeType);
  }
  
  public static boolean isMediaType(String mimeType) {
      boolean isMedia = false;
      if (!TextUtils.isEmpty(mimeType)) {
          mimeType = mimeType.toLowerCase(Locale.US);
          isMedia = isImage(mimeType) || isAudio(mimeType) || isVideo(mimeType);
      }
      return isMedia;
  }
  /**
  * Before using it,please do have a media type check.
  * @param context
  * @param srcPath
  * @param destPath
  * @return
  */
  public static int renameMediaFile(Context context, String srcPath, String destPath) {
      removeMediaFromLib(context, srcPath);
      sendScanFileBroadcast(context, destPath);
      return 0;
  }
}