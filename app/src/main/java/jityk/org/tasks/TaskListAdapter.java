package jityk.org.tasks;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.jityk.tasks.R;

public class TaskListAdapter extends CursorAdapter {
	
	private Context mContext;
	private StorageManager dbManager;

	public TaskListAdapter(Context context, Cursor c, int flags, StorageManager dbManager) {
		super(context, c, flags);
		this.dbManager = dbManager;
		this.mContext = context;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		String task = cursor.getString(cursor.getColumnIndex(StorageManager.TaskDB.TASKTEXT));

		TextView textView = (TextView) view.findViewById(R.id.taskText);

		Boolean done = cursor.getShort(cursor.getColumnIndex(StorageManager.TaskDB.TASKDONE)) > 0;

        final long id = cursor.getLong(cursor.getColumnIndex(StorageManager.TaskDB.ID));


		if (done) {
            Log.i("ADAPTER","TASK: " + id + " DONE:" + cursor.getShort(cursor.getColumnIndex(StorageManager.TaskDB.TASKDONE)) + "->" +task);
            textView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		} else {
            textView.setPaintFlags(0);
        }

		textView.setText(task);
		textView.setCompoundDrawables(null, null, null, null);

		ImageButton mDeleteButton = (ImageButton) view.findViewById(R.id.deleteButton);
		ImageButton mDoneButton = (ImageButton) view.findViewById(R.id.doneButton);


		mDeleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dbManager.deleteTask(id);
				notifyDataSetChanged();
				swapCursor(dbManager.all());
			}
		});

		mDoneButton.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dbManager.setTaskDone(id);
						notifyDataSetChanged();
						swapCursor(dbManager.all());
					}
				}
		);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return LayoutInflater.from(context).inflate(R.layout.list_layout,parent,false);
	}

}