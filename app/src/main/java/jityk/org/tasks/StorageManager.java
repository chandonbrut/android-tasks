package jityk.org.tasks;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StorageManager {
	
	private TaskDB mTaskDb;
	
	public StorageManager(Activity parent) {
		this.mTaskDb = new TaskDB(parent.getApplicationContext());
	}

	public static class TaskDB extends SQLiteOpenHelper {
		
		final public static String ID = "_id";
		final public static String TASKTEXT = "tasktext";
        final public static String TASKDONE = "done";
		final public static String TABLE = "task";
		final public static String[] columns = { ID, TASKTEXT, TASKDONE };
		
		final private static String CREATE_CMD = 
				"CREATE TABLE " + TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
							+ TASKTEXT + " TEXT NOT NULL,"
                            + TASKDONE + " SHORT NOT NULL DEFAULT 0"
                + ");";
		
		public TaskDB(Context context) {
			super(context, "task_db", null, 1);
//			context.deleteDatabase("task_db");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_CMD);
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

		}

	}

	public void createTask(String task) {
		ContentValues cv = new ContentValues();
		cv.put(TaskDB.TASKTEXT, task);
        cv.put(TaskDB.TASKDONE,0);
		mTaskDb.getWritableDatabase().insert(TaskDB.TABLE, null, cv);

	}
	
	public void retrieveTask(Long id) {

	}

	public void deleteTask(Long id) {
		mTaskDb.getWritableDatabase().delete(TaskDB.TABLE,TaskDB.ID + "=?", new String[] {String.valueOf(id)});
	}

	public void setTaskDone(Long id) {
		ContentValues cv = new ContentValues();
		cv.put(TaskDB.TASKDONE,1);
		mTaskDb.getWritableDatabase().update(TaskDB.TABLE,cv,TaskDB.ID + "=?",new String[] {String.valueOf(id)});

	}

	public Cursor all() {
		return mTaskDb.getWritableDatabase().query(TaskDB.TABLE,TaskDB.columns,null,new String[]{},null,null,null,null);
	}
		
}
