package net.iamcorbin.dialer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ContactPicker extends Activity implements OnItemClickListener {
	private final static String TAG = "ContactPicker";
	private Cursor c;
	private ListView lv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Hide the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		this.setContentView(R.layout.contact_picker);
		
		this.lv = (ListView)findViewById(R.id.contactsListView);
		
		String[] cols = new String[] {Contacts._ID, Contacts.DISPLAY_NAME, Contacts.TIMES_CONTACTED };
		//Only grab previously contacted contacts
		String where = Contacts.HAS_PHONE_NUMBER + " = 1"; 
		String[] whereparam = new String[] { "0" };//TODO: Add Prefereneces Here
		this.c = getContentResolver().query(Contacts.CONTENT_URI, cols, where, null, Contacts.DISPLAY_NAME);
		startManagingCursor(this.c);
		
		String[] from = new String[] { Contacts.DISPLAY_NAME };
		int[] to = new int[] { R.id.contactNameTextView };
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
												R.layout.contact,
												this.c,
												from, to);
		this.lv.setAdapter(adapter);
		this.lv.setOnItemClickListener(this);
		
	}

	public void onItemClick(AdapterView<?> parent, View item, int pos, long id) {
		this.c.moveToPosition(pos);
		
		Cursor numberCursor = getContentResolver().query(Phone.CONTENT_URI, 
													new String[] { Phone.NUMBER },
													Phone.CONTACT_ID + " = ?",
													new String[] { this.c.getString(this.c.getColumnIndexOrThrow(Contacts._ID)) },
													null);
		numberCursor.moveToFirst();
		if(numberCursor.getCount() > 1) {
			//Handle Multiple Numbers
			Intent pickNumber = new Intent(ContactPicker.this, NumberPicker.class);
			//Get numbers
			int x = 0;
			String[] numbers = new String[numberCursor.getCount()];
			do {
				numbers[x] = numberCursor.getString(0);
				x++;
			} while(numberCursor.moveToNext());
			//put data (contact name and numbers)
			pickNumber.putExtra("name", this.c.getString(this.c.getColumnIndexOrThrow(Contacts.DISPLAY_NAME)));
			pickNumber.putExtra("numbers",numbers);
			startActivityForResult(pickNumber, 1);
		} else {
			//Single Number
			Intent result = new Intent();
			result.putExtra("number", numberCursor.getString(0));
			setResult(Activity.RESULT_OK, result);
			finish();
		}
	}
	@Override
	public void onBackPressed() {
		setResult(Activity.RESULT_CANCELED);
		finish();
	}
	
	@Override
    public void onActivityResult(int reqCode, int resCode, Intent _data) {
		if(resCode == Activity.RESULT_OK) {
		
			Intent result = new Intent();
			result.putExtra("number", _data.getStringExtra("number"));
		
			setResult(Activity.RESULT_OK, result);
			finish();
		}
	}
}
