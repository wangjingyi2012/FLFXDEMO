package com.powecn.table;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.MotionEvent;
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
	public int each = 30;
	private Activity context;

	SurfaceHolder mSurfaceHolder = null;
	public Canvas canvas;
	public Paint paint;

	public Bitmap selectedIco;

	public int cellCount = 0;// 表格数量
	public int tableCol = 2;// 默认两列
	public int tableRow = 1;// 默认一列
	public int lastRowCell = 0;// 最后一行多出的格子

	// 数据表格设置
	public int tableX = 0, tableY = 0;// 从哪个位置开始画
	int cellWidth = 0;// 表格宽度
	int cellHeight = 0;// 表格高度
	int cellCount2 = 0;// 表格数量

	// 表格矩形框
	public Rect[] cellRect;

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

		tableX = swEach;
		tableY = shEach * (tableRow) * 2 + shEach * 8;

		initRect();

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
		paint.setTextSize(shEach);
		paint.setAntiAlias(true);
		// canvas.drawBitmap(selectedIco, 10, 10, paint);

		// 绘制学生
		int k = 0, xPos = 0, yPos = shEach * 2;
		// 姓名的X,Y坐标
		for (int i = 0; i < datas.size(); i++) {
			int j = i % tableCol;
			xPos = swEach * 2 + k * 70;
			if (j == 0 && i != 0) {
				k = 0;
				xPos = swEach * 2;
				yPos = yPos + shEach * 2;
			}
			k++;
			canvas.drawText(datas.get(i).getStudentName(), xPos, yPos, paint);
			// canvas.drawBitmap(selectedIco, xPos, yPos, null);
		}

		// 绘制表格
		this.drawTable(canvas, paint, 4, 6);
		Paint cellPaint2 = new Paint();
		cellPaint2.setAntiAlias(true);
		cellPaint2.setTextSize(shEach - 2);
		cellPaint2.setColor(Color.BLUE);
		canvas.drawText("厌学型", cellRect[1].left + 4, cellRect[1].bottom - 10,
				cellPaint2);
		canvas.drawText("被动型", cellRect[2].left + 4, cellRect[2].bottom - 10,
				cellPaint2);
		canvas.drawText("机械型", cellRect[3].left + 4, cellRect[3].bottom - 10,
				cellPaint2);
		canvas.drawText("进取型", cellRect[4].left + 4, cellRect[4].bottom - 10,
				cellPaint2);
		canvas.drawText("自主型", cellRect[5].left + 4, cellRect[5].bottom - 10,
				cellPaint2);

		// 第一行
		cellPaint2.setColor(Color.BLACK);
		cellPaint2.setStyle(Style.STROKE);
		canvas.drawText("认知", cellRect[6].left + 12, cellRect[6].top + shEach,
				cellPaint2);
		canvas.drawText("表现", cellRect[6].left + 12, cellRect[6].bottom - 10,
				cellPaint2);

		cellPaint2.setTextSize(swEach);
		canvas.drawText("认知障碍", cellRect[7].left + 5, cellRect[7].bottom - 20,
				cellPaint2);
		canvas.drawText("认知消极", cellRect[8].left + 5, cellRect[8].bottom - 20,
				cellPaint2);
		canvas.drawText("认知片面", cellRect[9].left + 5, cellRect[9].bottom - 20,
				cellPaint2);
		canvas.drawText("自信,自主", cellRect[10].left + 5,
				cellRect[10].bottom - 20, cellPaint2);
		canvas.drawText("自主,自由,", cellRect[11].left + 5, cellRect[11].top + 25,
				cellPaint2);
		canvas.drawText("有个性,", cellRect[11].left + 5, cellRect[11].top + 20
				+ shEach, cellPaint2);
		canvas.drawText("有想象力", cellRect[11].left + 5, cellRect[11].top + 2
				* shEach + 15, cellPaint2);

		// 第二行
		cellPaint2.setTextSize(shEach - 2);
		canvas.drawText("情绪", cellRect[12].left + 12,
				cellRect[12].top + shEach, cellPaint2);
		canvas.drawText("表现", cellRect[12].left + 12, cellRect[12].bottom - 10,
				cellPaint2);
		cellPaint2.setTextSize(swEach);
		canvas.drawText("厌烦,", cellRect[13].left + 15,
				cellRect[13].bottom - 40, cellPaint2);
		canvas.drawText("不快乐", cellRect[13].left + 15,
				cellRect[13].bottom - 15, cellPaint2);
		canvas.drawText("被动,麻木", cellRect[14].left + 5,
				cellRect[14].bottom - 20, cellPaint2);

		cellPaint2.setTextSize(swEach - 2);
		canvas.drawText("全身心投入", cellRect[15].left + 5,
				cellRect[15].bottom - 40, cellPaint2);
		canvas.drawText("刻苦用功,", cellRect[15].left + 5,
				cellRect[15].bottom - 25, cellPaint2);
		canvas.drawText("悬梁刺股", cellRect[15].left + 5,
				cellRect[15].bottom - 10, cellPaint2);

		cellPaint2.setTextSize(swEach);
		canvas.drawText("积极", cellRect[16].left + 15, cellRect[16].bottom - 20,
				cellPaint2);
		canvas.drawText("快乐有激情", cellRect[17].left, cellRect[17].bottom - 40,
				cellPaint2);
		canvas.drawText("有兴趣", cellRect[17].left, cellRect[17].bottom - 25,
				cellPaint2);
		canvas.drawText("享受学习", cellRect[17].left, cellRect[17].bottom - 10,
				cellPaint2);

		// 第三行
		cellPaint2.setTextSize(shEach - 2);
		canvas.drawText("意志", cellRect[18].left + 12,
				cellRect[18].top + shEach, cellPaint2);
		canvas.drawText("表现", cellRect[18].left + 12, cellRect[18].bottom - 10,
				cellPaint2);
		cellPaint2.setTextSize(swEach);
		canvas.drawText("反感,抵触", cellRect[19].left + 5,
				cellRect[19].bottom - 20, cellPaint2);

		canvas.drawText("需要压力", cellRect[20].left + 5,
				cellRect[20].bottom - 40, cellPaint2);
		canvas.drawText("督促", cellRect[20].left + 5, cellRect[20].bottom - 15,
				cellPaint2);
		canvas.drawText("努力", cellRect[21].left + 5, cellRect[21].bottom - 40,
				cellPaint2);
		canvas.drawText("按部就班", cellRect[21].left + 5,
				cellRect[21].bottom - 15, cellPaint2);
		canvas.drawText("自觉,自制", cellRect[22].left + 5,
				cellRect[22].bottom - 20, cellPaint2);
		canvas.drawText("意志独立", cellRect[23].left, cellRect[23].bottom - 40,
				cellPaint2);
		canvas.drawText("有执着目标", cellRect[23].left, cellRect[23].bottom - 15,
				cellPaint2);

		mSurfaceHolder.unlockCanvasAndPost(canvas);// 绘制完成并解锁画布
	}

	/**
	 * @param canvas
	 * @param paint
	 * @param rows
	 * @param cols
	 */
	public void drawTable(Canvas canvas, Paint paint, int rows, int cols) {
		this.canvas = canvas;
		this.paint = paint;
		cellWidth = (screenWidth - swEach * 2) / cols;// 表格宽度
		cellHeight = (screenHeight - shEach - tableY) / rows;// 表格高度
		canvas.drawLine(tableX, tableY, screenWidth - swEach, tableY, paint);// 上边
		canvas.drawLine(tableX, tableY, tableX, screenHeight - shEach, paint);// 左边
		canvas.drawLine(screenWidth - swEach, tableY, screenWidth - swEach,
				screenHeight - shEach, paint);// 右边
		canvas.drawLine(tableX, screenHeight - shEach, screenWidth - swEach,
				screenHeight - shEach, paint);// 底边
		// 横线
		for (int i = 1; i < rows; i++) {
			canvas.drawLine(tableX, tableY + cellHeight * i, screenWidth
					- swEach, tableY + cellHeight * i, paint);
		}
		// 竖线
		for (int i = 1; i < cols; i++) {
			canvas.drawLine(tableX + cellWidth * i, tableY, tableX + cellWidth
					* i, screenHeight - shEach, paint);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();

		for (int i = 0; i < cellCount2; i++) {
			if (cellRect[i].contains(x, y)) {
				System.out.println("A:" + i);
			}
		}

		return super.onTouchEvent(event);
	}

	/**
	 * 初始化矩形框
	 */
	public void initRect() {
		cellCount2 = 4 * tableCol;
		cellRect = new Rect[cellCount2];
		cellWidth = (screenWidth - swEach * 2) / 6;// 表格宽度
		cellHeight = (screenHeight - shEach - tableY) / 4;// 表格高度
		int cellX = tableX;
		int cellY = tableY;
		int k = 0;
		for (int i = 0; i < cellCount2; i++) {
			int j = i % tableCol;
			cellX = tableX + k * cellWidth;
			if (j == 0 && i != 0) {
				cellX = tableX;
				cellY = cellY + cellHeight;
				k = 0;
			}
			cellRect[i] = new Rect(cellX, cellY, cellX + cellWidth, cellY
					+ cellHeight);
			k++;
		}

	}

}
