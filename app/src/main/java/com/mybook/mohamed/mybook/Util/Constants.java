package com.mybook.mohamed.mybook.Util;


public class Constants  {
    public  static final int DB_VERSION = 1;
    public  static final String DB_NAME = "bookDB";
    public  static final String TABLE_NAME = "bookTable";
    // Table column
    public  static final  String KEY_ID = "_id";
    public  static final String KEY_BOOK_NAME = "_book_name";
    public  static final String KEY_AUTHOR ="_author";
    public  static final  String KEY_RATE = "_rate";
    public  static final  String KEY_DONE = "_done";
    public static final String KEY_DATE = "item_date";
    public static final String KEY_PATH = "_path";

    ////
    public static final Character[] toCharacterArray( String s ) {

        if ( s == null ) {
            return null;
        }

        int len = s.length();
        Character[] array = new Character[len];
        for (int i = 0; i < len ; i++) {
            array[i] = new Character(s.charAt(i));
        }

        return array;
    }


}
