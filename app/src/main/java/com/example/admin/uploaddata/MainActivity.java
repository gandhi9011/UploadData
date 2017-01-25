package com.example.admin.uploaddata;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{
    Button b1;
    EditText et1,et2;
    TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        b1=(Button)findViewById(R.id.b1);
        outputText=(TextView)findViewById(R.id.tv1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                GetXMLTask task = new GetXMLTask();
                String url = "http://192.168.1.72:8084/FileUpload/Helloworld";
                //String url = "http://192.168.1.72:8084/FileUpload/Helloworld?name="+et1.getText().toString()+"&pass="+et2.getText().toString()+"";
                Toast.makeText(MainActivity.this,url,Toast.LENGTH_LONG).show();
                task.execute(new String[] { url ,et1.getText().toString(),et2.getText().toString()});
            }
        });
    }

    private class GetXMLTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String output ="";
            try {
                URL obj = new URL(urls[0]);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
/*---------------------------------------------------GET------------------------------------------------*/
                // optional default is GET
                /*con.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                output = in.readLine()+".."+in.readLine();
                in.close();*/
/*----------------------------------------------------POST--------------------------------------------*/
                String urlParameters = "name="+urls[1]+"&pass="+urls[2]+"";

                // Send post request
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                output=response.toString();

                return output;
            } catch (Exception e) {
                Log.e("errr",e.getMessage());
            }
            return output+".............";
        }




        @Override
        protected void onPostExecute(String output) {
            outputText.setText(output);
        }
    }

}
