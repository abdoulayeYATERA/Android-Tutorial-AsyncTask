package com.yadevapp.tutorial.asynctask.util;

import android.os.Environment;
import android.util.Log;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by abdoulaye on 27/02/18.
 */

public class NetworkUtil {
  private final static String TAG = NetworkUtil.class.getSimpleName();
  private final static int mConnectTimout = 3000;
  private final static int mReadTimeout = 3000;

  /**
   *
   * @param url
   * @return
   * @throws IOException
   */
  public static HttpURLConnection getConfiguredHttpConnection(URL url) throws IOException {
    Log.d(TAG, "getConfiguredHttpConnection: " + url);
    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
    httpURLConnection.setRequestMethod("GET");
    httpURLConnection.setConnectTimeout(mConnectTimout);
    httpURLConnection.setReadTimeout(mReadTimeout);
    return httpURLConnection;
  }

  /**
   *
   * @param url
   * @throws IOException
   */
  public static void downloadFile(URL url) throws IOException {
    Log.d(TAG, "downloadFile: " + url.toString());
    String filename = FilenameUtils.getName(url.getPath());
    File destFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + filename);
    downloadFile(url, destFile);
  }

  /**
   *
   * @param url
   * @param destFile
   * @throws IOException
   */
  public static void downloadFile(URL url, File destFile) throws IOException {
    Log.d(TAG, "downloadFile: " + url.toString());
    HttpURLConnection httpURLConnection = NetworkUtil.getConfiguredHttpConnection(url);
    httpURLConnection.connect();
    InputStream in = httpURLConnection.getInputStream();
    OutputStream out = new FileOutputStream(destFile);
    StreamUtil.copyStreamData(in, out);
  }
}
