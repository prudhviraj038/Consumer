package co.unsap.consumer.datamodels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ServiceRequest implements Serializable{


    public  String _id;
    public String senderUserId;
    public String requestStatus;
    public String hasImage;
    public String subServiceId;
    public String subServiceName;
    public String serviceName;
    public String serviceId;
    public String receiverName;
    public String receiverUserId;
    public String senderName;
    public  String message;
    public String subject;
    public String requestCode;
    public  ArrayList<RequestImages> images;

    public String lat;
    public String lon;
    public String address;
    public String serviceType;
    public String scheduleOn;
    public String receiverProfilePic;
    public String updatedAt;
    public ArrayList<Proposal> proposals;


    public ServiceRequest(JSONObject jsonObject){

//        self.images = [[NSMutableArray alloc] init];
//
//        self._id = [[dictionary objectForKey:@"request"] valueForKey:@"_id"];

        try {
            _id = jsonObject.getJSONObject("request").getString("_id");
            updatedAt = jsonObject.getJSONObject("request").getString("updatedAt");
            lat = jsonObject.getJSONObject("request").getString("latitude");
            lon = jsonObject.getJSONObject("request").getString("longitude");
            address = jsonObject.getJSONObject("request").getString("address1");
            requestCode = jsonObject.getJSONObject("request").getString("requestNumber");
            senderUserId = jsonObject.getJSONObject("request").getString("senderUserId");
            requestStatus = jsonObject.getJSONObject("request").getString("requestStatus");
            hasImage = jsonObject.getJSONObject("request").getString("hasImage");
            subServiceId = jsonObject.getJSONObject("request").getString("subServiceId");
            subServiceName = jsonObject.getJSONObject("request").getString("subServiceName");
            serviceName = jsonObject.getJSONObject("request").getString("serviceName");
            serviceId = jsonObject.getJSONObject("request").getString("serviceId");
            receiverName = jsonObject.getJSONObject("request").getString("receiverName");
            receiverProfilePic = jsonObject.getJSONObject("request").getString("senderProfilePic");
            receiverUserId = jsonObject.getJSONObject("request").getString("receiverUserId");
            senderName = jsonObject.getJSONObject("request").getString("senderName");
            message = jsonObject.getJSONObject("request").getString("message");
            subject = jsonObject.getJSONObject("request").getString("subject");
            serviceType = jsonObject.getJSONObject("request").getString("serviceType");
            scheduleOn = jsonObject.getJSONObject("request").getString("scheduleOn");

            images = new ArrayList<>();
            proposals = new ArrayList<>();

            for(int i=0;i<jsonObject.getJSONArray("proposals").length();i++){

                proposals.add(new Proposal(jsonObject.getJSONArray("proposals").getJSONObject(i),"service"));
            }


            if(jsonObject.get("requestFiles").getClass()==JSONArray.class) {

                for (int i = 0; i < jsonObject.getJSONArray("requestFiles").length(); i++) {

                    images.add(new RequestImages(jsonObject.getJSONArray("requestFiles").getJSONObject(i)));
                }
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




}


