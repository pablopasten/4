package cl.ryc.forfimi.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.SimpleAdapter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cl.ryc.forfimi.R;
import cl.ryc.forfimi.comms.CommsHistorial;
import cl.ryc.forfimi.comms.CommsMessages;
import cl.ryc.forfimi.entities.Msg;

/**
 * Created by RyC on 10/05/2016.
 */
public class ViewHistorial {

    Context c;
    ProgressDialog pd;
    List<Msg> Negativos;
    CommsHistorial ch;

    public ViewHistorial (Context con, ProgressDialog p,String IdUsuario){

        this.c=con;
        this.pd=p;

        ch= new CommsHistorial(con,p,IdUsuario);
        ch.execute("");

    }


    public SimpleAdapter toListViewNegativos (List<Msg> n)
    {

        Negativos=n;
        SimpleAdapter sa=null;
        System.out.println("jojo");
        List<HashMap<String, Object>> Values = new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object> value,valu,val,vals,va,vls,v;
        int [] controles = new int []{R.id.txtContenido,R.id.iconoRed,R.id.iconoEstado,R.id.txtFecha};
        String [] keys = new String [] {"sKeyTexto","sKeyImagen","skeyTipo","skeyFecha"};


        System.out.println("hola");
        for(int cont=0; cont<n.size();cont++)
        {
            value = new HashMap<String,Object>();



            
            byte ptext[] = new byte[0];
            try {
                ptext = n.get(cont).getComentario().getBytes("ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                String coment = new String(ptext, "UTF-8");
                value.put("sKeyTexto",coment);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if(n.get(cont).getId_red_social()==3) {
                value.put("sKeyImagen", R.drawable.instas);
            }
            else if (n.get(cont).getId_red_social()==2){
                value.put("sKeyImagen", R.drawable.twitter);
            }
            else if(n.get(cont).getId_red_social()==4){
                value.put("sKeyImagen", R.drawable.googles);
            }


            if(n.get(cont).getTipoComentario().equals("negativo"))
            {
                value.put("skeyTipo",R.drawable.ic_sentiment_dissatisfied_black_18dp);
            }
            else
            {
                value.put("skeyTipo",R.drawable.ic_sentiment_satisfied_black_18dp);
            }

            value.put("skeyFecha",n.get(cont).getFecha());
            Values.add(value);

        }


        sa=new SimpleAdapter(c, Values, R.layout.controlesnegativos, keys, controles);
        return sa;
    }

}
