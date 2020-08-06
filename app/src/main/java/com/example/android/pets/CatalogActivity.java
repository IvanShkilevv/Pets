package com.example.android.pets;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.pets.data.ShelterContract.PetEntry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;

import com.example.android.pets.data.PetDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.android.pets.data.ShelterContract.PetEntry.COLUMN_PET_BREED;
import static com.example.android.pets.data.ShelterContract.PetEntry.COLUMN_PET_GENDER;
import static com.example.android.pets.data.ShelterContract.PetEntry.COLUMN_PET_NAME;
import static com.example.android.pets.data.ShelterContract.PetEntry.COLUMN_PET_WEIGHT;
import static com.example.android.pets.data.ShelterContract.PetEntry.CONTENT_URI;
import static com.example.android.pets.data.ShelterContract.PetEntry.GENDER_MALE;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    PetCursorAdapter petCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        ListView listView =  findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty_view));

        petCursorAdapter = new PetCursorAdapter(this, null);
        listView.setAdapter(petCursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Uri currentPetUri = ContentUris.withAppendedId(CONTENT_URI, id);
            Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
            intent.setData(currentPetUri);
            startActivity(intent);
            }
        });

       getLoaderManager().initLoader(0, null, this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertDummyData();
                return true;
            case R.id.action_delete_all_entries:
                getContentResolver().delete(CONTENT_URI, null, null);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertDummyData(){
        PetDbHelper mDbHelper = new PetDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PET_NAME, "Toto");
        values.put(COLUMN_PET_BREED, "Terrier");
        values.put(COLUMN_PET_GENDER, GENDER_MALE);
        values.put(COLUMN_PET_WEIGHT, 7);

        Uri newPetUri = getContentResolver().insert(CONTENT_URI, values);

        if (newPetUri==null) {
            Toast.makeText( this, getString(R.string.editor_insert_pet_failed), Toast.LENGTH_SHORT).show(); }
        else {Toast.makeText( this, getString(R.string.editor_insert_pet_successful), Toast.LENGTH_SHORT).show();}

    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
        };
        return new CursorLoader(this, CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        petCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        petCursorAdapter.swapCursor(null);
    }
}
