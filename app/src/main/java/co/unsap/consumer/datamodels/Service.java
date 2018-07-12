package co.unsap.consumer.datamodels;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by sriven on 5/6/2016.
 */
public class Service implements Serializable {
    public  String title,title_ar,id;
    boolean selected = false;

    public Service(String title, String title_ar, String id){
        this.title=title;
        this.title_ar=title_ar;
        this.id = id;
        selected=false;
    }

    Service(String title, String title_ar, String id, boolean selected){
        this.title=title;
        this.title_ar=title_ar;
        this.selected = selected;
        this.id = id;
    }

    public String getTitle(Context context) {
      //  if(Settings.get_user_language(context).equals("ar"))
        //    return title_ar;
        //else
            return  title;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
