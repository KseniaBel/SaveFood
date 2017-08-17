package com.savefood.persistency;

import java.util.ArrayList;

/**
 * Created by ksu on 11/29/2015.
 */
public enum Category {
    MEAT_FISH("Meat and Fish"),
    MILK_PRODUCTS("Milk products"),
    FRUITS_VEGETABLES("Fruits and vegetables"),
    DRINKS("Drinks"),
    DESSERTS("Desserts"),
    OTHER("Other");

    private String value;

    Category(String categoryName) {
        value = categoryName;
    }

    public String toSting(){
        return value;
    }

    /**
     * Create an array of Strings with the names of all categories
     * @return
     */
    public static String[] toStringArray() {
        ArrayList<String> toReturn = new ArrayList(values().length);
        for(Category cat: values()) {
            toReturn.add(cat.toSting());
        }

        return toReturn.toArray(new String[toReturn.size()]);
    }
}
