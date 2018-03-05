package com.yingying.calculator;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.regex.Pattern;
public class Calculator extends AppCompatActivity {

    public TextView screen;
    public String display = "";
    public String currentOperator = "";
    private String result = "";
    public Context CTX = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        screen = (TextView) findViewById(R.id.textView);
        screen.setText(display);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
     /*   fab.setOnTouchListener(new MyTouchListener());
        fab.setOnDragListener(new MyDragListener());*/
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //         .setAction("Action", null).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(CTX);
                builder.setMessage("Do you want to switch to Reminder?");
                builder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Calculator.this,Reminders.class);
                                startActivity(intent);

                            }
                        });

                builder.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder.create();
                alert11.show();


            }
        });

    }


    public void updateScreen() {
        screen.setText(display);

    }

    public void onClickNumber(View v) {
        Button b = (Button) v;
        display += b.getText();
        updateScreen();
    }

    public void onClickOperation(View v) {
        if (display == "") return;

        Button b = (Button) v;

        if (result != "") {
            String _display = result;
            clear();
            display = _display;
        }

        if (currentOperator != "") {
            Log.d("CalcX", "" + display.charAt(display.length() - 1));
            if (isOperator(display.charAt(display.length() - 1))) {
                display = display.replace(display.charAt(display.length() - 1), b.getText().charAt(0));
                updateScreen();
                return;
            } else {
                getResult();
                display = result;
                result = "";
            }
            currentOperator = b.getText().toString();
        }
        display += b.getText();
        currentOperator = b.getText().toString();
        updateScreen();
    }


    public void clear() {
        display = "";
        currentOperator = "";
        result = "";
        updateScreen();

    }

    private boolean isOperator(char op) {
        switch (op) {
            case '+':
            case '-':
            case 'x':
            case '÷':
                return true;
            default:
                return false;
        }
    }


    public void onClickClear(View v) {
        clear();
        updateScreen();

    }


    public double operate(String a, String b, String op) {
        switch (op) {
            case "+":
                return Double.valueOf(a) + Double.valueOf(b);
            case "-":
                return Double.valueOf(a) - Double.valueOf(b);
            case "÷":
                try {
                    return Double.valueOf(a) / Double.valueOf(b);
                } catch (Exception e) {
                    Log.d("Calc", e.getMessage());
                }
            case "*":
                return Double.valueOf(a) * Double.valueOf(b);
            default:
                return -1;

        }


    }

    private boolean getResult() {
        if (currentOperator == "") return false;
        String[] operation = display.split(Pattern.quote(currentOperator));
        if (operation.length < 2) return false;
        result = String.valueOf(operate(operation[0], operation[1], currentOperator));
        return true;
    }

    public void onClickEqual(View v) {
        if (display == "") return;
        if (!getResult()) return;
        screen.setText(display + "\n" + String.valueOf(result));
    }

   /* private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }
    class MyDragListener implements View.OnDragListener {

        private boolean dropEventNotHandled(DragEvent dragEvent) {
            return !dragEvent.getResult();
        }
        @Override
        public boolean onDrag(View v, DragEvent event) {
            View view;
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);

                    break;
                default:
                    break;
            }
            return true;
        }
    }*/






}
