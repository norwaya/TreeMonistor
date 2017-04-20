package com.xabaizhong.treemonistor.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;


/**
 * Created by admin on 2017/3/4.
 */

public class C_info_gather_item1 extends LinearLayout {
    String TAG = "my-edit-text";
    private Context context;
    private View view;


    private RelativeLayout layout;
    private TextView left;
    private EditText mid;
    private ImageView right;
    String left_text;
    float left_width;
    float left_font_size;
    int left_font_color;
    int mid_input_type;
    String mid_text_hint;
    float mid_font_size;
    int mid_color;
    int right_type;
    int right_icon;
    int mid_type;
    private Mid_CallBack callback_mid;
    private Right_CallBack callback_right;

    public C_info_gather_item1(Context context) {
        this(context, null);
    }

    public C_info_gather_item1(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.C_info_gather_item);
        left_text = ta.getString(R.styleable.C_info_gather_item_left_text);

        left_width = ta.getDimension(R.styleable.C_info_gather_item_left_width, 100f);
        left_font_size = ta.getDimension(R.styleable.C_info_gather_item_left_font_size, 14f);
        left_font_color = ta.getColor(R.styleable.C_info_gather_item_left_font_color, Color.BLACK);
        mid_text_hint = ta.getString(R.styleable.C_info_gather_item_mid_text_hint);
        mid_color = ta.getColor(R.styleable.C_info_gather_item_mid_font_color, Color.BLACK);
        mid_font_size = ta.getDimension(R.styleable.C_info_gather_item_mid_font_size, 14f);
        mid_input_type = ta.getInt(R.styleable.C_info_gather_item_input_type, 0);
        right_type = ta.getInt(R.styleable.C_info_gather_item_right_type, 0);
        right_icon = ta.getResourceId(R.styleable.C_info_gather_item_right_icon, android.R.drawable.ic_menu_close_clear_cancel);
        mid_type = ta.getInt(R.styleable.C_info_gather_item_mid_type, 0);
        ta.recycle();
        init();
    }

    private void init() {
        view = LayoutInflater.from(context).inflate(R.layout.custom_view_edit_view, null);
        finView();

        initLeft();
        initLayout();
        initMid();
        initRight();
        addView(view);
    }

    /*right*/
    final static int TYPE_FLAG = 0x001001;
    final static int TYPT_CLEAR = 0x001002;

    private void initRight() {
        OnClickListener listener = null;
        switch (right_type) {
            case TYPE_FLAG:
                right.setVisibility(VISIBLE);
                right.setImageDrawable(getResources().getDrawable(right_icon));
                listener = new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: ");
                        if (callback_right != null)
                            callback_right.onClickListener(v);
                    }
                };
                break;
            case TYPT_CLEAR:
                right.setVisibility(INVISIBLE);
                listener = new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mid.setText("");
                    }
                };
                break;
            default:
                right.setVisibility(INVISIBLE);
                listener = new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mid.setText("");
                    }
                };
                break;
        }

        right.setOnClickListener(listener);

    }


    final static int TYPE_READ = 0x001;
    final static int TYPE_WRITER = 0x002;

    private void initMid() {
        mid.setHint(mid_text_hint);
        mid.setTextSize(spToPx(context, mid_font_size));
        mid.setTextColor(mid_color);
        OnClickListener listener = null;


        switch (mid_type) {
            case TYPE_READ:
                mid.setFocusable(false);
                mid.setClickable(true);
                listener = new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback_mid != null)
                            callback_mid.onClickListener( v);
                    }
                };
                break;
            case TYPE_WRITER:
                listener = new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback_mid != null)
                            callback_mid.onClickListener( v);
                    }
                };
                initMidWhenWriter();
                break;
            default:
                listener = new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback_mid != null)
                            callback_mid.onClickListener(v);
                    }
                };
                initMidWhenWriter();
                Log.d(TAG, "initMid: " + mid_type);
                break;
        }
        mid.setOnClickListener(listener);
    }

    private void initMidWhenWriter() {
        setInputType();
        mid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mid.hasFocus() && !TextUtils.isEmpty(s))
                    right.setVisibility(VISIBLE);
                else
                    right.setVisibility(INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mid.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && !TextUtils.isEmpty(mid.getText()))
                    right.setVisibility(VISIBLE);
                else
                    right.setVisibility(INVISIBLE);

                if (hasFocus) {
                    layout.setBackground(getResources().getDrawable(R.drawable.edittext_style_line));
                } else {
                    layout.setBackground(getResources().getDrawable(R.drawable.edittext_style_line_normal));
                }

            }
        });
    }

    private void initLeft() {
        left.setWidth(dpToPx(context, left_width));
        left.setText(left_text);
        left.setTextSize(spToPx(context, left_font_size));
        left.setTextColor(left_font_color);
    }

    private void initLayout() {
        layout.setBackground(getResources().getDrawable(R.drawable.edittext_style_line_normal));
    }

    private void finView() {
        layout = (RelativeLayout) view.findViewById(R.id.layout);
        if (mid_type == TYPE_READ)
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: layout onclick");
                    if (callback_mid != null)
                        callback_mid.onClickListener(v);
                }
            });
        left = (TextView) view.findViewById(R.id.left);
        mid = (EditText) view.findViewById(R.id.mid);
        right = (ImageView) view.findViewById(R.id.right);
    }

    public String getText() {
        return mid.getText().toString();
    }

    public void setText(String text) {
        mid.setText(text);
    }

    final static int INPUT_TYPE_NUMBER = 0x002;
    final static int INPUT_TYPE_DECIMAL = 0x003;
    final static int INTPU_TYPE_TEXT = 0x004;
    private void setInputType() {

        switch (mid_input_type) {
            case INPUT_TYPE_NUMBER:
                mid.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case INPUT_TYPE_DECIMAL:
                mid.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            case INTPU_TYPE_TEXT:
                mid.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                break;
            default:
                break;
        }
    }

    public int dpToPx(Context context, float dpValue) {//dp转换为px
        float scale = context.getResources().getDisplayMetrics().density;//获得当前屏幕密度
        return (int) (dpValue * scale + 0.5f);
//        return (int) dpValue;
    }

    public static int spToPx(Context context, float spValue) {
//        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
//        return (int) (spValue * fontScale + 0.5f);
        return (int) spValue;
    }

    public interface Mid_CallBack {
        void onClickListener(View et);
    }

    public interface Right_CallBack {
        void onClickListener(View view);
    }

    public void setCallback_mid(Mid_CallBack callback_mid) {
        this.callback_mid = callback_mid;
    }

    public void setCallback_right(Right_CallBack callback_right) {
        this.callback_right = callback_right;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mid_type == TYPE_READ)
            return true;
        return super.onInterceptTouchEvent(ev);
    }

}
