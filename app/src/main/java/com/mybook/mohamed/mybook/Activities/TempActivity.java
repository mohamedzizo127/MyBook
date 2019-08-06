package com.mybook.mohamed.mybook.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mybook.mohamed.mybook.R;

public class TempActivity extends AppCompatActivity {
    //  String sortingBy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
      /*  Bundle extras = getIntent().getExtras();

         if (extras != null) {
               // Toast.makeText(this, sortingBy, Toast.LENGTH_LONG).show();
             sortingBy = extras.getString("sortingBy");

          }
          //code goes here
                 Intent returnIntent = getIntent();
                 returnIntent.putExtra("sortingBy",sortingBy);
                 setResult(RESULT_OK, returnIntent);

     }




    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


    */
    }
}
