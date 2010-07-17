package net.iamcorbin.dialer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialPadView extends LinearLayout implements OnClickListener {
	ImageButton buttons[] = new ImageButton[12];
	Button clearNumber;
	TextView display;
	
	public DialPadView(Context context, AttributeSet  attrs) {
		super(context, attrs);
		
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		li = (LayoutInflater)getContext().getSystemService(infService);
		li.inflate(R.layout.dialpad, this, true);
		
		this.display = (TextView)findViewById(R.id.displayNumber);
		
		this.buttons[0] = (ImageButton)findViewById(R.id.button0);
		this.buttons[1] = (ImageButton)findViewById(R.id.button1);
		this.buttons[2] = (ImageButton)findViewById(R.id.button2);
		this.buttons[3] = (ImageButton)findViewById(R.id.button3);
		this.buttons[4] = (ImageButton)findViewById(R.id.button4);
		this.buttons[5] = (ImageButton)findViewById(R.id.button5);
		this.buttons[6] = (ImageButton)findViewById(R.id.button6);
		this.buttons[7] = (ImageButton)findViewById(R.id.button7);
		this.buttons[8] = (ImageButton)findViewById(R.id.button8);
		this.buttons[9] = (ImageButton)findViewById(R.id.button9);
		this.buttons[10] = (ImageButton)findViewById(R.id.button_star);
		this.buttons[11] = (ImageButton)findViewById(R.id.button_pound);
		
		for(ImageButton button : this.buttons) {
			button.setOnClickListener(this);  
		}
		
		this.clearNumber = (Button)findViewById(R.id.buttonClearNumber);
		this.clearNumber.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if(display.length() > 0) {
					//trim the last character off
					String num = display.getText().toString();
					display.setText(num.substring(0, num.length()-1));
				}
			}
		});
		this.clearNumber.setOnLongClickListener(new Button.OnLongClickListener() {
			public boolean onLongClick(View v) {
				display.setText("");
				return true;
			}
		});
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
			case R.id.button_star:
				this.display.append("*");
				break;
			case R.id.button_pound:
				this.display.append("#");
				break;
		}
	}
}
