package com.yadevapp.tutorial.asynctask.async;

import android.os.AsyncTask;
import android.util.Log;
import com.yadevapp.tutorial.asynctask.util.NetworkUtil;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by abdoulaye on 01/03/18.
 */
public class DownloadFileAsyncTask extends AsyncTask<URL, Integer, Boolean> {
  private final String TAG = getClass().getSimpleName();
  /**
   * use weakReference to avoid memory leak
   */
  private WeakReference<CallBack> mCallBack;

  public DownloadFileAsyncTask(CallBack callBack) {
    Log.d(TAG, "DownloadFileAsyncTask: ");
    if (callBack == null) {
      throw new IllegalArgumentException("null callback");
    }
    mCallBack = new WeakReference<>(callBack);
  }

  @Override
  protected void onPreExecute() {
    Log.d(TAG, "onPreExecute: ");
    if (mCallBack.get() != null) {
      mCallBack.get().onPreExecute();
    }
  }

  /**
   *
   * @param params
   * @return true if the download succeed
   */
  @Override
  protected Boolean doInBackground(URL ... params) {
    Log.d(TAG, "doInBackground: ");
    try {
      URL url = params[0];
      NetworkUtil.downloadFile(url);
    } catch (MalformedURLException e) {
      Log.e(TAG, "doInBackground: ", e);
      return false;
    } catch (IOException e) {
      Log.e(TAG, "doInBackground: ", e);
      return false;
    }
    return true;
  }

  /**
   *
   * @param success true if the download succeed
   */
  @Override
  protected void onPostExecute(Boolean success) {
    Log.d(TAG, "onPostExecute: ");
    super.onPostExecute(success);
    if (mCallBack.get() != null) {
      mCallBack.get().onPostExectue(success);
    }
  }

  @Override
  protected void onCancelled(Boolean aBoolean) {
    Log.d(TAG, "onCancelled: ");
    super.onCancelled(aBoolean);
    if (mCallBack.get() != null) {
      mCallBack.get().onCancelled(aBoolean);
    }
  }

  @Override
  protected void onProgressUpdate(Integer... values) {
    Log.d(TAG, "onProgressUpdate: ");
    super.onProgressUpdate(values);
    if (mCallBack.get() != null) {
      mCallBack.get().onProgressUpdate(values);
    }
  }

  /**
   * async task callback interface
   */
  public interface CallBack {
    void onPreExecute();
    void onProgressUpdate(Integer... values);
    void onPostExectue(Boolean success);
    void onCancelled(Boolean success);
  }
}
