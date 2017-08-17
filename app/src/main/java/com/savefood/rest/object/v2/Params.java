
package com.savefood.rest.object.v2;

import java.util.ArrayList;
import java.util.List;

public class Params {

    private List<Object> sane = new ArrayList<Object>();
    private List<String> to = new ArrayList<String>();
    private List<String> q = new ArrayList<String>();
    private List<String> appId = new ArrayList<String>();
    private List<String> appKey = new ArrayList<String>();
    private List<String> from = new ArrayList<String>();

    /**
     * 
     * @return
     *     The sane
     */
    public List<Object> getSane() {
        return sane;
    }

    /**
     * 
     * @param sane
     *     The sane
     */
    public void setSane(List<Object> sane) {
        this.sane = sane;
    }

    /**
     * 
     * @return
     *     The to
     */
    public List<String> getTo() {
        return to;
    }

    /**
     * 
     * @param to
     *     The to
     */
    public void setTo(List<String> to) {
        this.to = to;
    }

    /**
     * 
     * @return
     *     The q
     */
    public List<String> getQ() {
        return q;
    }

    /**
     * 
     * @param q
     *     The q
     */
    public void setQ(List<String> q) {
        this.q = q;
    }

    /**
     * 
     * @return
     *     The appId
     */
    public List<String> getAppId() {
        return appId;
    }

    /**
     * 
     * @param appId
     *     The app_id
     */
    public void setAppId(List<String> appId) {
        this.appId = appId;
    }

    /**
     * 
     * @return
     *     The appKey
     */
    public List<String> getAppKey() {
        return appKey;
    }

    /**
     * 
     * @param appKey
     *     The app_key
     */
    public void setAppKey(List<String> appKey) {
        this.appKey = appKey;
    }

    /**
     * 
     * @return
     *     The from
     */
    public List<String> getFrom() {
        return from;
    }

    /**
     * 
     * @param from
     *     The from
     */
    public void setFrom(List<String> from) {
        this.from = from;
    }

}
