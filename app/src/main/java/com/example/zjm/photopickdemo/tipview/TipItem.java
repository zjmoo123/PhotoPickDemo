package com.example.zjm.photopickdemo.tipview;

import android.graphics.Color;

/**
 * Created by zjm on 16-9-29.
 */

public class TipItem {
    private String title;
    private int textColor = Color.WHITE;

    public TipItem(String title) {
        this.title = title;
    }

    public TipItem(String title, int textColor) {
        this.title = title;
        this.textColor = textColor;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public String getTitle() {

        return title;
    }

    public int getTextColor() {
        return textColor;
    }
}
