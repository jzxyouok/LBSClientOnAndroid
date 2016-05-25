package com.example.main;

import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.db.*;
import com.example.http.R;

public class TreeHighMesure extends Activity{
	private MySQLiteOpenHelper mySQLiteOpenHelper;
	private treeAdapter adapter;
	private TextView id, xbbh, ydbh;
	private TextView treenumber,treehigh;
	private TextView BZshow;
	private EditText BZ;
	private EditText edtName, edtPwd, edtAddress;
	private ListView mListView;
	private Button query,add,del;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewmain2);
		init();
		findView();
		
	}
	private void findView() {
		// TODO Auto-generated method stub
		query=(Button) findViewById(R.id.id_btn_query);
		query.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(dblook.this, "到了这里", Toast.LENGTH_SHORT).show();
				// TODO Auto-generated method stub
				List<TreeOne> trees=mySQLiteOpenHelper.queryData();
				if (adapter!=null) {
					adapter=null;
				}
				adapter=new treeAdapter(trees);
				mListView.setAdapter(adapter);
			}
			
		});
		add=(Button) findViewById(R.id.id_btn_add);
		add.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String xbbh=edtName.getText().toString();
				String ydbh=edtPwd.getText().toString();
				String treenumber=edtAddress.getText().toString();
				String Bz=BZ.getText().toString();
				ContentValues cv=new ContentValues();
				cv.put(constant.XBBH, xbbh);
				cv.put(constant.YDBH, ydbh);
				cv.put(constant.Treenumber, treenumber);
				cv.put(constant.BZ, Bz);
				//将数据放入cv中
				boolean flag=mySQLiteOpenHelper.insertData(cv);
				if(flag){
					Toast.makeText(TreeHighMesure.this, "插入成功", Toast.LENGTH_SHORT).show();
					
				}else{
					Toast.makeText(TreeHighMesure.this, "插入失败", Toast.LENGTH_SHORT).show();
				}
			}
			
		});
		del=(Button)findViewById(R.id.id_del);
		del.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mySQLiteOpenHelper.del();
				Toast.makeText(TreeHighMesure.this, "清空数据库", Toast.LENGTH_SHORT).show();
			}
			
		});
		edtName = (EditText) findViewById(R.id.id_edit1);
		edtPwd = (EditText) findViewById(R.id.id_edit2);
		edtAddress = (EditText) findViewById(R.id.id_edit3);
		BZ = (EditText) findViewById(R.id.id_BZ);
		mListView = (ListView) findViewById(R.id.listview);
		mListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//mySQLiteOpenHelper.edit(1,2.4D);
				TextView title = (TextView) view.findViewById(R.id.id); 
				String string = title.getText().toString(); 
				Toast.makeText(TreeHighMesure.this, string, Toast.LENGTH_SHORT).show();
				
				Intent intent=new Intent(TreeHighMesure.this, MeasureTree.class);
				intent.putExtra("result",string);
				startActivity(intent);
			}
			
		});
	}
	

	private void init() {
		// TODO Auto-generated method stub
		mySQLiteOpenHelper = new MySQLiteOpenHelper(TreeHighMesure.this);
	}
	
	
	
	class treeAdapter extends BaseAdapter{

		List<TreeOne> trees;
		public treeAdapter(List<TreeOne> _treeOnes){
			this.trees=_treeOnes;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return trees.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return trees.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView==null){
				convertView=LayoutInflater.from(TreeHighMesure.this).inflate(R.layout.items2, null);
				id=(TextView) convertView.findViewById(R.id.id);
				xbbh=(TextView) convertView.findViewById(R.id.xbbh);
				ydbh=(TextView) convertView.findViewById(R.id.ydbh);
				treenumber=(TextView) convertView.findViewById(R.id.treenum);
				treehigh=(TextView) convertView.findViewById(R.id.treehigh);
				BZshow=(TextView) convertView.findViewById(R.id.bz);
				
				id.setText(""+trees.get(position).getId());
				xbbh.setText("小班号："+trees.get(position).getxbbh()+" ");
				ydbh.setText("样地号："+trees.get(position).getydbh()+" ");
				treenumber.setText("树 编 号："+trees.get(position).gettreenumber()+" ");
				treehigh.setText("树  高："+trees.get(position).gethigh());
				BZshow.setText("备注:"+trees.get(position).getbz());
			}
			return convertView;
		}
		
	}
	
} 