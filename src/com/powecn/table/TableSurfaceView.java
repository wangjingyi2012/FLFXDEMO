package com.powecn.table;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.flfxdemo.R;
import com.powecn.pojo.Student;
import com.powecn.util.App;

public class TableSurfaceView extends SurfaceView implements Runnable,
		SurfaceHolder.Callback {

	public boolean mbLoop = false;// ����ѭ��

	public App app;
	private int screenWidth = 0;
	private int screenHeight = 0;
	private Activity context;

	SurfaceHolder mSurfaceHolder = null;
	public Canvas canvas;
	public Paint paint;

	public Bitmap selectedIco;

	public int cellCount = 0;// �������
	public int tableCol = 2;// Ĭ������

	List<Student> datas;// ѧ������
	List<Student> selectedStudents;// ��ѡ���ѧ��

	public void init() {
		app = (App) context.getApplication();
		this.screenWidth = app.getScreenWidth();
		this.screenHeight = app.getScreenHeight();
		mSurfaceHolder = this.getHolder();
		mSurfaceHolder.addCallback(this);
		this.setFocusable(true);

		paint = new Paint();

		selectedIco = BitmapFactory.decodeResource(getResources(),
				R.drawable.selected_icon);
	}

	public TableSurfaceView(Activity context, List<Student> datas, int tableCol) {
		super(context);
		this.context = context;
		mbLoop = true;

		this.cellCount = datas.size();
		this.tableCol = tableCol;

		init();

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
		canvas = mSurfaceHolder.lockCanvas();// �õ�����������
		if (mSurfaceHolder == null || canvas == null) {
			return;
		}
		paint.setColor(Color.BLACK);
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(selectedIco, 10, 10, paint);

		mSurfaceHolder.unlockCanvasAndPost(canvas);// ������ɲ���������
	}

}
