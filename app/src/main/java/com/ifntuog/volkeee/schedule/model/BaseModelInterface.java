package com.ifntuog.volkeee.schedule.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by volkeee on 12/1/17.
 */

interface BaseModelInterface {
    @Override
    String toString();
    void parseJSON(JSONObject jsonObject) throws JSONException;
    JSONObject toJson() throws JSONException;
}
