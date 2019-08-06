package com.mybook.mohamed.mybook.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aditya.filebrowser.Constants;
import com.aditya.filebrowser.FileBrowserWithCustomHandler;
import com.mybook.mohamed.mybook.Data.DatabaseHandler;
import com.mybook.mohamed.mybook.Model.BookDataObject;
import com.mybook.mohamed.mybook.R;
import com.mybook.mohamed.mybook.Util.PathUtil;

public class AddActivity extends AppCompatActivity {
    private EditText bookNameED,authorED,rateED;
    private RadioButton yesRB,noRB;
    private Button saveBt,pathBt;
    private DatabaseHandler db;
    private BookDataObject bookDataObject;
    private Uri fileUri;
    private static final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setupView();
        saveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewBook();
            }
        });
        pathBt.setOnClickListener(new View.OnClickListener() {
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
            String displayName = PathUtil.getFileName(fileUri, AddActivity.this);
            bookNameED.setText(displayName);

            //  Log.d("::::::",fileUri.toString());
         //   Log.d("::::::",fileUri.getPath());

        }
    }


    private void setupView() {
        bookNameED = (EditText) findViewById(R.id.addEdNameID);
        authorED = (EditText)findViewById(R.id.addEdAutherID);
        rateED = (EditText) findViewById(R.id.addEtRateID);
        yesRB = (RadioButton) findViewById(R.id.addRBYesID);
        noRB = (RadioButton)findViewById(R.id.addRBNoID);
        saveBt = (Button) findViewById(R.id.addBtSaveID);
        pathBt = (Button) findViewById(R.id.addBtSelectID);
        db = new DatabaseHandler(this);
    }
    private void AddNewBook() {
        bookDataObject = new BookDataObject();
         if (bookNameED.getText().toString().isEmpty() || authorED.getText().toString().isEmpty()){
            Toast.makeText(this,"Must Enter Book Name And Author ",Toast.LENGTH_SHORT).show();
            return;
        }else{
             bookDataObject.setName(bookNameED.getText().toString());
            bookDataObject.setAuthor(authorED.getText().toString());

             if (rateED.getText().toString().isEmpty()){
                bookDataObject.setRate(Integer.parseInt(String.valueOf(0)));//not exist any rate
            }else{
                bookDataObject.setRate(Integer.parseInt(rateED.getText().toString()));

            }
            if (yesRB.isChecked()){bookDataObject.setIsDone(1);}//book is done reading
            else bookDataObject.setIsDone(0);//book dosn't readed
        }
        if (fileUri != null){
            bookDataObject.setFilePath(fileUri.toString());
            Toast.makeText(AddActivity.this, "The Book Was Added With File Path", Toast.LENGTH_LONG).show();
          //  Log.d("::::::",fileUri.getPath()+"");
        }
        else{
            bookDataObject.setFilePath(null);//empty no file path
            Toast.makeText(AddActivity.this, "The Book Was Added Without File Path", Toast.LENGTH_LONG).show();
          //  Log.d("::::::",fileUri.toString()+"NOOOOOOOOOOOOO");
        }
        db.addBook(bookDataObject);
        db.close();
        popupDialog();

    }

    private void popupDialog() {
        //create an alert dialog
       AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.comfermation_dialoge,null);
        TextView alertHeader = (TextView) v.findViewById(R.id.confirmationTv);
        alertHeader.setText(R.string.add_comfirmatin);
        Button no = (Button) v.findViewById(R.id.confirmationBtnNoID);
        Button yes = (Button)v.findViewById(R.id.confirmationBtnYesID);
        alertDialogBuilder.setView(v);
         final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                //retturn to mainActivity
                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete items from add Activity
                bookNameED.setText("");
                authorED.setText("");
                rateED.setText("");
                yesRB.setChecked(false);
                noRB.setChecked(true);
                 alertDialog.dismiss();
            }
        });
    }

}
