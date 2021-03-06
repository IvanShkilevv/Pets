package com.example.android.pets;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import static com.example.android.pets.data.ShelterContract.PetEntry.COLUMN_PET_BREED;
import static com.example.android.pets.data.ShelterContract.PetEntry.COLUMN_PET_NAME;

public class PetCursorAdapter extends CursorAdapter {

    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 );
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = view.findViewById(R.id.name);
        TextView breedTextView = view.findViewById(R.id.summary);

        String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_NAME));
        String breed = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_BREED));

        nameTextView.setText(name);
        breedTextView.setText(breed);

    }
}