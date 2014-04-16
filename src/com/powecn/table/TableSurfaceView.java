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

	public boolean mbLoop = false;// 控制循环

	public App app;
	private int screenWidth = 0;
	private int screenHeight = 0;
	public int swEach = 0;
	public int shEach = 0;
	public int each = 40;
	private Activity context;

	SurfaceHolder mSurfaceHolder = null;
	public Canvas canvas;
	public Paint paint;

	public Bitmap selectedIco;

	public int cellCount = 0;// 表格数量
	public int tableCol = 2;// 默认两列
	public int tableRow = 1;// 默认一列
	public int lastRowCell = 0;// 最后一行多出的格子

	List<Student> datas;// 学生数据
	List<Student> selectedStudents;// 被选择的学生

	public void init() {
		app = (App) context.getApplication();
		this.screenWidth = app.getScreenWidth();
		this.screenHeight = app.getScreenHeight();
		swEach = screenWidth / each;
		shEach = screenHeight / each;
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
		this.datas = datas;
		mbLoop = true;

		init();

		this.cellCount = datas.size();
		this.tableCol = tableCol;
		if (cellCount % tableCol == 0) {
			this.tableRow = cellCount / tableCol;
		} else {
			this.tableRow = cellCount / tableCol + 1;
			lastRowCell = cellCount % tableCol;
		}

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

		paint.setColor(Color.BLACK);
		canvas.drawColor(Color.WHITE);
		paint.setTextSize(22);
		paint.setAntiAlias(true);
		// canvas.drawBitmap(selectedIco, 10, 10, paint);

		// 绘制学生
		for (int i = 0; i < datas.size(); i++) {
			int j = i % tableCol;
			int xPos = swEach * 3 + j * swEach * 6, yPos = shEach * 10;// 姓名X,Y坐标
			if (j == 0) {
				xPos = swEach * 3;
				yPos += shEach * 4;
			}

			canvas.drawText(datas.get(i).getStudentName(), xPos, yPos, paint);
			// canvas.drawBitmap(selectedIco, xPos, yPos, null);
		}

		mSurfaceHolder.unlockCanvasAndPost(canvas);// 绘制完成并解锁画布
	}

}
