package com.mybook.mohamed.mybook.Activities;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
 import android.widget.Toast;
import com.mybook.mohamed.mybook.Data.DatabaseHandler;
import com.mybook.mohamed.mybook.Model.BookDataObject;
import com.mybook.mohamed.mybook.R;
import com.mybook.mohamed.mybook.UI.BookAdaptor;
import com.mybook.mohamed.mybook.Util.Constants;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdaptor bookAdapter;
    private List<BookDataObject> bookListFromDB;
    private List<BookDataObject> listItemToShowInRecycleView;
    private DatabaseHandler db;
    private String sortingBy;
    private FloatingActionButton fabSearch;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //////////////////////////////////////////////////////////////////////////////
        sortingBy = Constants.KEY_AUTHOR;
        getAllAndSetupAdaptor(sortingBy,null);
        //////////////////////////////////
        setUPFab();
        //////////////////////////////////////////////////////////////////////////////////////////////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }




    private void getAllAndSetupAdaptor(String sortingBy, List<BookDataObject> list) {
        recyclerView = (RecyclerView) findViewById(R.id.MainRecyclerViewID);
        //to sure allitem is fixed size are true
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookListFromDB = new ArrayList<>();
        listItemToShowInRecycleView = new ArrayList<>();
        bookListFromDB.clear();
        listItemToShowInRecycleView.clear();
        if (list == null) {
            db = new DatabaseHandler(this);
            bookListFromDB = db.getAllBooks(sortingBy);
            for (BookDataObject bookDataObject : bookListFromDB) {
                BookDataObject book = new BookDataObject();
                book.setName(bookDataObject.getName());
                book.setAuthor(bookDataObject.getAuthor());
                book.setRate(bookDataObject.getRate());
                book.setIsDone(bookDataObject.getIsDone());//1=is done and 0=is'nt done
                book.setDate(bookDataObject.getDate());
                book.setId(bookDataObject.getId());
                book.setFilePath(bookDataObject.getFilePath());
                listItemToShowInRecycleView.add(book);
            }
        }else{
            listItemToShowInRecycleView = list;

        }
        bookAdapter = new BookAdaptor(this, listItemToShowInRecycleView);
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();
        db.close();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.menueNameID:
                sortingBy = Constants.KEY_BOOK_NAME;
                getAllAndSetupAdaptor(sortingBy,null);

                 break;

            case R.id.menueAuthorID:
                sortingBy = Constants.KEY_AUTHOR;
                getAllAndSetupAdaptor(sortingBy,null);


                break;

            case R.id.menueRateID:
                sortingBy = Constants.KEY_RATE;
                getAllAndSetupAdaptor(sortingBy,null);

                break;

            case R.id.menueDateID:
                sortingBy = Constants.KEY_BOOK_NAME;
                getAllAndSetupAdaptor(sortingBy,null);

                break;

        }
         //noinspection SimplifiableIfStatement
        if (id == R.id.menueDateID||id==R.id.menueRateID||id==R.id.menueAuthorID||id==R.id.menueNameID) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    private void setUPFab() {
        fabAdd = (FloatingActionButton) findViewById(R.id.mainFabAddID);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);

            }
        });
        fabSearch = (FloatingActionButton) findViewById(R.id.mainFabSerchID);
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.search_dialoge,null);
                final EditText nameEdSearch = (EditText) v.findViewById(R.id.searchEdNameID);
                final EditText authorEdSearch = (EditText) v.findViewById(R.id.searchEdAuthorID);
                Button search = (Button)v.findViewById(R.id.searchBtnSearchID);
                alertDialogBuilder.setView(v);
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                alertDialog.setCancelable(false);
                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nameSearch = nameEdSearch.getText().toString();
                        String authorSearch = authorEdSearch.getText().toString();
                        if (nameEdSearch.getText().toString().isEmpty() && authorEdSearch.getText().toString().isEmpty()){
                            alertDialog.dismiss();

                        }else if (!nameSearch.isEmpty()){
                            //get book data opject by name
                            try{
                                db = new DatabaseHandler(MainActivity.this);
                                List<BookDataObject> nameListFromDB = new ArrayList<>();
                                List<BookDataObject> nameToShowInRecycleView = new ArrayList<>();
                                nameListFromDB.clear();
                                nameToShowInRecycleView.clear();
                                nameListFromDB =  db.getBookByName(nameSearch);
                                for (BookDataObject bookDataObject : nameListFromDB) {
                                    BookDataObject book = new BookDataObject();
                                    book.setName(bookDataObject.getName());
                                    book.setAuthor(bookDataObject.getAuthor());
                                    book.setRate(bookDataObject.getRate());
                                    book.setIsDone(bookDataObject.getIsDone());//1=is done and 0=is'nt done
                                    book.setDate(bookDataObject.getDate());
                                    book.setId(bookDataObject.getId());
                                    book.setFilePath(bookDataObject.getFilePath());
                                    nameToShowInRecycleView.add(book);
                                }
                                getAllAndSetupAdaptor(sortingBy, nameToShowInRecycleView);
                                db.close();
                                 alertDialog.dismiss();

                            }catch (Exception e){
                                Toast.makeText(MainActivity.this,"No such Book Name",Toast.LENGTH_LONG).show();
                            }


                        }else {
                            try{
                                db = new DatabaseHandler(MainActivity.this);
                                List<BookDataObject> autherListFromDB = new ArrayList<>();
                                List<BookDataObject> autherToShowInRecycleView = new ArrayList<>();
                                autherListFromDB.clear();
                                autherToShowInRecycleView.clear();
                                autherListFromDB =  db.getBookByAuthor(authorSearch);
                                for (BookDataObject bookDataObject : autherListFromDB) {
                                    BookDataObject book = new BookDataObject();
                                    book.setName(bookDataObject.getName());
                                    book.setAuthor(bookDataObject.getAuthor());
                                    book.setRate(bookDataObject.getRate());
                                    book.setIsDone(bookDataObject.getIsDone());//1=is done and 0=is'nt done
                                    book.setDate(bookDataObject.getDate());
                                    book.setId(bookDataObject.getId());
                                    book.setFilePath(bookDataObject.getFilePath());
                                    autherToShowInRecycleView.add(book);
                                }
                                getAllAndSetupAdaptor(sortingBy, autherToShowInRecycleView);
                                db.close();
                                alertDialog.dismiss();
                            }catch (Exception e){
                                Toast.makeText(MainActivity.this,"No such Author Name",Toast.LENGTH_LONG).show();

                            }


                        }
                    }
                });

            }});

    }
    }









