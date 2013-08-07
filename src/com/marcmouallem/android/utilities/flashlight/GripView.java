package com.marcmouallem.android.utilities.flashlight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GripView extends View {
    
    Paint paint;
    PathShape gripLine;
    ShapeDrawable shapeDrawable;

    public GripView(Context context, AttributeSet attrs) {
        
        super(context, attrs);
        
//        paint = new Paint();
//        paint.setColor(Color.RED);
//        
//        Path path = new Path();
//        path.moveTo(0.0f, 0.0f);
//        path.lineTo(1.0f, 1.0f);
//
//        gripLine = new PathShape(path, 1f, 1f);
        
        int x = 10;
        int y = 10;
        int width = 300;
        int height = 50;
        
        shapeDrawable = new ShapeDrawable(new RectShape());
        shapeDrawable.getPaint().setColor(0xff74AC23);
        shapeDrawable.setBounds(x, y, x + width, y + height);
        
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        
        super.onDraw(canvas);
        
//        gripLine.draw(canvas, paint);
        shapeDrawable.draw(canvas);
        Log.d("blarkar", "drawed");
    }
    

//    private PathShape gripLine;
//
//    public GripView(Context context) {
//
//        super(context);
//
//        int x = 10;
//        int y = 10;
//        int width = 300;
//        int height = 50;
//
//        Path path = new Path();
//        path.moveTo(0.0f, 0.0f);
//        path.lineTo(1.0f, 1.0f);
//
//        gripLine = new PathShape(path, 1f, 1f);
//
////        mDrawable = new ShapeDrawable(new RectShape());
////
////        mDrawable.getPaint().setColor(0xff74AC23);
////        mDrawable.setBounds(x, y, x + width, y + height);
//
//    }
//
//    public GripView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//
//        int x = 10;
//        int y = 10;
//        int width = 300;
//        int height = 50;
//
//        Path path = new Path();
//        path.moveTo(0.0f, 0.0f);
//        path.lineTo(1.0f, 1.0f);
//
//        gripLine = new PathShape(path, 1f, 1f);
////        mDrawable = new ShapeDrawable(new RectShape());
////
////        mDrawable.getPaint().setColor(0xff74AC23);
////        mDrawable.setBounds(x, y, x + width, y + height);
//
//    }
//
//    public GripView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//
//        int x = 10;
//        int y = 10;
//        int width = 300;
//        int height = 50;
//
//        Path path = new Path();
//        path.moveTo(0.0f, 0.0f);
//        path.lineTo(1.0f, 1.0f);
//
//        gripLine = new PathShape(path, 1f, 1f);
////        mDrawable = new ShapeDrawable(new RectShape());
////
////        mDrawable.getPaint().setColor(0xff74AC23);
////        mDrawable.setBounds(x, y, x + width, y + height);
//
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        if (canvas != null) {
//            Paint paint = new Paint();
//            paint.setColor(Color.RED);
//        
//            gripLine.draw(canvas, paint);
//            Log.d("blarkar", "sdfasdf");
//        } else {
//
//        }
//
//    }

}
