package cl.ryc.forfimi.helpers;

/**
 * Created by RyC on 07/04/2016.
 */
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonArrayHelper
{


    private static JSONArray JsonHelper;
    private static JSONObject JObjectHelper;
    public JsonArrayHelper()
    {

    }
    public static JSONArray getJsonHelper() {
        return JsonHelper;
    }
    public static void setJsonHelper(JSONArray jsonHelper) {
        JsonHelper = jsonHelper;
    }


    public static void setToNullJsonHelper()
    {
        JsonHelper=null;
    }
    public static JSONObject getJObjectHelper() {
        return JObjectHelper;
    }
    public static void setJObjectHelper(JSONObject jObjectHelper) {
        JObjectHelper = jObjectHelper;
    }

    public static void setToNullJsonObjectHelper()
    {
        JObjectHelper=null;
    }



}

