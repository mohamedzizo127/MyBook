package com.mybook.mohamed.mybook.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.mybook.mohamed.mybook.Model.BookDataObject;
import com.mybook.mohamed.mybook.Util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private Context context;

    public DatabaseHandler( Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOK_TABLE = "CREATE TABLE "+ Constants.TABLE_NAME +
            "(" + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
            + Constants.KEY_BOOK_NAME +" TEXT,"
                + Constants.KEY_AUTHOR + " TEXT," + Constants.KEY_RATE + " INTEGER,"
                + Constants.KEY_DONE + " INTEGER," +  Constants.KEY_DATE +  " LONG ," + Constants.KEY_PATH + " TEXT " +
                " );";
          db.execSQL(CREATE_BOOK_TABLE);
}

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME );
        onCreate(sqLiteDatabase);
    }
 /*
 CRUD OPERATION
  */
    //ADD GROCERY


    public void addBook(BookDataObject bookDataObject){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(Constants.KEY_ID,grocery.getId());
        values.put(Constants.KEY_BOOK_NAME,bookDataObject.getName());
        values.put(Constants.KEY_AUTHOR,bookDataObject.getAuthor());
        values.put(Constants.KEY_RATE,bookDataObject.getRate());
        values.put(Constants.KEY_DONE,bookDataObject.getIsDone());
        values.put(Constants.KEY_DATE, System.currentTimeMillis());
        values.put(Constants.KEY_PATH,bookDataObject.getFilePath());
        db.insert(Constants.TABLE_NAME,null,values);
        db.close();

     }

    //get Book item By Id
    public BookDataObject getBookByID(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[] {Constants.KEY_ID,
                        Constants.KEY_BOOK_NAME,
                        Constants.KEY_AUTHOR, Constants.KEY_RATE, Constants.KEY_DONE, Constants.KEY_DATE, Constants.KEY_PATH}
                 ,Constants.KEY_ID + " =?",
                new String[] {String.valueOf(id)},
                null,
                null,
                null,
                null);

        if (cursor != null) cursor.moveToFirst();
        BookDataObject bookDataObject = new BookDataObject();
        //any thing get from cursor is String
        bookDataObject.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
        bookDataObject.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_BOOK_NAME)));
        bookDataObject.setAuthor(cursor.getString(cursor.getColumnIndex(Constants.KEY_AUTHOR)));
        bookDataObject.setRate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_RATE))));
        bookDataObject.setIsDone(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_DONE))));
        bookDataObject.setFilePath(cursor.getString(cursor.getColumnIndex(Constants.KEY_PATH)));
        //convert time stamp to something readable because it saved in mileSecond
        long milliSecondLong = cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE));
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy    HH:mm:ss");
        Date resultdate = new Date(milliSecondLong);
        String date = sdf.format(resultdate);
        bookDataObject.setDate(date);
        cursor.close();
        db.close();
        return bookDataObject ;

    }
    //search  books  By Name
    public List<BookDataObject> getBookByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<BookDataObject> bookList = new ArrayList<>();
        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,
                        Constants.KEY_BOOK_NAME,
                        Constants.KEY_AUTHOR, Constants.KEY_RATE, Constants.KEY_DONE, Constants.KEY_DATE, Constants.KEY_PATH}
                , Constants.KEY_BOOK_NAME + " LIKE ? ", //WHERE STATEMENT
                new String[]{"%"+name+"%"},//ARG OF WHERE
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                BookDataObject bookDataObject = new BookDataObject();
                bookDataObject.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                bookDataObject.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_BOOK_NAME)));
                bookDataObject.setAuthor(cursor.getString(cursor.getColumnIndex(Constants.KEY_AUTHOR)));
                bookDataObject.setRate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_RATE))));
                bookDataObject.setIsDone(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_DONE))));
                bookDataObject.setFilePath(cursor.getString(cursor.getColumnIndex(Constants.KEY_PATH)));
                //convert time stamp to something readable because it saved in mileSecond
                long milliSecondLong = cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE));
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy    HH:mm:ss");
                Date resultdate = new Date(milliSecondLong);
                String date = sdf.format(resultdate);
                bookDataObject.setDate(date);
                bookList.add(bookDataObject);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookList ;
    }
    //search books item By Author
    public List<BookDataObject> getBookByAuthor(String author) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<BookDataObject> bookList = new ArrayList<>();
        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,
                        Constants.KEY_BOOK_NAME,
                        Constants.KEY_AUTHOR, Constants.KEY_RATE, Constants.KEY_DONE, Constants.KEY_DATE, Constants.KEY_PATH}
                , Constants.KEY_AUTHOR + " LIKE ?",
                new String[]{"%"+author+"%"},
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                BookDataObject bookDataObject = new BookDataObject();
                bookDataObject.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                bookDataObject.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_BOOK_NAME)));
                bookDataObject.setAuthor(cursor.getString(cursor.getColumnIndex(Constants.KEY_AUTHOR)));
                bookDataObject.setRate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_RATE))));
                bookDataObject.setIsDone(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_DONE))));
                bookDataObject.setFilePath(cursor.getString(cursor.getColumnIndex(Constants.KEY_PATH)));
                //convert time stamp to something readable because it saved in mileSecond
                long milliSecondLong = cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE));
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy    HH:mm:ss");
                Date resultdate = new Date(milliSecondLong);
                String date = sdf.format(resultdate);
                bookDataObject.setDate(date);
                bookList.add(bookDataObject);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookList ;
    }

    //get all book in database
    public List<BookDataObject> getAllBooks(String sortingBy) {
        //get the value of sortby as name of column
         SQLiteDatabase db = this.getReadableDatabase();
        List<BookDataObject> bookList = new ArrayList<>();
        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,
                        Constants.KEY_BOOK_NAME,
                        Constants.KEY_AUTHOR,Constants.KEY_RATE,Constants.KEY_DONE, Constants.KEY_DATE, Constants.KEY_PATH}
                 ,null,
                null, null,
                null,sortingBy);

        if (cursor.moveToFirst()) {
            do {
                BookDataObject bookDataObject = new BookDataObject();
                bookDataObject.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                bookDataObject.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_BOOK_NAME)));
                bookDataObject.setAuthor(cursor.getString(cursor.getColumnIndex(Constants.KEY_AUTHOR)));
                bookDataObject.setRate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_RATE))));
                bookDataObject.setIsDone(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_DONE))));
                bookDataObject.setFilePath(cursor.getString(cursor.getColumnIndex(Constants.KEY_PATH)));
                //convert time stamp to something readable because it saved in mileSecond
                long milliSecondLong = cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE));
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy    HH:mm:ss");
                Date resultdate = new Date(milliSecondLong);
                String date = sdf.format(resultdate);
                bookDataObject.setDate(date);
                bookList.add(bookDataObject);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookList ;
    }


    //update book item
    public int updateBook(BookDataObject bookDataObject){
        int check;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_BOOK_NAME,bookDataObject.getName());
        values.put(Constants.KEY_AUTHOR,bookDataObject.getAuthor());
        values.put(Constants.KEY_RATE,bookDataObject.getRate());
        values.put(Constants.KEY_DONE,bookDataObject.getIsDone());
        values.put(Constants.KEY_DATE, System.currentTimeMillis());
        values.put(Constants.KEY_PATH,bookDataObject.getFilePath());
        check=  db.update(Constants.TABLE_NAME,values,Constants.KEY_ID + "=?",
                new String[]{String.valueOf(bookDataObject.getId())});
         db.close();
        return check;
    }

    //delete item from grocery List
    public  void  deleteBook (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME,
                Constants.KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();

    }
    //get count
    public int getBookCount(){
        int count;
        String  COUNT_QUERY = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(COUNT_QUERY,null );
        count =  cursor.getCount();
        db.close();
        cursor.close();
        return count;
    }




}
