package co.unsap.consumer.datamodels;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by mac on 7/16/18.
 */

public class RequestImages implements Serializable{

   public String image_id;
    public String image_url;


    RequestImages(JSONObject jsonObject) throws JSONException {

        image_url = jsonObject.getString("secureFileUrl");


    }


}
