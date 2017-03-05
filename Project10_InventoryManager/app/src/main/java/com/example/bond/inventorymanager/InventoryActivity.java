package com.example.bond.inventorymanager;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bond.inventorymanager.data.InventoryContract;
import com.example.bond.inventorymanager.data.InventoryContract.InventoryEntry;
import com.example.bond.inventorymanager.data.InventoryProvider;


public class InventoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    /** Identifier for the pet data loader */
    private static final int INVENTORY_LOADER = 0;

    /** Adapter for the ListView */
    InventoryCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventoryActivity.this, EditItemActivity.class);
                startActivity(intent);
            }
        });

        // Find the ListView which will be populated with the item data
        ListView inventoryListView = (ListView) findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        inventoryListView.setEmptyView(emptyView);

        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        // There is no pet data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new InventoryCursorAdapter(this, null);
        inventoryListView.setAdapter(mCursorAdapter);

        // Kick off the loader
        getLoaderManager().initLoader(INVENTORY_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_ITEM_NAME,
                InventoryEntry.COLUMN_ITEM_IMAGE,
                InventoryEntry.COLUMN_ITEM_PRICE,
                InventoryEntry.COLUMN_ITEM_QTY
                };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,           // Parent activity context
                InventoryEntry.CONTENT_URI,     // Provider content URI to query
                projection,                     // Columns to include in the resulting Cursor
                null,                           // No selection clause
                null,                           // No selection arguments
                null);                          // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }

    public void clickOnInventoryItem(long id) {
        // Create new intent
        Intent intent = new Intent(InventoryActivity.this, EditItemActivity.class);
        // Form URI to fetch the current item of inventory
        Uri currentItemUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id);
        // Load the intent with the data
        intent.setData(currentItemUri);
        startActivity(intent);
    }

    public void clickOnItemSale(long id, int quantity) {
        if(quantity > 0){
            // Generate URI to update the quantity of the item
            Uri mSaleOneItem = Uri.parse(InventoryEntry.CONTENT_URI +"/"+ id +"/"+ InventoryContract.PATH_SELL_ITEM);

            ContentValues values = new ContentValues();
            quantity = quantity - 1;
            values.put(InventoryEntry.COLUMN_ITEM_QTY, quantity);

            int rowsAffected = getContentResolver().update(mSaleOneItem, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, "Item cannot be sold successfully!",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, "Item sold successfully!",
                        Toast.LENGTH_SHORT).show();
            }
            //dbHelper.sellOneItem(id, quantity);
            //adapter.swapCursor(dbHelper.readStock());
        } else {
            Toast.makeText(this, "Item cannot be sold successfully!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
