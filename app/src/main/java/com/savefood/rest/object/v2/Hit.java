
package com.savefood.rest.object.v2;


public class Hit {

    private Recipe recipe;
    private boolean bookmarked;
    private boolean bought;

    /**
     * 
     * @return
     *     The recipe
     */
    public Recipe getRecipe() {
        return recipe;
    }

    /**
     * 
     * @param recipe
     *     The recipe
     */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * 
     * @return
     *     The bookmarked
     */
    public boolean isBookmarked() {
        return bookmarked;
    }

    /**
     * 
     * @param bookmarked
     *     The bookmarked
     */
    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    /**
     * 
     * @return
     *     The bought
     */
    public boolean isBought() {
        return bought;
    }

    /**
     * 
     * @param bought
     *     The bought
     */
    public void setBought(boolean bought) {
        this.bought = bought;
    }


    @Override
    public String toString() {
        return getRecipe().getLabel();
    }
}
