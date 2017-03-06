package com.xabaizhong.treemonitor.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RadioButton;

import com.xabaizhong.treemonitor.R;

/**
 * Created by admin on 2017/2/23.
 */

public class MyRadio extends RadioButton {
    public MyRadio(Context context) {
        this(context,null);
    }

    public MyRadio(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }


    private int mDrawableSize;// xml文件中设置的大小


    public MyRadio(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        Drawable drawableLeft = null, drawableTop = null, drawableRight = null, drawableBottom = null;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MyRadio);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            Log.i("MyRadio", "attr:" + attr);
            switch (attr) {
                case R.styleable.MyRadio_drawableSize:
                    mDrawableSize = a.getDimensionPixelSize(R.styleable.MyRadio_drawableSize, 50);
                    Log.i("MyRadio", "mDrawableSize:" + mDrawableSize);
                    break;
                case R.styleable.MyRadio_drawableTop:
                    drawableTop = a.getDrawable(attr);
                    break;
                case R.styleable.MyRadio_drawableBottom:
                    drawableRight = a.getDrawable(attr);
                    break;
                case R.styleable.MyRadio_drawableRight:
                    drawableBottom = a.getDrawable(attr);
                    break;
                case R.styleable.MyRadio_drawableLeft:
                    drawableLeft = a.getDrawable(attr);
                    break;
                default:
                    break;
            }
        }
        a.recycle();

        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);

    }

    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left,
                                                        Drawable top, Drawable right, Drawable bottom) {

        if (left != null) {
            left.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (right != null) {
            right.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (top != null) {
            top.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        setCompoundDrawables(left, top, right, bottom);
    }


    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        if(isChecked()){
            setTextColor(Color.parseColor("#04b00f"));
            if(onRadioCheckedListenter != null)
                onRadioCheckedListenter.checked(radioId);
        }else{
            setTextColor(Color.BLACK);
        }
    }
    public interface OnRadioCheckedListenter{
        int NEWS = 0;
        int FUNCTION = 1;
        int EXPERT = 2;
        int SETTING = 3;
        void checked(int page);
    }
    OnRadioCheckedListenter onRadioCheckedListenter;
    int radioId;
    public void setOnRadioCheckedListenter(OnRadioCheckedListenter onRadioCheckedListenter,int id) {
        this.onRadioCheckedListenter = onRadioCheckedListenter;
        radioId = id;
    }
}


