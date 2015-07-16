package com.example.matsuda.testtodo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.matsuda.testtodo.R;
import com.example.matsuda.testtodo.adapter.TaskEditAdapter;
import com.example.matsuda.testtodo.model.Task;

public class EditActivity extends AppCompatActivity {
    public static final String TAG = DetailActivity.class.getSimpleName();
    public Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        this.task = (Task)intent.getSerializableExtra("task");
        String name = this.task.name;
        if (name == null || name.isEmpty()) {
            setTitle("ToDoの新規作成");
        } else  {
            setTitle(this.task.name + "の編集");
        }
        // DEBUG
        Bundle args = intent.getExtras();
        Log.d(TAG, args.toString());

        TaskEditAdapter adapter = new TaskEditAdapter(this, this.task);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
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
