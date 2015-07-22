package com.example.matsuda.testtodo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matsuda.testtodo.R;
import com.example.matsuda.testtodo.adapter.TaskEditAdapter;
import com.example.matsuda.testtodo.model.Task;

public class EditActivity extends AppCompatActivity implements TaskEditAdapter.ViewSelectionListener {
    private static final String TAG = EditActivity.class.getSimpleName();

    public static final int REQUEST_CODE_CREATE = 1;
    public static final int REQUEST_CODE_UPDATE = 2;

    private TaskEditAdapter adapter;
    protected Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        this.task = (Task)intent.getSerializableExtra("task");
        setTitle();

        adapter = new TaskEditAdapter(this, this.task);
        adapter.setViewSelectionListener(this);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);

        Button cancelButton = (Button)findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button saveButton = (Button)findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClickListener();
            }
        });
    }

    protected void setTitle() {}

    protected void saveClickListener() {
        if (this.task.save(this)) {
            Intent intent = new Intent();
            intent.putExtra("task", this.task);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "did fall to save task.", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onViewSelected(int position) {
        switch (position) {
            case 1:
                presentPriorityDialog();
                break;
            case 2:
                break;
        }
    }

    private void presentPriorityDialog() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice);
        Task.Priority[] priorities = Task.Priority.values();
        int size = priorities.length;
        for (int i = size - 1, count = 0; i >= count; i++) {
            adapter.add(priorities[i].toString());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("選択してください");
        int idx = size - 1 - this.task.priority.toInt();
        builder.setSingleChoiceItems(adapter, idx, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int size = Task.Priority.values().length;
                Task.Priority priority = Task.Priority.getEnum(size - 1 - which);
                EditActivity.this.task.priority = priority;
                EditActivity.this.adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
