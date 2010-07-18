package net.iamcorbin.dialer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Dialer extends Activity implements OnClickListener {
	//Intent IDs
	private static final int PICK_CONTACT = 1;
	private static final int DIAL_NUMBER = 2;
	private static final int USER_PREFS = 3;

	//Debug tag
	private static final String TAG = "Dialer";
	
	//Preferences
	private static final String PREF_NUMBER = "PREF_NUMBER";
	private static final String PREF_BUTTON_TYPE = "PREF_BUTTON_TYPE";
	
	//Components
	private Button buttonClear;
	private Button buttonDial;
	private TextView number;
	LinearLayout buttons[] = new LinearLayout[12];
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.main);
        
        this.buttons[0] = (LinearLayout)findViewById(R.id.button0);
		this.buttons[1] = (LinearLayout)findViewById(R.id.button1);
		this.buttons[2] = (LinearLayout)findViewById(R.id.button2);
		this.buttons[3] = (LinearLayout)findViewById(R.id.button3);
		this.buttons[4] = (LinearLayout)findViewById(R.id.button4);
		this.buttons[5] = (LinearLayout)findViewById(R.id.button5);
		this.buttons[6] = (LinearLayout)findViewById(R.id.button6);
		this.buttons[7] = (LinearLayout)findViewById(R.id.button7);
		this.buttons[8] = (LinearLayout)findViewById(R.id.button8);
		this.buttons[9] = (LinearLayout)findViewById(R.id.button9);
		this.buttons[10] = (LinearLayout)findViewById(R.id.buttonStar);
		this.buttons[11] = (LinearLayout)findViewById(R.id.buttonPound);
        this.buttonClear = (Button)findViewById(R.id.buttonClearNumber);
        this.buttonDial = (Button)findViewById(R.id.buttonDial);
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
    			Intent intent_contact = new Intent(Dialer.this, ContactPicker.class);
    			startActivityForResult(intent_contact, PICK_CONTACT);
    			return true;
    		case R.id.user_prefs:
    			Intent intent_prefs = new Intent(Dialer.this, UserPreferences.class);
    			startActivityForResult(intent_prefs, USER_PREFS);
    			return true;
    	}
    	
    	return false;
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
					Toast.makeText(this,"Dialing number - " + num + " . . . ", Toast.LENGTH_SHORT).show();
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
    	
    	Context context = getApplicationContext();
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);    	
    	String button_type = prefs.getString(PREF_BUTTON_TYPE, "button_green");
    	if(button_type.equals("button_green")) {
    			for(LinearLayout button : this.buttons) {
    				button.setBackgroundResource(R.drawable.button_green);  
    			}
    			this.buttonDial.setBackgroundResource(R.drawable.button_green);
    			this.buttonClear.setBackgroundResource(R.drawable.button_green);
    	} else if(button_type.equals("button_smoke")) {
    		for(LinearLayout button : this.buttons) {
				button.setBackgroundResource(R.drawable.button_smoke);  
    		}
			this.buttonDial.setBackgroundResource(R.drawable.button_smoke);
			this.buttonClear.setBackgroundResource(R.drawable.button_smoke);
    	}
    }
}