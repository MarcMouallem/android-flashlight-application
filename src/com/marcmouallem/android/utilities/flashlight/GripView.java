package com.marcmouallem.android.utilities.flashlight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
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
    
    int width;
    int height;
    
    int shortLineIndent;
    int longLineIndent;
    int shortLineStart;
    int shortLineEnd;
    int longLineStart;
    int longLineEnd;    
    
    int lineWidth;
    int targetLineSpacing;
    
    int numberOfLines;
    int actualLineSpacing;
    
    
        
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
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[] {10,20}, 0));
        
        
        path = new Path();
//        path.moveTo(20, 20);
//        path.lineTo(500, 20);
////        path.lineTo(1000, 0);
////        path.lineTo(0, 0);
//        path.close();

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
        
        width = rightMostCoordinate - leftMostCoordinate;
        height = bottomMostCoordinate - topMostCoordinate;
        
        shortLineIndent = 110;
        longLineIndent = 40;
        shortLineStart = leftMostCoordinate + shortLineIndent;
        shortLineEnd = rightMostCoordinate - shortLineIndent;
        longLineStart = leftMostCoordinate + longLineIndent;
        longLineEnd = rightMostCoordinate - longLineIndent;        
        
        lineWidth = 2;
        targetLineSpacing = 50;
        
        /* Derived from:
         * height = targetLineSpacing + numberOfLine(targetLineSpacing + lineWidth)
         * Note: By now the height and lineWidth has been calculated. We Have an idea of what we want the spacing to
         * be, we then calculate how many lines can fit comfortably on the screen. We round to the nearest odd number
         * to end and start with a short line.
         */     
        numberOfLines = roundToNearestOdd((height - targetLineSpacing) / (targetLineSpacing + lineWidth));
        
        /* Derived from:
         * height = numberOfLines * lineWidth + numberOfLines * actualLineWidth + actualLineWidth
         */
        actualLineSpacing = Math.round((height - (numberOfLines * lineWidth)) / (numberOfLines + 1));
        
        paint.setStrokeWidth(lineWidth);
        
        Log.v("member", "leftMostCoordinate: " + leftMostCoordinate);
        Log.v("member", "topMostCoordinate: " + topMostCoordinate);
        Log.v("member", "rightMostCoordinate: " + rightMostCoordinate);
        Log.v("member", "bottomMostCoordinate: " + bottomMostCoordinate);
        Log.v("member", "numberOfLines: " + numberOfLines);
        Log.v("member", "acutalLineSpacing: " + actualLineSpacing);
        
        setMeasuredDimension(rightMostCoordinate - leftMostCoordinate, bottomMostCoordinate - topMostCoordinate);
       
    }
    
    private int roundToNearestOdd(double d) {
        
        int nearestOdd;
        int roundedDown = (int) Math.floor(d);
        
        if (Math.floor(d) % 2 == 0) {
            nearestOdd = roundedDown + 1; 
        } else {
            nearestOdd = roundedDown;
        }
        
        return nearestOdd;
        
    }

    @Override
    protected void onDraw(Canvas canvas) {
        
        super.onDraw(canvas);

        int y = actualLineSpacing;
        for (int lineNumber = 0; 
             lineNumber < numberOfLines - 1; 
             lineNumber += 2, y += actualLineSpacing) {
            
            path.moveTo(shortLineStart, y);
            path.lineTo(shortLineEnd, y);
            
            y += actualLineSpacing;
            
            path.moveTo(longLineStart, y);
            path.lineTo(longLineEnd, y);
            
        }
        
        /* Draw last line without subsequent long line. */
        path.moveTo(shortLineStart, y);
        path.lineTo(shortLineEnd, y);   
        path.close();
        
        canvas.drawPath(path, paint);
        
    }
    
    

}
