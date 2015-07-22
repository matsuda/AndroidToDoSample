package com.example.matsuda.testtodo.adapter;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.matsuda.testtodo.R;
import com.example.matsuda.testtodo.model.Task;

/**
 * Created by matsuda on 15/07/16.
 */
public class TaskEditAdapter extends BaseAdapter implements View.OnFocusChangeListener, View.OnKeyListener {
    private static final String TAG = TaskEditAdapter.class.getSimpleName();
    private static final int TYPE_SINGLELINE = 0;
    private static final int TYPE_MULTILINES = 1;
    private static final int TYPE_SELECTION = 2;

    private Context context;
    private LayoutInflater inflater;
    private Task task;

    /**
     * delegate
     */
    public interface ViewSelectionListener {
        void onViewSelected(int position);
    }

    private ViewSelectionListener selectionListener;

    public void setViewSelectionListener(ViewSelectionListener listener) {
        selectionListener = listener;
    }

    public TaskEditAdapter(Context context, Task task) {
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.task = task;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 1:
            case 2:
                return TYPE_SELECTION;
            case 3:
                return TYPE_MULTILINES;
            default:
                return TYPE_SINGLELINE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_SELECTION:
                convertView = getSelectionView(position, convertView, parent);
                break;
            case TYPE_MULTILINES:
                convertView = getMultiLinesView(position, convertView, parent);
                break;
            default:
                convertView = getSingleLineView(position, convertView, parent);
                break;
        }
        return convertView;
    }

    private View getSingleLineView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.edit_text_list, parent, false);
            holder = new ViewHolder();
            holder.captionView = (TextView) convertView.findViewById(R.id.caption);
            holder.valueView = (EditText) convertView.findViewById(R.id.value);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        assignValueToViewHolder(position, holder);
        holder.valueView.setTag(position);
        holder.valueView.setOnFocusChangeListener(this);
        // holder.valueView.setOnKeyListener(this);
        return convertView;
    }

    private View getMultiLinesView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.edit_multilines_text_list, parent, false);
            holder = new ViewHolder();
            holder.captionView = (TextView) convertView.findViewById(R.id.caption);
            holder.valueView = (EditText) convertView.findViewById(R.id.value);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        assignValueToViewHolder(position, holder);
        holder.valueView.setTag(position);
        holder.valueView.setOnFocusChangeListener(this);
        return convertView;
    }

    private View getSelectionView(final int position, View convertView, ViewGroup parent) {
        ViewHolderSelection holder;
        if (convertView == null) {
            /** delegateの場合 */
            // convertView = inflater.inflate(R.layout.value_default_list, parent, false);
            /** spinnerの場合 */
            convertView = inflater.inflate(R.layout.edit_selection_list, parent, false);
            holder = new ViewHolderSelection();
            holder.captionView = (TextView) convertView.findViewById(R.id.caption);
            // holder.valueView = (TextView) convertView.findViewById(R.id.value);
            holder.valueView = (Spinner) convertView.findViewById(R.id.value);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderSelection) convertView.getTag();
        }
        assignValueToSelectionViewHolder(position, holder);
        /**
         * delegateを利用する
         * Activity側でAlertDialogを利用してドロップボックス表示
         */
        /*
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectionListener != null) {
                    selectionListener.onViewSelected(position);
                }
            }
        });
        */
        return convertView;
    }

    private void assignValueToViewHolder(final int position, ViewHolder holder) {
        switch (position) {
            case 0:
                holder.captionView.setText(context.getString(R.string.task_name) + " : ");
                holder.valueView.setText(task.name);
                break;
            case 3:
                holder.captionView.setText(context.getString(R.string.task_memo) + " : ");
                holder.valueView.setText(task.memo);
                break;
            default:
                break;
        }
    }

    private void assignValueToSelectionViewHolder(final int position, ViewHolderSelection holder) {
        switch (position) {
            case 1: {
                /**
                 * Spinnerを利用してドロップボックス表示
                 */
                holder.captionView.setText(context.getString(R.string.task_priority) + " : ");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_item);
                adapter.add(Task.Priority.High.toString());
                adapter.add(Task.Priority.Normal.toString());
                adapter.add(Task.Priority.Low.toString());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                Spinner spinner = holder.valueView;
                spinner.setAdapter(adapter);
                String priorityString = this.task.priority.toString();
                int idx = adapter.getPosition(priorityString);
                spinner.setSelection(idx);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int size = Task.Priority.values().length;
                        Task.Priority priority = Task.Priority.getEnum(size - 1 - position);
                        TaskEditAdapter.this.task.priority = priority;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                /** delegateの場合 */
                // holder.valueView.setText(task.priority.toString());
                break;
            }
            case 2:
                holder.captionView.setText(context.getString(R.string.task_date) + " : ");
                // holder.valueView.setText(task.date);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            if (v.getTag() == null) return;
            int position = (int)v.getTag();
            EditText textView = (EditText)v;
            String value = textView.getText().toString();
            switch (position) {
                case 0:
                    task.name = value;
                    break;
                case 1:
                    // task.priority = value;
                    break;
                case 2:
                    // task.date = value;
                    break;
                case 3:
                    task.memo = value;
                    break;
            }
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_DOWN) return false;
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return false;
    }

    private static class ViewHolder {
        TextView captionView;
        EditText valueView;
    }
    private static class ViewHolderSelection {
        TextView captionView;
        Spinner valueView;
        // TextView valueView;
    }
}
