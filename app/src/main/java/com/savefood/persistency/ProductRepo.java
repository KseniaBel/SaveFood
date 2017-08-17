package com.savefood.persistency;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ksu on 11/23/2015.
 */
public class ProductRepo {
    private DBHelper dbHelper;

    public ProductRepo(DBHelper dbHelper) {
       this.dbHelper = dbHelper;
    }

    /**
     * Insert new product into database
     * @param product
     */
    public void insert(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Product.KEY_EXPIREDATE, product.getExpireDate().getTime());
        values.put(Product.KEY_QUANTITY, product.getQuantity());
        values.put(Product.KEY_FK_PRODUCT_TYPE, product.getProductType().getId());

        // Inserting Row
        db.insert(Product.TABLE, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Update the quantity or expiry date of the existing product
     * @param product
     */
    public void update(Product product) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Product.KEY_QUANTITY, product.getQuantity());
        values.put(Product.KEY_EXPIREDATE, product.getExpireDate().getTime());

        db.update(Product.TABLE, values, Product.KEY_ID + "=" + product.getId(), null);
        db.close(); // Closing database connection
    }

    /**
     * Delete product from the database based on id
     * @param Product_Id
     */
    public void delete(int Product_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Product.TABLE, Product.KEY_ID + "=" + Product_Id, null);
        db.close(); // Closing database connection
    }

    /**
     * Return a list of all products sorted by expiry date
     * @return
     */
    public ArrayList<Product> listSortedProducts() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =
         "SELECT prod.id as id, prod.quantity as quantity, prod.expiry_date as expiry_date, prodType.id as prodTypeId, prodType.name as prodName ,prodType.category as prodCategory FROM Product prod, ProductsType prodType WHERE prod.fk_product_type=prodType.id ORDER BY prod.expiry_date;";


        ArrayList<Product> products = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Product currentProduct = new Product();
                ProductsType productType = new ProductsType();
                currentProduct.setProductType(productType);

                currentProduct.setId(cursor.getInt(cursor.getColumnIndex("id")));

                Date expiryDate = new Date(cursor.getLong(cursor.getColumnIndex("expiry_date")));
                currentProduct.setExpireDate(expiryDate);

                currentProduct.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));

                productType.setId(cursor.getInt(cursor.getColumnIndex("prodTypeId")));
                productType.setName(cursor.getString(cursor.getColumnIndex("prodName")));
                productType.setCategory(cursor.getString(cursor.getColumnIndex("prodCategory")));
                products.add(currentProduct);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return products;
    }

    /**
     * Return true if there is at least one product that about to expire
     * @return
     */
    public boolean hasProductsAboutToExpire() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 24); //for those which are about to expire

        String selectQuery = "select count(*) from Product where expiry_date <= " + cal.getTimeInMillis();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        cursor.close();

        return count>0;
    }

    /**
     * Return product with specified id.
     * @param productID
     * @return
     */
    public Product findByID(int productID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        /*String selectQuery =  "SELECT * FROM " + Product.TABLE + " prod, "+ ProductsType.TABLE +" prodType " +
                "WHERE prod."+Product.KEY_FK_PRODUCT_TYPE+"=prodType."+ProductsType.KEY_ID+" " +
                "ORDER BY prod." + Product.KEY_EXPIREDATE;*/

        String selectQuery =
                "SELECT prod.id as id, prod.quantity as quantity, prod.expiry_date as expiry_date, prodType.id as prodTypeId, prodType.name as prodName ,prodType.category as prodCategory FROM Product prod, ProductsType prodType WHERE prod.id="+productID+" and prod.fk_product_type=prodType.id ORDER BY prod.expiry_date;";


        Product currentProduct = null;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
                currentProduct = new Product();
                ProductsType productType = new ProductsType();
                currentProduct.setProductType(productType);

                currentProduct.setId(cursor.getInt(cursor.getColumnIndex("id")));

                Date expiryDate = new Date(cursor.getLong(cursor.getColumnIndex("expiry_date")));
                currentProduct.setExpireDate(expiryDate);

                currentProduct.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));

                productType.setId(cursor.getInt(cursor.getColumnIndex("prodTypeId")));
                productType.setName(cursor.getString(cursor.getColumnIndex("prodName")));
                productType.setCategory(cursor.getString(cursor.getColumnIndex("prodCategory")));
        }

        cursor.close();
        db.close();
        return currentProduct;
    }
}
