package com.yadevapp.tutorial.asynctask.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.yadevapp.tutorial.asynctask.R;
import com.yadevapp.tutorial.asynctask.async.DownloadFileAsyncTask;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Button mButton;
    private TextView mUrlEditText;
    private DownloadFileAsyncTask.CallBack mCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.activity_main_execute_Button);
        mUrlEditText = findViewById(R.id.activity_main_url_edittext);
        mCallBack = createAsyncTaskCallBack();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlString = mUrlEditText.getText().toString();
                try {
                    URL url = new URL(urlString);
                    DownloadFileAsyncTask asyncTask = new DownloadFileAsyncTask(mCallBack);
                    asyncTask.execute(url);
                } catch (MalformedURLException e) {
                    Toast.makeText(MainActivity.this, "incorrect url", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "onClick: ", e);
                }
            }
        });
    }

    /**
     * create download async task callback
     * @return
     */
    public DownloadFileAsyncTask.CallBack createAsyncTaskCallBack() {
        Log.d(TAG, "createAsyncTaskCallBack: ");
        return  new DownloadFileAsyncTask.CallBack() {
            @Override
            public void onPreExecute() {
                Log.d(TAG, "onPreExecute: ");
                mButton.setText("downloading file...");
            }

            @Override
            public void onProgressUpdate(Integer... values) {
                Log.d(TAG, "onProgressUpdate: ");
            }

            @Override
            public void onPostExectue(Boolean success) {
                Log.d(TAG, "onPostExectue: ");
                if (!success) {
                    mButton.setText("download error");
                    return;
                }
                mButton.setText("file downloaded!");
            }

            @Override
            public void onCancelled(Boolean success) {
                Log.d(TAG, "onCancelled");
                mButton.setText("Cancelled!");
            }
        };
    }
}
