package net.iamcorbin.dialer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialPadView extends LinearLayout implements OnClickListener {
	Button buttons[] = new Button[12];
	TextView display;
	
	public DialPadView(Context context, AttributeSet  attrs) {
		super(context, attrs);
		
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		li = (LayoutInflater)getContext().getSystemService(infService);
		li.inflate(R.layout.dialpad, this, true);
		
		this.display = (TextView)findViewById(R.id.displayNumber);
		
		this.buttons[0] = (Button)findViewById(R.id.button0);
		this.buttons[1] = (Button)findViewById(R.id.button1);
		this.buttons[2] = (Button)findViewById(R.id.button2);
		this.buttons[3] = (Button)findViewById(R.id.button3);
		this.buttons[4] = (Button)findViewById(R.id.button4);
		this.buttons[5] = (Button)findViewById(R.id.button5);
		this.buttons[6] = (Button)findViewById(R.id.button6);
		this.buttons[7] = (Button)findViewById(R.id.button7);
		this.buttons[8] = (Button)findViewById(R.id.button8);
		this.buttons[9] = (Button)findViewById(R.id.button9);
		this.buttons[10] = (Button)findViewById(R.id.buttonStar);
		this.buttons[11] = (Button)findViewById(R.id.buttonPound);
		
		for(Button button : this.buttons) {
			button.setOnClickListener(this);  
		}
	}

	public void onClick(View v) {
		//Clear display if a message is displayed before adding a number
		Pattern pattern = Pattern.compile("[0-9*#]");
        Matcher matcher = pattern.matcher(this.display.getText().toString());
        if(!matcher.find()) {
        	this.display.setText("");
        }
		switch(v.getId()) {
			case R.id.button0:
				this.display.append("0");
				break;
			case R.id.button1:
				this.display.append("1");
				break;
			case R.id.button2:
				this.display.append("2");
				break;
			case R.id.button3:
				this.display.append("3");
				break;
			case R.id.button4:
				this.display.append("4");
				break;
			case R.id.button5:
				this.display.append("5");
				break;
			case R.id.button6:
				this.display.append("6");
				break;
			case R.id.button7:
				this.display.append("7");
				break;
			case R.id.button8:
				this.display.append("8");
				break;
			case R.id.button9:
				this.display.append("9");
				break;
			case R.id.buttonStar:
				this.display.append("*");
				break;
			case R.id.buttonPound:
				this.display.append("#");
				break;
		}
	}
}
