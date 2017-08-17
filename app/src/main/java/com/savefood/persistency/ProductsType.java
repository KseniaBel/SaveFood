package com.savefood.persistency;

import android.content.Context;
/**
 * Created by user on 22/11/2015.
 */
public class ProductsType {
    // Labels table name
    public static final String TABLE = "ProductsType";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_CATEGORY = "category";

    private int id;
    private String name;
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
