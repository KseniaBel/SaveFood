
package com.savefood.rest.object.v2;


public class Ingredient {

    private String text;
    private float quantity;
    private String measure;
    private String food;
    private float weight;

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 
     * @return
     *     The quantity
     */
    public float getQuantity() {
        return quantity;
    }

    /**
     * 
     * @param quantity
     *     The quantity
     */
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    /**
     * 
     * @return
     *     The measure
     */
    public String getMeasure() {
        return measure;
    }

    /**
     * 
     * @param measure
     *     The measure
     */
    public void setMeasure(String measure) {
        this.measure = measure;
    }

    /**
     * 
     * @return
     *     The food
     */
    public String getFood() {
        return food;
    }

    /**
     * 
     * @param food
     *     The food
     */
    public void setFood(String food) {
        this.food = food;
    }

    /**
     * 
     * @return
     *     The weight
     */
    public float getWeight() {
        return weight;
    }

    /**
     * 
     * @param weight
     *     The weight
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

}
