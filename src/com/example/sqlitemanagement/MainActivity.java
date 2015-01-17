package com.example.sqlitemanagement;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		final Button b1=(Button)findViewById(R.id.button1);
		final Button b2 = (Button)findViewById(R.id.button2);
		final Button b3 = (Button)findViewById(R.id.button3);
		final Button b4 = (Button)findViewById(R.id.button4);
		final Button b5 = (Button)findViewById(R.id.button5);
		
		final EditText et1 = (EditText)findViewById(R.id.editText1);
		final EditText et2 = (EditText)findViewById(R.id.editText2);
		final EditText et3 = (EditText)findViewById(R.id.editText3);
		
		final DBAdapter db = new DBAdapter(MainActivity.this);
		/**-------------------------------------------------------------------------------------------------------------------------**/
												/** Method for getting a Inserting Contact **/
		b1.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				//Calling open() from DBAdapter Class
				db.open();
				//Calling insertContact() from DBAdapter Class
				db.insertContact(et2.getText().toString(),et3.getText().toString());
				//Calling close from DBAdapter Class
				db.close();
				//Showing Toast for completion of Insert 
				Toast.makeText(getBaseContext(), "Inserted", Toast.LENGTH_SHORT).show();
			}
		});
		/**-------------------------------------------------------------------------------------------------------------------------**/
		
		/**-------------------------------------------------------------------------------------------------------------------------**/
													/** Method for getting a All Contact **/
		b2.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				//Calling open() from DBAdapter Class
				db.open();
				//Creating object for Cursor and by using database object db we are calling getAllContacts() which implemented in DBAdapter class
				Cursor c = db.getAllContacts();
				//Making the cursor to be in the first row and first column of the Table "contacts" from database "MYdb"
				if(c.moveToFirst()) 
				{
					do 
					{
						//Calling DisplayContact() which is implemented below
						DisplayContact(c);
					}
					//Making the cursor to move to next column and row untill it is available
					while (c.moveToNext());
				}
				//Calling close from DBAdapter Class
				db.close();
			}
				//Implementation of DisplayContact method
				private void DisplayContact (Cursor c)
				{
					Toast.makeText(getBaseContext(), "id: " + c.getString(0) + "\n" + "Name: " + c.getString(1) + "\n" + "Email "+c.getString(2), Toast.LENGTH_SHORT).show();
				}
		});
		/**-------------------------------------------------------------------------------------------------------------------------**/
		
		/**-------------------------------------------------------------------------------------------------------------------------**/
												/** Method for getting a Single Contact **/
		b3.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				//Calling open() from DBAdapter Class
				db.open();
				//Creating object for Cursor and by using database object db we are calling getContact() which implemented in DBAdapter class which takes Integer argument( i,e row id)
				//The row id is supplied from EditText et1
				Cursor c = db.getContact(Integer.parseInt(et1.getText().toString()));
				//Making the cursor to be in the first row and first column of the Table "contacts" from database "MYdb"
				if(c.moveToFirst()) 
				{
					//Calling DisplayContact() which is implemented below
					DisplayContact(c);
				} 
				else 
				{
					//If the row id supplied from EditText et1 is not found 
					Toast.makeText(getBaseContext(), "No contact found", Toast.LENGTH_SHORT).show();
				}
				//Calling close from DBAdapter Class
				db.close();
			}
			//Implementation of DisplayContact method
			private void DisplayContact(Cursor c)
			{
				Toast.makeText(getBaseContext(), "id: " + c.getString(0) + "\n" + "Name : " + c.getString(1) + "\n" + "Email : " + c.getString(2), Toast.LENGTH_SHORT).show();
			}
		});
		/**-------------------------------------------------------------------------------------------------------------------------**/
		
		/**-------------------------------------------------------------------------------------------------------------------------**/
												/** Method for Updating a  Single Contact **/
		b4.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				//Calling open() from DBAdapter Class
				db.open();
				//Calling updateContact()using db object which takes three parametrs supplied from EditText et1,et2,et3 
				if(db.updateContact(Integer.parseInt(et1.getText().toString()), et2.getText().toString(), et3.getText().toString()))
					{
					//Toast for if update is done successfully
					Toast.makeText(getBaseContext(), "Update Succeessfully", Toast.LENGTH_SHORT).show();
					}
				else
				{
					//Toast for if update is done failed
					Toast.makeText(getBaseContext(), "Updated Fialed", Toast.LENGTH_SHORT).show();
					//Calling close from DBAdapter Class
					db.close();
				}
			}
		});
		/**-------------------------------------------------------------------------------------------------------------------------**/
		
		/**-------------------------------------------------------------------------------------------------------------------------**/
												/** Method for Deleting a Single Contact **/
		b5.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				//Calling open() from DBAdapter Class
				db.open();
				//Using database object db we are calling deleteContact() which implemented in DBAdapter class which takes Integer argument( i,e row id)
				//The row id is supplied from EditText et1
				db.deleteContact(Integer.parseInt(et1.getText().toString()));
				//Calling close from DBAdapter Class
				db.close();
			}
		});
		/**-------------------------------------------------------------------------------------------------------------------------**/
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}