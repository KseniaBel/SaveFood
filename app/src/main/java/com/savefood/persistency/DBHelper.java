package com.savefood.persistency;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 22/11/2015.
 */
public class DBHelper extends SQLiteOpenHelper{
    //version number to upgrade database version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SaveFood";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
        initProductTypes(db);
    }


    private void initProductTypes(SQLiteDatabase db) {
        insertProductType(db, 0, "Pork", Category.MEAT_FISH);
        insertProductType(db, 1, "Chicken", Category.MEAT_FISH);
        insertProductType(db, 2, "Beef", Category.MEAT_FISH);
        insertProductType(db, 3, "Salomon", Category.MEAT_FISH);
        insertProductType(db, 4, "Shrimps", Category.MEAT_FISH);
        insertProductType(db, 5, "Milk", Category.MILK_PRODUCTS);
        insertProductType(db, 6, "Cheese", Category.MILK_PRODUCTS);
        insertProductType(db, 7, "Yogurt", Category.MILK_PRODUCTS);
        insertProductType(db, 8, "Butter", Category.MILK_PRODUCTS);
        insertProductType(db, 9, "Cream", Category.MILK_PRODUCTS);
        insertProductType(db, 10, "Tomatoes", Category.FRUITS_VEGETABLES);
        insertProductType(db, 11, "Mushrooms", Category.FRUITS_VEGETABLES);
        insertProductType(db, 12, "Strawberries", Category.FRUITS_VEGETABLES);
        insertProductType(db, 13, "Broccoli", Category.FRUITS_VEGETABLES);
        insertProductType(db, 14, "Juice", Category.DRINKS);
        insertProductType(db, 15, "Smoothy", Category.DRINKS);
        insertProductType(db, 16, "Tiramisu", Category.DESSERTS);
        insertProductType(db, 17, "Pancakes", Category.DESSERTS);
        insertProductType(db, 18, "Mousse", Category.DESSERTS);
        insertProductType(db, 19, "Eggs", Category.OTHER);
    }

    private void createTables(SQLiteDatabase db) {
        createProductTypesTable(db);
        createProductsTable(db);
    }

    private void createProductsTable(SQLiteDatabase db) {
        String createTableStmt =" CREATE TABLE " + Product.TABLE + " ( " + Product.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + Product.KEY_QUANTITY + " INTEGER, " +
                "" + Product.KEY_EXPIREDATE + " LONG, " +
                "" + Product.KEY_FK_PRODUCT_TYPE + "  INTEGER, FOREIGN KEY ("+ Product.KEY_FK_PRODUCT_TYPE+") REFERENCES " +
                "" + ProductsType.TABLE + "(" + ProductsType.KEY_ID+"));";

        db.execSQL(createTableStmt);
    }

    private void createProductTypesTable(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + ProductsType.TABLE  + "("
                + ProductsType.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + ProductsType.KEY_NAME + " TEXT ,"
                + ProductsType.KEY_CATEGORY + " TEXT)";

        db.execSQL(CREATE_TABLE);
    }

    private void insertProductType(SQLiteDatabase db, int id, String type, Category category) {
        ContentValues values = new ContentValues();

        values.put(ProductsType.KEY_ID, id);
        values.put(ProductsType.KEY_NAME, type);
        values.put(ProductsType.KEY_CATEGORY, category.toSting());

        db.insert(ProductsType.TABLE, null, values);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
