package cl.ryc.forfimi.comms;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cl.ryc.forfimi.Login;
import cl.ryc.forfimi.Sigin;
import cl.ryc.forfimi.error.ErrorHandler;
import cl.ryc.forfimi.helpers.DataFromServer;
import cl.ryc.forfimi.helpers.GlobalPersist;
import cl.ryc.forfimi.helpers.Parametros;

/**
 * Created by RyC on 12/04/2016.
 */
public class CommsSignIn extends AsyncTask{

    ProgressDialog pd;
    Context c;

    String Nombre1,Nombre2,Apellido1,Apellido2,Mail,Password,Twitter;

    JSONArray result;
    Parametros parametro;
    ErrorHandler eh;
    int Proviene;
    GlobalPersist gp;
    String des_salida;


    int Error;

    public CommsSignIn(ProgressDialog p, Context con, String N1,String N2, String A1,
    String A2,String M, String PSW,String TWT, int prov){

        eh= ErrorHandler.getInstance();
        this.pd=p;
        this.c=con;
        this.Nombre1=N1;
        this.Nombre2=N2;
        this.Apellido1=A1;
        this.Apellido2=A2;
        this.Mail=M;
        this.Password=PSW;
        this.Twitter=TWT;
        this.Proviene=prov;
        gp=GlobalPersist.getInstance(con);

        if(Nombre2.equals(""))
        {
            Nombre2="_";

        }

        if(Apellido2.equals(""))
        {
            Apellido2="_";
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {


        DataFromServer dfs= new DataFromServer();
        parametro= Parametros.getInstance();

        String URL=parametro.getHOSTURL()+parametro.getPORT()+parametro.getAPI()+parametro.getServicioCreaUSuario()+"nombre1="+Nombre1.toString()+";nombre2="+Nombre2.toString()+
                ";apellido1="+Apellido1.toString()+";apellido2="+Apellido2.toString()+";correo="+Mail.toString()+";contraseña="+Password.toString()+
                ";twitter="+Twitter.toString()+";keyMovil="+gp.getGlobalPersist("IDGCM");

        System.out.println(URL);
        result=dfs.GetDataFromServer(URL, 1);


        if(result!=null && eh.getLastError()==0)
        {
            //Vamos a sacar los resultados del json

            for(int cont=0;cont<result.length();cont++)
            {
                JSONObject json_data=null;
                try
                {
                    json_data = this.result.getJSONObject(cont);

                    Error=json_data.getInt("cod_salida");
                    des_salida=json_data.getString("des_salida");





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

        if(Proviene==1) {
            Sigin.onCreateUserAsyncBack(Error);
        }
        else{
            if(Error==0) {

                LoginComms cl = new LoginComms(c, pd, Mail.toString(), Password.toString(), 1,0);

                cl.execute("");
            }
            else{
                if(des_salida.equals("ya existe!!") && this.Proviene==0)
                {
                    LoginComms cl = new LoginComms(c, pd, Mail.toString(), Password.toString(), 0,1);

                    cl.execute("");
                }
                else {
                    Login.ErrorOnCreateUserWithFacebook();
                }
            }

        }
        pd.dismiss();
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



}
