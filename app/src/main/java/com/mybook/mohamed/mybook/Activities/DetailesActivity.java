package com.mybook.mohamed.mybook.Activities;

 import android.content.Intent;
 import android.net.Uri;
 import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.mybook.mohamed.mybook.R;
import com.mybook.mohamed.mybook.Util.PathUtil;

 import java.net.URISyntaxException;

public class DetailesActivity extends AppCompatActivity  {
    private TextView name,author,rate,date;
    private CheckBox done;
    private Button back,open;
    private static int REQUEST_CODE = 100;
    //may be fixed the date and added it
     private int bookId;
   private Bundle bundle;
   private String filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailes);
        name = (TextView) findViewById(R.id.detailsEtnameID);
        author = (TextView) findViewById(R.id.detailesEdAuthorID);
        rate = (TextView)findViewById(R.id.detailesEtRateID);
        date = (TextView) findViewById(R.id.detailesTvDateID);
        done = (CheckBox)findViewById(R.id.detailesCBID);
        back = (Button) findViewById(R.id.detailesBtnBack);
        open = (Button) findViewById(R.id.detailesBtnOpenID);
         bundle = getIntent().getExtras();
        if (bundle != null){
            filePath = bundle.getString("file");
            Toast.makeText(this,filePath + ""+bundle.getString("name"),Toast.LENGTH_LONG).show();
            bookId = bundle.getInt("id");
            name.setText(bundle.getString("name"));
            author.setText(bundle.getString("author"));
            date.setText(bundle.getString("date"));
            rate.setText(String.valueOf(bundle.getInt("rate")));
            int checkValue =  bundle.getInt("done");
            if (checkValue == 1) //1= done
            {done.setChecked(true);}
            else done.setChecked(false);
        }else Toast.makeText(this,"No Book to Show ",Toast.LENGTH_SHORT).show();

back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(DetailesActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }
});


open.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        try {
            openPDF();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.d("==============", e.getMessage()+"");


        }

    }
});
    }

    private void openPDF() throws URISyntaxException {

        String filePathString = PathUtil.getPath(DetailesActivity.this,Uri.parse(filePath));

/*
            try {
                Intent intent = new Intent();
                Log.d("+++++++++++++++++",filePath);
                Log.d("+++++++++++++++++",filePathString);
                Log.d("++++++++++++++++++",Environment.getExternalStorageDirectory().getAbsolutePath());

                intent.setPackage("com.adobe.reader");
                String fillll = "/storage/emulated/0/Download/1358C21.pdf";
                intent.setDataAndType(Uri.parse(fillll), "application/pdf");
                startActivity(intent);

            } catch (ActivityNotFoundException activityNotFoundException) {
                activityNotFoundException.printStackTrace();

                throw activityNotFoundException;
            } catch (Exception otherException) {
                otherException.printStackTrace();

                throw otherException;
            }
       /* File file = new File(filePathString+"C21.pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file=new File(filePath);
            Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
           Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(filePath), "application/pdf");
            intent = Intent.createChooser(intent, "Open File");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
      /*  File file = new File(filePathString);
        Log.d("+++++++++++++++++",filePath);
        Log.d("+++++++++++++++++",filePathString);
        Log.d("++++++++++++++++++",Environment.getExternalStorageDirectory().getAbsolutePath());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

       /* Intent objIntent = new Intent(Intent.ACTION_VIEW);
       // objIntent.setDataAndType(Uri.parse(filePath), "application/pdf");
        objIntent.setDataAndType(Uri.parse("file:///" + filePathString + "/" +name.getText().toString()), "application/pdf");
        objIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(objIntent);//Starting the pdf viewer





/*
        Log.d("==============", filePathString+"");

        //Log.d("==============",Uri.parse(filePath).getPath()+"");
         Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.parse(filePath), "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent i = Intent.createChooser(target, "Open PDF");
        startActivity(i);





















       /* Uri temp = Uri.parse(filePath);
        String path = temp.getPath(); // "/mnt/sdcard/FileName.mp3"
        File file = new File(new URI(path));
          //Log.d("::::::", temp.getEncodedPath()+"");
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent i = Intent.createChooser(target, "Open PDF");
       // Log.d("::::::",filePath+"");
        startActivity(i);

        /*
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(filePath));
        startActivity(browserIntent);
           Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(filePath), "application/pdf");
            intent = Intent.createChooser(intent, "Open File");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
    }





    @Override
    protected void onPause() {
        super.onPause();

    }
}
