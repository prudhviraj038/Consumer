package co.unsap.consumer.datamodels;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by mac on 7/16/18.
 */

public class Proposal  implements Serializable{

   public String id,
            request_id,
            proposer_id,
            proposer_name,
            proposer_profile_pic,
            latest_comment,
            latest_comment_at,
            proposer_hired="0",
            proposer_rating="0",
            proposer_reviews="0",
            proposer_fav_count="0";





   public  Proposal(JSONObject jsonObject,String source) throws JSONException {




        if(source.equals("service")){
            id = jsonObject.getString("_id");
            request_id = jsonObject.getString("requestId");
            proposer_id = jsonObject.getString("proposedBy");
            proposer_name = jsonObject.getString("proposedByName");
            proposer_profile_pic = jsonObject.getString("proposedByProfilePic");

        }else {


            id = jsonObject.getJSONObject("proposal").getString("_id");
            request_id = jsonObject.getJSONObject("proposal").getString("requestId");
            proposer_id = jsonObject.getJSONObject("proposal").getString("proposedBy");
            proposer_profile_pic = jsonObject.getJSONObject("proposal").getString("proposedByProfilePic");

            proposer_name = jsonObject.getJSONObject("proposedByProfile").getString("fullName");


 //               proposer_hired = jsonObject.getJSONObject("proposedByProfile").getString("noOfTimesHired");
//                proposer_rating = jsonObject.getJSONObject("proposedByProfile").getString("avgRating");
//                proposer_reviews = jsonObject.getJSONObject("proposedByProfile").getString("noOfTimesRated");
//                proposer_fav_count = jsonObject.getJSONObject("proposedByProfile").getString("favoriteCount");
//
//
//
//               if(TextUtils.isEmpty(proposer_hired)){
//
//                   Log.e("hired","yes");
//                   proposer_hired = "120";
//               }else{
//                   Log.e("hired","no");
//
//               }






            latest_comment = jsonObject.getJSONObject("latestComment").getString("proposalComment");
            latest_comment_at = jsonObject.getJSONObject("latestComment").getString("updated_at");
        }
    }


}
