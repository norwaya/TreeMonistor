package com.xabaizhong.treemonistor.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;

/**
 * Created by admin on 2017/3/6.
 */

public class C_info_gather_item2 extends LinearLayout {
    private Context context;
    private RelativeLayout layout;
    View view;
    TextView left_text;
    Button mid_button;
    ImageView right_image;

    String left_text_content;
    float left_width;
    int left_font_size;
    int left_font_color;
    float mid_font_size;
    int mid_font_color;
    int right_src;
    /*<attr name="left_text" format="string"/>
    <attr name="left_width" format="dimension"/>
    <attr name="left_font_size" format="integer"/>
    <attr name="left_font_color" format="color"/>
    <attr name="mid_font_size" format="dimension"/>
    <attr name="mid_font_color" format="color"/>
    <attr name="right_src" format="reference"/>*/

    public C_info_gather_item2(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.C_info_gather_item);
        left_text_content = ta.getString(R.styleable.C_info_gather_item_left_text);
        left_width = ta.getDimension(R.styleable.C_info_gather_item_left_width, 100f);
        left_font_size = ta.getInt(R.styleable.C_info_gather_item_left_font_size, 20);
        left_font_color = ta.getColor(R.styleable.C_info_gather_item_left_font_color, Color.BLACK);
        mid_font_size = ta.getDimension(R.styleable.C_info_gather_item_mid_font_size, 20f);
        mid_font_color = ta.getColor(R.styleable.C_info_gather_item_mid_font_color, Color.BLACK);
        right_src = ta.getResourceId(R.styleable.C_info_gather_item_right_src, R.drawable.ic_fragment_expert_bug);
        ta.recycle();

        this.view = LayoutInflater.from(context).inflate(R.layout.custom_view_button_view, null);
        findView();
        initLayout();
        initLeft();
        initMid();
        initRight();
        addView(view);

    }

    private void initLayout() {
        layout.setBackground(getResources().getDrawable(R.drawable.edittext_style_line_normal));
    }

    private void initLeft() {
        left_text.setText(left_text_content);
        left_text.setWidth(dpToPx(context, left_width));
        left_text.setTextSize(spToPx(context, left_font_size));
        left_text.setTextColor(left_font_color);

    }

    private void initMid() {
        mid_button.setBackgroundColor(Color.TRANSPARENT);
        mid_button.setTextSize(spToPx(context, mid_font_size));
        mid_button.setTextColor(mid_font_color);
    }


    private void initRight() {
        right_image.setImageDrawable(getResources().getDrawable(right_src));

    }

    private void findView() {
        layout = (RelativeLayout) view.findViewById(R.id.layout);
        left_text = (TextView) view.findViewById(R.id.left_text);
        mid_button = (Button) view.findViewById(R.id.mid_button);
        right_image = (ImageView) view.findViewById(R.id.right_image);
    }

    private CallBack callback;

    public void setCallback(CallBack callback) {
        this.callback = callback;
    }

    public interface CallBack {
        void onClickListener(View v);
    }

    public int dpToPx(Context context, float dpValue) {//dp转换为px
        float scale = context.getResources().getDisplayMetrics().density;//获得当前屏幕密度
        return (int) (dpValue * scale + 0.5f);
    }

    public static int spToPx(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
