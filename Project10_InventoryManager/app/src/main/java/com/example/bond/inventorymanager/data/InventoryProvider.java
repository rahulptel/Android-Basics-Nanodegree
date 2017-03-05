package com.example.bond.inventorymanager.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Patterns;

import com.example.bond.inventorymanager.R;
import com.example.bond.inventorymanager.data.InventoryContract.InventoryEntry;

import java.util.List;

/**
 * Created by bond on 2/21/2017.
 */

public class InventoryProvider extends ContentProvider {

    /** Tag for the log messages */
    public static final String LOG_TAG = InventoryProvider.class.getSimpleName();

    /** URI matcher code for the content URI for the inventory table */
    private static final int ITEMS = 100;

    /** URI matcher code for the content URI for a single item in the inventory table */
    private static final int ITEM_ID = 101;

    /** URI matcher code to update the number of items for a single item */
    private static final int SELL_ITEM = 102;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_INVENTORY, ITEMS);
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_INVENTORY + "/#", ITEM_ID);
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_INVENTORY + "/#/" +
                InventoryContract.PATH_SELL_ITEM, SELL_ITEM);
    }

    /** Database helper object */
    private InventoryDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new InventoryDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // Hold the result in the following cursor
        Cursor cursor;

        switch (sUriMatcher.match(uri)){
            // Fetch all the items from the inventory database
            case ITEMS:
                cursor = database.query(InventoryEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            // Fetch details about a particular item from the inventory database
            case ITEM_ID:
                // Set the selection criterion
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(InventoryEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)){
            case ITEMS:
                return InventoryContract.InventoryEntry.CONTENT_LIST_TYPE;

            case ITEM_ID:
                return InventoryContract.InventoryEntry.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + sUriMatcher.match(uri));
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (sUriMatcher.match(uri)){
            case ITEMS:
                return insertItem(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for uri: " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (sUriMatcher.match(uri)){
            case ITEM_ID:
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return deleteItem(selection, selectionArgs);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        switch (sUriMatcher.match(uri)){
            case ITEM_ID:
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateItem(values, selection, selectionArgs);

            case SELL_ITEM:
                List pathSegments = uri.getPathSegments();
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(pathSegments.get(1))};
                return updateItem(values, selection, selectionArgs);

        }
        return 0;
    }

    Uri insertItem(Uri uri, ContentValues values){
        Log.d(LOG_TAG, "InsertItem");
        // Check that the name is not null
        String name = values.getAsString(InventoryEntry.COLUMN_ITEM_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Item name required");
        }

        Integer quantity = values.getAsInteger(InventoryEntry.COLUMN_ITEM_QTY);
        if (quantity < 0){
            throw new IllegalArgumentException("Item quantity must be greater than zero");
        }

        Integer price = values.getAsInteger(InventoryEntry.COLUMN_ITEM_PRICE);
        if (price < 0){
            throw new IllegalArgumentException("Item quantity must be greater than zero");
        }

        String image = values.getAsString(InventoryEntry.COLUMN_ITEM_IMAGE);
        if (image == null) {
            throw new IllegalArgumentException("Item image required");
        }

        String supplier_name = values.getAsString(InventoryEntry.COLUMN_SUPPLIER_NAME);
        if (supplier_name == null) {
            throw new IllegalArgumentException("Item supplier name required");
        }

        String supplier_email = values.getAsString(InventoryEntry.COLUMN_SUPPLIER_EMAIL);
        if (supplier_email == null || !(Patterns.EMAIL_ADDRESS.matcher(supplier_email).matches())) {
            throw new IllegalArgumentException("Invalid email id");
        }

        String supplier_phone = values.getAsString(InventoryEntry.COLUMN_SUPPLIER_PHONE);
        if (supplier_phone == null) {
            throw new IllegalArgumentException("Item supplier phone number required");
        }

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new item with the given values
        long id = database.insert(InventoryEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    public int updateItem(ContentValues values, String selection, String[] selectionArgs){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(InventoryEntry.TABLE_NAME, values, selection, selectionArgs);

        if(rowsUpdated > 0){
            getContext().getContentResolver().notifyChange(InventoryEntry.CONTENT_URI, null);
            return rowsUpdated;
        }
        return 0;
    }

    public int deleteItem(String selection, String[] selectionArgs){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.delete(InventoryEntry.TABLE_NAME, selection, selectionArgs);
        if( rowsUpdated > 0){
            getContext().getContentResolver().notifyChange(InventoryEntry.CONTENT_URI, null);
            return rowsUpdated;
        }
        return 0;
    }
}
