package cl.ryc.forfimi.comms;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    List<Noticia> Listado;

    ErrorHandler eh;

    SimpleAdapter sa;

    JSONArray result;


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


        result=dfs.GetDataFromServer(URL,1);

        if(result!=null && eh.getLastError()==0) {

                /* ARMAMOS EL JSON*/
            Listado=new ArrayList<Noticia>();

            for(int cont=0;cont<result.length();cont++) {
                JSONObject json_data = null;

                try
                {
                    json_data = this.result.getJSONObject(cont);

                    if(json_data.getInt("cod_salida")==0) {
                        if(json_data.getInt("tipo_noticia")==1){ //===> Corresponde a una noticia
                            Noticia n= new Noticia();

                            n.setTitulo(json_data.getString("titulo"));
                            n.setContenido(json_data.getString("contenido"));
                            n.setImagen(json_data.getString("foto"));

                            Listado.add(n);
                        }
                    }

                    //ViewsHelper.setStatusComms(NoError);

                }
                catch (JSONException e)
                {
                    // TODO Auto-generated catch block
                    // ViewsHelper.setStatusComms(JsonProblem);
                    e.printStackTrace();
                }
            }


                /*ARMAMOS EL ADAPTER */

            ViewNoticias vn = new ViewNoticias(this.c);

            sa = vn.toListView(Listado);
        }

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
