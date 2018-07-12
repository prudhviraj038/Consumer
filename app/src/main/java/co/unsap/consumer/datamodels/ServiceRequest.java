package co.unsap.consumer.datamodels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceRequest  {


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
    public JSONArray images;
    public String lat;
    public String lon;
    public String address;
    public String serviceType;
    public String scheduleOn;
    public String receiverProfilePic;
    public String updatedAt;
    public JSONArray proposals;


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

            images = new JSONArray();
            proposals = new JSONArray();

            images = jsonObject.getJSONArray("requestFiles");
            proposals = jsonObject.getJSONArray("proposals");

//        self.updatedAt = [[dictionary objectForKey:@"request"] valueForKey:@"updatedAt"];
//        self.lat = [[dictionary objectForKey:@"request"] objectForKey:@"latitude"];
//        self.lon = [[dictionary objectForKey:@"request"] objectForKey:@"longitude"];
//        self.address = [[dictionary objectForKey:@"request"] objectForKey:@"address1"];
//
//        self.requestCode = [[dictionary objectForKey:@"request"] valueForKey:@"requestNumber"];
//        self.senderUserId = [[dictionary objectForKey:@"request"] valueForKey:@"senderUserId"];
//        self.requestStatus = [[dictionary objectForKey:@"request"] valueForKey:@"requestStatus"];
//        self.hasImage = [[dictionary objectForKey:@"request"] valueForKey:@"hasImage"];
//        self.subServiceId = [[dictionary objectForKey:@"request"] valueForKey:@"subServiceId"];
//        self.subServiceName = [[dictionary objectForKey:@"request"] valueForKey:@"subServiceName"];
//        self.serviceName = [[dictionary objectForKey:@"request"] valueForKey:@"serviceName"];
//        self.serviceId = [[dictionary objectForKey:@"request"] valueForKey:@"serviceId"];
//        self.receiverName = [[dictionary objectForKey:@"request"] valueForKey:@"receiverName"];
//        self.receiverName = [[NSString stringWithFormat:@"%@",self.receiverName] capitalizedString];
//
//        self.receiverProfilePic = [[dictionary objectForKey:@"request"] valueForKey:@"receiverProfilePic"];
//
//        self.receiverUserId = [[dictionary objectForKey:@"request"] valueForKey:@"receiverUserId"];
//
//        self.senderName = [[dictionary objectForKey:@"request"] valueForKey:@"senderName"];
//        self.senderName = [[NSString stringWithFormat:@"%@",self.senderName] capitalizedString];
//
//        self.message = [[dictionary objectForKey:@"request"] valueForKey:@"message"];
//        self.subject = [[dictionary objectForKey:@"request"] valueForKey:@"subject"];
//        self.serviceType = [[dictionary objectForKey:@"request"] valueForKey:@"serviceType"];
//        self.scheduleOn = [[dictionary objectForKey:@"request"] valueForKey:@"scheduleOn"];
//
//        self.images   = [dictionary objectForKey:@"requestFiles"];
//
//        self.proposals = [dictionary objectForKey:@"proposals"];



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}


