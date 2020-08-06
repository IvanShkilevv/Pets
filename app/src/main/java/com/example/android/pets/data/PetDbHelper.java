package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.pets.data.ShelterContract.PetEntry;
import androidx.annotation.Nullable;

public class PetDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = PetDbHelper.class.getSimpleName();
    public static final String DATABASE_NAME = "shelter.db";
    public static final int DATABASE_VERSION = 1;

    String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + PetEntry.TABLE_NAME + " ("
            + PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
            + PetEntry.COLUMN_PET_BREED + " TEXT, "
            + PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
            + PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";
    String SQL_DELETE_PETS_TABLE =  "DROP TABLE " + PetEntry.TABLE_NAME + ";" ;


    public PetDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PETS_TABLE);
        onCreate(db);
    }
}