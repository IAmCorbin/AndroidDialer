package net.iamcorbin.dialer;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class UserPreferences extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.userpreferences);
	}
}
