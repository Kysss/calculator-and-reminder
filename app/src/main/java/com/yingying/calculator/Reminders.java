package com.yingying.calculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Reminders extends AppCompatActivity {
    ArrayList<Reminder> reminderList;
   static ArrayList<Boolean>checks  = new ArrayList<Boolean>();
    Context CTX = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView tv = (TextView)findViewById(R.id.textView5);
        ListView RL = (ListView)findViewById(R.id.reminderList);
        ImageView add = (ImageView)findViewById(R.id.imageView);
        ImageView delete= (ImageView)findViewById(R.id.imageView2);

        Database db = new Database(CTX);
        if(db.isEmpty(db)){
            tv.setText("You have no reminder yet.");
        }else{
            tv.setText("");
        }

        reminderList = new ArrayList<Reminder>();
        loadReminders();
        final ListAdapter listAdapter = new ListAdapter(this,reminderList);
        RL.setAdapter(listAdapter);

        for(int b = 0; b< reminderList.size();b++){
            checks.add(b,false);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CTX);
                builder.setMessage("Do you want to switch to Calculator?");
                builder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Reminders.this,Calculator.class);
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

        assert add != null;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = LayoutInflater.from(Reminders.this).inflate(R.layout.userinput, null);


                    AlertDialog.Builder builder = new AlertDialog.Builder(CTX);
                builder.setTitle("Type in your reminder.");
                    builder.setView(view);
              //  final EditText ui= view.findViewById(R.id.ui);


                    builder.setPositiveButton(
                            "Add",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                 //   View view = (LayoutInflater.from(Reminders.this)).inflate(R.layout.userinput, null);

                                    Database db = new Database(CTX);
                                    EditText userinput = (EditText) view.findViewById(R.id.ui);
                                    String ui = userinput.getText().toString();
                                    db.putInfo(db, ui);

                                    Intent intent = new Intent(Reminders.this,Reminders.class);
                                    startActivity(intent);
                                    finish();

                                }
                            });

                    builder.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder.create();
                    alert11.show();
            }
        });


        assert delete != null;
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CTX);
                builder.setTitle("Delete Reminder(s)");
                builder.setMessage("Are you sure you want to delete the selected reminder(s)? They cannot be restored after deletion.");


                builder.setPositiveButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Database db = new Database(CTX);
                                ArrayList<Boolean> checks = Reminders.checks;
                                if (checks.size() > 0) {
                                    ArrayList deleteList = new ArrayList();
                                    for (int i = 0; i < reminderList.size(); i++) {
                                        if (checks.get(i) == true) {
                                            Reminder r = reminderList.get(i);
                                            String date = r.getDate();
                                            String c = r.getContent();
                                            db.deleteReminder(db, date, c);
                                        }
                                    }
                                    Intent intent = new Intent(Reminders.this, Reminders.class);
                                    startActivity(intent);
                                    finish();
                                }


                            }
                        });

                builder.setNegativeButton(
                        "Cancel",
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




    public void loadReminders(){
        Database db = new Database(CTX);
        Cursor CR = db.getInformation(db);
        CR.moveToFirst();
        while(!CR.isAfterLast()){
            if(CR.getString(0)!=null && CR.getString(1)!=null){
                reminderList.add(new Reminder(CR.getString(0),CR.getString(1)));
            }
            CR.moveToNext();
        }
    }

}
