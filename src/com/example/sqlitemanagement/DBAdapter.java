package com.example.sqlitemanagement;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBAdapter 
{
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_EMAIL = "email";
	private static final String TAG = "DBAdapter";
	private static final String DATABASE_NAME = "MyDB";
	private static final String DATABASE_TABLE = "contacts";
	private static final int DATABASE_VERSION = 1;
	//CREATE Statement 
	private static final String DATABASE_CREATE = "create table contacts (_id integer primary key autoincrement, " + "name text not null, email text not null);";
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	public DBAdapter(Context ctx)
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context) 
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		@Override
		//Implemetation onCreate() for database
		//db is the object created for database
		//Any database operation can be done by using db object
		public void onCreate(SQLiteDatabase db) 
		{
			try 
			{
				//execSQL() is the method that can be used execute any database statement
				//DATABASE_CREATE statement is defined above
				db.execSQL(DATABASE_CREATE);
			}
			catch (SQLException e) 
			{
				//Exception in execSQL should be handled 
				e.printStackTrace();
			}
		}
		@Override
		//Implementation onUpgrade() for database
		//onUpgrade() will be called if the database version is changed and it will not be called if we do the change in table(adding/deleting row or column),
		//if any change is done we should also change the version number
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) 
		{
			// TODO Auto-generated method stub
		}
	}
	/**-------------------------------------------------------------------------------------------------------------------------**/
													/**Implementation of open() **/
	public void open() 
	{
		// TODO Auto-generated method stub
		//getWritableDatabase() is a method used to do the changes in database , it can be accessed by DBHelper
		db = DBHelper.getWritableDatabase();
		return;
	}
	/**-------------------------------------------------------------------------------------------------------------------------**/
	/**-------------------------------------------------------------------------------------------------------------------------**/
													/**Implementation of close()**/
	public void close() 
	{
		// TODO Auto-generated method stub
		DBHelper.close();
	}
	/**-------------------------------------------------------------------------------------------------------------------------**/
	/**-------------------------------------------------------------------------------------------------------------------------**/
													/**Implementation of InsertContact() ,Which takes two String arguments **/
	public long insertContact(String name, String email) 
	{
		// TODO Auto-generated method stub
		//ContentValues is a class used to insert values  , insert can be done by using the object of ContentValues
		ContentValues initialValues = new ContentValues();
		//Inserting values 
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_EMAIL, email);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	/**-------------------------------------------------------------------------------------------------------------------------**/
	/**-------------------------------------------------------------------------------------------------------------------------**/
													/**Implementation of getAllContacts()**/
	public Cursor getAllContacts() 
	{
		// TODO Auto-generated method stub
		//query for getting all contact
		return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME, KEY_EMAIL}, null, null, null, null, null);
		//return null;
	}
	/**-------------------------------------------------------------------------------------------------------------------------**/
	/**-------------------------------------------------------------------------------------------------------------------------**/
													/**Implementation of getContact()**/
	public Cursor getContact(long rowId) 
	{
		// TODO Auto-generated method stub
		//query for getting single contact
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME, KEY_EMAIL}, KEY_ROWID + "=" + rowId, null,null,null,null,null);
		if(mCursor != null) 
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	/**-------------------------------------------------------------------------------------------------------------------------**/
	/**-------------------------------------------------------------------------------------------------------------------------**/
													/**Implementation of UpdateContact() ,Which takes two String arguments **/
	public boolean updateContact(long rowId, String name, String email)
	{
		// TODO Auto-generated method stub
		ContentValues args = new ContentValues();
		//ContentValues is a class used to insert values  , insert can be done by using the object of ContentValues
		args.put(KEY_NAME, name);
		args.put(KEY_EMAIL, email);
		return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
	/**-------------------------------------------------------------------------------------------------------------------------**/
	/**-------------------------------------------------------------------------------------------------------------------------**/
													/**Implementation of deleteContact**/
	public boolean deleteContact(long rowId) 
	{
		// TODO Auto-generated method stub
		//query for deleting single contact
		return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}
	/**-------------------------------------------------------------------------------------------------------------------------**/
}