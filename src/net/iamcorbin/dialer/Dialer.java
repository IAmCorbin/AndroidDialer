package net.iamcorbin.dialer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class Dialer extends Activity implements OnClickListener {
	private static final int PICK_CONTACT = 1;
	private static final int DIAL_NUMBER = 2;
	private static final String TAG = "Dialer";
	
	//Preferences
	private static final String PREF_NUMBER = "PREF_NUMBER";
	
	private ImageButton buttonDial;
	private TextView number;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.main);
        
        this.buttonDial = (ImageButton)findViewById(R.id.buttonDial);
        this.number = (TextView)findViewById(R.id.displayNumber);
                
        this.buttonDial.setOnClickListener(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	switch(item.getItemId()) {
    		case R.id.pick_contact:
    			Intent intent = new Intent(Dialer.this, ContactPicker.class);
    			startActivityForResult(intent, PICK_CONTACT);
    			return true;
    	}
    	
    	return true;
    }
    
    public void onClick(View v) {
    	switch(v.getId()) {
    		case R.id.buttonDial:
    			//Check for a valid number
    			String num = this.number.getText().toString();
				Pattern pattern = Pattern.compile("[^0-9*#.() -]");
		        Matcher matcher = pattern.matcher(num);
				if(!matcher.find()) {
    				//Valid number - DIAL
					this.number.setText("Dialing number - " + num + " . . . ");
        			Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + num));
        			startActivityForResult(intent, DIAL_NUMBER);
				} else
					this.number.setText("Invalid Number");	
    			break;
    	}
    }
    
    @Override
    public void onActivityResult(int reqCode, int resCode, Intent _data) {
    	super.onActivityResult(reqCode, resCode, _data);
		switch(reqCode) {
			case PICK_CONTACT:
				if(resCode == Activity.RESULT_OK)
					this.number.setText(_data.getStringExtra("number"));
		    	break;
			case DIAL_NUMBER:
				this.number.setText("");
				break;
		}
	}
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putString(PREF_NUMBER, this.number.getText().toString());
    	
    	super.onSaveInstanceState(savedInstanceState);    	
    }
    
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
    	if(savedInstanceState != null && savedInstanceState.containsKey(PREF_NUMBER))
    		this.number.setText(savedInstanceState.getString(PREF_NUMBER));
    }
}