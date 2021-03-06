package com.example.flfxdemo;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.powecn.pojo.Student;
import com.powecn.table.TableSurfaceView;
import com.powecn.util.App;

public class MainActivity extends FinalActivity {

	private App app;
	TableSurfaceView tableView;
	private int screenWidth = 10;// 屏幕宽度
	private int screenHeight = 10;// 屏幕高度

	// 学生数据
	List<Student> datas;

	public void init() {

		app = (App) getApplication();
		// 设置屏幕不关闭
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		this.screenWidth = dm.widthPixels;
		this.screenHeight = dm.heightPixels;
		app.setScreenWidth(screenWidth);
		app.setScreenHeight(screenHeight);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		pushData();
		init();
		tableView = new TableSurfaceView(this, datas, 6);		
		setContentView(tableView);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} 
		return super.onOptionsItemSelected(item);
	}

	

	/**
	 * 填充测试数据
	 */
	public void pushData() {
		datas = new ArrayList<Student>();
		for (int i = 0; i < 30; i++) {
			Student student = new Student("王" + i, "认知", "A");
			datas.add(student);
		}
	}

}
