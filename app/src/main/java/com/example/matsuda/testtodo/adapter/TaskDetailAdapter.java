package com.example.matsuda.testtodo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.matsuda.testtodo.R;
import com.example.matsuda.testtodo.model.Task;

/**
 * Created by matsuda on 15/07/16.
 */
public class TaskDetailAdapter extends BaseAdapter {
    public static final String TAG = TaskDetailAdapter.class.getSimpleName();
    private static final int TYPE_DEFAULT = 0;
    private static final int TYPE_MULTILINES = 1;

    Context context;
    LayoutInflater inflater;
    Task task;

    public TaskDetailAdapter(Context context, Task task) {
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.task = task;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 3:
                return TYPE_MULTILINES;
            default:
                return TYPE_DEFAULT;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return task;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        switch (getItemViewType(position)) {
            case TYPE_MULTILINES:
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.value_multilines_list, parent, false);
                    holder = new ViewHolder();
                    holder.captionView = (TextView)convertView.findViewById(R.id.caption);
                    holder.valueView = (TextView)convertView.findViewById(R.id.value);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder)convertView.getTag();
                }
                break;
            default:
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.value_default_list, parent, false);
                    holder = new ViewHolder();
                    holder.captionView = (TextView)convertView.findViewById(R.id.caption);
                    holder.valueView = (TextView)convertView.findViewById(R.id.value);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder)convertView.getTag();
                }
                break;
        }
        TextView captionView = (TextView)convertView.findViewById(R.id.caption);
        TextView valueView = (TextView)convertView.findViewById(R.id.value);
        switch (position) {
            case 0:
                holder.captionView.setText(context.getString(R.string.task_name));
                holder.valueView.setText(task.name);
                break;
            case 1:
                holder.captionView.setText(context.getString(R.string.task_priority));
                holder.valueView.setText(task.priority.toString());
                break;
            case 2:
                holder.captionView.setText(context.getString(R.string.task_date));
                holder.valueView.setText(task.date);
                break;
            case 3:
                holder.captionView.setText(context.getString(R.string.task_memo));
                holder.valueView.setText(task.memo);
                break;
            default:
                break;
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView captionView;
        TextView valueView;
    }
}