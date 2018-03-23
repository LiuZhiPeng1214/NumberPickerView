package com.eme.numberpickerlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by eme on 2018/3/21.
 */

public class NumberPickerView extends LinearLayout implements View.OnClickListener,TextWatcher{
    //当前输入框可输入的值（默认为不限制）
    private int maxValue = Integer.MAX_VALUE;

    //当前的库存量（默认为不限制）
    private int currentInventory = Integer.MAX_VALUE;

    //默认字体的大小
    private int textDefaultSize = 13;

    // 中间输入框的‘输入值
    public EditText mNumText;

    //默认输入框最小值
    private int minDefaultNum = 0;

    // 默认设置起批量
    private int moq = 1;

    // 监听事件(负责警戒值回调)
    private OnClickInputListener onClickInputListener;
    public  TextView             mSubText;
    public  TextView             mAddText;

    public NumberPickerView(Context context) {
        super(context);
    }

    public NumberPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initNumberPickerView(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void initNumberPickerView(final Context context, AttributeSet attrs) {
        //加载定义好的布局文件
        LayoutInflater.from(context).inflate(R.layout.number_button, this);
        mSubText = (TextView) findViewById(R.id.button_sub);
        mAddText = (TextView) findViewById(R.id.button_add);
        mNumText = (EditText) findViewById(R.id.middle_count);

        //添加监听事件
        mAddText.setOnClickListener(this);
        mSubText.setOnClickListener(this);
        mNumText.setOnClickListener(this);
        mNumText.addTextChangedListener(this);

        //获取自定义属性的相关内容
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberButton);
        //中间的编辑框是否可编辑
        boolean aBoolean = typedArray.getBoolean(R.styleable.NumberButton_editable, true);
        //+和-文本的宽度 geDiemension返回float getDimensionPixelSize四舍五入+  getDimensionPixeloffset四舍五入-
        int buttonWidth = typedArray.getDimensionPixelSize(R.styleable.NumberButton_buttonWidth, -1);
        //+和-文本的颜色
        int textColor = typedArray.getColor(R.styleable.NumberButton_textColor, 0xff000000);
        //+和-文本的字体大小
        int textSize = typedArray.getDimensionPixelSize(R.styleable.NumberButton_textSize, -1);
        // 中间显示数量的按钮宽度
        final int editextWidth = typedArray.getDimensionPixelSize(R.styleable.NumberButton_editextWidth, -1);
        //必须调用这个，因为自定义View会随着Activity创建频繁的创建array
        typedArray.recycle();

        //设置输入框是否可用
        setEditable(aBoolean);
        //初始化控件颜色

        mNumText.setTextColor(textColor);

        //初始化字体,注意默认的是px单位，要转换
        if (textSize > 0) {
            mNumText.setTextSize(px2sp(context, textSize));
        } else {
            mNumText.setTextSize(textDefaultSize);
        }

        //设置文本框的宽高
        if (buttonWidth > 0) {
            LayoutParams layoutParams = new LayoutParams(buttonWidth, LayoutParams.MATCH_PARENT);
            mAddText.setLayoutParams(layoutParams);
            mSubText.setLayoutParams(layoutParams);
        } else {
            Log.d("NumPickerView", "文本采用默认的宽高");
        }
        //设置输入框的宽高
        if (editextWidth > 0) {
            LayoutParams layoutParams = new LayoutParams(editextWidth, LayoutParams.MATCH_PARENT);
            mNumText.setLayoutParams(layoutParams);
        } else {
            Log.d("NumPickerView", "编辑框采用默认的宽高");
        }
    }

    /**
     * @param editable 设置输入框是否可编辑
     */
    private void setEditable(boolean editable) {
        if (editable) {
            mNumText.setFocusable(true);
            mNumText.setKeyListener(new DigitsKeyListener());
        } else {
            mNumText.setFocusable(false);
            mNumText.setKeyListener(null);
        }
    }

    /**
     * @return 获取输入框的最终数字值
     */
    public int getNumText() {
        try {
            String textNum = mNumText.getText().toString().trim();
            return Integer.parseInt(textNum);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            mNumText.setText(String.valueOf(minDefaultNum));
            return minDefaultNum;
        }
    }

    /**
     * 设置当前的最大值，即库存的上限
     */
    public NumberPickerView setCurrentInventory(int maxInventory) {
        this.currentInventory = maxInventory;
        return this;
    }

    /**
     * @return 获取当前的库存
     */
    public int getCurrentInvventory() {
        return currentInventory;
    }

    public int getMinDefaultNum() {
        return minDefaultNum;
    }

    public NumberPickerView setMoq(int moq) {
        if (moq <= 0) {
            this.moq = 1;
        }else {
            this.moq = moq;
        }
        return this;
    }
    public int  getMoq() {
        return moq;
    }
    /**
     * 设置默认的最小值
     *
     * @param minDefaultNum
     * @return
     */
    public NumberPickerView setMinDefaultNum(int minDefaultNum) {
        this.minDefaultNum = minDefaultNum;
        return this;
    }

    public int getMaxValue() {
        return maxValue;
    }

    /**
     *  最大限制量
     * @param maxValue
     * @return
     */
    public NumberPickerView setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    /**
     * @param currentNum 设置当前输入框的值
     * @return NumPickerView
     */
    public NumberPickerView setCurrentNum(int currentNum) {
        if (currentNum > minDefaultNum) {
            if (currentNum <= currentInventory) {
                mNumText.setText(String.valueOf(currentNum));
            } else if(currentNum >maxValue){
                mNumText.setText(String.valueOf(maxValue));
            }else {
                mNumText.setText(String.valueOf(currentInventory));
            }
        } else {
            mNumText.setText(String.valueOf(minDefaultNum));
        }
        return this;
    }

    public NumberPickerView setmOnClickInputListener(OnClickInputListener mOnWarnListener) {
        this.onClickInputListener = mOnWarnListener;
        return this;
    }

    @Override
    public void onClick(View view) {
        int widgetId = view.getId();
        int numText = getNumText();
        if (widgetId == R.id.button_sub) {
            if ((numText-1) * moq > minDefaultNum + 1) {
                mNumText.setText(String.valueOf(numText - 1));
                subListener();
            } else {
                mNumText.setText(String.valueOf(minDefaultNum));
                //小于警戒值
                mSubText.setEnabled(false);
                mAddText.setEnabled(true);
                warningForMinInput();
                Log.d("NumberPicker", "减少已经到达极限");
            }
        } else if (widgetId == R.id.button_add) {
            if (maxValue< currentInventory) {
                if ((numText+1) * moq < maxValue) {
                    mNumText.setText(String.valueOf(numText + 1));
                    addListener();
                }else {
                    // 超过限制数量
                    mNumText.setText(String.valueOf(maxValue / moq));
                    mSubText.setEnabled(true);
                    mAddText.setEnabled(false);
                    warningForMaxInput();
                }
            }else if (maxValue >= currentInventory){
                if ((numText+1) * moq < currentInventory) {
                    mNumText.setText(String.valueOf(numText + 1));
                    addListener();
                }else {
                    // 超过限制数量
                    mNumText.setText(String.valueOf(currentInventory / moq));
                    mSubText.setEnabled(true);
                    mAddText.setEnabled(false);
                    warningForInventory();
                }
            }


        }
        mNumText.setSelection(mNumText.getText().toString().length());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        try {
            mNumText.removeTextChangedListener(this);
            String inputText = editable.toString().trim();
            if (inputText.startsWith("0") && inputText.length()>1) {
                inputText = inputText.substring(1,inputText.length());
            }
            if (!TextUtils.isEmpty(inputText)) {
                int inputNum = Integer.parseInt(inputText);
                if (inputNum *moq < minDefaultNum) {
                    // 小于警戒值
                    mNumText.setText(String.valueOf(minDefaultNum / moq));
                    warningForMinInput();
                    mSubText.setEnabled(false);
                    mAddText.setEnabled(true);
                } else if (maxValue < currentInventory) {
                    //限量值小于库存
                    System.out.println("限量值小于库存");
                    if (inputNum * moq < maxValue) {
                        mNumText.setText(inputText);
                        mSubText.setEnabled(true);
                        mAddText.setEnabled(true);
                        editListener();
                    } else if (inputNum * moq >= maxValue ) {
                         // 超过限量
                        mNumText.setText(String.valueOf(maxValue / 4));
                        mSubText.setEnabled(true);
                        mAddText.setEnabled(false);
                        warningForMaxInput();
                    }

                } else if (maxValue >= currentInventory) {
                    System.out.println("限量值大于 库存");
                    // 限量值大于 库存
                    if (inputNum * moq < currentInventory) {
                        mNumText.setText(inputText);
                        mSubText.setEnabled(true);
                        mAddText.setEnabled(true);
                        editListener();
                    } else if (inputNum * moq >= currentInventory ) {
                        // 超过限量
                        mNumText.setText(String.valueOf(currentInventory / moq));
                        mSubText.setEnabled(true);
                        mAddText.setEnabled(false);
                        warningForInventory();
                    }
                }
            }else {
                mNumText.setText(String.valueOf(0));
                mSubText.setEnabled(false);
                mAddText.setEnabled(true);
                editListener();
            }
            mNumText.addTextChangedListener(this);
            mNumText.setSelection(mNumText.getText().toString().length());
        } catch (NumberFormatException e) {
            e.printStackTrace();

        }

    }

    /**
     * 超过的库存限制
     * Warning for inventory.
     */
    private void warningForInventory() {
        if (onClickInputListener != null)
            onClickInputListener.onWarningForInventory(currentInventory);
    }

    /**
     * 小于最小值回调
     * Warning for inventory.
     */
    private void warningForMinInput() {
        if (onClickInputListener != null)
            onClickInputListener.onWarningMinInput(minDefaultNum);
    }

    /**
     * 查过最大值值回调
     * Warning for inventory.
     */
    private void warningForMaxInput() {
        if (onClickInputListener != null)
            onClickInputListener.onWarningMaxInput(maxValue);
    }

    private void subListener() {
        if (onClickInputListener != null) {
            onClickInputListener.subListener(getNumText());
        }
    }

    private void addListener() {
        if (onClickInputListener != null) {
            onClickInputListener.addListener(getNumText());
        }
    }

    private void editListener() {
        if (onClickInputListener != null) {
            onClickInputListener.editListener(getNumText());
        }
    }
    /**
     * 超过警戒值回调
     */
    public interface OnClickInputListener {
        void onWarningForInventory(int inventory);

        void onWarningMinInput(int minValue);

        void onWarningMaxInput(int maxValue);

        void subListener(int currentValue);
        void addListener(int currentValue);
        void editListener(int currentValue);


    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

}
