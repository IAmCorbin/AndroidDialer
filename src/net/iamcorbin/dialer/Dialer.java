package net.iamcorbin.dialer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Dialer extends Activity implements OnClickListener {
	private static final String TAG = "Dialer";
	private Button buttonDial;
	private TextView number;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
     
        this.buttonDial = (Button)findViewById(R.id.buttonDial);
        this.number = (TextView)findViewById(R.id.displayNumber);
                
        this.buttonDial.setOnClickListener(this);
    }
    
    public void onClick(View v) {
    	switch(v.getId()) {
    		case R.id.buttonDial:
    			String num = this.number.getText().toString();
				Pattern pattern = Pattern.compile("[^0-9*#]");
		        Matcher matcher = pattern.matcher(num);
				if(!matcher.find()) {
    				this.number.setText("Dialing number - " + num + " . . . ");
        			Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + num));
        			startActivityForResult(intent,1);
				} else
					this.number.setText("Invalid Number");	
    			break;
    	}
    }
    
    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data) {
		super.onActivityResult(reqCode, resCode, data);
		this.number.setText("");
	}
}