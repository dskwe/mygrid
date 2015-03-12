package com.example.drawlines;

//import android.R.layout;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Drawl bDrawl;
	private TextView mTextView;
	private SpinnerAdapter mSpinnerAdapter; 
	//private View myl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bDrawl=new Drawl(this);
	    setContentView(bDrawl);//��view��ͼ�ŵ�Activity����ʾ
	    //myl=findViewById(R.layout.activity_main);   
	    //Drawable drawable = new BitmapDrawable(bitmap) ;    
        //myl.setBackgroundDrawable(drawable);  
		//setContentView(R.layout.activity_main);
	    /*
	  //�Ƿ�����ActionBarͼ��ĵ�������  
	    ActionBar mActionBar = getActionBar();  
	  //�Ƿ�����ActionBarͼ��ĵ�������  
        mActionBar.setDisplayHomeAsUpEnabled(true);  
        mActionBar.setTitle("");  
          
        //1������Action Bar�ĵ���ģʽ  
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);  
        //2����ʼ���������������������б�����  
        mSpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.navigation_list_array,  
                android.R.layout.simple_spinner_dropdown_item);  
        //4��ΪActionBar����Adapter��Ϊ�������¼�����  
        mActionBar.setListNavigationCallbacks(mSpinnerAdapter, new MyOnNavigationListener());  
        mTextView = (TextView)findViewById(R.id.screen_second_textview);  
	    */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		  switch (item.getItemId()) {
		    case R.id.input3:
		      Drawl.InputNum=3;
		      Drawl.RestartGame();
		      bDrawl=new Drawl(this);
			  setContentView(bDrawl);
		      return true;
		    case R.id.input4:
			      Drawl.InputNum=4;
			      Drawl.RestartGame();
			      bDrawl=new Drawl(this);
				  setContentView(bDrawl);
			      //Drawl
			      return true;
		    case R.id.input5:
			      Drawl.InputNum=5;
			      Drawl.RestartGame();
			      bDrawl=new Drawl(this);
				  setContentView(bDrawl);
			      //Drawl
			      return true;
		    case R.id.input6:
			      Drawl.InputNum=6;
			      Drawl.RestartGame();
			      bDrawl=new Drawl(this);
				  setContentView(bDrawl);
			      //Drawl
			      return true;
		    case R.id.input7:
			      Drawl.InputNum=7;
			      Drawl.RestartGame();
			      bDrawl=new Drawl(this);
				  setContentView(bDrawl);
			      //Drawl
			      return true;
		      /*
		    case R.id.toast:
			      Drawl.InputNum=6;
			      Drawl.RestartGame();
			      bDrawl=new Drawl(this);
				  setContentView(bDrawl);
			      //Drawl
			      return true;
		    */
		    default:
		      return super.onOptionsItemSelected(item);
		  }
		}
	
}
