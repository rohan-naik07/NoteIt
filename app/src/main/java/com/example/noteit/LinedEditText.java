package com.example.noteit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

public class LinedEditText extends AppCompatEditText {
    private Rect mRect;
    private Paint paint;


    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRect=new Rect();
        paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(0xff888888);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height=((View)this.getParent()).getHeight();
        int lineHeight=getLineHeight();
        int no=height/lineHeight;
        Rect r=mRect;
        Paint p=paint;

        int baseline=getLineBounds(0,r);

        for(int i=0;i<no;i++)
        {
            canvas.drawLine(r.left,baseline+1,r.right,baseline+1,p);
            baseline+=lineHeight;
        }
        super.onDraw(canvas);
    }
}
