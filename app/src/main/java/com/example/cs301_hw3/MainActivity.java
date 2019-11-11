package com.example.cs301_hw3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class MainActivity extends AppCompatActivity {

    /**
     External Citation
     Date: 9 November 2019
     Problem: Was having trouble addding button to gridview
     Resource:
     https://stackoverflow.com/questions/17638714/adding-button-to-a-gridview-in-android
     Solution: Wasn't able to implement the button to the grid view
     */

    /**
     External Citation
     Date: 3 November 2019
     Problem: Didn't know how to use grid layout for android studio
     Resource:
     https://developer.android.com/reference/android/widget/GridLayout
     Solution: Read documentation to make grid layout
     */

    private static final int col = 4;
    private static final int board = col * col;
    private static GestureDetect GridView;
    private static int colWidth, colHeight;
    private static String[] pieceList;
    public static final String up = "up";
    public static final String down = "down";
    public static final String left = "left";
    public static final String right = "right";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setDimensions();
        scramble();
    }

    /**
     External Citation
     Date: 3 November 2019
     Problem: Cannot resolve constructor 'String(int)' error
     Resource:
     https://stackoverflow.com/questions/39025076/unsure-how-to-resolve-cannot-resolve-constructor-error
     Solution: Changed parantheses to brackets (changed String(board) to String[board] );
     */
    private void init() {
        pieceList = new String[board];
        for (int i = 0; i <board; i++){
            pieceList[i] =  String.valueOf(i);
        }
        GridView = (GestureDetect) findViewById(R.id.grid);
        GridView.setNumColumns(col);
    }

    private void scramble() {
        int index;
        String temp;

        for(int i = pieceList.length - 1; i > 0; i--){
            index = ThreadLocalRandom.current().nextInt(i+1);
            temp = pieceList[index];
            pieceList[index] = pieceList[i];
            pieceList[i] = temp;
        }
    }

    /**
     External Citation
     Date: 8 November 2019
     Problem: Needed to learn how to use TreeObserver for touch mode change
     Resource:
     https://developer.android.com/reference/android/view/ViewTreeObserver
     Solution: Read documentation from android and used it for setDimensions  method
     */
    private void setDimensions() {
        ViewTreeObserver vto = GridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                GridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = GridView.getMeasuredWidth();
                int displayHeight = GridView.getMeasuredHeight();
                int statusbarHeight = getStatusBarHeight(getApplicationContext());
                int requiredHeight = displayHeight - statusbarHeight;

                colWidth = displayWidth / col;
                colHeight = requiredHeight / col;

                display(getApplicationContext());
            }
        });
    }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private static void display(Context context) {
        ArrayList<Button> buttons = new ArrayList<>();
        Button button;

        for (int i = 0;i< pieceList.length;i++){
            button = new Button(context);

            if (pieceList[i].equals("0")) {
                button.setBackgroundResource(R.drawable.number1);
            }
            else if (pieceList[i].equals("1")) {
                button.setBackgroundResource(R.drawable.number2);
            }
            else if (pieceList[i].equals("2")){
                button.setBackgroundResource(R.drawable.number3);
            }
            else if (pieceList[i].equals("3")){
                button.setBackgroundResource(R.drawable.number4);
            }
            else if (pieceList[i].equals("4")){
                button.setBackgroundResource(R.drawable.number5);
            }
            else if (pieceList[i].equals("5")){
                button.setBackgroundResource(R.drawable.number6);
            }
            else if (pieceList[i].equals("6")){
                button.setBackgroundResource(R.drawable.number7);
            }
            else if (pieceList[i].equals("7")){
                button.setBackgroundResource(R.drawable.number8);
            }
            else if (pieceList[i].equals("8")){
                button.setBackgroundResource(R.drawable.number9);
            }
            else if (pieceList[i].equals("9")){
                button.setBackgroundResource(R.drawable.number10);
            }
            else if (pieceList[i].equals("10")){
                button.setBackgroundResource(R.drawable.number11);
            }
            else if (pieceList[i].equals("11")){
                button.setBackgroundResource(R.drawable.number12);
            }
            else if (pieceList[i].equals("12")){
                button.setBackgroundResource(R.drawable.number13);
            }
            else if (pieceList[i].equals("13")){
                button.setBackgroundResource(R.drawable.number14);
            }
            else if (pieceList[i].equals("14")){
                button.setBackgroundResource(R.drawable.number15);
            }
            else if (pieceList[i].equals("15")){
                button.setBackgroundResource(R.drawable.number0);
            }

            buttons.add(button);
        }
        GridView.setAdapter(new Adapter(buttons,colWidth,colHeight));
    }


    // win condition method
    private static boolean isSolved() {
        boolean solved = false;

        for (int i = 0; i < pieceList.length; i++) {
            if (pieceList[i].equals(String.valueOf(i))) {
                solved = true;
            } else {
                solved = false;
                break;
            }
        }
        return solved;
    }

    /**
     External Citation
     Date: 8 November 2019
     Problem: Wanted to know how to use toasts for win condition
     Resource:
     https://developer.android.com/guide/topics/ui/notifiers/toasts
     Solution: I read the documentation to learn how to use toasts
     */
    // method for changing piece on grid
    private static void change(Context context, int currentPos, int change) {
        String newPosition = pieceList[currentPos + change];
        pieceList[currentPos + change] = pieceList[currentPos];
        pieceList[currentPos] = newPosition;
        display(context);

        if (isSolved()) Toast.makeText(context, "WINNER!!", Toast.LENGTH_SHORT).show();
    }

    // method for moving pieces
    public static void moveTiles(Context context, String direction, int pos) {
        // KNOWN DEFICIENCY: this method allows sliding between numbers
        // I couldn't figure out how to only allow sliding and moving of numbers to blank space
        if (pos == 0) {
            if (direction.equals(right)) change(context, pos, 1);
            else if (direction.equals(down)) change(context, pos, col);

        } else if (pos == col - 1) {
            if (direction.equals(left))
                change(context, pos, -1);
            else if (direction.equals(down))
                change(context, pos, col);

        } else if (pos > col - 1 && pos < board - col && pos % col == 0) {
            if (direction.equals(up))
                change(context, pos, -col);
            else if (direction.equals(right))
                change(context, pos, 1);
            else if (direction.equals(down))
                change(context, pos, col);

        } else if (pos == col * 2- 1 || pos == col * 4 - 1) {
            if (direction.equals(up))
                change(context, pos, -col);
            else if (direction.equals(left))
                change(context, pos, -1);
            else if (direction.equals(down)) {
                if (pos <= board - col - 1)
                    change(context, pos, col);
            }
        } else if (pos < board - 1 && pos > board - col) {
            if (direction.equals(up))
                change(context, pos, -col);
            else if (direction.equals(left))
                change(context, pos, -1);
            else if (direction.equals(right))
                change(context, pos, 1);

        } else if (pos == board - col) {
            if (direction.equals(up))
                change(context, pos, -col);
            else if (direction.equals(right))
                change(context, pos, 1);

        }else if (pos > 0 && pos < col - 1) {
            if (direction.equals(left))
                change(context, pos, -1);
            else if (direction.equals(down))
                change(context, pos, col);
            else if (direction.equals(right))
                change(context, pos, 1);

        } else {
            if (direction.equals(up))
                change(context, pos, -col);
            else if (direction.equals(left))
                change(context, pos, -1);
            else if (direction.equals(right))
                change(context, pos, 1);
            else
                change(context, pos, col);
        }
    }

}
