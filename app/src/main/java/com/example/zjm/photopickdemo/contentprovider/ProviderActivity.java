package com.example.zjm.photopickdemo.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zjm.photopickdemo.R;

public class ProviderActivity extends AppCompatActivity {
   // private static final UriMatcher sUriMatcher;

 //   sUriMatcher.addURI("com.example.zjm.photopickdemo","table3",1);

    private MainDatabaseHelper mOpenHelper;
    private static final String DBNAME ="mydb";
    private SQLiteDatabase db;

    public boolean onCreate(){
        mOpenHelper=new MainDatabaseHelper(this);
        return true;
    }

//    public Cursor insert(Uri uri, ContentValues values){
//        db=mOpenHelper.getWritableDatabase();
//        return
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
    }

    private static final String SQL_CREATE_MAIN = "CREATE TABLE " +
            "main " +                       // Table's name
            "(" +                           // The columns in the table
            " _ID INTEGER PRIMARY KEY, " +
            " WORD TEXT"+
    " FREQUENCY INTEGER " +
            " LOCALE TEXT )";
    /**
     * Helper class that actually creates and manages the provider's underlying data repository.
     */
    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {

        /*
         * Instantiates an open helper for the provider's SQLite data repository
         * Do not do database creation and upgrade here.
         */
        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
        }

        /*
         * Creates the data repository. This is called when the provider attempts to open the
         * repository and SQLite reports that it doesn't exist.
         */
        public void onCreate(SQLiteDatabase db) {

            // Creates the main table
            db.execSQL(SQL_CREATE_MAIN);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

}
