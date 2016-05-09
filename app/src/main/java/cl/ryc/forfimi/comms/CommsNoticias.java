package cl.ryc.forfimi.comms;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.SimpleAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cl.ryc.forfimi.Noticias;
import cl.ryc.forfimi.entities.Noticia;
import cl.ryc.forfimi.error.ErrorHandler;
import cl.ryc.forfimi.helpers.DataFromServer;
import cl.ryc.forfimi.helpers.Parametros;
import cl.ryc.forfimi.view.ViewNoticias;

/**
 * Created by RyC on 28/04/2016.
 */
public class CommsNoticias extends AsyncTask {



    Context c;
    ProgressDialog pd;
    Parametros parametro;
    JSONArray result;

    List<Noticia> Listado;

    ErrorHandler eh;

    SimpleAdapter sa;


    public CommsNoticias (Context con, ProgressDialog p)
    {
        this.c=con;
        this.pd=p;
    }

    @Override
    protected Object doInBackground(Object[] params) {


        DataFromServer dfs= new DataFromServer();
        parametro= Parametros.getInstance();

        Listado= new ArrayList<Noticia>();
        String URL=parametro.getHOSTURL()+parametro.getPORT()+parametro.getAPI()+parametro.getServicioNoticias();

        /* ARMAMOS EL JSON



         */



        /*ARMAMOS EL ADAPTER



         */


        ViewNoticias vn= new ViewNoticias(this.c);

        sa=vn.toListView(Listado);

        return null;
    }

    @Override
    protected void onPreExecute()
    {
        // TODO Auto-generated method stub
        super.onPreExecute();

        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.show();

    }

    protected void onPostExecute(Object result)
    {
        // TODO Auto-generated method stub
        super.onPostExecute(result);

        int Error=0;

        Noticias.backOnGetNoticias(sa);
        pd.dismiss();
    }




}
