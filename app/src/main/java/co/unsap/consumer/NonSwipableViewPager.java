package co.unsap.consumer;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by sriven on 7/4/2018.
 */

public class NonSwipableViewPager extends ViewPager {
	public NonSwipableViewPager(Context context){
		super(context);
	}
	public NonSwipableViewPager(Context context , AttributeSet attributeSet){
		super(context,attributeSet);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		// Never allow swiping to switch between pages
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Never allow swiping to switch between pages
		return false;
	}

}
