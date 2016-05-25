package com.example.main;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.Editable;
import android.view.*;
import android.widget.*;

import java.math.BigDecimal;

import com.android.db.*;
import com.example.http.R;

public class MainActivity extends Activity 
implements SensorEventListener,SurfaceHolder.Callback,View.OnClickListener
{
	private MySQLiteOpenHelper mySQLiteOpenHelper;
	private SurfaceView mySurfaceView;
	private Sensor sensor;
	private SensorManager sensorMgr;
	private Toast mToast;
	private Camera myCamera;
	private SurfaceHolder mySurfaceHolder;
	private Button mLockButtomBtn;
	private Button topButton;
	private Button mSettingBtn;
	private float y = 0.0F;
	private float L = 1.7F;
	private EditText mInputHeightET;
	private TextView mInputTv;
	int state=0;
	int t1=0,t2=0,t3=0;
	int s1=0,s2=0,s3=0;
	double ce_treehigh=0.0D;
	private double length=2.0D;
	
	String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surfacefortree);
        this.mySurfaceView = ((SurfaceView)findViewById(R.id.mySurfaceView));
        this.mySurfaceHolder = this.mySurfaceView.getHolder();
        this.mySurfaceHolder.addCallback(this);
        this.mySurfaceHolder.setType(3);
        this.mLockButtomBtn = ((Button)findViewById(R.id.lockbuttombtn));
        this.mLockButtomBtn.setOnClickListener(this);
        this.topButton=((Button)findViewById(R.id.top));
        this.topButton.setOnClickListener(this);
        this.mSettingBtn = ((Button)findViewById(R.id.settingbtn));
        this.mSettingBtn.setOnClickListener(this);
        this.sensorMgr = ((SensorManager)getSystemService("sensor"));
        this.sensor = this.sensorMgr.getDefaultSensor(3);
        this.sensorMgr.registerListener(this, this.sensor, 0);
        this.mToast = Toast.makeText(this, "", 0);
        this.mToast.show();
        initdb();
        Intent intent = getIntent();
        result=intent.getStringExtra("result");
    }
    private void initdb() {
		// TODO Auto-generated method stub
		mySQLiteOpenHelper = new MySQLiteOpenHelper(MainActivity.this);
	}
    private float setFormat(double paramDouble)
    {
      return (float)new BigDecimal(paramDouble).setScale(2, 4).doubleValue();
    }
    
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		this.y = event.values[1];
		int i = (int)this.y;
		if(state==0){
			t1=i;
		}else if(state==1){
			s3=0-t1;
			t2=i;
		}else if(state==2){
			s1=t1-t2;
			t3=i;
		}else if(state==3){
			//s2=t2-t3;
			//ce_treehigh=length*SIN(s1+s3)*SIN(s1+s2)/(SIN(s1)*SIN(s1+s2+s3));
			//mySQLiteOpenHelper.edit(Integer.valueOf(id).intValue(),d);
			/*this.mToast.setText( d + "\n\n");*/
		}
		while (true)
	    {
		
		 this.mToast.setText(result+" "+t1+" "+s3+" "+t2+" "+s1+" "+t3+"  "+s2+" " + "\n\n");
		 //this.mToast.setText(getString(R.string.heightis) + ce_treehigh + getString(R.string.m) + "\n\n"); 
		 if (hasWindowFocus())
	       this.mToast.show();
	      return;
	    }
	}

	public double SIN(int arg){
		return Math.sin(3.141592653589793D * arg / 180.0D);	
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder paramSurfaceHolder) {
		// TODO Auto-generated method stub
		try
	    {
	      this.myCamera = Camera.open();
	      Display localDisplay = ((WindowManager)getSystemService("window")).getDefaultDisplay();
	      this.myCamera.getParameters().setPreviewSize(localDisplay.getWidth(), localDisplay.getHeight());
	      this.myCamera.setDisplayOrientation(90);
	      this.myCamera.setPreviewDisplay(paramSurfaceHolder);
	      return;
	    }
	    catch (Exception localException)
	    {
	    }
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {//当surface发生任何结构性的变化时（格式或者大小），该方法就会被立即调用
		// TODO Auto-generated method stub
		Camera.Parameters localParameters = this.myCamera.getParameters();
	    this.myCamera.setParameters(localParameters);
	    this.myCamera.startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		this.myCamera.setPreviewCallback(null);
	    this.myCamera.stopPreview();
	    this.myCamera.release();
	    this.myCamera = null;
	}

	@Override
	public void onClick(View paramView) {
		// TODO Auto-generated method stub
		switch (paramView.getId()){
		default:	      return;
		case R.id.lockbuttombtn:
			state=2;
			return;
		case R.id.settingbtn:
			//this.mToast.setText(result+" "+t1+" "+s3+" "+t2+" "+s1+" "+t3+"  "+s2+" " + "\n\n");
			state=1;
			return;
		case R.id.top:
			state=3;
			s2=t2-t3;
			ce_treehigh=length*SIN(s1+s3)*SIN(s1+s2)/(SIN(s1)*SIN(s1+s2+s3));
			mySQLiteOpenHelper.edit(result,ce_treehigh);
			Intent intent=new Intent(MainActivity.this, TreeHighMesure.class);
			startActivity(intent);
			return;
		}
	}
	private void makeDialog(){
		this.mToast.cancel();
		View localView = LayoutInflater.from(this).inflate(R.layout.add,null);
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setTitle(R.string.action_settings);
		this.mInputTv =((TextView)localView.findViewById(R.id.inputTv));
		this.mInputTv.setText(R.string.input_phone_height);
		this.mInputHeightET = ((EditText)localView.findViewById(R.id.heightEdit));
		this.mInputHeightET.setText(MainActivity.this.L+"");
		while (true)
	    {
			localBuilder.setView(localView);
			localBuilder.setPositiveButton(R.string.ok,new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String str = MainActivity.this.mInputHeightET.getText().toString();
					double d = Double.valueOf(str).doubleValue();
					MainActivity.this.L = MainActivity.this.setFormat(d);
				}
				
			});
			localBuilder.setNegativeButton(R.string.cancel, null);
			localBuilder.show();
			return;
	    }
	}
}
