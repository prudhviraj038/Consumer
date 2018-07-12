package co.unsap.consumer;

/**
 * Created by mac on 6/26/18.
 */

public class Constants {


    public static final String BASE_URL = "https://u-snap.herokuapp.com/api/";


    public static String capitalizeString(String string){

        StringBuffer res = new StringBuffer();

        String[] strArr = string.split(" ");
        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            str = new String(stringArray);

            res.append(str).append(" ");
        }

        return res.toString();
    }


}
