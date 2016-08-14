package com.santos.diego.tenisrank;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class PreferencesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        getFragmentManager().beginTransaction().replace(android.R.id.content,new PreferencesFragment()).commit();

    }
}
