/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetsDbHelper;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>{

   private PetsDbHelper mDBHelper ;
   private static final int PET_LOADER = 0;
   private PetCursorAdapter mCursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDBHelper = new PetsDbHelper(this);
        //displayDatabaseInfo();

        ListView petListView = (ListView)findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);
        mCursorAdapter = new PetCursorAdapter(this,null);
        petListView.setAdapter(mCursorAdapter);

        //kick off loader
       getLoaderManager().initLoader(PET_LOADER,null,this);

       petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(getApplicationContext(),EditorActivity.class);
               // here we getting current pet id i.e
               // content://com.example.android.pets/pets/3
               Uri currentPetUri = ContentUris.withAppendedId(PetContract.PetEntry.CONTENT_URI,id);
               intent.setData(currentPetUri);


               startActivity(intent);
           }
       });


    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */


      private void insertPets() {

          ContentValues values = new ContentValues();
          values.put(PetContract.PetEntry.COLUMN_PET_NAME, "Griffeld");
          values.put(PetContract.PetEntry.COLUMN_PET_BREED , "Bulldog");
          values.put(PetContract.PetEntry.COLUMN_PET_GENDER, PetContract.PetEntry.GENDER_MALE);
          values.put(PetContract.PetEntry.COLUMN_PET_WEIGHT, 4);


          Uri newUri = getContentResolver().insert(PetContract.PetEntry.CONTENT_URI ,values);
          // Show a toast message depending on whether or not the insertion was successful
          if (newUri == null) {
              // If the new content URI is null, then there was an error with insertion.
              Toast.makeText(this, getString(R.string.editor_insert_pet_failed),
                      Toast.LENGTH_SHORT).show();
          } else {
              // Otherwise, the insertion was successful and we can display a toast.
              Toast.makeText(this, getString(R.string.editor_insert_pet_successful),
                      Toast.LENGTH_SHORT).show();
          }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                //Inserting dummy data
                insertPets();

                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                deleteAllPets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Helper method to delete all pets in the database.
     */
    private void deleteAllPets() {
        int rowDeleted =getContentResolver().delete(PetContract.PetEntry.CONTENT_URI,null,null);
        Log.v("CatalogActivity", rowDeleted +  " rows deleted from pet database");
        if(rowDeleted == 0){
            Toast.makeText(this,getString(R.string.action_delete_all_entries_no_item),Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,getString(R.string.action_delete_all_entries_done),Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = { PetContract.PetEntry._ID,
                               PetContract.PetEntry.COLUMN_PET_NAME,
                               PetContract.PetEntry.COLUMN_PET_BREED};
        return new CursorLoader(this,
                                   PetContract.PetEntry.CONTENT_URI,
                                     projection,
                null,null,null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
      mCursorAdapter.swapCursor(null);
    }
}
