package com.example.cs301_hw3;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import java.util.ArrayList;

/**
 External Citation
 Date: 9 November 2019
 Problem: Needed to learn how to implement BaseAdapter for touch events (sliding)
 Resource:
 https://developer.android.com/reference/android/widget/BaseAdapter
 Solution: Read documentation from android
 */

public class Adapter extends BaseAdapter {
    private ArrayList<Button> theButtons = null;
    private int theColWidth, theColHeight;

    public Adapter(ArrayList<Button> buttons, int colWidth, int colHeight){
        theButtons = buttons;
        theColWidth = colWidth;
        theColHeight = colHeight;
    }
    @Override
    public int getCount() {
        return theButtons.size();
    }
    @Override
    public Object getItem(int position) {
        return (Object) theButtons.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;

        if (convertView == null) {
            button = theButtons.get(position);
        } else {
            button = (Button) convertView;
        }


        /**
         External Citation
         Date: 9 November 2019
         Problem: Needed to virtualize list of items (width and height of gridview)
         Resource:
         https://developer.android.com/reference/android/widget/AbsListView
         Solution: Read documentation from android
         */

        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(theColWidth, theColHeight);
        button.setLayoutParams(params);

        return button;
    }
}
