package co.unsap.consumer.FontUtilities;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by mac on 6/27/18.
 */

public class TypeFactory {

    private String A_BOLD= "fonts/Roboto-Bold.ttf";
    private String A_LIGHT="fonts/Roboto-Light.ttf";
    private String A_REGULAR= "fonts/Roboto-Regular.ttf";
    private String O_ITALIC= "fonts/Roboto-Italic.ttf";
    private String O_REGULAR="fonts/Roboto-Medium.ttf";

    Typeface ambleBold;
    Typeface ambleLight;
    Typeface ambleRegular;
    Typeface ambleItalic;
    Typeface ambleMedium;

    public TypeFactory(Context context){
        ambleBold = Typeface.createFromAsset(context.getAssets(),A_BOLD);
        ambleLight = Typeface.createFromAsset(context.getAssets(),A_LIGHT);
        ambleRegular = Typeface.createFromAsset(context.getAssets(),A_REGULAR);
        ambleItalic = Typeface.createFromAsset(context.getAssets(),O_ITALIC);
        ambleMedium = Typeface.createFromAsset(context.getAssets(),O_REGULAR);
    }

}
