package cl.ryc.forfimi.comms;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cl.ryc.forfimi.Messages;
import cl.ryc.forfimi.error.ErrorHandler;
import cl.ryc.forfimi.helpers.DataFromServer;
import cl.ryc.forfimi.helpers.GlobalPersist;
import cl.ryc.forfimi.helpers.Parametros;

/**
 * Created by RyC on 10/05/2016.
 */
public class CommsChart extends AsyncTask {

    Number[] series1Numbers;
    Parametros parametro;
    ErrorHandler eh;
    JSONArray result;
    Context c;
    Messages Act;


    public CommsChart(Context con,Messages A)
    {

        this.c=con;
        Act=A;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        DataFromServer dfs= new DataFromServer();
        parametro= Parametros.getInstance();
        GlobalPersist gp= GlobalPersist.getInstance(c);
        String URL=parametro.getHOSTURL()+parametro.getPORT()+parametro.getAPI()+parametro.getChartService()+"id_usuario="+gp.getGlobalPersist("IdUsuario");
        System.out.println("Charts: "+URL);
        result=dfs.GetDataFromServer(URL,1);



        if(result!=null && eh.getLastError()==0) {

            series1Numbers= new Number[7];

            for(int cont=0;cont<7;cont++) {
                JSONObject json_data = null;

                try {
                    json_data = this.result.getJSONObject(cont);
                    System.out.println("Posicion "+cont+"Dato= "+json_data.getInt("dato"));
                    series1Numbers[cont]=json_data.getInt("dato");

                }
                catch (JSONException e)
                {
                    // TODO Auto-generated catch block
                    // ViewsHelper.setStatusComms(JsonProblem);
                    e.printStackTrace();
                }
            }

        }
        return null;
    }


    protected void onPostExecute(Object result)
    {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        System.out.println("Voy a Imprimir el resultado");
        if(series1Numbers!=null) {
            for (int cont = 0; cont < 7; cont++) {
                System.out.println(series1Numbers[cont]);
            }


        }
        Act.backfromGetData(series1Numbers);
    }
}
