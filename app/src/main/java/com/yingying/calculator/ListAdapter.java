package com.yingying.calculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yingying Xia on 2016/6/1.
 */
public class ListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    ArrayList<Reminder> reminders;



    private class ViewHolder {
        CheckBox checkbox;
        TextView date;
       // TextView content;
    }
    public ListAdapter(Context context, ArrayList<Reminder>reminders){
        this.context = context;
        this.inflater=LayoutInflater.from(context);
        this.reminders=reminders;
    }

    @Override
    public int getCount() {
        return reminders.size();
    }

    @Override
    public Object getItem(int position) {
        return reminders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.single_reminder,null);
            holder.date =(TextView)convertView.findViewById(R.id.textView2);
            holder.checkbox=(CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);
        }
       else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.date.setText(reminders.get(position).getDate());
        holder.checkbox.setText(reminders.get(position).getContent());


        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((CheckBox)v).isChecked()){
                    Reminders.checks.set(position,true);
                }
                else{
                    Reminders.checks.set(position,false);
                }
            }
        });

        return convertView;
    }


}
