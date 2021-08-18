package yuchen.game.cs309gam1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import java.util.Vector;


class my{//globle varible
    public static int score=0;//score
    public static int width,h;//width height
    public static float scale;//scale for different screen
    public static Vector<plane> list=new Vector<plane>();//list of all fight
    public static Vector<plane> enemylist=new Vector<plane>();//enemy fight
    public static Bitmap myplane,enemyplane,bg,bullet;//image：player plane, enemy plane, background, bullet
    public static myplane my;//player plane
    public static bg b;//background
}

public class game1 extends View{
    private Paint p=new Paint();
    private float x,y;//the coordinate when touch the screen
    private float myx,myy;//the coordinate of the player's plane

    public game1(Context context) {
        super(context);
        //TODO

        setBackgroundColor(Color.GRAY);//set up the background color
        my.myplane= BitmapFactory.decodeResource(getResources(),R.mipmap.myplane);// get the image
        my.enemyplane=BitmapFactory.decodeResource(getResources(),R.mipmap.enemyplane);
        my.bullet=BitmapFactory.decodeResource(getResources(),R.mipmap.bullet);
        my.bg=BitmapFactory.decodeResource(getResources(), R.mipmap.bg);

        new Thread(new re()).start();//build a new thread
        new Thread(new loaddr()).start();//build a new thread for enemy
    }
    @Override
    protected void onDraw(Canvas g) {
        super.onDraw(g);
        g.drawBitmap(my.b.img,null,my.b.r,p);// paint the background

        for(int i=0;i<my.list.size();i++){// all flight object are add tot my.list
            plane h=my.list.get(i);           //then print it out
            g.drawBitmap(h.img,null,h.r,p);
        }
        g.drawText("kill："+my.score,0,my.h-50,p);

    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {//get the width of the screen
        super.onSizeChanged(w, h, oldw, oldh);
        my.width=w;//get width
        my.h=h;//height

        my.scale= (float) (Math.sqrt(my.width * my.h)/ Math.sqrt(1920 * 1080));
        p.setTextSize(50*my.scale);//set up the scale of the kill
        p.setColor(Color.WHITE);//set up to white
        //game start
        my.b=new bg();//initial backgroud
        my.my=new myplane();//initial player plane
    }
    private class re implements Runnable {
        @Override
        public void run() {
            //refresh every 10ms
            while(true){
                try { Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
                postInvalidate();//refresh
            }
        }
    }
    private class loaddr implements Runnable{
        @Override
        public void run() {
            while(true){
                //every 300ms will have a new enemy
                try {Thread.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
                try {
                    new enemyplane();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
class plane{
    public RectF r=new RectF();//locate the position
    public int hp;//life
    public float width,height;//width and height
    public Bitmap img;//image



    public void setX(float x){
        r.left=x;
        r.right=x+width;
    }
    public void setY(float y){
        r.top=y;
        r.bottom=y+height;
    }

    public boolean touch(plane obj,float px) {//to find out crash or not
        //TODO
        px*=my.scale;

        return false;

    }
}
class bg extends plane implements  Runnable{//background
    public bg(){
        width=my.width;
        height=my.h*2;//the height of background should be twice of the screen
        img=my.bg;
        setX(0);
        setY(-my.h);
        new Thread(this).start();
    }
    @Override
    public void run() {
        //TODO
    }
}

class enemyplane extends plane implements Runnable{//enemy plane
    private long sd0=(long) (Math.random()*10)+10;// there will be a random number between (10,20) for enemy plane speed

    public enemyplane(){

        width=height=200*my.scale;
        //the position for enemy plane
        setX((float)( Math.random()*(my.width-width)));//x is random
        setY(-height);//start out of screen
        img=my.enemyplane;
        hp=12;//life will be 12
        my.list.add(this);//add enemy plane to the print list
        my.enemylist.add(this);//add to enemy list
        new Thread(this).start();
    }

    @Override
    public void run() {
        //TODO
    }
}

class myplane extends plane implements Runnable{//player plane

    public myplane(){
        width=height=200*my.scale;//every time need to times scale for pixel
        //set up initial place
        setX(my.width/2-width/2);
        setY(my.h*0.7f-height/2);
        img=my.myplane;//initial the image
        my.list.add(this);//add the image to the list
        new Thread(this).start();//the thread for shot the bullet
    }

    @Override
    public void run() {
        //Todo
    }
}
class bullet extends plane implements Runnable {//bullet
    private int dps;
    private float sd0;

    public bullet(plane plane) {
        width=height=90*my.scale;
        img=my.bullet;//img
        sd0=6*my.scale;//
        dps=6;//damage=6
        //set the bullet above the player's plane
        setX(plane.r.top);
        setY(plane.r.top-height/2);
        my.list.add(this);//add the bullet to the list
        new Thread(this).start();//build new thread which bullet move upward
    }


    @Override
    public void run() {
        //TODO
    }
}