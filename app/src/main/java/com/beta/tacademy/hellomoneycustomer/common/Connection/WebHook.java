package com.beta.tacademy.hellomoneycustomer.common.Connection;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by leegunjoon on 2016. 10. 17..
 */
public class WebHook extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... messages) {
        JSONObject json = null;
        String result = null;
        Connect con = new Connect("https://hooks.slack.com/services/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");

        json = new JSONObject();

        try {
            json.put("text", messages[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        result = con.postString(con.getURL(), json);

        return null;
    }
}

// 사용법                  new WebHook().execute("쓰고싶은말",null,null);
