package co.unsap.consumer.datamodels;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by mac on 7/23/18.
 */

public class ServiceProvider implements Serializable {
//07-23 14:01:44.298 30486-30486/co.unsap.consumer E/userProfile:
// {"lastName":"Provider ","defaultComment":"","phone":"7382223117","filePublicId":"profilePicture\/1531390690294_imageName_m5izeb",
// "imageUrl":"https:\/\/res.cloudinary.com\/hlkdbdswu\/image\/upload\/v1531390690\/profilePicture\/1531390690294_imageName_m5izeb.jpg",
// "countryCode":"91","commentUpdatedAt":"2018-07-03T16:05:24.485Z","serviceType":"","endTime":"","avgRating":"","noOfTimesHired":""
// ,"updatedAt":"2018-07-18T09:59:13.112Z","startTime":"","favoriteCount":"","email":"prudhvi@yellowsoft.in","createdAt":"2018-06-02T14:08:36.359Z",
// "accountStatus":"Created","deviceToken":"fd598085db6dd8eafd41645c297bbaa95725151348f565dce7d52702b9c75f9a","userId":"pru1527948516359",
// "role":"provider","fullName":"Prudhvi Raj Provider ","firstName":"Prudhvi Raj","noOfTimesRated":"",
// "consumerDeviceToken":"1859a76e71053ff53283adb203f90f1e3bec27dd1dcf55e39373824184588441"}

   public String lastName,defaultComment,phone,filePublicId,imageUrl,countryCode,commentUpdatedAt,serviceType,endTime,avgRating,noOfTimesHired,updatedAt,startTime,
            favoriteCount,email,createdAt,accountStatus,userId,role,fullName,firstName,noOfTimesRated,isFavorite;
    public  String serviceName,serviceAddress;
   // public  JSONArray providerServices;
   // public  JSONArray provideraddresses;
    public  String providercharge;

    public ServiceProvider(JSONObject jsonObject){


        try{


            fullName = jsonObject.getJSONObject("userProfile").getString("fullName");
            defaultComment = jsonObject.getJSONObject("userProfile").getString("defaultComment");
            phone = jsonObject.getJSONObject("userProfile").getString("phone");
            imageUrl = jsonObject.getJSONObject("userProfile").getString("imageUrl");
            endTime = jsonObject.getJSONObject("userProfile").getString("endTime");
            startTime = jsonObject.getJSONObject("userProfile").getString("startTime");
            noOfTimesHired = jsonObject.getJSONObject("userProfile").getString("noOfTimesHired");
            avgRating = jsonObject.getJSONObject("userProfile").getString("avgRating");
            favoriteCount = jsonObject.getJSONObject("userProfile").getString("favoriteCount");
            noOfTimesRated = jsonObject.getJSONObject("userProfile").getString("noOfTimesRated");
            userId = jsonObject.getJSONObject("userProfile").getString("userId");


            if(jsonObject.getJSONArray("providerServicesInfo").length()>0){

                serviceName = jsonObject.getJSONArray("providerServicesInfo").getJSONObject(0).getString("serviceName");
                providercharge = jsonObject.getJSONArray("providerServicesInfo").getJSONObject(0).getString("chargePerHour");


            }
            if(jsonObject.getJSONArray("userAddresses").length()>0) {

                serviceAddress = jsonObject.getJSONArray("userAddresses").getJSONObject(0).getString("address1");

            }

           // providerServices = jsonObject.getJSONArray("providerServicesInfo");
           // provideraddresses = jsonObject.getJSONArray("userAddresses");

        }catch (Exception ex){

            ex.printStackTrace();
        }

    }


}