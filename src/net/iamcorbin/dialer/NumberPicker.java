package net.iamcorbin.dialer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class NumberPicker extends Activity implements OnItemClickListener {
	private TextView label;
	private ListView numbersListView;
	private ArrayList<String> numbersArrayList;
	private ArrayAdapter<String> aa;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Hide the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.number_picker);
		
		this.label = (TextView)findViewById(R.id.pick_number_name);
		
		this.numbersListView = (ListView)findViewById(R.id.numbersListView);
		this.numbersArrayList = new ArrayList<String>();
		this.aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.numbersArrayList);
		this.numbersListView.setAdapter(this.aa);
		
		Intent intent = getIntent();
		//Grab contact name
		String name = intent.getStringExtra("name");
		String msg = this.label.getText().toString();
		this.label.setText(name + " | " + msg);
		//Grab the numbers for this contact
		String[] numbers = intent.getStringArrayExtra("numbers");
		//add numbers to array list
		for(int x = 0; x < numbers.length; x++) {
			this.numbersArrayList.add(numbers[x]);
		}
		this.aa.notifyDataSetChanged();
		this.numbersListView.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> parent, View item, int pos, long id) {
		Intent number = new Intent();
		number.putExtra("number", this.numbersArrayList.get(pos).toString());
		this.setResult(Activity.RESULT_OK, number);
		finish();
	}
	
	@Override
	public void onDestroy() {
		this.setResult(Activity.RESULT_CANCELED);
		super.onDestroy();
	}
}
