package com.example.arcadia;



import java.util.ArrayList;
import java.util.List;

import android.R.bool;
import android.R.id;
import android.R.xml;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ListView;

public class GameView extends GridLayout {

    private Card[][] cardsMap = new Card[4][4]; // use 2d matrix to record
    private List<Point> emptyPoints = new ArrayList<Point>(); // put everything in a list


    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initGameView();
    }

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    private void initGameView() { // initialize

        setColumnCount(4); // GridLayout is 4 column
        setBackgroundColor(0xffbbada0); // background color
        addCards(250, 250);
        addRandomNum();
        addRandomNum();
        setOnTouchListener(new View.OnTouchListener() {


            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {

                            if (offsetX < -5) {
                                swipeLeft();
                                // System.out.println("left");
                            } else if (offsetX > 5) {
                                swipeRight();
                                // System.out.println("right");
                            }

                        } else {

                            if (offsetY < -5) {
                                swipeUp();
                                // System.out.println("up");
                            } else if (offsetY > 5) {
                                swipeDown();
                                // System.out.println("down");
                            }

                        }
                        break;
                }

                return true;
            }
        });


    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w, h) -10 ) / 4;
        int cardHeight = cardWidth;

        //addCards(cardWidth, cardHeight);

        //startGame(); //start game


    }

    private void addCards(int cardWidth, int cardHeight) {

        Card card;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                card = new Card(getContext());
                card.setNum(0); // start with all 0 and not showing up at this case
                addView(card, cardWidth, cardHeight); //

                cardsMap[x][y] = card;
            }
        }
    }

    private void startGame() { // is restart should clearing up the earilier game then add random number

        Game2048Activity.getGame2048Activity().clearScore(); // score clear up

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }

    private void addRandomNum() { // add rondam number

        emptyPoints.clear();

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {

                if (cardsMap[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y));

                }

            }
        }

        Point p = emptyPoints
                .remove((int) (Math.random() * emptyPoints.size())); // rendom remove a point

        cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4); // add a number here

    }


    private void swipeLeft() {

        boolean merge = false;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x + 1; x1 < 4; x1++) {

                    if (cardsMap[x1][y].getNum() > 0) {

                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);

                            x--;
                            merge = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) { // if the space is not empty and the number are equal add number and put to this position
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);

                            // when add point increase score
                            Game2048Activity.getGame2048Activity().addScore(
                                    cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }

        if (merge) { // when add number check gameover or not
            addRandomNum();
            checkComplete(); // if no space and cant add together, check gameover or not
        }
    }

    private void swipeRight() {

        boolean merge = false;

        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardsMap[x1][y].getNum() > 0) {

                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);

                            x++;

                            merge = true;

                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);

                            // add point
                            Game2048Activity.getGame2048Activity().addScore(
                                    cardsMap[x][y].getNum());

                            merge = true;
                        }

                        break;

                    }
                }
            }
        }

        if (merge) {
            addRandomNum();
            checkComplete();
        }

    }

    private void swipeUp() {

        boolean merge = false;

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y + 1; y1 < 4; y1++) {

                    if (cardsMap[x][y1].getNum() > 0) {

                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);

                            y--;

                            merge = true;

                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);

                            // add point
                            Game2048Activity.getGame2048Activity().addScore(
                                    cardsMap[x][y].getNum());

                            merge = true;

                        }

                        break;

                    }
                }
            }
        }

        if (merge) {
            addRandomNum();
            checkComplete();
        }

    }

    private void swipeDown() {

        boolean merge = false;

        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardsMap[x][y1].getNum() > 0) { // not empty

                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);

                            y++;

                            merge = true;

                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);

                            // when add together add point
                            Game2048Activity.getGame2048Activity().addScore(
                                    cardsMap[x][y].getNum());

                            merge = true;

                        }

                        break;

                    }
                }
            }
        }

        if (merge) {
            addRandomNum();
            checkComplete();
        }

    }

    // gameover or not
    private void checkComplete() {

        boolean complete = true;

        All: for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {

                // 5 situation for gameover or not
                if (cardsMap[x][y].getNum() == 0
                        || (x > 0 && cardsMap[x][y].equals(cardsMap[x - 1][y]))
                        || (x < 3 && cardsMap[x][y].equals(cardsMap[x + 1][y]))
                        || (y > 0 && cardsMap[x][y].equals(cardsMap[x][y - 1]))
                        || (y < 3 && cardsMap[x][y].equals(cardsMap[x][y + 1]))) {

                    complete = false;
                    break All; // sign jump out of all loop
                }
            }
        }

        if (complete) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setTitle("Hello")
                    .setMessage("Game Over")
                    .setPositiveButton("restart",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    startGame();
                                }
                            });

            dialog.setNegativeButton("Quit", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Game2048Activity.getGame2048Activity().finish();
                }
            });
            dialog.show();
        }

    }

}
