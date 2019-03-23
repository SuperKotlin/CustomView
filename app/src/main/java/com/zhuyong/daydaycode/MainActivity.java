package com.zhuyong.daydaycode;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    private CustomTextView mCustomTextView;

    private EditText mEtTextStr;
    private EditText mEtTextColor;
    private EditText mEtRectColor;
    private EditText mEtRectLineColor;

    private SeekBar mSeekBarWidth;
    private SeekBar mSeekBarHeight;
    private SeekBar mSeekBarLineWidth;
    private SeekBar mSeekBarDividerWidth;
    private SeekBar mSeekBarRadius;
    private SeekBar mSeekBarTextSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        mContext = this;

        mCustomTextView = findViewById(R.id.custom_view);
        mEtTextStr = findViewById(R.id.et_str);
        mEtTextColor = findViewById(R.id.seek_text_color);
        mEtRectColor = findViewById(R.id.seek_rect_color);
        mEtRectLineColor = findViewById(R.id.seek_rect_line_color);

        mSeekBarWidth = findViewById(R.id.seek_rect_width);
        mSeekBarHeight = findViewById(R.id.seek_rect_height);
        mSeekBarLineWidth = findViewById(R.id.seek_line_width);
        mSeekBarDividerWidth = findViewById(R.id.seek_divider_width);
        mSeekBarRadius = findViewById(R.id.seek_radius);
        mSeekBarTextSize = findViewById(R.id.seek_text_size);

        initView();

    }

    private void initView() {

        mEtTextStr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCustomTextView.start(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSeekBarWidth.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());
        mSeekBarHeight.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());
        mSeekBarLineWidth.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());
        mSeekBarDividerWidth.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());
        mSeekBarRadius.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());
        mSeekBarTextSize.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());
    }

    /**
     * 修文字颜色
     *
     * @param view
     */
    public void onClickTextColor(View view) {
        String mColorStr = mEtTextColor.getText().toString().trim();
        mCustomTextView.setTextColor(mColorStr);
    }


    /**
     * 修背景颜色
     *
     * @param view
     */
    public void onClickRectColor(View view) {
        String mColorStr = mEtRectColor.getText().toString().trim();
        mCustomTextView.setRectColor(mColorStr);
    }

    /**
     * 修背景颜色
     *
     * @param view
     */
    public void onClickRectLineColor(View view) {
        String mColorStr = mEtRectLineColor.getText().toString().trim();
        mCustomTextView.setRectLineColor(mColorStr);
    }


    /**
     * 自定义SeekBar监听类
     */
    private class MyOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.seek_text_size://文字大小调节
                    mCustomTextView.setTextSize(ScreenUtils.sp2px(mContext, progress + 10));
                    break;
                case R.id.seek_radius://背景框圆角调节
                    mCustomTextView.setmRadius(ScreenUtils.dip2px(mContext, progress));
                    break;
                case R.id.seek_divider_width://背景框间隔宽度调节
                    mCustomTextView.setmDividerWidth(ScreenUtils.dip2px(mContext, progress + 3));
                    break;
                case R.id.seek_line_width://边框宽度调节
                    mCustomTextView.setmRectFLineWidth(ScreenUtils.dip2px(mContext, progress));
                    break;
                case R.id.seek_rect_height://背景框高度调节
                    mCustomTextView.setmRectHeight(ScreenUtils.dip2px(mContext, progress + 30));
                    break;
                case R.id.seek_rect_width://背景框宽度调节
                    mCustomTextView.setmRectWidth(ScreenUtils.dip2px(mContext, progress + 20));
                    break;
                default:
                    break;
            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

}
