package cl.ryc.forfimi.comms;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import cl.ryc.forfimi.Login;
import cl.ryc.forfimi.helpers.GlobalPersist;

/**
 * Created by RyC on 18/04/2016.
 */
public class GetIDCloudMessage extends AsyncTask {


    GoogleCloudMessaging gcm;
    String ID;
    String SENDER_ID="1088673017505";
    Context c;
    public GetIDCloudMessage(Context con)
    {
        this.c=con;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        gcm = GoogleCloudMessaging.getInstance(this.c);
        try {
            this.ID= gcm.register(SENDER_ID);
            GlobalPersist.setGlobalPersist("IDGCM",this.ID);
            System.out.println("MyID GCM="+this.ID);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: "+e.getMessage());

        }
        return null;
    }


    protected void onPostExecute(Object result)
    {
        // TODO Auto-generated method stub
        super.onPostExecute(result);

        Login.BackAffterGCM();



    }
}
