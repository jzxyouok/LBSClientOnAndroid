package com.example.main;

import com.android.db.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.anroid.net.DialogUtil;
import com.example.http.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity2 extends Activity {
	// Progress Dialog
    private ProgressDialog pDialog;
    private TextView tv_head;
    JSONParser jsonParser = new JSONParser();
    EditText inputName;
    EditText inputPass;
   
    Button upback;
    Button submit;
    private SharedPreferences sp;

    // url to create new product
    private static String url_up = constant.url_up+"index";
    private static final String TAG_MESSAGE = "message";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surfacefortree);
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE); 
        submit=(Button)findViewById(R.id.submit_get);
        inputName=(EditText)findViewById(R.id.name);
        inputPass=(EditText)findViewById(R.id.pass);
        if(sp.getBoolean("AUTO_ISCHECK", false)) 
        {
        	Intent intent = new Intent(MainActivity2.this,LocationAct.class);  
            MainActivity2.this.startActivity(intent); 
        }
        submit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Up().execute(); 
			}
        	
        });
    }
    class Up extends AsyncTask<String, String, String> {
    	@Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity2.this);
            pDialog.setMessage("正在登录...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			String name = inputName.getText().toString();
            String pass = inputPass.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("pass", pass));

            // getting JSON Object
            // Note that create product url accepts POST method
           try{
            JSONObject json = jsonParser.makeHttpRequest(url_up,
                    "POST", params);
            String message = json.getString(TAG_MESSAGE);
            return message;
           }catch(Exception e){
               e.printStackTrace();
               return "error";     
           }
			
		}
		 protected void onPostExecute(String message) {                    
	           pDialog.dismiss();
	           //message 为接收doInbackground的返回值
	           if(message.equals("ok"))
	           {
	        	   Toast.makeText(getApplicationContext(), R.string.login, 8000).show();
	        	   Editor editor = sp.edit(); 
                   editor.putString("USER_NAME", inputName.getText().toString());  
                   editor.putString("PASSWORD",inputPass.getText().toString());
                   editor.putBoolean("AUTO_ISCHECK", true);
                   editor.commit();
                   Intent intent = new Intent(MainActivity2.this,LocationAct.class);  
                   MainActivity2.this.startActivity(intent);  
	           }else if(message.equals("no"))
	           {
	        	   Toast.makeText(getApplicationContext(), R.string.nouser, 8000).show(); 
	           }else if(message.equals("wrong"))
	           {
	        	   Toast.makeText(getApplicationContext(), R.string.wrongpass, 8000).show(); 
	           }
	           //Toast.makeText(getApplicationContext(), message, 8000).show(); 
	    }
    }

 }