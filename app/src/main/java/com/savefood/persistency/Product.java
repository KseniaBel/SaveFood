package com.savefood.persistency;

import java.util.Date;

/**
 * Created by ksu on 11/23/2015.
 */
public class Product {
    // Labels table name
    public static final String TABLE = "Product";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_EXPIREDATE = "expiry_date";
    public static final String KEY_QUANTITY = "quantity";
    public static final String KEY_FK_PRODUCT_TYPE = "fk_product_type";

    private int id;
    private Date expireDate;
    private int quantity;
    private ProductsType productType;

    private boolean isSelected;//this we need for the GUI checkboxes

    public ProductsType getProductType() {
        return productType;
    }

    public void setProductType(ProductsType productType) {
        this.productType = productType;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getExpireDate() {return expireDate;}

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
