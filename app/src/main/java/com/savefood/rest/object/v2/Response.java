
package com.savefood.rest.object.v2;

import java.util.ArrayList;
import java.util.List;

public class Response {

    private String q;
    private int from;
    private int to;
    private Params params;
    private boolean more;
    private int count;
    private List<Hit> hits = new ArrayList<Hit>();

    /**
     * 
     * @return
     *     The q
     */
    public String getQ() {
        return q;
    }

    /**
     * 
     * @param q
     *     The q
     */
    public void setQ(String q) {
        this.q = q;
    }

    /**
     * 
     * @return
     *     The from
     */
    public int getFrom() {
        return from;
    }

    /**
     * 
     * @param from
     *     The from
     */
    public void setFrom(int from) {
        this.from = from;
    }

    /**
     * 
     * @return
     *     The to
     */
    public int getTo() {
        return to;
    }

    /**
     * 
     * @param to
     *     The to
     */
    public void setTo(int to) {
        this.to = to;
    }

    /**
     * 
     * @return
     *     The params
     */
    public Params getParams() {
        return params;
    }

    /**
     * 
     * @param params
     *     The params
     */
    public void setParams(Params params) {
        this.params = params;
    }

    /**
     * 
     * @return
     *     The more
     */
    public boolean isMore() {
        return more;
    }

    /**
     * 
     * @param more
     *     The more
     */
    public void setMore(boolean more) {
        this.more = more;
    }

    /**
     * 
     * @return
     *     The count
     */
    public int getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 
     * @return
     *     The hits
     */
    public List<Hit> getHits() {
        return hits;
    }

    /**
     * 
     * @param hits
     *     The hits
     */
    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }

}
