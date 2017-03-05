package com.example.bond.inventorymanager;

import android.Manifest;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.Toast;

import com.example.bond.inventorymanager.data.InventoryContract.InventoryEntry;


public class EditItemActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{

    /** Log tag */
    private static final String LOG_TAG = EditItemActivity.class.getSimpleName();

    /** Permission to write on the file*/
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    /** Identifier for the item data loader*/
    private static final int CURRENT_ITEM_LOADER = 0;

    /** Uri to hold the request type*/
    private Uri mCurrentItemUri;
    private Uri mImageUri;
    /** Catch current item id if it's mentioned in the Uri*/
    long currentItemId;

    /** Edit texts*/
    private EditText edit_name;
    private EditText edit_price;
    private EditText edit_quantity;
    private EditText edit_supplier_name;
    private EditText edit_supplier_email;
    private EditText edit_supplier_phone;

    /** Image buttons*/
    private ImageButton btn_decrease_qty;
    private ImageButton btn_increase_qty;
    private Button btn_call;
    private Button btn_email;
    private Button btn_image_select;

    /** Image*/
    private ImageView image_display;

    private static final int PICK_IMAGE_REQUEST = 0;

    private Boolean mItemHasChanged = false;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mItemHasChanged boolean to true.
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mItemHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        // Find the current intent
        Intent intent = getIntent();
        mCurrentItemUri = intent.getData();

        /* Catching handles*/
        edit_name = (EditText) findViewById(R.id.product_name_edit);
        edit_price = (EditText) findViewById(R.id.price_edit);
        edit_quantity = (EditText) findViewById(R.id.quantity_edit);
        image_display = (ImageView) findViewById(R.id.image_view);
        edit_supplier_name = (EditText) findViewById(R.id.supplier_name_edit);
        edit_supplier_email = (EditText) findViewById(R.id.supplier_email_edit);
        edit_supplier_phone = (EditText) findViewById(R.id.supplier_phone_edit);
        btn_image_select = (Button) findViewById(R.id.select_image);
        btn_decrease_qty = (ImageButton) findViewById(R.id.decrease_quantity);
        btn_increase_qty = (ImageButton) findViewById(R.id.increase_quantity);
        btn_call = (Button) findViewById(R.id.button_call);
        btn_email = (Button) findViewById(R.id.button_email);

        //Check if the current intent is null or not
        if (mCurrentItemUri == null){
            //New item is added to inventory
            setTitle(getString(R.string.editor_activity_title_new_item));

            btn_email.setEnabled(false);
            btn_call.setEnabled(false);
            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a pet that hasn't been created yet.)
            invalidateOptionsMenu();
        } else{
            //We are editing an item
            setTitle(getString(R.string.editor_activity_title_edit_item));

            // Initialize a loader to read the item data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(CURRENT_ITEM_LOADER, null, this);
        }

        btn_image_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* checkImageReadPermission() will check whether we have the permission to read from
                * the external storage. If we already have the permission then call the image selector.
                * If we don't have the permission, requestPermission() will trigger
                * onRequestPermissionResult() which will in turn call image selector.
                */
                checkImageReadPermission();
                mItemHasChanged = true;
            }
        });

        btn_decrease_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseItemQuantity();
            }
        });

        btn_increase_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseItemQuantity();
            }
        });

        /* Touch listeners to check whether the item has been modified or not*/
        edit_name.setOnTouchListener(mTouchListener);
        edit_price.setOnTouchListener(mTouchListener);
        edit_name.setOnTouchListener(mTouchListener);
        edit_quantity.setOnTouchListener(mTouchListener);
        edit_supplier_name.setOnTouchListener(mTouchListener);
        edit_supplier_email.setOnTouchListener(mTouchListener);
        edit_supplier_phone.setOnTouchListener(mTouchListener);
        btn_image_select.setOnTouchListener(mTouchListener);
        btn_decrease_qty.setOnTouchListener(mTouchListener);
        btn_increase_qty.setOnTouchListener(mTouchListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.
        if (mCurrentItemUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Since the editor shows all items attributes, define a projection that contains
        // all columns from the inventory table
        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_ITEM_NAME,
                InventoryEntry.COLUMN_ITEM_QTY,
                InventoryEntry.COLUMN_ITEM_PRICE,
                InventoryEntry.COLUMN_SUPPLIER_NAME,
                InventoryEntry.COLUMN_SUPPLIER_EMAIL,
                InventoryEntry.COLUMN_SUPPLIER_PHONE,
                InventoryEntry.COLUMN_ITEM_IMAGE};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentItemUri,         // Query the content URI for the current pet
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of pet attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_NAME);
            int qtyColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_QTY);
            int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_PRICE);
            int imageColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_IMAGE);
            int supplierNameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_SUPPLIER_NAME);
            int supplierEmailColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_SUPPLIER_EMAIL);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_SUPPLIER_PHONE);

            // Extract out the value from the Cursor for the given column index
            String item_name = cursor.getString(nameColumnIndex);
            int quantity = cursor.getInt(qtyColumnIndex);
            int price = cursor.getInt(priceColumnIndex);
            String image = cursor.getString(imageColumnIndex);
            final String supplier = cursor.getString(supplierNameColumnIndex);
            final String email = cursor.getString(supplierEmailColumnIndex);
            final String phone = cursor.getString(supplierPhoneColumnIndex);

            // Update the views on the screen with the values from the database
            edit_name.setText(item_name);
            edit_price.setText(Integer.toString(price));
            edit_quantity.setText(Integer.toString(quantity));
            edit_supplier_name.setText(supplier);
            edit_supplier_phone.setText(phone);
            edit_supplier_email.setText(email);
            image_display.setImageURI(Uri.parse(image));
            mImageUri = Uri.parse(image);

            // Set the click listeners on call and email button
            btn_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent to phone
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse(phone.trim()));
                    startActivity(intent);
                }
            });

            btn_email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent to email
                    Intent intent = new Intent(android.content.Intent.ACTION_SENDTO);
                    intent.setType("text/plain");
                    intent.setData(Uri.parse("mailto:" + email.trim()));
                    intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "New order");
                    String bodyMessage = "Request to deliver the order as soon as possible " +
                            supplier.trim() +". \n Thank you.";
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, bodyMessage);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        edit_name.setText("");
        edit_quantity.setText("");
        edit_price.setText("");
        edit_supplier_name.setText("");
        edit_supplier_email.setText("");
        edit_supplier_phone.setText("");
        image_display = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save item to database
                saveItem();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the pet hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mItemHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditItemActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditItemActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     * This method is called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        // If the pet hasn't changed, continue with handling back button press
        if (!mItemHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Prompt the user to confirm that they want to delete this pet.
     */
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the item.
                deleteItem();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the item.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /** Check whether the application has permission to read the image from external storage*/
    public void checkImageReadPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Will fire the callback onRequestPermissionResult
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            return;
        }
        openImageSelector();
    }

    /** Fire the implicit intent to open images */
    private void openImageSelector() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    /** Check whether the permission for reading external storage has been granted successfully or not*/
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openImageSelector();
                    // permission was granted
                }
            }
        }
    }

    /** We have successfully selected the image from the local*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code READ_REQUEST_CODE.
        // If the request code seen here doesn't match, it's the response to some other intent,
        // and the below code shouldn't run at all.

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.  Pull that uri using "resultData.getData()"

            if (resultData != null) {
                mImageUri = resultData.getData();
                image_display.setImageURI(mImageUri);
                //imageView.invalidate();
            }
        }
    }

    /** Get the user input and save into the database*/
    public void saveItem(){
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = edit_name.getText().toString().trim();
        String qtyString = edit_quantity.getText().toString().trim();
        String priceString = edit_price.getText().toString().trim();
        String supplierNameString = edit_supplier_name.getText().toString().trim();
        String supplierEmailString = edit_supplier_email.getText().toString().trim();
        String supplierPhoneString = edit_supplier_phone.getText().toString().trim();
        String imageString = mImageUri.toString();

        // Check if this is supposed to be a new pet
        // and check if all the fields in the editor are blank
        if (mImageUri == null &&
                TextUtils.isEmpty(nameString) && TextUtils.isEmpty(qtyString) &&
                TextUtils.isEmpty(priceString) && TextUtils.isEmpty(supplierNameString) &&
                TextUtils.isEmpty(supplierEmailString) && TextUtils.isEmpty(supplierPhoneString)){
            // Since no fields were modified, we can return early without creating a new pet.
            // No need to create ContentValues and no need to do any ContentProvider operations.

            return;
        }

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_ITEM_NAME, nameString);
        values.put(InventoryEntry.COLUMN_ITEM_QTY, Integer.parseInt(qtyString));
        values.put(InventoryEntry.COLUMN_ITEM_PRICE, Integer.parseInt(priceString));
        values.put(InventoryEntry.COLUMN_SUPPLIER_NAME, supplierNameString);
        values.put(InventoryEntry.COLUMN_SUPPLIER_EMAIL, supplierEmailString);
        values.put(InventoryEntry.COLUMN_SUPPLIER_PHONE, supplierPhoneString);
        values.put(InventoryEntry.COLUMN_ITEM_IMAGE, imageString);

        // Determine if this is a new or existing item by checking if mCurrentItemUri is null or not
        if (mCurrentItemUri == null) {
            // This is a NEW item, so insert a new item into the provider,
            // returning the content URI for the new item.
            Uri newUri = getContentResolver().insert(InventoryEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.toast_insert_item_fail),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.toast_insert_item_success),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING item, so update the item with content URI: mCurrentItemUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentItemUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentItemUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.toast_update_item_fail),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.toast_update_item_success),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /** Delete an item from Inventory */
    public void deleteItem(){
        // Only perform the delete if this is an existing item.
        if (mCurrentItemUri != null) {
            // Call the ContentResolver to delete the item at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentPetUri
            // content URI already identifies the pet that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentItemUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.toast_delete_item_fail),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.toast_delete_item_success),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Close the activity
        finish();
    }

    private void increaseItemQuantity(){
        String quantity = edit_quantity.getText().toString().trim();
        if (TextUtils.isEmpty(quantity)){
            edit_quantity.setText("1");
        } else {
            int currentQuantity =  Integer.parseInt(quantity);
            edit_quantity.setText(Integer.toString(currentQuantity + 1));
        }
        return;
    }

    private void decreaseItemQuantity(){
        String quantity = edit_quantity.getText().toString().trim();
        if (TextUtils.isEmpty(quantity) || TextUtils.equals(quantity,"0")){
            Toast.makeText(this, "Cannot decrease quantity!", Toast.LENGTH_SHORT).show();
        } else {
            int currentQuantity =  Integer.parseInt(quantity);
            edit_quantity.setText(Integer.toString(currentQuantity - 1));
        }
        return;
    }

}
