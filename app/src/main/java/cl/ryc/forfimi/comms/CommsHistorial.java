package cl.ryc.forfimi.comms;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cl.ryc.forfimi.Historial;
import cl.ryc.forfimi.entities.Msg;
import cl.ryc.forfimi.error.ErrorHandler;
import cl.ryc.forfimi.helpers.DataFromServer;
import cl.ryc.forfimi.helpers.Parametros;

/**
 * Created by RyC on 10/05/2016.
 */
public class CommsHistorial extends AsyncTask {


    Context c;
    ProgressDialog pd;

    JSONArray result1;
    List<Msg> Negativos;

    Parametros parametro;
    String URL1;
    String Usuario;
    ErrorHandler eh;

    public CommsHistorial(Context con, ProgressDialog p, String idUsuario) {

        pd = p;
        c = con;
        this.Usuario = idUsuario;
    }


    @Override
    protected Object doInBackground(Object[] params) {

        DataFromServer dfs = new DataFromServer();
        parametro = Parametros.getInstance();
        Negativos = new ArrayList<Msg>();

        URL1 = parametro.getHOSTURL() + parametro.getPORT() + parametro.getAPI() + parametro.getGetMensajesHistoricos() + ";id_usuario=" + this.Usuario;
        System.out.println(URL1);
        result1 = dfs.GetDataFromServer(URL1, 1);

        if (result1 != null && eh.getLastError() == 0) {
            //Vamos a sacar los resultados del json

            for (int cont = 0; cont < result1.length(); cont++) {
                JSONObject json_data = null;
                try {
                    json_data = this.result1.getJSONObject(cont);
                    Msg m = new Msg();


                    m.setComentario(json_data.getString("comentario"));
                    m.setId_red_social(json_data.getInt("id_red_social"));
                    m.setFecha(json_data.getString("fecha"));
                    m.setTipoComentario(json_data.getString("tipo_comentario"));


                    Negativos.add(m);


                    //ViewsHelper.setStatusComms(NoError);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    // ViewsHelper.setStatusComms(JsonProblem);
                    e.printStackTrace();
                }

            }
        }

        return null;
    }


    @Override
    protected void onPreExecute() {
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

        Historial.onAsyncBack(Negativos, Error);
        pd.dismiss();
    }

}