
package com.liqi.test3399.view;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PointerLocationView extends View implements OnTouchListener {
	public static class PointerState {
		private final ArrayList<Float> mXs = new ArrayList<Float>();
		private final ArrayList<Float> mYs = new ArrayList<Float>();
		private boolean mCurDown;
		private int mCurX;
		private int mCurY;
		private float mCurPressure;
		private float mCurSize;
		private int mCurWidth;
		private VelocityTracker mVelocity;
	}

	private int NP = 0;
	private final Paint mTextPaint;
	private final Paint mPaint;
	private final Paint mTargetPaint;
	private final Paint mPathPaint;
	private boolean mCurDown;
	private int mCurNumPointers;
	private int mMaxNumPointers;
	private final ArrayList<PointerState> mPointers = new ArrayList<PointerState>();

	private boolean mPrintCoords = true;

	public PointerLocationView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PointerLocationView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		setFocusable(true);
		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTextSize(50 * getResources().getDisplayMetrics().density);
		mTextPaint.setARGB(255, 255, 0, 0);
		mTextPaint.setTextAlign(Align.RIGHT);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setARGB(255, 255, 255, 255);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(2);
		mTargetPaint = new Paint();
		mTargetPaint.setAntiAlias(false);
		mTargetPaint.setARGB(255, 0, 0, 192);
		mPathPaint = new Paint();
		mPathPaint.setAntiAlias(false);
		mPathPaint.setARGB(255, 0, 96, 255);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(1);

		PointerState ps = new PointerState();
		ps.mVelocity = VelocityTracker.obtain();
		mPointers.add(ps);
	}

	public void setPrintCoords(boolean state) {
		mPrintCoords = state;
	}

	
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		((ViewGroup)getParent()).setOnTouchListener(this);
	}

	
	protected void onDraw(Canvas canvas) {
		synchronized (mPointers) {
			NP = mPointers.size();
			if (onPointCountChangeListener != null) {
				onPointCountChangeListener.onPointCountChange(mPointers.size());
			}
			/*canvas.drawText("Current Point:" + mCurNumPointers, getWidth() / 2, getHeight() / 2,
					mTextPaint);*/

			for (int p = 0; p < NP; p++) {
				final PointerState ps = mPointers.get(p);

				if (mCurDown && ps.mCurDown) {
					canvas.drawLine(0, (int) ps.mCurY, getWidth(),
							(int) ps.mCurY, mTargetPaint);
					canvas.drawLine((int) ps.mCurX, 0, (int) ps.mCurX,
							getHeight(), mTargetPaint);
					int pressureLevel = (int) (ps.mCurPressure * 255);
					mPaint.setARGB(255, pressureLevel, 128, 255 - pressureLevel);
					canvas.drawPoint(ps.mCurX, ps.mCurY, mPaint);
					canvas.drawCircle(ps.mCurX, ps.mCurY, ps.mCurWidth, mPaint);
				}
			}

			for (int p = 0; p < NP; p++) {
				final PointerState ps = mPointers.get(p);

				final int N = ps.mXs.size();
				float lastX = 0, lastY = 0;
				boolean haveLast = false;
				boolean drawn = false;
				mPaint.setARGB(255, 128, 255, 255);
				for (int i = 0; i < N; i++) {
					float x = ps.mXs.get(i);
					float y = ps.mYs.get(i);
					if (Float.isNaN(x)) {
						haveLast = false;
						continue;
					}
					if (haveLast) {
						canvas.drawLine(lastX, lastY, x, y, mPathPaint);
						canvas.drawPoint(lastX, lastY, mPaint);
						drawn = true;
					}
					lastX = x;
					lastY = y;
					haveLast = true;
				}

				if (drawn) {
					if (ps.mVelocity != null) {
						mPaint.setARGB(255, 255, 64, 128);
						float xVel = ps.mVelocity.getXVelocity() * (1000 / 60);
						float yVel = ps.mVelocity.getYVelocity() * (1000 / 60);
						canvas.drawLine(lastX, lastY, lastX + xVel, lastY
								+ yVel, mPaint);
					} else {
						canvas.drawPoint(lastX, lastY, mPaint);
					}
				}
			}
		}
	}

	public void addTouchEvent(MotionEvent event) {
		synchronized (mPointers) {
			int action = event.getAction();

			NP = mPointers.size();

			if (action == MotionEvent.ACTION_DOWN) {
				for (int p = 0; p < NP; p++) {
					final PointerState ps = mPointers.get(p);
					ps.mXs.clear();
					ps.mYs.clear();
					ps.mVelocity = VelocityTracker.obtain();
					ps.mCurDown = false;
				}
				mPointers.get(0).mCurDown = true;
				mMaxNumPointers = 0;
				if (mPrintCoords) {
					Log.i("Pointer", "Pointer 1: DOWN");
				}
			}

			if ((action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN) {

				Log.i("Pointer", "action down");
				final int index = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
				final int id = event.getPointerId(index);
				while (NP <= id) {
					PointerState ps = new PointerState();
					ps.mVelocity = VelocityTracker.obtain();
					mPointers.add(ps);
					NP++;
				}
				final PointerState ps = mPointers.get(id);
				ps.mVelocity = VelocityTracker.obtain();
				ps.mCurDown = true;
				if (mPrintCoords) {
					Log.i("Pointer", "Pointer " + (id + 1) + ": DOWN");
				}
			}

			final int NI = event.getPointerCount();

			mCurDown = action != MotionEvent.ACTION_UP
					&& action != MotionEvent.ACTION_CANCEL;
			mCurNumPointers = mCurDown ? NI : 0;
			if (mMaxNumPointers < mCurNumPointers) {
				mMaxNumPointers = mCurNumPointers;
			}

			for (int i = 0; i < NI; i++) {
				final int id = event.getPointerId(i);
				final PointerState ps = mPointers.get(id);
				ps.mVelocity.addMovement(event);
				ps.mVelocity.computeCurrentVelocity(1);
				final int N = event.getHistorySize();
				for (int j = 0; j < N; j++) {
					if (mPrintCoords) {
						Log.i("Pointer",
								"Pointer " + (id + 1) + ": ("
										+ event.getHistoricalX(i, j) + ", "
										+ event.getHistoricalY(i, j) + ")"
										+ " Prs="
										+ event.getHistoricalPressure(i, j)
										+ " Size="
										+ event.getHistoricalSize(i, j));
					}
					ps.mXs.add(event.getHistoricalX(i, j));
					ps.mYs.add(event.getHistoricalY(i, j));
				}
				if (mPrintCoords) {
					Log.i("Pointer",
							"Pointer " + (id + 1) + ": (" + event.getX(i)
									+ ", " + event.getY(i) + ")" + " Prs="
									+ event.getPressure(i) + " Size="
									+ event.getSize(i));
				}
				ps.mXs.add(event.getX(i));
				ps.mYs.add(event.getY(i));
				ps.mCurX = (int) event.getX(i);
				ps.mCurY = (int) event.getY(i);
				// Log.i("Pointer", "Pointer #" + p + ": (" + ps.mCurX
				// + "," + ps.mCurY + ")");
				ps.mCurPressure = event.getPressure(i);
				ps.mCurSize = event.getSize(i);
				ps.mCurWidth = (int) (ps.mCurSize * (getWidth() / 3));
			}

			if ((action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP) {

				Log.i("Pointer", "action up");
				final int index = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
				final int id = event.getPointerId(index);
				final PointerState ps = mPointers.get(id);
				ps.mXs.add(Float.NaN);
				ps.mYs.add(Float.NaN);
				ps.mCurDown = false;
				if (mPrintCoords) {
					Log.i("Pointer", "Pointer " + (id + 1) + ": UP");
				}
			}

			if (action == MotionEvent.ACTION_UP) {
				for (int i = 0; i < NI; i++) {
					final int id = event.getPointerId(i);
					final PointerState ps = mPointers.get(id);
					if (ps.mCurDown) {
						ps.mCurDown = false;
						if (mPrintCoords) {
							Log.i("Pointer", "Pointer " + (id + 1) + ": UP");
						}
					}
				}
			}

			postInvalidate();
		}
	}


	public interface OnPointCountChangeListener {
		public void onPointCountChange(int newPointCount);
	}

	private OnPointCountChangeListener onPointCountChangeListener;

	public void setOnPointCountChangeListener(
			OnPointCountChangeListener onPointCountChangeListener) {
		this.onPointCountChangeListener = onPointCountChangeListener;
	}
	
	
	public boolean onTouch(View v, MotionEvent event) {
		addTouchEvent(event);

		return true;
	}
}
