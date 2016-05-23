package cl.ryc.forfimi.comms;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cl.ryc.forfimi.Perfil;
import cl.ryc.forfimi.entities.RedSocial;
import cl.ryc.forfimi.entities.Usuario;
import cl.ryc.forfimi.error.ErrorHandler;
import cl.ryc.forfimi.helpers.DataFromServer;
import cl.ryc.forfimi.helpers.GlobalPersist;
import cl.ryc.forfimi.helpers.Parametros;

/**
 * Created by RyC on 21/04/2016.
 */
public class CommsPerfil extends AsyncTask {

    Context c;
    ProgressDialog pd;
    Parametros params;
    GlobalPersist gp;
    ErrorHandler eh;
    JSONArray result;
    Usuario user;


    public CommsPerfil(Context con,ProgressDialog p ){

        this.c=con;
        this.pd=p;
        params=Parametros.getInstance();
        gp=GlobalPersist.getInstance(con);
        eh= ErrorHandler.getInstance();

    }


    @Override
    protected Object doInBackground(Object[] params) {

        String URL=this.params.getHOSTURL()+this.params.getPORT()+this.params.getAPI()+this.params.getServicioUsuario()+"id_usuario="+this.gp.getGlobalPersist("IdUsuario");
        DataFromServer dfs= new DataFromServer();

        System.out.println(URL);
        result=dfs.GetDataFromServer(URL,1);

        if(result!=null && eh.getLastError()==0) {
            //Vamos a sacar los resultados del json

            for (int cont = 0; cont < result.length(); cont++) {

                JSONObject json_data = null;

                try {

                    json_data = this.result.getJSONObject(cont);

                    user= new Usuario();
                    user.setNombre1(json_data.getString("nom1"));
                    user.setNombre2(json_data.getString("nom2"));
                    user.setApellido1(json_data.getString("apell1"));
                    user.setApellido2(json_data.getString("apell2"));
                    user.setEmpresa1(json_data.getString("emp1").replace("_"," "));
                    user.setEmpresa2(json_data.getString("emp2").replace("_"," "));
                    user.setPassword(json_data.getString("contraseÃ±a"));

                    JSONArray redes= json_data.getJSONArray("rs");

                    for (int conta=0;conta<redes.length();conta++){
                        JSONObject red= redes.getJSONObject(conta);

                        RedSocial rs= new RedSocial();

                        rs.setIdRedSocial(red.getInt("id_red_social"));
                        System.out.println(red.getString("n_usuario_red"));
                                rs.setNombreUsuarioRedSocial(red.getString("n_usuario_red"));

                        user.setRedes(rs);


                    }


                    JSONArray words= json_data.getJSONArray("palabras");

                    for (int conta=0;conta<words.length();conta++){
                        JSONObject word= words.getJSONObject(conta);
                        System.out.println(word.getString("palabra"));
                        user.setPalabras(word.getString("palabra"));


                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
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

    @Override
    protected void onPostExecute(Object result)
    {
        // TODO Auto-generated method stub
        super.onPostExecute(result);

        Perfil.backFromGetDataUser(user);
        pd.dismiss();
    }
}
