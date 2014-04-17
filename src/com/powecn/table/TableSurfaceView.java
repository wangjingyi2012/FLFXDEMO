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

	public boolean mbLoop = false;// ����ѭ��

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

	public int cellCount = 0;// �������
	public int tableCol = 2;// Ĭ������
	public int tableRow = 1;// Ĭ��һ��
	public int lastRowCell = 0;// ���һ�ж���ĸ���

	// ���ݱ������
	public int tableX = 0, tableY = 0;// ���ĸ�λ�ÿ�ʼ��
	int cellWidth = 0;// �����
	int cellHeight = 0;// ���߶�
	int cellCount2 = 0;// �������

	// �����ο�
	public Rect[] cellRect;

	List<Student> datas;// ѧ������
	List<Student> selectedStudents;// ��ѡ���ѧ��

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
		canvas = mSurfaceHolder.lockCanvas();// �õ�����������
		if (mSurfaceHolder == null || canvas == null) {
			return;
		}

		paint.setColor(Color.BLACK);
		canvas.drawColor(Color.WHITE);
		paint.setTextSize(shEach);
		paint.setAntiAlias(true);
		// canvas.drawBitmap(selectedIco, 10, 10, paint);

		// ����ѧ��
		int k = 0, xPos = 0, yPos = shEach * 2;
		// ������X,Y����
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

		// ���Ʊ��
		this.drawTable(canvas, paint, 4, 6);
		Paint cellPaint2 = new Paint();
		cellPaint2.setAntiAlias(true);
		cellPaint2.setTextSize(shEach - 2);
		cellPaint2.setColor(Color.BLUE);
		canvas.drawText("��ѧ��", cellRect[1].left + 4, cellRect[1].bottom - 10,
				cellPaint2);
		canvas.drawText("������", cellRect[2].left + 4, cellRect[2].bottom - 10,
				cellPaint2);
		canvas.drawText("��е��", cellRect[3].left + 4, cellRect[3].bottom - 10,
				cellPaint2);
		canvas.drawText("��ȡ��", cellRect[4].left + 4, cellRect[4].bottom - 10,
				cellPaint2);
		canvas.drawText("������", cellRect[5].left + 4, cellRect[5].bottom - 10,
				cellPaint2);

		// ��һ��
		cellPaint2.setColor(Color.BLACK);
		cellPaint2.setStyle(Style.STROKE);
		canvas.drawText("��֪", cellRect[6].left + 12, cellRect[6].top + shEach,
				cellPaint2);
		canvas.drawText("����", cellRect[6].left + 12, cellRect[6].bottom - 10,
				cellPaint2);

		cellPaint2.setTextSize(swEach);
		canvas.drawText("��֪�ϰ�", cellRect[7].left + 5, cellRect[7].bottom - 20,
				cellPaint2);
		canvas.drawText("��֪����", cellRect[8].left + 5, cellRect[8].bottom - 20,
				cellPaint2);
		canvas.drawText("��֪Ƭ��", cellRect[9].left + 5, cellRect[9].bottom - 20,
				cellPaint2);
		canvas.drawText("����,����", cellRect[10].left + 5,
				cellRect[10].bottom - 20, cellPaint2);
		canvas.drawText("����,����,", cellRect[11].left + 5, cellRect[11].top + 25,
				cellPaint2);
		canvas.drawText("�и���,", cellRect[11].left + 5, cellRect[11].top + 20
				+ shEach, cellPaint2);
		canvas.drawText("��������", cellRect[11].left + 5, cellRect[11].top + 2
				* shEach + 15, cellPaint2);

		// �ڶ���
		cellPaint2.setTextSize(shEach - 2);
		canvas.drawText("����", cellRect[12].left + 12,
				cellRect[12].top + shEach, cellPaint2);
		canvas.drawText("����", cellRect[12].left + 12, cellRect[12].bottom - 10,
				cellPaint2);
		cellPaint2.setTextSize(swEach);
		canvas.drawText("�ᷳ,", cellRect[13].left + 15,
				cellRect[13].bottom - 40, cellPaint2);
		canvas.drawText("������", cellRect[13].left + 15,
				cellRect[13].bottom - 15, cellPaint2);
		canvas.drawText("����,��ľ", cellRect[14].left + 5,
				cellRect[14].bottom - 20, cellPaint2);

		cellPaint2.setTextSize(swEach - 2);
		canvas.drawText("ȫ����Ͷ��", cellRect[15].left + 5,
				cellRect[15].bottom - 40, cellPaint2);
		canvas.drawText("�̿��ù�,", cellRect[15].left + 5,
				cellRect[15].bottom - 25, cellPaint2);
		canvas.drawText("�����̹�", cellRect[15].left + 5,
				cellRect[15].bottom - 10, cellPaint2);

		cellPaint2.setTextSize(swEach);
		canvas.drawText("����", cellRect[16].left + 15, cellRect[16].bottom - 20,
				cellPaint2);
		canvas.drawText("�����м���", cellRect[17].left, cellRect[17].bottom - 40,
				cellPaint2);
		canvas.drawText("����Ȥ", cellRect[17].left, cellRect[17].bottom - 25,
				cellPaint2);
		canvas.drawText("����ѧϰ", cellRect[17].left, cellRect[17].bottom - 10,
				cellPaint2);

		// ������
		cellPaint2.setTextSize(shEach - 2);
		canvas.drawText("��־", cellRect[18].left + 12,
				cellRect[18].top + shEach, cellPaint2);
		canvas.drawText("����", cellRect[18].left + 12, cellRect[18].bottom - 10,
				cellPaint2);
		cellPaint2.setTextSize(swEach);
		canvas.drawText("����,�ִ�", cellRect[19].left + 5,
				cellRect[19].bottom - 20, cellPaint2);

		canvas.drawText("��Ҫѹ��", cellRect[20].left + 5,
				cellRect[20].bottom - 40, cellPaint2);
		canvas.drawText("����", cellRect[20].left + 5, cellRect[20].bottom - 15,
				cellPaint2);
		canvas.drawText("Ŭ��", cellRect[21].left + 5, cellRect[21].bottom - 40,
				cellPaint2);
		canvas.drawText("�����Ͱ�", cellRect[21].left + 5,
				cellRect[21].bottom - 15, cellPaint2);
		canvas.drawText("�Ծ�,����", cellRect[22].left + 5,
				cellRect[22].bottom - 20, cellPaint2);
		canvas.drawText("��־����", cellRect[23].left, cellRect[23].bottom - 40,
				cellPaint2);
		canvas.drawText("��ִ��Ŀ��", cellRect[23].left, cellRect[23].bottom - 15,
				cellPaint2);

		mSurfaceHolder.unlockCanvasAndPost(canvas);// ������ɲ���������
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
		cellWidth = (screenWidth - swEach * 2) / cols;// �����
		cellHeight = (screenHeight - shEach - tableY) / rows;// ���߶�
		canvas.drawLine(tableX, tableY, screenWidth - swEach, tableY, paint);// �ϱ�
		canvas.drawLine(tableX, tableY, tableX, screenHeight - shEach, paint);// ���
		canvas.drawLine(screenWidth - swEach, tableY, screenWidth - swEach,
				screenHeight - shEach, paint);// �ұ�
		canvas.drawLine(tableX, screenHeight - shEach, screenWidth - swEach,
				screenHeight - shEach, paint);// �ױ�
		// ����
		for (int i = 1; i < rows; i++) {
			canvas.drawLine(tableX, tableY + cellHeight * i, screenWidth
					- swEach, tableY + cellHeight * i, paint);
		}
		// ����
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
	 * ��ʼ�����ο�
	 */
	public void initRect() {
		cellCount2 = 4 * tableCol;
		cellRect = new Rect[cellCount2];
		cellWidth = (screenWidth - swEach * 2) / 6;// �����
		cellHeight = (screenHeight - shEach - tableY) / 4;// ���߶�
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
