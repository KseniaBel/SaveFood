
package com.savefood.rest.object.v2;

import java.util.ArrayList;
import java.util.List;

public class Digest {

    private String label;
    private String tag;
    private Object schemaOrgTag;
    private float total;
    private boolean hasRDI;
    private float daily;
    private String unit;
    private List<Sub> sub = new ArrayList<Sub>();

    /**
     * 
     * @return
     *     The label
     */
    public String getLabel() {
        return label;
    }

    /**
     * 
     * @param label
     *     The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 
     * @return
     *     The tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * 
     * @param tag
     *     The tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 
     * @return
     *     The schemaOrgTag
     */
    public Object getSchemaOrgTag() {
        return schemaOrgTag;
    }

    /**
     * 
     * @param schemaOrgTag
     *     The schemaOrgTag
     */
    public void setSchemaOrgTag(Object schemaOrgTag) {
        this.schemaOrgTag = schemaOrgTag;
    }

    /**
     * 
     * @return
     *     The total
     */
    public float getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(float total) {
        this.total = total;
    }

    /**
     * 
     * @return
     *     The hasRDI
     */
    public boolean isHasRDI() {
        return hasRDI;
    }

    /**
     * 
     * @param hasRDI
     *     The hasRDI
     */
    public void setHasRDI(boolean hasRDI) {
        this.hasRDI = hasRDI;
    }

    /**
     * 
     * @return
     *     The daily
     */
    public float getDaily() {
        return daily;
    }

    /**
     * 
     * @param daily
     *     The daily
     */
    public void setDaily(float daily) {
        this.daily = daily;
    }

    /**
     * 
     * @return
     *     The unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 
     * @param unit
     *     The unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 
     * @return
     *     The sub
     */
    public List<Sub> getSub() {
        return sub;
    }

    /**
     * 
     * @param sub
     *     The sub
     */
    public void setSub(List<Sub> sub) {
        this.sub = sub;
    }

}
