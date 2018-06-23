
// custom view

package com.example.snippet.circularprogressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class CustomView extends View {

    private float left, top, right, bottom;
    private float x, y, a;
    private int p;
    private boolean visible;

    // constructor
    public CustomView(Context context) {
        super(context);

        a = 0.0f;
        p = 0;

        visible = false;
    }

    // render screen
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!visible) return;

        Paint paint = new Paint();
        Path path = new Path();

        // save current matrix
        canvas.save();

        paint.setAntiAlias(true);

        // draw circle
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(20.0f);
        path.arcTo(left, top, right, bottom, 270.0f, a, true);
        canvas.drawPath(path, paint);

        // draw text
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1.0f);
        paint.setTextSize(75.0f);
        canvas.drawText(String.format("%3d %%", p), x, y, paint);

        // restore the matrix saved above
        canvas.restore();
    }

    // center circle
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float r;

        x = w / 2.0f;
        y = h / 2.0f;
        r = w<h?w:h;
        r/=4.0f;

        left   = x - r;
        top    = y - r;
        right  = x + r;
        bottom = y + r;

        x-=58.0f;
        y+=37.5f;
    }

    // animated circle
    public void update(int i){

        p = i;
        a = ((float)i / 100.0f) * 360.0f;

        invalidate();
    }

    // hide circle
    public void hide(){
        visible = false;
        invalidate();
    }

    // show circle
    public void show(){
        visible = true;
        invalidate();
    }
}
