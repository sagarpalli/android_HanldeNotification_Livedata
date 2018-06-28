package net.simplifiedcoding.recyclerviewoptionsmenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.File;
import java.util.Arrays;

/**
 * Created by Sekhar on 6/15/18.
 */

public class LauncherActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_activity);
        Spinner categorySpinner=(Spinner)findViewById(R.id.spinner_category);
        categorySpinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,getResources().getStringArray(R.array.categories)));
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
       @Override
       public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
           String item = (String) adapterView.getAdapter().getItem(i);
           SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
           sp.edit().putString("CATEGORY",item).commit();
       }

       @Override
       public void onNothingSelected(AdapterView<?> adapterView) {

       }
   });
        Button buttonProceed=(Button)findViewById(R.id.button_proceed);
        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LauncherActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        readInternalStorage();
    }


    private void readInternalStorage()
    {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File[] list = externalStorageDirectory.listFiles();
        if(list==null)
        {
            Log.d("FILEPATH", "readInternalStorage: list os null");
            return;
        }
        for (File path :
                list) {
        //    Log.d("FILEPATH",path.getAbsolutePath());

        }

        File whatsapp=new File(Environment.getExternalStorageDirectory()+ "/WhatsApp/Backups");
        File[] files = whatsapp.listFiles();

        for (File path :
                files) {
            if(path.canRead()){
                Log.d("FILEPATH", "readInternalStorage: Can Read.");
            }
            Log.d("FILEPATH",path.getAbsolutePath());

        }
    }
}
