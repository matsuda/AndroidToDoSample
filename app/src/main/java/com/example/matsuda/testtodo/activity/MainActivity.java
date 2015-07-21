package com.example.matsuda.testtodo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.matsuda.testtodo.R;
import com.example.matsuda.testtodo.adapter.TaskListAdapter;
import com.example.matsuda.testtodo.model.Task;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private TaskListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Task一覧");

        this.tasks = Task.mockTasks(20);
        adapter = new TaskListAdapter(this, 0, tasks);

        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_add) {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("task", new Task());
            startActivityForResult(intent, EditActivity.REQUEST_CODE_CREATE);
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView listView = (ListView)parent;
        Task task = (Task)listView.getItemAtPosition(position);
        presentDetailActivity(task);
    }

    private void presentDetailActivity(Task task) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("task", task);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EditActivity.REQUEST_CODE_CREATE:
                if (resultCode == RESULT_OK) {
                    Task task = (Task)data.getSerializableExtra("task");
                    if (task != null) {
                        this.tasks.add(task);
                        this.adapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }
}
