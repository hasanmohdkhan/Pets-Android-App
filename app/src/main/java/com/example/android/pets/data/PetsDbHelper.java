package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.pets.data.PetContract.PetEntry;


/**
 * Created by hasanZian on 25-01-2018.
 *  Class for open or connect SQLitedatabase
 */

public class PetsDbHelper  extends SQLiteOpenHelper{
   //Database Name and Version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shelter.db";
    //Constructor with context
    public PetsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

         String  SQL_CREATE_PET_TABLE = "CREATE TABLE " + PetEntry.TABLE_NAME + "(" +
                   PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                   PetContract.PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL," +
                   PetContract.PetEntry.COLUMN_PET_BREED + " TEXT," +
                   PetContract.PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL," +
                   PetContract.PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";

            Log.d("CREATE comd " ," " +SQL_CREATE_PET_TABLE);

            db.execSQL(SQL_CREATE_PET_TABLE);
        }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
