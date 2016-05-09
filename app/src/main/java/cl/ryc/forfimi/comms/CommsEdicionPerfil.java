package cl.ryc.forfimi.comms;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cl.ryc.forfimi.entities.Usuario;
import cl.ryc.forfimi.error.ErrorHandler;
import cl.ryc.forfimi.helpers.DataFromServer;
import cl.ryc.forfimi.helpers.GlobalPersist;
import cl.ryc.forfimi.helpers.Parametros;

/**
 * Created by RyC on 21/04/2016.
 */
public class CommsEdicionPerfil extends AsyncTask {

    Context c;
    ProgressDialog pd;

    String PSW,EAct,EAnt,TWT,INSTA;
    String[]Words;
    GlobalPersist gp;
    Parametros params;
    ErrorHandler eh;
    JSONArray result;
    boolean ErrorGeneral;


    public CommsEdicionPerfil(Context con, ProgressDialog p, String Password,String EmpresaActual, String EmpresaAnterior,
                              String Twitter, String Instagram, String[]Palabras){

            this.c=con;
            this.pd=p;
            this.PSW=Password;
            this.EAct=EmpresaActual;
            this.EAnt=EmpresaAnterior;
            this.TWT=Twitter;
            this.INSTA=Instagram;
            this.Words=Palabras;

            gp=GlobalPersist.getInstance(this.c);

            params=Parametros.getInstance();

            //Asumo termina ok

            ErrorGeneral=true;

    }



    @Override
    protected Object doInBackground(Object[] params) {


        if(EditInfoUser()){

            if(SendWordsToServer(Words)) {

                EditRRSSUSer();
            }
            else{
                ErrorGeneral=false;
            }
        }
        else{
            ErrorGeneral=false;
        }
        return null;
    }


    public boolean SendWordsToServer(String [] arr)
    {
        String Url;
        int error=0;
        for ( String ss : arr) {
            //Acà vamos a Enviar las palabras al Servidor
            Url="";
            Url=this.params.getHOSTURL()+this.params.getPORT()+this.params.getAPI()+this.params.getServicioEnviaPalabras()+
            "id_usuario="+this.gp.getGlobalPersist("IdUsuario")+";palabra="+ss;

            DataFromServer dfs= new DataFromServer();

            System.out.println(Url);
            result=null;
            result=dfs.GetDataFromServer(Url,0);

            for (int cont = 0; cont < result.length(); cont++) {

                JSONObject json_data = null;

                try {

                    json_data = this.result.getJSONObject(cont);

                    if(json_data.getInt("cod_salida")==1)
                    {
                       error=error+1;
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }

        }

        if(error>1)
        {
            return false;
        }
        else
        {
            return true; // todas se enviaron ok
        }
    }

    public boolean EditInfoUser(){

        //Acà Vamos a Enviar toda la info del usuario;
        boolean e=true; // asumo que no hay error;
        String Url=this.params.getHOSTURL()+this.params.getPORT()+this.params.getAPI()+this.params.getServicioModificarUsuario()+
                "id_usuario="+this.gp.getGlobalPersist("IdUsuario")+";pass="+this.PSW+";emp1="+this.EAct+";emp2="+this.EAnt;

        DataFromServer dfs= new DataFromServer();

        System.out.println(Url);
        result=dfs.GetDataFromServer(Url,1);

        for (int cont = 0; cont < result.length(); cont++) {

            JSONObject json_data = null;

            try {

                json_data = this.result.getJSONObject(cont);

                if(json_data.getInt("cod_salida")==1)
                {
                    e=false;
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }


        return  e;
    }

    public void EditRRSSUSer(){

        //Acà Vamos a enviar las redes sociales del usuario
        if(!this.INSTA.equals("")){
            String Url=this.params.getHOSTURL()+this.params.getPORT()+this.params.getAPI()+this.params.getEditRSS()+"usuario="+this.INSTA+
                    ";id_usuario="+this.gp.getGlobalPersist("IdUsuario")+";id_red=3";

            DataFromServer dfs= new DataFromServer();

            System.out.println(Url);
            result=dfs.GetDataFromServer(Url,1);
        }


        if(!this.TWT.equals("")){
            String Url=this.params.getHOSTURL()+this.params.getPORT()+this.params.getAPI()+this.params.getEditRSS()+"usuario="+this.TWT+
                    ";id_usuario="+this.gp.getGlobalPersist("IdUsuario")+";id_red=2";

            DataFromServer dfs= new DataFromServer();

            System.out.println(Url);
            result=dfs.GetDataFromServer(Url,1);
        }
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

    @Override
    protected void onPostExecute(Object result)
    {
        // TODO Auto-generated method stub
        super.onPostExecute(result);


        pd.dismiss();
    }
}
