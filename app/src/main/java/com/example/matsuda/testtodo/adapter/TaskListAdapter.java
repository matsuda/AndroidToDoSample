package com.example.matsuda.testtodo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.matsuda.testtodo.R;
import com.example.matsuda.testtodo.model.Task;

import java.util.List;

/**
 * Created by matsuda on 15/07/15.
 */
public class TaskListAdapter extends ArrayAdapter<Task> {

    static class ViewHolder {
        TextView nameView;
        TextView dateView;
    }

    private LayoutInflater layoutInflater;
    private ViewHolder viewHolder;

    public TaskListAdapter(Context context, int resource, List<Task> objects) {
        super(context, resource, objects);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // convertView = layoutInflater.inflate(R.layout.task_list, null);
            convertView = layoutInflater.inflate(R.layout.task_list, parent, false);
            viewHolder = new  ViewHolder();
            viewHolder.nameView = (TextView)convertView.findViewById(R.id.name);
            viewHolder.dateView = (TextView)convertView.findViewById(R.id.date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Task task = (Task)getItem(position);
        viewHolder.nameView.setText(task.name);
        viewHolder.dateView.setText(task.date);

        return convertView;
    }
}
