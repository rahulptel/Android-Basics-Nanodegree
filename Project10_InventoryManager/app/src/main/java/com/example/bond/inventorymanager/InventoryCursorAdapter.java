package com.example.bond.inventorymanager;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bond.inventorymanager.data.InventoryContract.InventoryEntry;
import com.example.bond.inventorymanager.data.InventoryDbHelper;

/**
 * Created by bond on 2/21/2017.
 */

public class InventoryCursorAdapter extends CursorAdapter {

    private final InventoryActivity activity;

    public InventoryCursorAdapter(InventoryActivity context, Cursor c){
        super(context, c, 0);
        this.activity = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.inventory_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Get the text views from the list item to be inflated
        TextView item_name = (TextView) view.findViewById(R.id.item_name);
        TextView item_quantity = (TextView) view.findViewById(R.id.item_quantity);
        TextView item_sell = (TextView) view.findViewById(R.id.item_sale);
        TextView item_price = (TextView) view.findViewById(R.id.item_price);
        ImageView item_image = (ImageView) view.findViewById(R.id.item_image);

        int idColumnIndex = cursor.getColumnIndex(InventoryEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_NAME);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_QTY);
        int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_PRICE);
        int imageColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_IMAGE);

        final long id = cursor.getLong(idColumnIndex);
        String name = cursor.getString(nameColumnIndex);
        final int quantity = cursor.getInt(quantityColumnIndex);
        int price = cursor.getInt(priceColumnIndex);
        String image = cursor.getString(imageColumnIndex);

        item_name.setText(name);
        item_quantity.setText(Integer.toString(quantity));
        item_price.setText(Integer.toString(price));
        item_image.setImageURI(Uri.parse(image));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.clickOnInventoryItem(id);
            }
        });

        item_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.clickOnItemSale(id, quantity);
            }
        });
    }
}
