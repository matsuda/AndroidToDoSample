package com.example.matsuda.testtodo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.matsuda.testtodo.App;
import com.example.matsuda.testtodo.R;
import com.example.matsuda.testtodo.adapter.TaskListAdapter;
import com.example.matsuda.testtodo.model.Observer;
import com.example.matsuda.testtodo.model.Task;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private ListView listView;

    private Observer observer = new Observer() {
        @Override
        public void update(Object object) {
            if (object instanceof Task) {
                MainActivity.this.updateTask((Task)object);
            }
        }
    };

    private void updateTask(Task task) {
        int position = -1;
        for (int i = 0, c = this.tasks.size(); i < c; i++) {
            Task t = this.tasks.get(i);
            if (t.id == task.id) {
                position = i;
                break;
            }
        }
        Log.d(TAG, "position : " + position);
        if (position < 0) { return; }

        Task newTask = Task.findById(this, task.id);
        if (newTask == null) { return; }
        Log.d(TAG, "find task");

        this.tasks.set(position, newTask);
        View view = listView.getChildAt(position);
        listView.getAdapter().getView(position, view, listView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Task一覧");

        // this.tasks = Task.mockTasks(20);
        this.tasks = Task.findAll(this, "20");

        TaskListAdapter adapter = new TaskListAdapter(this, 0, tasks);

        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        App.addObserver(observer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        App.removeObserver(observer);
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
            Intent intent = new Intent(this, CreateActivity.class);
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
                        ((TaskListAdapter)this.listView.getAdapter()).notifyDataSetChanged();
//                        Task nTask = Task.findById(this, task.id);
//                        if (nTask != null) {
//                            this.tasks.add(nTask);
//                            this.adapter.notifyDataSetChanged();
//                        }
                    }
                }
                break;
        }
    }
}
