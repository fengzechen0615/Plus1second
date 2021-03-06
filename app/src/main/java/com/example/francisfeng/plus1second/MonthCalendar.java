package com.example.francisfeng.plus1second;

/**
 * Created by francisfeng on 16/05/2017.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.francisfeng.plus1second_test.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MonthCalendar extends View{

    private String TAG = "MonthCalendar";

    private int mBgMonth, mBgWeek, mBgDay, mBgPre;
    private int mTextColorMonth;
    private float mTextSizeMonth;
    private int mMonthRowL, mMonthRowR;
    private float mMonthRowSpac;
    private float mMonthSpac;
    private int mTextColorWeek;
    private float mTextSizeWeek;
    private int mTextColorDay;
    private float mTextSizeDay;
    private int mTextColorPreFinish, mTextColorPreUnFinish;
    private float mTextSizePre;
    private int mSelectTextColor;
    private int mSelectBg, mCurrentBg;
    private float mSelectRadius, mCurrentBgStrokeWidth;
    private float[] mCurrentBgDashPath;

    private float mLineSpac;
    private float mTextSpac;

    private Paint mPaint;
    private Paint bgPaint;

    private float titleHeight, weekHeight, dayHeight, preHeight, oneHeight;
    private int columnWidth;

    private Date month;
    private boolean isCurrentMonth;
    private int currentDay, selectDay, lastSelectDay;

    private int dayOfMonth;
    private int firstIndex;
    private int firstLineNum, lastLineNum;
    private int lineNum;
    private String[] WEEK_STR = new String[]{"Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat", };


    public MonthCalendar(Context context) {
        this(context, null);
    }
    public MonthCalendar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public MonthCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性的值
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MonthCalendar, defStyleAttr, 0);

        mBgMonth = a.getColor(R.styleable.MonthCalendar_mBgMonth, Color.TRANSPARENT);
        mBgWeek = a.getColor(R.styleable.MonthCalendar_mBgWeek, Color.TRANSPARENT);
        mBgDay = a.getColor(R.styleable.MonthCalendar_mBgDay, Color.TRANSPARENT);
        mBgPre = a.getColor(R.styleable.MonthCalendar_mBgPre, Color.TRANSPARENT);

        mMonthRowL = a.getResourceId(R.styleable.MonthCalendar_mMonthRowL, R.drawable.custom_calendar_row_left);
        mMonthRowR = a.getResourceId(R.styleable.MonthCalendar_mMonthRowR, R.drawable.custom_calendar_row_right);
        mMonthRowSpac = a.getDimension(R.styleable.MonthCalendar_mMonthRowSpac, 20);
        mTextColorMonth = a.getColor(R.styleable.MonthCalendar_mTextColorMonth, Color.BLACK);
        mTextSizeMonth = a.getDimension(R.styleable.MonthCalendar_mTextSizeMonth, 100);
        mMonthSpac = a.getDimension(R.styleable.MonthCalendar_mMonthSpac, 20);
        mTextColorWeek = a.getColor(R.styleable.MonthCalendar_mTextColorWeek, Color.BLACK);
        mTextSizeWeek = a.getDimension(R.styleable.MonthCalendar_mTextSizeWeek, 70);
        mTextColorDay = a.getColor(R.styleable.MonthCalendar_mTextColorDay, Color.GRAY);
        mTextSizeDay = a.getDimension(R.styleable.MonthCalendar_mTextSizeDay, 70);
        mTextColorPreFinish = a.getColor(R.styleable.MonthCalendar_mTextColorPreFinish, Color.BLUE);
        mTextColorPreUnFinish = a.getColor(R.styleable.MonthCalendar_mTextColorPreUnFinish, Color.BLUE);
        mTextSizePre = a.getDimension(R.styleable.MonthCalendar_mTextSizePre, 40);
        mSelectTextColor = a.getColor(R.styleable.MonthCalendar_mSelectTextColor, Color.YELLOW);
        mCurrentBg = a.getColor(R.styleable.MonthCalendar_mCurrentBg, Color.GRAY);
        try {
            int dashPathId = a.getResourceId(R.styleable.MonthCalendar_mCurrentBgDashPath, R.array.MonthCalendar_currentDay_bg_DashPath);
            int[] array = getResources().getIntArray(dashPathId);
            mCurrentBgDashPath = new float[array.length];
            for(int i=0;i<array.length;i++){
                mCurrentBgDashPath[i]=array[i];
            }
        }catch (Exception e){
            e.printStackTrace();
            mCurrentBgDashPath = new float[]{2, 3, 2, 3};
        }
        mSelectBg = a.getColor(R.styleable.MonthCalendar_mSelectBg, Color.YELLOW);
        mSelectRadius = a.getDimension(R.styleable.MonthCalendar_mSelectRadius, 20);
        mCurrentBgStrokeWidth = a.getDimension(R.styleable.MonthCalendar_mCurrentBgStrokeWidth, 5);
        mLineSpac = a.getDimension(R.styleable.MonthCalendar_mLineSpac, 20);
        mTextSpac = a.getDimension(R.styleable.MonthCalendar_mTextSpac, 20);
        a.recycle();

        initCompute();

    }

    private void initCompute(){
        mPaint = new Paint();
        bgPaint = new Paint();
        mPaint.setAntiAlias(true);
        bgPaint.setAntiAlias(true);

        map = new HashMap<>();

        mPaint.setTextSize(mTextSizeMonth);
        titleHeight = FontUtil.getFontHeight(mPaint) + 2 * mMonthSpac;
        mPaint.setTextSize(mTextSizeWeek);
        weekHeight = FontUtil.getFontHeight(mPaint);
        mPaint.setTextSize(mTextSizeDay);
        dayHeight = FontUtil.getFontHeight(mPaint);
        mPaint.setTextSize(mTextSizePre);
        preHeight = FontUtil.getFontHeight(mPaint);
        oneHeight = mLineSpac + dayHeight + mTextSpac + preHeight;

        String cDateStr = getMonthStr(new Date());
        setMonth(cDateStr);
    }

    private void setMonth(String Month){

        month = str2Date(Month);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        Date cM = str2Date(getMonthStr(new Date()));
        if(cM.getTime() == month.getTime()){
            isCurrentMonth = true;
            selectDay = currentDay;
        }else{
            isCurrentMonth = false;
            selectDay = 0;
        }
        Log.d(TAG, "设置月份："+month+"   今天"+currentDay+"号, 是否为当前月："+isCurrentMonth);
        calendar.setTime(month);
        dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        firstIndex = calendar.get(Calendar.DAY_OF_WEEK)-1;
        lineNum = 1;
        firstLineNum = 7-firstIndex;
        lastLineNum = 0;
        int shengyu = dayOfMonth - firstLineNum;
        while (shengyu>7){
            lineNum ++;
            shengyu-=7;
        }
        if(shengyu>0){
            lineNum ++;
            lastLineNum = shengyu;
        }
        Log.i(TAG, getMonthStr(month)+"一共有"+dayOfMonth+"天,第一天的索引是："+firstIndex+"   有"+lineNum+
                "行，第一行"+firstLineNum+"个，最后一行"+lastLineNum+"个");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        columnWidth = widthSize / 7;
        float height = titleHeight + weekHeight + ((lineNum+1) * oneHeight);
        Log.v(TAG, "标题高度："+titleHeight+" 星期高度："+weekHeight+" 每行高度："+oneHeight+
                " 行数："+ lineNum + "  \n控件高度："+height);
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                (int)height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawMonth(canvas);
        drawWeek(canvas);
        drawDayAndPre(canvas);
    }

    private int rowLStart, rowRStart, rowWidth;
    private void drawMonth(Canvas canvas){
        bgPaint.setColor(mBgMonth);
        RectF rect = new RectF(0, 0, getWidth(), titleHeight);
        canvas.drawRect(rect, bgPaint);
        mPaint.setTextSize(mTextSizeMonth);
        mPaint.setColor(mTextColorMonth);
        float textLen = FontUtil.getFontlength(mPaint, getMonthStr(month));
        float textStart = (getWidth() - textLen)/ 2;
        canvas.drawText(getMonthStr(month), textStart,
                mMonthSpac+ FontUtil.getFontLeading(mPaint), mPaint);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mMonthRowL);
        int h = bitmap.getHeight();
        rowWidth = bitmap.getWidth();
        rowLStart = (int)(textStart-2*mMonthRowSpac-rowWidth);
        canvas.drawBitmap(bitmap, rowLStart+mMonthRowSpac , (titleHeight - h)/2, new Paint());
        bitmap = BitmapFactory.decodeResource(getResources(), mMonthRowR);
        rowRStart = (int)(textStart+textLen);
        canvas.drawBitmap(bitmap, rowRStart+mMonthRowSpac, (titleHeight - h)/2, new Paint());
    }
    private void drawWeek(Canvas canvas){
        bgPaint.setColor(mBgWeek);
        RectF rect = new RectF(0, titleHeight, getWidth(), titleHeight + weekHeight);
        canvas.drawRect(rect, bgPaint);
        mPaint.setTextSize(mTextSizeWeek);
        mPaint.setColor(mTextColorWeek);
        for(int i = 0; i < WEEK_STR.length; i++){
            int len = (int) FontUtil.getFontlength(mPaint, WEEK_STR[i]);
            int x = i * columnWidth + (columnWidth - len)/2;
            canvas.drawText(WEEK_STR[i], x, titleHeight + FontUtil.getFontLeading(mPaint), mPaint);
        }
    }
    private void drawDayAndPre(Canvas canvas){
        float top = titleHeight+weekHeight;
        for(int line = 0; line <lineNum; line++){
            if(line == 0){
                drawDayAndPre(canvas, top, firstLineNum, 0, firstIndex);
            }else if(line == lineNum-1){
                top += oneHeight;
                drawDayAndPre(canvas, top, lastLineNum, firstLineNum+(line-1)*7, 0);
            }else{
                top += oneHeight;
                drawDayAndPre(canvas, top, 7, firstLineNum+(line-1)*7, 0);
            }
        }
    }

    private void drawDayAndPre(Canvas canvas, float top,
                               int count, int overDay, int startIndex){
//        Log.e(TAG, "总共"+dayOfMonth+"天  有"+lineNum+"行"+ "  已经画了"+overDay+"天,下面绘制："+count+"天");
        //背景
        float topPre = top + mLineSpac + dayHeight;
        bgPaint.setColor(mBgDay);
        RectF rect = new RectF(0, top, getWidth(), topPre);
        canvas.drawRect(rect, bgPaint);

        bgPaint.setColor(mBgPre);
        rect = new RectF(0, topPre, getWidth(), topPre + mTextSpac + dayHeight);
        canvas.drawRect(rect, bgPaint);

        mPaint.setTextSize(mTextSizeDay);
        float dayTextLeading = FontUtil.getFontLeading(mPaint);
        mPaint.setTextSize(mTextSizePre);
        float preTextLeading = FontUtil.getFontLeading(mPaint);
//        Log.v(TAG, "当前日期："+currentDay+"   选择日期："+selectDay+"  是否为当前月："+isCurrentMonth);
        for(int i = 0; i<count; i++){
            int left = (startIndex + i)*columnWidth;
            int day = (overDay+i+1);

            mPaint.setTextSize(mTextSizeDay);

            //如果是当前月，当天日期需要做处理
            if(isCurrentMonth && currentDay == day){
                mPaint.setColor(mTextColorDay);
                bgPaint.setColor(mCurrentBg);
                bgPaint.setStyle(Paint.Style.STROKE);  //空心
                PathEffect effect = new DashPathEffect(mCurrentBgDashPath, 1);
                bgPaint.setPathEffect(effect);   //设置画笔曲线间隔
                bgPaint.setStrokeWidth(mCurrentBgStrokeWidth);       //画笔宽度
                //绘制空心圆背景
                canvas.drawCircle(left+columnWidth/2, top + mLineSpac +dayHeight/2,
                        mSelectRadius-mCurrentBgStrokeWidth, bgPaint);
            }
            //绘制完后将画笔还原，避免脏笔
            bgPaint.setPathEffect(null);
            bgPaint.setStrokeWidth(0);
            bgPaint.setStyle(Paint.Style.FILL);

            //选中的日期，如果是本月，选中日期正好是当天日期，下面的背景会覆盖上面绘制的虚线背景
            if(selectDay == day){
                //选中的日期字体白色，橙色背景
                mPaint.setColor(mSelectTextColor);
                bgPaint.setColor(mSelectBg);
                //绘制橙色圆背景，参数一是中心点的x轴，参数二是中心点的y轴，参数三是半径，参数四是paint对象；
                canvas.drawCircle(left+columnWidth/2, top + mLineSpac +dayHeight/2, mSelectRadius, bgPaint);
            }else{
                mPaint.setColor(mTextColorDay);
            }

            int len = (int) FontUtil.getFontlength(mPaint, day+"");
            int x = left + (columnWidth - len)/2;
            canvas.drawText(day+"", x, top + mLineSpac + dayTextLeading, mPaint);

            mPaint.setTextSize(mTextSizePre);
            Month.DayFinish finish = map.get(day);
            String preStr = "";
            if(finish!=null){
                if(finish.all < 3) {
                    mPaint.setColor(mTextColorPreFinish);
                }else{
                    mPaint.setColor(mTextColorPreUnFinish);
                }
                preStr = ""+finish.all;

            }else{
                mPaint.setColor(mTextColorPreUnFinish);
            }
            len = (int) FontUtil.getFontlength(mPaint, preStr);
            x = left + (columnWidth - len)/2;
            canvas.drawText(preStr, x, topPre + mTextSpac + preTextLeading, mPaint);
        }
    }

    private String getMonthStr(Date month){
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM");
        return df.format(month);
    }
    private Date str2Date(String str){
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM");
            return df.parse(str);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private PointF focusPoint = new PointF();
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                focusPoint.set(event.getX(), event.getY());
                touchFocusMove(focusPoint, false);
                break;
            case MotionEvent.ACTION_MOVE:
                focusPoint.set(event.getX(), event.getY());
                touchFocusMove(focusPoint, false);
                break;
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                focusPoint.set(event.getX(), event.getY());
                touchFocusMove(focusPoint, true);
                break;
        }
        return true;
    }

    public void touchFocusMove(final PointF point, boolean eventEnd) {
        Log.e(TAG, "点击坐标：("+point.x+" ，"+point.y+"),事件是否结束："+eventEnd);
        if(point.y<=titleHeight){
            if(eventEnd && listener!=null){
                if(point.x>=rowLStart && point.x<(rowLStart+2*mMonthRowSpac+rowWidth)){
                    Log.w(TAG, "点击左箭头");
                    listener.onLeftRowClick();
                }else if(point.x>rowRStart && point.x<(rowRStart + 2*mMonthRowSpac+rowWidth)){
                    Log.w(TAG, "点击右箭头");
                    listener.onRightRowClick();
                }else if(point.x>rowLStart && point.x <rowRStart){
                    listener.onTitleClick(getMonthStr(month), month);
                }
            }
        }else if(point.y<=(titleHeight+weekHeight)){
            if(eventEnd && listener!=null){
                int xIndex = (int)point.x / columnWidth;
                Log.e(TAG, "列宽："+columnWidth+"  x坐标余数："+(point.x / columnWidth));
                if((point.x / columnWidth-xIndex)>0){
                    xIndex += 1;
                }
                if(listener!=null){
                    listener.onWeekClick(xIndex-1, WEEK_STR[xIndex-1]);
                }
            }
        }else{
            touchDay(point, eventEnd);
        }
    }

    private boolean responseWhenEnd = false;
    private void touchDay(final PointF point, boolean eventEnd){
        boolean availability = false;
        float top = titleHeight+weekHeight+oneHeight;
        int foucsLine = 1;
        while(foucsLine<=lineNum){
            if(top>=point.y){
                availability = true;
                break;
            }
            top += oneHeight;
            foucsLine ++;
        }
        if(availability){
            int xIndex = (int)point.x / columnWidth;
            if((point.x / columnWidth-xIndex)>0){
                xIndex += 1;
            }
//            Log.e(TAG, "列宽："+columnWidth+"  x坐标余数："+(point.x / columnWidth));
            if(xIndex<=0)
                xIndex = 1;
            if(xIndex>7)
                xIndex = 7;
//            Log.e(TAG, "事件在日期部分，第"+foucsLine+"/"+lineNum+"行, "+xIndex+"列");
            if(foucsLine == 1){
                if(xIndex<=firstIndex){
                    Log.e(TAG, "点到开始空位了");
                    setSelectedDay(selectDay, true);
                }else{
                    setSelectedDay(xIndex-firstIndex, eventEnd);
                }
            }else if(foucsLine == lineNum){
                if(xIndex>lastLineNum){
                    Log.e(TAG, "点到结尾空位了");
                    setSelectedDay(selectDay, true);
                }else{
                    setSelectedDay(firstLineNum + (foucsLine-2)*7+ xIndex, eventEnd);
                }
            }else{
                setSelectedDay(firstLineNum + (foucsLine-2)*7+ xIndex, eventEnd);
            }
        }else{
            setSelectedDay(selectDay, true);
        }
    }
    private void setSelectedDay(int day, boolean eventEnd){
        Log.w(TAG, "选中："+day+"  事件是否结束"+eventEnd);
        selectDay = day;
        invalidate();
        if(listener!=null && eventEnd && responseWhenEnd && lastSelectDay!=selectDay) {
            lastSelectDay = selectDay;
            listener.onDayClick(selectDay, getMonthStr(month) + "/" +selectDay, map.get(selectDay));
        }
        responseWhenEnd = !eventEnd;
    }

    private Map<Integer, Month.DayFinish> map;
    public void setRenwu(String month, List<Month.DayFinish> list){
        setMonth(month);

        if(list!=null && list.size()>0){
            map.clear();
            for(Month.DayFinish finish : list){
                map.put(finish.day, finish);
            }
        }
        invalidate();
    }
    public void setRenwu(List<Month.DayFinish> list){
        if(list!=null && list.size()>0){
            map.clear();
            for(Month.DayFinish finish : list){
                map.put(finish.day, finish);
            }
        }
        invalidate();
    }
    public void monthChange(int change){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(month);
        calendar.add(Calendar.MONTH, change);
        setMonth(getMonthStr(calendar.getTime()));
        map.clear();
        invalidate();
    }

    private onClickListener listener;
    public void setOnClickListener(onClickListener listener){
        this.listener = listener;
    }
    interface onClickListener{

        public abstract void onLeftRowClick();
        public abstract void onRightRowClick();
        public abstract void onTitleClick(String monthStr, Date month);
        public abstract void onWeekClick(int weekIndex, String weekStr);
        public abstract void onDayClick(int day, String dayStr, Month.DayFinish finish);
    }

}

