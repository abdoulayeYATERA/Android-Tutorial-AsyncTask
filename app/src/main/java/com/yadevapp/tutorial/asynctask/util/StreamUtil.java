package com.yadevapp.tutorial.asynctask.util;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by abdoulaye on 27/02/18.
 */
public class StreamUtil {
  private final static String TAG = StreamUtil.class.getSimpleName();

  /**
   *
   * @param in
   * @param out
   * @throws IOException
   */
  public static void copyStreamData(InputStream in, OutputStream out) throws IOException {
    Log.d(TAG, "copyStreamData: ");
    byte[] buffer = new byte[1024];
    int bufferLength;

    while ((bufferLength = in.read(buffer)) != -1) {
      out.write(buffer, 0, bufferLength);
    }
    in.close();
    out.close();
  }
}
