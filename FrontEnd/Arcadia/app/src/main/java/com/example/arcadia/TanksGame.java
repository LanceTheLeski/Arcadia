package com.example.arcadia;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import static com.example.arcadia.R.mipmap.blacktank;
import static com.example.arcadia.R.mipmap.car;
import static com.example.arcadia.R.mipmap.tankhitbymine;
import static com.example.arcadia.R.mipmap.tankruningovercar;
import static com.example.arcadia.R.mipmap.tankstuck;

public class TanksGame extends View {
    Canvas canvas;
    Paint paint;
    Tank tank;
    Rect rectangle;
    Paint p;
    Bitmap B;
    public TanksGame(Context context){
        super(context);
        tank = new Tank();
        tank.bmp = BitmapFactory.decodeResource(getResources(), blacktank);
        B = BitmapFactory.decodeResource(getResources(), car);
        tank.x = 200;
        tank.y = 200;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        rectangle = new Rect();
        rectangle.r = 400;
        rectangle.l = 100;
        rectangle.t = 500;
        rectangle.b = 600;
        p = new Paint();
        p.setColor(Color.BLUE);
    }
    private void HitLake(){
        if (tank.x >= rectangle.l && tank.x <= rectangle.r &&
                tank.y <= rectangle.b && tank.y >= rectangle.t){
            tank.bmp =  BitmapFactory.decodeResource(getResources(), tankstuck);
        }
    }
    private void HitLandMine(){
        if (tank.x >= 500 && tank.x <= 600 &&
                tank.y <= 600 && tank.y >= 500){
            tank.bmp =  BitmapFactory.decodeResource(getResources(), tankhitbymine);
        }
    }
    private void HitCar() {
        if (tank.x >= 600 && tank.x <= 800 &&
                tank.y <= 1200 && tank.y >= 1000) {
            tank.bmp = BitmapFactory.decodeResource(getResources(), tankruningovercar);
        }
    }

    class Tank {
        Bitmap bmp;
        float x, y;
    }
    class Rect{
        float r, l, t, b;
    }

    @Override
    public void draw(Canvas canvas){
        canvas = canvas;
        super.draw(canvas);
        canvas.drawColor(Color.GREEN);
        canvas.drawRect(rectangle.l, rectangle.t, rectangle.r, rectangle.b, p);
        canvas.drawBitmap(B,600,1000,paint);
        canvas.drawBitmap(tank.bmp,tank.x,tank.y,paint);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        tank.bmp = BitmapFactory.decodeResource(getResources(), blacktank);
        tank.x = event.getX();
        tank.y = event.getY();
        HitLake();
        HitLandMine();
        HitCar();
        invalidate();
        return true;
    }
}