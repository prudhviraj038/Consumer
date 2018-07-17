package co.unsap.consumer.datamodels;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by mac on 7/16/18.
 */

public class ProposalComments implements Serializable{

   public String id,
            commenter_name,
            commenter_profile_pic,
            latest_comment,
            latest_comment_at;





   public ProposalComments(JSONObject jsonObject) throws JSONException {
            id = jsonObject.getString("_id");
            latest_comment = jsonObject.getString("proposalComment");
            latest_comment_at = jsonObject.getString("updated_at");
            commenter_profile_pic = jsonObject.getString("proposalCommentByProfilePic");
        }
    }



