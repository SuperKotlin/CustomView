package com.zhuyong.daydaycode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义单字符展示控件
 */
public class CustomTextView extends View {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 背景框画笔
     */
    private Paint mPaintBackground;
    /**
     * 背景框边框画笔
     */
    private Paint mPaintBackgroundLine;
    /**
     * 文字画笔
     */
    private Paint mPaintText;
    /**
     * 间隔宽度
     */
    private float mDividerWidth;
    /**
     * 背景框宽度
     */
    private float mRectWidth;
    /**
     * 背景框高度
     */
    private float mRectHeight;
    /**
     * 背景框圆角
     */
    private float mRadius;
    /**
     * 背景框边框宽度
     */
    private float mRectFLineWidth;
    /**
     * 字符数据
     */
    private char[] mTextArr = new char[]{};

    //构造函数
    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String str = array.getString(R.styleable.CustomTextView_ct_text);
        //这里判断字符串是否为null，否则会异常
        if (TextUtils.isEmpty(str)) str = "";
        float textSize = array.getDimension(R.styleable.CustomTextView_ct_textSize, ScreenUtils.sp2px(mContext, 14));
        int textColor = array.getColor(R.styleable.CustomTextView_ct_textColor, Color.WHITE);
        mRectWidth = array.getDimension(R.styleable.CustomTextView_ct_rect_width, ScreenUtils.dip2px(mContext, 30));
        mRectHeight = array.getDimension(R.styleable.CustomTextView_ct_rect_height, ScreenUtils.dip2px(mContext, 40));
        mDividerWidth = array.getDimension(R.styleable.CustomTextView_ct_rect_divider, ScreenUtils.dip2px(mContext, 4));
        int mRectBackgroundColor = array.getColor(R.styleable.CustomTextView_ct_rect_background_color, Color.BLACK);
        mRadius = array.getDimension(R.styleable.CustomTextView_ct_rect_radius, ScreenUtils.dip2px(mContext, 4));
        int mRectFLineColor = array.getColor(R.styleable.CustomTextView_ct_rect_line_color, Color.BLACK);
        mRectFLineWidth = array.getDimension(R.styleable.CustomTextView_ct_rect_line_width, ScreenUtils.dip2px(mContext, 2));
        array.recycle();

        initView(str, textSize, textColor, mRectBackgroundColor, mRectFLineColor);

    }

    /**
     * 初始化
     *
     * @param str                  字符串
     * @param textSize             文字大小
     * @param textColor            文字颜色
     * @param mRectBackgroundColor 背景框颜色
     * @param mRectFLineColor      边颜色
     */
    private void initView(String str, float textSize, int textColor, int mRectBackgroundColor, int mRectFLineColor) {

        /**
         * 初始化字符数组
         */
        char a[] = str.toCharArray();
        this.mTextArr = a;

        /**
         * 文字画笔初始化
         */
        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(textColor);
        mPaintText.setTextSize(textSize);

        /**
         *背景框画笔初始化
         */
        mPaintBackground = new Paint();
        mPaintBackground.setAntiAlias(true);
        mPaintBackground.setColor(mRectBackgroundColor);
        mPaintBackground.setStyle(Paint.Style.FILL);

        /**
         *边框画笔初始化
         */
        mPaintBackgroundLine = new Paint();
        mPaintBackgroundLine.setAntiAlias(true);
        mPaintBackgroundLine.setColor(mRectFLineColor);
        mPaintBackgroundLine.setStyle(Paint.Style.STROKE);
        mPaintBackgroundLine.setStrokeWidth(mRectFLineWidth);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 获取字符的数量
         * 计算总间隔宽度和总背景框的宽度之和，就是整个View的宽度
         * 背景框的高度作为Vie的高度
         */
        int size = mTextArr.length;
        float mDividerWidthTotal = (size - 1) * mDividerWidth;
        float mmRectWidthTotal = size * mRectWidth;
        setMeasuredDimension((int) (mDividerWidthTotal + mmRectWidthTotal), (int) mRectHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mTextArr.length; i++) {

            //计算第i个背景框的左上角的x坐标
            float start = i * (mRectWidth + mDividerWidth);
            //初始化RectF的坐标，这里需要注意考虑画笔本身的宽度
            RectF rectF = new RectF(start + mRectFLineWidth / 2, mRectFLineWidth / 2,
                    start + mRectWidth - mRectFLineWidth / 2, getHeight() - mRectFLineWidth / 2);
            //绘制背景框
            canvas.drawRoundRect(rectF, mRadius, mRadius, mPaintBackground);
            //绘制边框
            canvas.drawRoundRect(rectF, mRadius, mRadius, mPaintBackgroundLine);
            //绘制字符
            String text = mTextArr[i] + "";
            //获取字符宽度
            float textWidth = mPaintText.measureText(text, 0, text.length());
            float dx = start + mRectWidth / 2 - textWidth / 2;
            Paint.FontMetricsInt fontMetricsInt = mPaintText.getFontMetricsInt();
            float dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
            float baseLine = getHeight() / 2 + dy;
            canvas.drawText(text, dx, baseLine, mPaintText);
        }
    }

    /**
     * 赋值-测量-绘制
     *
     * @param str
     */
    public void start(String str) {
        char a[] = str.toCharArray();
        this.mTextArr = a;
        /**
         * 重新测量
         */
        requestLayout();
    }

    /**
     * 修改边框颜色
     *
     * @param colorStr
     */
    public void setRectLineColor(String colorStr) {
        try {
            int color = Color.parseColor(colorStr);
            mPaintBackgroundLine.setColor(color);
            invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置边框的宽度
     *
     * @param mRectFLineWidth
     */
    public void setmRectFLineWidth(float mRectFLineWidth) {
        this.mRectFLineWidth = mRectFLineWidth;
        mPaintBackgroundLine.setStrokeWidth(this.mRectFLineWidth);
        invalidate();
    }

    /**
     * 设置背景框的宽度
     *
     * @param mRectWidth
     */
    public void setmRectWidth(float mRectWidth) {
        this.mRectWidth = mRectWidth;
        requestLayout();
    }

    /**
     * 设置背景框的高度
     *
     * @param mRectHeight
     */
    public void setmRectHeight(float mRectHeight) {
        this.mRectHeight = mRectHeight;
        requestLayout();
    }

    /**
     * 修改背景颜色
     *
     * @param colorStr
     */
    public void setRectColor(String colorStr) {
        try {
            int color = Color.parseColor(colorStr);
            mPaintBackground.setColor(color);
            invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置圆角大小
     *
     * @param mRadius
     */
    public void setmRadius(float mRadius) {
        this.mRadius = mRadius;
        invalidate();
    }

    /**
     * 设置字符的大小
     *
     * @param size
     */
    public void setTextSize(float size) {
        mPaintText.setTextSize(size);
        invalidate();
    }

    /**
     * 修改文字颜色
     *
     * @param colorStr
     */
    public void setTextColor(String colorStr) {
        try {
            int color = Color.parseColor(colorStr);
            mPaintText.setColor(color);
            invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置背景框直接的间隔宽度
     *
     * @param mDividerWidth
     */
    public void setmDividerWidth(float mDividerWidth) {
        this.mDividerWidth = mDividerWidth;
        requestLayout();
    }
}
