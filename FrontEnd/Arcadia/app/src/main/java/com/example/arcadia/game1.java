package com.example.arcadia;




import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

import java.util.Vector;

import static android.app.PendingIntent.getActivity;
import static androidx.core.content.ContextCompat.startActivity;


class my{
    public static int score=0;//score
    public static int w,h;//width and height
    public static float scale;//scale for different screen
    public static Vector<plane> list=new Vector<plane>();//list for all flight
    public static Vector<plane> enemylist=new Vector<plane>();
    public static Vector<plane> enemylist2=new Vector<plane>();//enemy plane list
    public static Bitmap myplane,enemyplane,enemyplane2,background,bullet,pause;//image
    public static myplane my;//myplane

    //public static background bg;//background
    public static pause ps;
}

public class game1 extends View{
    private Paint p=new Paint();
    private float x,y;//coordinate
    private float myx,myy;//the coordinate when player touch the screen


    public game1(Context context) {
        super(context);
        Button pause = (Button) findViewById (R.id.pauseButton);
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent e) {
                if(e.getAction()==MotionEvent.ACTION_DOWN){
                    x=e.getX();
                    y=e.getY();
                    myx=my.my.r.left;
                    myy=my.my.r.top;
                }
                float xx=myx+e.getX()-x;
                float yy=myy+e.getY()-y;
                xx=xx<my.w-my.my.w/2?xx:my.w-my.my.w/2;
                xx=xx>-my.my.w/2?xx:-my.my.w/2;
                yy=yy<my.h-my.my.h/2?yy:my.h-my.my.h/2;
                yy=yy>-my.my.h/2?yy:-my.my.h/2;
                my.my.setX(xx);
                my.my.setY(yy);
                return true;
            }
        });

        setBackgroundColor(Color.BLACK);//set background to black

        my.myplane= BitmapFactory.decodeResource(getResources(),R.mipmap.myplane);
        my.enemyplane=BitmapFactory.decodeResource(getResources(),R.mipmap.enemyplane);
        my.enemyplane2=BitmapFactory.decodeResource(getResources(),R.mipmap.enemyplane2);
        my.bullet=BitmapFactory.decodeResource(getResources(),R.mipmap.bullet);
       // my.background=BitmapFactory.decodeResource(getResources(), R.mipmap.background);
        my.pause=BitmapFactory.decodeResource(getResources(),R.mipmap.pause);
        new Thread(new re()).start();//new thread
        new Thread(new loaddr()).start();//new thread for enemy plane
        new Thread(new loaddr2()).start();
    }
    @Override
    protected void onDraw(Canvas g) {
        super.onDraw(g);
        //g.drawBitmap(my.bg.img,null,my.bg.r,p);//show the background

        for(int i=0;i<my.list.size();i++){//add all flight to the list
            plane h=my.list.get(i);
            g.drawBitmap(h.img,null,h.r,p);
        }
        g.drawText("Score："+my.score,0,my.h-50,p);

        //g.drawBitmap(my.ps.img,2,10,null);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {//get the size of the screen
        super.onSizeChanged(w, h, oldw, oldh);
        my.w=w;//get width
        my.h=h;//get height

        my.scale= (float) (Math.sqrt(my.w * my.h)/ Math.sqrt(1920 * 1080));
        p.setTextSize(50*my.scale);//setting the size of the socre
        p.setColor(Color.WHITE);//set as white
        //game start
        //my.bg=new background();//initialize the background
        my.my=new myplane();//initialize my plane
    }
    private class re implements Runnable {
        @Override
        public void run() {
            //refreash every 10 ms
            while(true){
                try { Thread.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
                postInvalidate();
            }
        }
    }
    private class loaddr implements Runnable{
        @Override
        public void run() {
            while(true){
                //refreash enemyplane every 800ms
                try {Thread.sleep(800);} catch (InterruptedException e) {e.printStackTrace();}
                try {
                    new enemyplane();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class loaddr2 implements Runnable{
        @Override
        public void run() {
            while(true){
                //refreash enemyplane every 3000ms
                try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
                try {
                    new enemyplane2();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
class plane{
    public RectF r=new RectF();//locate the position
    public int hp;//hp
    public float w,h;//width and height
    public Bitmap img;//image


    public void setX(float x){
        r.left=x;
        r.right=x+w;
    }
    public void setY(float y){
        r.top=y;
        r.bottom=y+h;
    }

    public boolean hit(plane obj,float px) {
        px*=my.scale;
        if (r.left+px - obj.r.left <= obj.w && obj.r.left - this.r.left+px <= this.w-px-px)
            if (r.top+px - obj.r.top <= obj.h && obj.r.top - r.top+px <= h-px-px) {
                return true;
            }
        return false;

    }
    /*public boolean hit2(plane obj, plane obj2, float px1, float px2){
        px1*=my.scale;
        px2*=my.scale;
        if(r.left+px1 - obj.r.left <= obj.w && obj.r.left - this.r.left+px1 <= this.w-px1-px1 )
        return false;
    }

     */

}
class pause extends plane implements Runnable {
    public pause(){
       // w=my.w;
       // h=my.h;
        img=my.pause;

        //new Thread(this).start();
    }
    @Override
    public void run() {
    }
}
/*
class background extends plane implements  Runnable{//background
    public background(){
        w=my.w;
        h=my.h*2;//image should be twice higher than the screen
        img=my.background;
        setX(0);
        setY(-my.h);
        new Thread(this).start();
    }
    @Override
    public void run() {
        //the background goes downward
        while(true){
            try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
            if(r.top+2<=0){
                setY(r.top+2);
            }else{
                setY(-my.h);
            }
        }
    }
}

 */

class enemyplane extends plane implements Runnable{//enemy plane
    private long sd0=(long) (Math.random()*10)+20;//random speed for enemy

    public enemyplane(){

        w=h=200*my.scale;
        //initial for enemy position
        setX((float)( Math.random()*(my.w-w)));//x is random
        setY(-h);
        img=my.enemyplane;
        hp=12;//hp=12
        my.list.add(this);//add to the list
        my.enemylist.add(this);//add to enemy list
        new Thread(this).start();
    }

    @Override
    public void run() {
        boolean flag1 =false;
        while(hp>0){
            try {Thread.sleep(sd0);} catch (InterruptedException e) {e.printStackTrace();}
            setY(r.top+2*my.scale);
            if(r.top>=my.h)break;

            try {

                plane h=my.list.get(0);
                if(hit(h,30)){//hit the target or not

                    flag1=true;
                    //try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}

                    Game1Activity.getGame1Activity().finish();

                    break;

                }

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        //while(true){
           // try {Thread.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
           // setY(r.top-sd0);
           /* for(int i=0;i<my.enemylist.size();i++){
                plane h=my.enemylist.get(i);
                plane my_plane=my.myplane;
                if(hit2(h,30)){//hit the target or not
                    //enemy hp - bullet
                    flag1=true;

                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            break;
        }

            */
        //}

        my.list.remove(this);
        my.enemylist.remove(this);
    }
}

class enemyplane2 extends plane implements Runnable{//enemy plane
    private long sd0=(long) (Math.random()*10)+20;//random speed for enemy

    public enemyplane2(){

        w=h=200*my.scale;
        //initial for enemy position
        setX((float)( Math.random()*(my.w-w)));//x is random
        setY(-h);
        img=my.enemyplane2;
        hp=48;//hp=12
        my.list.add(this);//add to the list
        my.enemylist2.add(this);//add to enemy list
        new Thread(this).start();
    }

    @Override
    public void run() {
        boolean flag2 =false;
        while(hp>0){
            try {Thread.sleep(sd0);} catch (InterruptedException e) {e.printStackTrace();}
            setY(r.top+2*my.scale);
            if(r.top>=my.h)break;

            try {

                plane h=my.list.get(0);
                if(hit(h,30)){//hit the target or not

                    flag2=true;

                    Game1Activity.getGame1Activity().finish();
                    break;

                }

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        my.list.remove(this);
        my.enemylist2.remove(this);
    }
}
class myplane extends plane implements Runnable{//my plane

    public myplane(){
        w=h=200*my.scale;
        //set initial position
        setX(my.w/2-w/2);
        setY(my.h*0.7f-h/2);
        img=my.myplane;//initial my plane
        my.list.add(this);//add my plane to the list
        new Thread(this).start();//thread for bullet
    }

    @Override
    public void run() {
        while(true){
            //shot 120ms
            try {Thread.sleep(120);} catch (InterruptedException e) {e.printStackTrace();}
            new bullet(this);


        }
    }
}
class bullet extends plane implements Runnable{//mybullet
    private int dps;
    private float sd0;

    public bullet(plane plane){
        w=20*my.scale;
        h=30*my.scale;
        img=my.bullet;//initial image
        sd0=6*my.scale;//speed=6
        dps=6;//damage=6
        //set above plane
        setX(plane.r.left+plane.w/2-w/2);
        setY(plane.r.top-h/2);
        my.list.add(this);//add to the list
        new Thread(this).start();//new thread for bullet
    }

    @Override
    public void run() {
        boolean flag=false;
        while(true){
            try {Thread.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
            setY(r.top-sd0);

            try {
                for(int i=0;i<my.enemylist.size();i++){
                    plane h=my.enemylist.get(i);
                    if(hit(h,30)){//hit the target or not
                        h.hp-=dps;//enemy hp - bullet
                        flag=true;
                        my.score++;//score +1
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            try {
                for(int i=0;i<my.enemylist2.size();i++){
                    plane h=my.enemylist2.get(i);
                    if(hit(h,30)){//hit the target or not
                        h.hp-=dps;//enemy hp - bullet
                        flag=true;
                        my.score = my.score+2;//score +1
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }


            if(flag || r.top+h<=0)break;//if bullet hit the enemy or out of screen close loop
        }
        my.list.remove(this);//delete from the list
    }
}
