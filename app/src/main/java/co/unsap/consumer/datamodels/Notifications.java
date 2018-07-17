package co.unsap.consumer.datamodels;

import android.app.Notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sriven on 7/13/2018.
 */

public class Notifications {
	public String notify_message;
	public String notify_sender;

	public Notifications(JSONObject jsonObject){

		try {
			notify_message = jsonObject.getString("_id");
			notify_sender = jsonObject.getString("notificationMessage");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
