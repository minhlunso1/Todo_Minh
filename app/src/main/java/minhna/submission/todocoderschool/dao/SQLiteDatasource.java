package minhna.submission.todocoderschool.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatasource {

	private MyDBHelper helper;

	private SQLiteDatabase database;

	public SQLiteDatasource(Context context) {
		helper = new MyDBHelper(context);
		database = helper.getWritableDatabase();
	}
	
	public List<String> getAll(){
		List<String> list = new ArrayList<String>();
	
		String query = "SELECT  * FROM " + MyDBHelper.TABLE_NAME;
		Cursor cursor = database.rawQuery(query, null);
		
		if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex(helper.COL_ToDo)));
            } while (cursor.moveToNext());
        }
		cursor.close();
		return list;
	}
	public List<Long> getAllId(){
		List<Long> list = new ArrayList<>();

		String query = "SELECT  * FROM " + MyDBHelper.TABLE_NAME;
		Cursor cursor = database.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getLong(cursor.getColumnIndex(helper.COL_Id)));
			} while (cursor.moveToNext());
		}
		cursor.close();
		return list;
	}

	public long addToDo(String toDo) {
		ContentValues values = new ContentValues();
		values.putNull(MyDBHelper.COL_Id);
		values.put(MyDBHelper.COL_ToDo, toDo);

		long newRowId = database.insert(MyDBHelper.TABLE_NAME, null, values);

		return newRowId;
	}

    public int updateToDo(long id, String toDo) {
        ContentValues values = new ContentValues();
        values.put(MyDBHelper.COL_ToDo, toDo);
        String whereClause = MyDBHelper.COL_Id + "=" + id;
        int result = database.update(MyDBHelper.TABLE_NAME, values,
				whereClause, null);
        return result;
    }

	public int deleteItem(long id) {
		return database.delete(MyDBHelper.TABLE_NAME, MyDBHelper.COL_Id + "=?", new String[] { String.valueOf(id) });
	}

}
