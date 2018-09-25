package jityk.org.tasks;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.jityk.tasks.R;

public class MainActivity extends Activity {
	
	private CursorAdapter mAdapter;
	private Button mTaskButton;
	private EditText mTaskEditTask;

	private StorageManager storage;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.storage = new StorageManager(this);

        Cursor c = storage.all();

        mAdapter = new TaskListAdapter(getApplicationContext(), c, 0, storage);

        ListView lv = (ListView)findViewById(R.id.taskList);
        lv.setAdapter(mAdapter);

        mTaskButton = (Button) findViewById(R.id.taskButton);
        mTaskEditTask = (EditText) findViewById(R.id.taskEditText);

        mTaskButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        storage.createTask(mTaskEditTask.getText().toString());
                        mAdapter.swapCursor(storage.all());
                    }
                }
        );

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }
}
