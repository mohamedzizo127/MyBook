package com.mybook.mohamed.mybook.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.mybook.mohamed.mybook.Data.DatabaseHandler;
import com.mybook.mohamed.mybook.Model.BookDataObject;
import com.mybook.mohamed.mybook.R;
import com.mybook.mohamed.mybook.Util.PathUtil;

public class UpdateActivity extends AppCompatActivity {
    private TextView header, name, author, rate;
    private EditText bookNameED,authorED,rateED;
    private RadioButton yesRB,noRB;
    private Button saveBt,selectPath;
    private Bundle bundle;
    private DatabaseHandler db;
    private BookDataObject bookDataObject;
    private int idForUpdate;
    private static final int REQUEST_CODE = 2;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
         db = new DatabaseHandler(this);
        setupView();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            setViewFromIntent();

            }
            saveBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateTheBook();
                }
            });
        selectPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //    or using Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
                startActivityForResult(intent, REQUEST_CODE);
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            fileUri = data.getData();
            String displayName = PathUtil.getFileName(fileUri, UpdateActivity.this);
            bookNameED.setText(displayName);

        }

        }

    private void updateTheBook() {
        bookDataObject = new BookDataObject();
        bookDataObject.setId(idForUpdate);
        if (bookNameED.getText().toString().isEmpty() || authorED.getText().toString().isEmpty()){
            Toast.makeText(this,"Must Enter Book Name And Author ",Toast.LENGTH_SHORT).show();
            return;
        }else{
            bookDataObject.setName(bookNameED.getText().toString());
            bookDataObject.setAuthor(authorED.getText().toString());
            bookDataObject.setFilePath("");
            if (rateED.getText().toString().isEmpty()){
                bookDataObject.setRate(Integer.parseInt(String.valueOf(0)));
            }else{
                bookDataObject.setRate(Integer.parseInt(rateED.getText().toString()));

            }
             if (yesRB.isChecked()){bookDataObject.setIsDone(1);}
            else bookDataObject.setIsDone(0);
        }
        bookDataObject.setFilePath(fileUri.toString());
        db.updateBook(bookDataObject);
        // Log.d("::::::",fileUri.toString());
        // Log.d("::::::",fileUri.getPath());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (fileUri.toString().isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "The Book Was Updated Without File Path", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(UpdateActivity.this, "The Book Was Updated With File Path", Toast.LENGTH_LONG).show();

                }
                //retturn to mainActivity
                Intent intent = new Intent(UpdateActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        },1000);//equal 1 sec


    }


    private void setViewFromIntent() {
        //set views from Intent
        bookNameED.setText(bundle.getString("name"));
        authorED.setText(bundle.getString("author"));
        rateED.setText(bundle.getInt("rate")+ "");
        if (bundle.getInt("done") == 1){ //1=done or yes that book was readed
            yesRB.setChecked(true);
        }else noRB.setChecked(true);
        //get id for update
        idForUpdate = bundle.getInt("id");
        fileUri = Uri.parse(bundle.getString("filePath"));
          Log.d("::::::",fileUri.toString());
           Log.d("::::::",fileUri.getPath());

    }

    private void setupView() {
         bookNameED = (EditText) findViewById(R.id.updateEdNameID);
        authorED = (EditText)findViewById(R.id.updateEdAuthorID);
        rateED = (EditText) findViewById(R.id.updateEdRateID);
        yesRB = (RadioButton) findViewById(R.id.updateRBYesID);
        noRB = (RadioButton)findViewById(R.id.updateRBNoID);
        saveBt = (Button) findViewById(R.id.updateBtnSaveID);
        selectPath = (Button) findViewById(R.id.updateBtnSelectID);
        }
    @Override
    protected void onPause() {
        super.onPause();
     }
}
