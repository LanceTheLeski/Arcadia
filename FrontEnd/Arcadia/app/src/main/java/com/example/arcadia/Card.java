package com.example.arcadia;


import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {

    private int num = 0;
    private TextView label;     //cards needs character

    public Card(Context context) {
        super(context);

        label = new TextView(getContext());   //init
        label.setTextSize(32);    //set size
        label.setBackgroundColor(0x33ffffff);     //set background
        label.setGravity(Gravity.CENTER);

        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10, 10, 0, 0);

        addView(label, lp);

        setNum(0);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;

        if(num <= 0){
            label.setText("");         //if card is 0 show nothing
        }else {
            label.setText(num+"");
        }
    }

    public boolean equals(Card card) {             //判断两个卡片上的数字是否相同
        return this.getNum() == card.getNum();
    }

}
