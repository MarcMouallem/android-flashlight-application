package com.marcmouallem.android.utilities.flashlight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

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
    
    int drawingHeight;
    int topPadding;
    
    Paint linePaint;
    PathShape gripLine;
    ShapeDrawable shapeDrawable;
    Path path;
    ShapeDrawable line;
    
/**********************************************************************************************************************
 * Member Variables END
 **********************************************************************************************************************/

    public GripView(Context context, AttributeSet attrs) {
        
        super(context, attrs);
        
        linePaint = new Paint();
        linePaint.setColor(Color.GRAY);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setPathEffect(new DashPathEffect(new float[] {10,20}, 0));       
        
        path = new Path();
        
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
        
        lineWidth = 3;
        targetLineSpacing = 50;
        
        /* Derived from:
         * height = targetLineSpacing + numberOfLine(targetLineSpacing + lineWidth)
         * Note: By now the height and lineWidth has been calculated. We Have an idea of what we want the spacing to
         * be, we then calculate how many lines can fit comfortably on the screen. We round to the nearest odd number
         * to end and start with a short line.
         */     
        numberOfLines = roundToNearestOdd((height - targetLineSpacing) / (targetLineSpacing + lineWidth));
        
        /* Derived from:
         * height = numberOfLines * lineWidth + numberOfLines * actualLineSpacing + actualLineSpacing
         */
        actualLineSpacing = Math.round((height - (numberOfLines * lineWidth)) / (numberOfLines + 1));
        
        drawingHeight = actualLineSpacing + numberOfLines * (lineWidth + actualLineSpacing);
        topPadding = Math.round((height - drawingHeight) / 2);
        
        linePaint.setStrokeWidth(lineWidth);
        
        Log.v("member", "leftMostCoordinate: " + leftMostCoordinate);
        Log.v("member", "topMostCoordinate: " + topMostCoordinate);
        Log.v("member", "rightMostCoordinate: " + rightMostCoordinate);
        Log.v("member", "bottomMostCoordinate: " + bottomMostCoordinate);
        
        Log.v("member", "width: " + width);
        Log.v("member", "height: " + height);
        
        Log.v("member", "shortLineIndent: " + shortLineIndent);
        Log.v("member", "longLineIndent: " + longLineIndent);
        Log.v("member", "shortLineStart: " + shortLineStart);
        Log.v("member", "shortLineEnd: " + shortLineEnd);
        Log.v("member", "longLineStart: " + longLineStart);
        Log.v("member", "longLineEnd: " + longLineEnd);
        
        Log.v("member", "lineWidth: " + lineWidth);
        Log.v("member", "targetLineSpacing: " + targetLineSpacing);
        
        Log.v("member", "numberOfLines: " + numberOfLines);
        Log.v("member", "acutalLineSpacing: " + actualLineSpacing);
        
        Log.v("member", "drawingHeight: " + drawingHeight);
        Log.v("member", "topPadding: " + topPadding);
        
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
        
        canvas.drawColor(Color.BLACK);

        int y = topPadding + actualLineSpacing;
        for (int lineNumber = 0; 
             lineNumber < numberOfLines - 1; 
             lineNumber += 2, y += actualLineSpacing + lineWidth) {
            
            path.moveTo(shortLineStart, y);
            path.lineTo(shortLineEnd, y);
            Log.v("draw", "Drew path from (" + shortLineStart + ", " + y + ") to (" + shortLineEnd + ", " + y + ").");
            
            y += actualLineSpacing + lineWidth;
            
            path.moveTo(longLineStart, y);
            path.lineTo(longLineEnd, y);
            Log.v("draw", "Drew path from (" + longLineStart + ", " + y + ") to (" + longLineEnd + ", " + y + ").");
            
        }
        
        /* Draw last line without subsequent long line. */
        path.moveTo(shortLineStart, y);
        path.lineTo(shortLineEnd, y);
        Log.v("draw", "Drew path from (" + shortLineStart + ", " + y + ") to (" + shortLineEnd + ", " + y + ").");
        
        path.close();

        canvas.drawPath(path, linePaint);
        
    }
    
    

}
