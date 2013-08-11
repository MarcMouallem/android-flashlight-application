package com.marcmouallem.android.utilities.flashlight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

public class GripView extends View {
    
/**********************************************************************************************************************
 * Member Variables BEGIN
 **********************************************************************************************************************/
    
    final int     SHORT_LINE_INDENT_DP   = 55;
    final int     LONG_LINE_INDENT_DP    = 25;
    final int     LINE_WIDTH_DP          = 2;
    final int     TARGET_LINE_SPACING_DP = 25;
    final float[] DASH_INTERVALS         = {10, 20};
    final int     DASH_PHASE             = 0;
    
    DisplayMetrics displayMetrics;  
    
    int shortLineIndentPx;
    int longLineIndentPx;
    int lineWidthPx;
    int targetLineSpacingPx;
    
    Path           gripLines;
    Paint          gripLinesPaint;
    DashPathEffect gripLinesDashEffect;
      
    int windowLeftMostCoordinate;
    int windowTopMostCoordinate;
    int windowRightMostCoordinate;
    int windowBottomMostCoordinate; 
    int windowWidthPx;
    int windowHeightPx;
    
    int shortLineStart;
    int shortLineEnd;
    int longLineStart;
    int longLineEnd;     
    int numberOfLines;
    int actualLineSpacingPx;   
    int drawingHeightPx;
    int topPaddingPx;
           
/**********************************************************************************************************************
 * Member Variables END & Constructors BEGIN
 **********************************************************************************************************************/

    public GripView(Context context, AttributeSet attrs) {
        
        super(context, attrs);
        
        displayMetrics = getResources().getDisplayMetrics();
        
        shortLineIndentPx   = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
                                                              SHORT_LINE_INDENT_DP, 
                                                              displayMetrics);
        longLineIndentPx    = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
                                                              LONG_LINE_INDENT_DP, 
                                                              displayMetrics);
        lineWidthPx         = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
                                                              LINE_WIDTH_DP, 
                                                              displayMetrics);
        targetLineSpacingPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
                                                              TARGET_LINE_SPACING_DP, 
                                                              displayMetrics);
        
        Log.v("value", "shortLineIndentPx: " + shortLineIndentPx);
        Log.v("value", "longLineIndentPx: " + longLineIndentPx);
        Log.v("value", "lineWidthPx: " + lineWidthPx);
        Log.v("value", "targetLineSpacingPx: " + targetLineSpacingPx); 
        
        gripLines = new Path();       
        gripLinesPaint = new Paint();
        gripLinesPaint.setColor(Color.GRAY);
        gripLinesPaint.setStyle(Paint.Style.STROKE);
        gripLinesPaint.setStrokeWidth(lineWidthPx);
        gripLinesDashEffect = new DashPathEffect(DASH_INTERVALS, DASH_PHASE);
        gripLinesPaint.setPathEffect(gripLinesDashEffect);
      
    }
    
/**********************************************************************************************************************
 * Constructors END
 **********************************************************************************************************************/
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       
        windowLeftMostCoordinate = getLeft();
        windowTopMostCoordinate = getTop();
        windowRightMostCoordinate = getRight();
        windowBottomMostCoordinate = getBottom();      
        windowWidthPx = windowRightMostCoordinate - windowLeftMostCoordinate;
        windowHeightPx = windowBottomMostCoordinate - windowTopMostCoordinate;
                       
        shortLineStart = windowLeftMostCoordinate + shortLineIndentPx;
        shortLineEnd = windowRightMostCoordinate - shortLineIndentPx;
        longLineStart = windowLeftMostCoordinate + longLineIndentPx;
        longLineEnd = windowRightMostCoordinate - longLineIndentPx;        
              
        /* Derived from:
         * height = targetLineSpacing + numberOfLine(targetLineSpacing + lineWidth)
         * Note: By now the height and lineWidth has been calculated. We Have an idea of what we want the spacing to
         * be, we then calculate how many lines can fit comfortably on the screen. We round to the nearest odd number
         * to end and start with a short line.
         */     
        numberOfLines = roundToNearestOdd((windowHeightPx - targetLineSpacingPx) / (targetLineSpacingPx + lineWidthPx));
        
        /* Derived from:
         * height = numberOfLines * lineWidth + numberOfLines * actualLineSpacing + actualLineSpacing
         */
        actualLineSpacingPx = Math.round((windowHeightPx - (numberOfLines * lineWidthPx)) / (numberOfLines + 1));
        
        drawingHeightPx = actualLineSpacingPx + numberOfLines * (lineWidthPx + actualLineSpacingPx);
        topPaddingPx = Math.round((windowHeightPx - drawingHeightPx) / 2);       
  
        Log.v("value", "windowLeftMostCoordinate: " + windowLeftMostCoordinate);
        Log.v("value", "windowTopMostCoordinate: " + windowTopMostCoordinate);
        Log.v("value", "windowRightMostCoordinate: " + windowRightMostCoordinate);
        Log.v("value", "windowBottomMostCoordinate: " + windowBottomMostCoordinate);    
        Log.v("value", "windowWidthPx: " + windowWidthPx);
        Log.v("value", "windowHeightPx: " + windowHeightPx);     
        
        Log.v("value", "shortLineStart: " + shortLineStart);
        Log.v("value", "shortLineEnd: " + shortLineEnd);
        Log.v("value", "longLineStart: " + longLineStart);
        Log.v("value", "longLineEnd: " + longLineEnd);
        Log.v("value", "numberOfLines: " + numberOfLines);
        Log.v("value", "actualLineSpacingPx: " + actualLineSpacingPx);
        Log.v("value", "drawingHeightPx: " + drawingHeightPx);
        Log.v("value", "topPaddingPx: " + topPaddingPx);
        
        setMeasuredDimension(windowRightMostCoordinate - windowLeftMostCoordinate, windowBottomMostCoordinate - windowTopMostCoordinate);
       
    }

    @Override
    protected void onDraw(Canvas canvas) {
        
        super.onDraw(canvas);
        
        canvas.drawColor(Color.BLACK);

        int y = topPaddingPx + actualLineSpacingPx;
        for (int lineNumber = 0; 
             lineNumber < numberOfLines - 1; 
             lineNumber += 2, y += actualLineSpacingPx + lineWidthPx) {
            
            gripLines.moveTo(shortLineStart, y);
            gripLines.lineTo(shortLineEnd, y);
            Log.v("draw", "Drew path from (" + shortLineStart + ", " + y + ") to (" + shortLineEnd + ", " + y + ").");
            
            y += actualLineSpacingPx + lineWidthPx;
            
            gripLines.moveTo(longLineStart, y);
            gripLines.lineTo(longLineEnd, y);
            Log.v("draw", "Drew path from (" + longLineStart + ", " + y + ") to (" + longLineEnd + ", " + y + ").");
            
        }
        
        /* Draw last line without subsequent long line. */
        gripLines.moveTo(shortLineStart, y);
        gripLines.lineTo(shortLineEnd, y);
        Log.v("draw", "Drew path from (" + shortLineStart + ", " + y + ") to (" + shortLineEnd + ", " + y + ").");
        
        gripLines.close();

        canvas.drawPath(gripLines, gripLinesPaint);
        
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
    

}
