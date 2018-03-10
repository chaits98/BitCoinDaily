package com.example.chait.bitcoindaily;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chait on 13-02-2018.
 */

public class currencyData {

    String mPrice;

    public static currencyData fromJSON(JSONObject jsonObject){
        try {
            currencyData data = new currencyData();
            data.mPrice = jsonObject.getString("last");
            return data;
        }
        catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
