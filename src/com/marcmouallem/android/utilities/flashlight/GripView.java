package com.marcmouallem.android.utilities.flashlight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class GripView extends View {
    
/**********************************************************************************************************************
 * Member Variables BEGIN
 **********************************************************************************************************************/
    
    int leftMostCoordinate;
    int topMostCoordinate;
    int rightMostCoordinate;
    int bottomMostCoordinate;
        
//    WindowManager windowManager;
//    Display display;
    
    
    
    Paint paint;
    PathShape gripLine;
    ShapeDrawable shapeDrawable;
    Path path;
    ShapeDrawable line;
    
/**********************************************************************************************************************
 * Member Variables END
 **********************************************************************************************************************/

    public GripView(Context context, AttributeSet attrs) {
        
        super(context, attrs);
        
        
//        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        display = windowManager.getDefaultDisplay();
        
        
        
        
        
        
        
        
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        
        
        path = new Path();
        path.moveTo(0, 0);
        path.lineTo(1000, 1000);
        path.lineTo(1000, 0);
        path.lineTo(0, 0);
        path.close();

//        
//        shapeDrawable = new ShapeDrawable(new RectShape());
//        line = new ShapeDrawable(gripLine); 
//        shapeDrawable.getPaint().setColor(0xff74AC23);
//        shapeDrawable.setBounds(0, 0, 500, 500);
        
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       
        leftMostCoordinate = getLeft();
        topMostCoordinate = getTop();
        rightMostCoordinate = getRight();
        bottomMostCoordinate = getBottom();
        
        Log.v("member", "leftMostCoordinate: " + leftMostCoordinate);
        Log.v("member", "topMostCoordinate: " + topMostCoordinate);
        Log.v("member", "rightMostCoordinate: " + rightMostCoordinate);
        Log.v("member", "bottomMostCoordinate: " + bottomMostCoordinate);
        
        setMeasuredDimension(rightMostCoordinate - leftMostCoordinate, bottomMostCoordinate - topMostCoordinate);
       
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        
        super.onDraw(canvas);
        
        
        
        canvas.drawPath(path, paint);
        
    }

}
