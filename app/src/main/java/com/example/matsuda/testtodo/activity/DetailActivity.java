package com.example.matsuda.testtodo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.matsuda.testtodo.R;
import com.example.matsuda.testtodo.adapter.TaskDetailAdapter;
import com.example.matsuda.testtodo.model.Task;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();
    public Task task;
    private TaskDetailAdapter adapter;

    public void setTask(Task task) {
        this.task = task;
        if (this.adapter != null) {
            this.adapter.setTask(task);
            this.adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        // DEBUG
        // Bundle args = intent.getExtras();
        // Log.d(TAG, args.toString());
        Task task = (Task)intent.getSerializableExtra("task");
        setTask(task);
        setTitle(task.name);

        adapter = new TaskDetailAdapter(this, this.task);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("task", this.task);
            startActivityForResult(intent, EditActivity.REQUEST_CODE_UPDATE);
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EditActivity.REQUEST_CODE_UPDATE:
                if (resultCode == RESULT_OK) {
                    Task task = (Task)data.getSerializableExtra("task");
                    if (task != null) {
                        setTask(task);
                    }
                }
                break;
        }
    }
}
