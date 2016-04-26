package cl.ryc.forfimi.comms;

import android.os.AsyncTask;

import cl.ryc.forfimi.error.ErrorHandler;
import cl.ryc.forfimi.helpers.DataFromServer;
import cl.ryc.forfimi.helpers.Parametros;

/**
 * Created by RyC on 26/04/2016.
 */
public class CommsSendPassword extends AsyncTask{

    String Email;

    public CommsSendPassword(String mail)
    {
        String Email=mail;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        Parametros parametro;

        ErrorHandler eh;

        DataFromServer dfs= new DataFromServer();
        parametro= Parametros.getInstance();

        String URL=parametro.getHOSTURL()+parametro.getPORT()+parametro.getAPI()+parametro.getServicioSendPassword()+"correo="+Email;
        dfs.GetDataFromServer(URL,1);

        return null;
    }
}
