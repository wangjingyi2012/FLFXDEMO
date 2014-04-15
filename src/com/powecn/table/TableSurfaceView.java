package com.powecn.table;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.flfxdemo.R;
import com.powecn.util.App;

public class TableSurfaceView extends SurfaceView implements Runnable,
		SurfaceHolder.Callback {

	public boolean mbLoop = false;// 控制循环

	public App app;
	private int screenWidth = 0;
	private int screenHeight = 0;
	private Context context;

	SurfaceHolder mSurfaceHolder = null;
	public Canvas canvas;
	public Paint paint;

	public Bitmap selectedIco;

	public int cellCount = 0;// 表格数量
	public int tableCol = 2;// 默认两列

	public void init() {
		app = (App) context.getApplicationContext();
		this.screenWidth = app.getScreenWidth();
		this.screenHeight = app.getScreenHeight();
		mSurfaceHolder = this.getHolder();
		mSurfaceHolder.addCallback(this);
		this.setFocusable(true);

		selectedIco = BitmapFactory.decodeResource(getResources(),
				R.drawable.selected_icon);
	}

	public TableSurfaceView(Context context) {
		super(context);
		this.context = context;
		mbLoop = true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		new Thread(this).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		mbLoop = false;
	}

	@Override
	public void run() {
		while (mbLoop) {
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				e.printStackTrace();
			}
			synchronized (mSurfaceHolder) {
				draw();
			}
		}
	}

	public void draw() {
		canvas = mSurfaceHolder.lockCanvas();// 得到并锁定画布
		if (mSurfaceHolder == null || canvas == null) {
			return;
		}
	}

}
