package com.savefood.persistency;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 22/11/2015.
 */
public class ProductsTypesRepo {
    private DBHelper dbHelper;

    public ProductsTypesRepo(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    /**
     * Insert new product type into the database, return new product type id.
     * @param productType
     * @return
     */
    public int insert(ProductsType productType) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProductsType.KEY_NAME, productType.getName());
        values.put(ProductsType.KEY_CATEGORY, productType.getCategory());

        // Inserting Row
        long ProductTypeId = db.insert(ProductsType.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) ProductTypeId;
    }


    /**
     * Return product type with specified category and name.
     * @param name
     * @param category
     * @return
     */
    public ProductsType getProductTypeByNameAndCategory(String name,String category) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                ProductsType.KEY_ID + "," +
                ProductsType.KEY_NAME + "," +
                ProductsType.KEY_CATEGORY  +
                " FROM " + ProductsType.TABLE
                + " WHERE " +
                ProductsType.KEY_NAME + "='" + name+"' and " +
                ProductsType.KEY_CATEGORY + "='" + category + "'";

        ProductsType productType = new ProductsType();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                productType.setId(cursor.getInt(cursor.getColumnIndex(ProductsType.KEY_ID)));
                productType.setName(cursor.getString(cursor.getColumnIndex(ProductsType.KEY_NAME)));
                productType.setCategory(cursor.getString(cursor.getColumnIndex(ProductsType.KEY_CATEGORY)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productType;
    }


    /**
     * Return a list of products types belonging to the same category.
     * @param category
     * @return
     */
    public List<String> getProductTypesByCategory(String category) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(ProductsType.TABLE, new String[]{ProductsType.KEY_NAME},ProductsType.KEY_CATEGORY + " = '" + category + "'", null, null, null,null,null);

        List<String> productTypes = new ArrayList<>();

        while(cursor.moveToNext()) {
            productTypes.add(cursor.getString(0));
        }

        return productTypes;
    }

}
