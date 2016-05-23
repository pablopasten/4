package cl.ryc.forfimi.helpers;

/**
 * Created by RyC on 07/04/2016.
 */
import android.content.Context;
import android.content.SharedPreferences;

public class GlobalPersist
{


    private static SharedPreferences settings;
    private Context c;

    private static GlobalPersist instance;


    private GlobalPersist(Context con)
    {
        this.c=con;
        settings=c.getSharedPreferences("GlobalPreference",0);
    }


    public static GlobalPersist getInstance(Context con)
    {
        if(instance==null)
        {
            instance=new GlobalPersist(con);
        }


        return instance;
    }



    public static void setGlobalPersist(String NameToken,String ContentVar)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(NameToken, ContentVar);

        editor.commit();
    }



    public static String getGlobalPersist(String NameToken)
    {
        return settings.getString(NameToken, "0");
    }

    public static void deleteKey(String NameToken)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(NameToken);
        editor.commit();
    }


    public static boolean VaidateExistsKey(String NameToken)
    {
        String value = settings.getString(NameToken, null);

        if (value == null) {
            return false;
        } else {
            // handle the value
            return true;
        }

    }

    public static void clearAll()
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();

    }


}

