package com.example.main;

import com.example.http.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class OptionPage extends Activity {
	private SharedPreferences sp;
	private Button btn_logout;
	private Button btn_gps;
	private Button btn_treehighmesure;
	private TextView infoshow;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		//Btn_logout
		super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE); 
        
        infoshow=(TextView)findViewById(R.id.textView);
        infoshow.setText("当前登录的用户是："+sp.getString("USER_NAME", ""));
        
		btn_logout=(Button)findViewById(R.id.Btn_logout);
        btn_logout.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
        		Editor editor = sp.edit(); 
        		editor.putBoolean("AUTO_ISCHECK", false);
                editor.commit();
                Intent intent = new Intent(OptionPage.this,Login.class);  
                OptionPage.this.startActivity(intent);  
			}
        });
        btn_gps=(Button)findViewById(R.id.Btn_GPS);
        btn_gps.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(OptionPage.this,LocationAct.class);  
                OptionPage.this.startActivity(intent); 
			}
        	
        });
        btn_treehighmesure=(Button)findViewById(R.id.Btn_TreeHighMesure);
        btn_treehighmesure.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(OptionPage.this,LocationSix.class);  
                OptionPage.this.startActivity(intent); 
			}
        	
        });
	}
}
