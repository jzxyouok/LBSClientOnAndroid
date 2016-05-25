package com.example.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.android.db.constant;
import com.example.http.R;






import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LocationSix extends Activity{
	Button button;
	EditText log;
	EditText lat;
	LocationManager manager;
	Location location;
	String user_name;
	private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private  Handler hd;
    
    Button upback;
    Button submit;
    private SharedPreferences sp;

    private static String url_up = constant.url_up+"getlocation";
    private static final String TAG_MESSAGE = "message";
    
	//send arguement end
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        user_name=sp.getString("USER_NAME", "10086");
        
        log=(EditText)findViewById(R.id.longitute);
        lat=(EditText)findViewById(R.id.latitude);
        button=(Button)findViewById(R.id.getGPS);
        manager=(LocationManager)getSystemService(LOCATION_SERVICE);
        //从GPS_PROVIDER获取最近的定位信息
        location=manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateView(location);
        //判断GPS是否可用
        System.out.println("state="+manager.isProviderEnabled(LocationManager.GPS_PROVIDER));
        hd =new Handler(){
        	@Override
    		public void handleMessage(Message msg) {
    			
    			// TODO Auto-generated method stub
    			if(msg.what == 0){
    				//这里可以进行UI操作，如Toast，Dialog等
    				new Loc_up().execute();
    			}
    		}
        };
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//设置每60秒，每移动十米向LocationProvider获取一次GPS的定位信息
				//当LocationProvider可用，不可用或定位信息改变时，调用updateView,更新显示
				Timer timer = new Timer();
				timer.schedule(new TimerTask(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						//new Loc_up().execute();
						Message message=new Message();
           			  	message.what=0;
           			  	hd.sendMessage(message);
					}
					
				}, 500,10000);
			}
		});
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				//
				updateView(manager.getLastKnownLocation(provider));
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				updateView(null);
			}
			
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				//location为变化完的新位置，更新显示
				updateView(location);
			}
		});
        
    }
    //更新显示内容的方法
    public void updateView(Location location)
    {
    	if(location==null)
    	{
    		lat.setText("未获得服务");
    		return;
    	}
    	
    	lat.setText(location.getLatitude()+"");
    	log.setText(location.getLongitude()+"");
    	//new Loc_up().execute();
    }
    class Loc_up extends AsyncTask<String, String, String> {

    	@Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LocationSix.this);
            pDialog.setMessage("sending...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			String longitude = log.getText().toString();
            String lantitude = lat.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_name",user_name));
            params.add(new BasicNameValuePair("log", longitude));
            params.add(new BasicNameValuePair("lan", lantitude));
            
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
	           if(message.equals("success"))
	           {
	        	   Toast.makeText(getApplicationContext(), R.string.sendGPS, 8000).show();
                   
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