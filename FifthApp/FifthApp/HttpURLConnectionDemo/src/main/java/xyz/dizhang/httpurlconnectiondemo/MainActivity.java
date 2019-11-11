package xyz.dizhang.httpurlconnectiondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private ProgressBar progressBar;

    private AsyncTask httpTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("");

                httpTask = new HttpTask().execute("https://www.baidu.com");
            }
        });
    }

    private class HttpTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "HttpTask Started ...", Toast.LENGTH_SHORT).show();
            textView.setText("Started ...");
        }

        @Override
        protected String doInBackground(String... urls) {
            String out = null;
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                try {
                    InputStream in = new BufferedInputStream(connection.getInputStream());

                    StringBuilder sb = new StringBuilder();
                    BufferedReader r = new BufferedReader(new InputStreamReader(in),1000);
                    for (String line = r.readLine(); line != null; line = r.readLine()){
                        sb.append(line);
                    }

                    in.close();

                    out = sb.toString();
                } finally {
                    connection.disconnect();
                }
            } catch (MalformedURLException e) {
                Log.e("Http", "URL Error:" + e.getMessage());
                e.printStackTrace();

            } catch (IOException e) {
                Log.e("Http", "IOException: " + e.getMessage());
                e.printStackTrace();
            }

            return out;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), "HttpTask Done.", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            textView.setText("Done.");
            System.out.println(s);
            super.onPostExecute(s);
        }
    }
}
