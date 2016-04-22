package cl.ryc.forfimi.comms;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import cl.ryc.forfimi.entities.Usuario;
import cl.ryc.forfimi.helpers.GlobalPersist;

/**
 * Created by RyC on 21/04/2016.
 */
public class CommsEdicionPerfil extends AsyncTask {

    Context c;
    ProgressDialog pd;

    String PSW,EAct,EAnt,TWT,INSTA;
    String[]Words;
    GlobalPersist gp;


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

    }



    @Override
    protected Object doInBackground(Object[] params) {


        EditInfoUser();
        SendWordsToServer(Words);
        EditRRSSUSer();
        return null;
    }


    public void SendWordsToServer(String [] arr)
    {
        for ( String ss : arr) {
            //Acà vamos a Enviar las palabras al Servidor
            System.out.println(ss);
        }
    }

    public void EditInfoUser(){

        //Acà Vamos a Enviar toda la info del usuario;
    }

    public void EditRRSSUSer(){

        //Acà Vamos a enviar las redes sociales del usuario
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
