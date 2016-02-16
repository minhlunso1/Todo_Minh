package minhna.submission.todocoderschool.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "ToDoDB";

	public static final String TABLE_NAME = "List";
	public static final String COL_Id = "_id";
	public static final String COL_ToDo = "ToDo";

	private String createTableStatement = "CREATE TABLE " + TABLE_NAME + " ("
			+ COL_Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_ToDo + " TEXT NOT NULL)";
	public MyDBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(createTableStatement);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
}
