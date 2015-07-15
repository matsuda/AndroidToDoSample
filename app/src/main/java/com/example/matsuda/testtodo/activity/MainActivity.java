package com.example.matsuda.testtodo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.matsuda.testtodo.R;
import com.example.matsuda.testtodo.adapter.TaskAdapter;
import com.example.matsuda.testtodo.model.Task;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private static final ArrayList<Task> tasks = new ArrayList<Task>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 10; i++) {
            Task task = new Task();
            task.name = "task" + i;
            task.date = "2015/01/23 18:25";
            tasks.add(task);
        }
        TaskAdapter adapter = new TaskAdapter(this, 0, tasks);

        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
