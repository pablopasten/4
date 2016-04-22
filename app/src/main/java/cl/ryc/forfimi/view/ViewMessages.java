package cl.ryc.forfimi.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cl.ryc.forfimi.R;
import cl.ryc.forfimi.comms.CommsMessages;
import cl.ryc.forfimi.entities.Msg;

/**
 * Created by RyC on 11/04/2016.
 */
public class ViewMessages {


    Context c;
    ProgressDialog pd;
    List<Msg> Positivos,Negativos;
    CommsMessages cm;
    public ViewMessages(Context con, ProgressDialog p,String IdUsuario){

        this.c=con;
        this.pd=p;

        cm= new CommsMessages(con,p,IdUsuario);
        cm.execute("");

    }


    public SimpleAdapter toListViewNegativos (List<Msg> n)
    {

        Negativos=n;
        SimpleAdapter sa=null;

        List<HashMap<String, Object>> Values = new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object> value,valu,val,vals,va,vls,v;
        int [] controles = new int []{R.id.txtContenido,R.id.iconoRed};
        String [] keys = new String [] {"sKeyTexto","sKeyImagen"};
        


        for(int cont=0; cont<n.size();cont++)
        {
            value = new HashMap<String,Object>();





            value.put("sKeyTexto", n.get(cont).getComentario());
           if(n.get(cont).getId_red_social()==1) {
                value.put("sKeyImagen", R.drawable.facebook);
            }
            else if (n.get(cont).getId_red_social()==2){
            value.put("sKeyImagen", R.drawable.twitter);
            }
            Values.add(value);

        }


        sa=new SimpleAdapter(c, Values, R.layout.controlesnegativos, keys, controles);
        return sa;
    }


    public SimpleAdapter toListViewPositivos (List<Msg> p)
    {

        Positivos=p;
        SimpleAdapter sa=null;

        /*List<HashMap<String, Object>> Values = new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object> value,valu,val,vals,va,vls,v;
        int [] controles = new int []{R.id.txtContenido ,R.id.iconoRed};
        String [] keys = new String [] {"sKeyTexto","sKeyImagen"};


        for(int cont=0; cont<p.size();cont++)
        {
            value = new HashMap<String,Object>();





            value.put("sKeyTexto", p.get(cont).getComentario());
            if(p.get(cont).getId_red_social()==1) {
                value.put("sKeyImagen", R.drawable.facebook);
            }
            else if (p.get(cont).getId_red_social()==2){
                value.put("sKeyImagen", R.drawable.twitter);
            }
            Values.add(value);

        }

        sa=new SimpleAdapter(c, Values, R.layout.controlespositivos, keys, controles);*/
        return sa;
    }

}
