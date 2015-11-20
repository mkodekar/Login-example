package com.emagic.loginexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class LoginDataBaseAdapter {
	static final String DATABASE_NAME = "login.db";
	static final int DATABASE_VERSION = 1;
	public static final int NAME_COLUMN = 1;
	public static final String DATABASE_TABLE = "LOGIN";
	// TODO: Create public field for each column in your table.
	// SQL Statement to create a new database.
	static final String DATABASE_CREATE = "create table " + DATABASE_TABLE
			+ "( " + "ID" + " integer primary key autoincrement,"
			+ "NAME text,USERNAME  text," + "PASSWORD text," + "EMAIL text,"
			+ "MOBILE text," + "ADDRESS text); ";
	// Variable to hold the database instance
	public SQLiteDatabase db;
	// Context of the application using the database.
	private final Context context;
	// Database open/upgrade helper
	private DataBaseHelper dbHelper;

	public LoginDataBaseAdapter(Context _context) {
		context = _context;
		dbHelper = new DataBaseHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public LoginDataBaseAdapter open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		db.close();
	}

	public SQLiteDatabase getDatabaseInstance() {
		return db;
	}

	public void insertEntry(String name, String userName, String password,
			String email, String mobileNumber, String address) {
		ContentValues newValues = new ContentValues();
		Cursor c = db.rawQuery("SELECT * FROM " + DATABASE_TABLE
				+ " WHERE userName=?", new String[] { userName });
		System.out.println("add:" + c.getCount());
		if (c.getCount() > 0) {

			Toast.makeText(context, "Username Already Exist",
					Toast.LENGTH_SHORT).show();
		} else {

			newValues.put("NAME", name);
			newValues.put("USERNAME", userName);
			newValues.put("PASSWORD", password);
			newValues.put("EMAIL", email);
			newValues.put("MOBILE", mobileNumber);
			newValues.put("ADDRESS", address);

			// Insert the row into your table
			db.insert(DATABASE_TABLE, null, newValues);
			// /Toast.makeText(context, "Reminder Is Successfully Saved",
			// Toast.LENGTH_LONG).show();
		}
	}

	public int deleteEntry(String UserName) {
		// String id=String.valueOf(ID);
		String where = "USERNAME=?";
		int numberOFEntriesDeleted = db.delete("LOGIN", where,
				new String[] { UserName });
		// Toast.makeText(context,
		// "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted,
		// Toast.LENGTH_LONG).show();
		return numberOFEntriesDeleted;
	}

	public String getSinlgeEntry(String userName) {
		Cursor cursor = db.query("LOGIN", null, " USERNAME=?",
				new String[] { userName }, null, null, null);
		if (cursor.getCount() < 1) // UserName Not Exist
		{
			cursor.close();
			return "NOT EXIST";
		}
		cursor.moveToFirst();
		String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
		cursor.close();
		return password;
	}

	public void updateEntry(String name, String userName, String password,
			String email, String mobileNumber, String address) {
		// Define the updated row content.
		ContentValues updatedValues = new ContentValues();
		// Assign values for each row.
		updatedValues.put("NAME", name);
		updatedValues.put("USERNAME", userName);
		updatedValues.put("PASSWORD", password);
		updatedValues.put("EMAIL", email);
		updatedValues.put("MOBILE", mobileNumber);
		updatedValues.put("ADDRESS", address);

		String where = "USERNAME = ?";
		db.update("LOGIN", updatedValues, where, new String[] { userName });
	}
}
