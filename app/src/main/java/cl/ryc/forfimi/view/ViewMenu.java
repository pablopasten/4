package cl.ryc.forfimi.view;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cl.ryc.forfimi.R;

/**
 * Created by RyC on 08/04/2016.
 */
public class ViewMenu {


    Context c;
    public ViewMenu(Context con){

        this.c=con;

    }

    public SimpleAdapter toListView ()
    {
        System.out.println("Armando Lista");
        System.out.println("Armando Lista");

        SimpleAdapter sa=null;

        List<HashMap<String, Object>> Values = new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object> value,valu,val,vals,va,vls,v;
        int [] controles = new int []{R.id.texto,R.id.fondo};
        String [] keys = new String [] {"sKeyTexto","sKeyFondo"};


        value = new HashMap<String,Object>();


        System.out.println("Armando Lista");
        System.out.println("Armando Lista");
        System.out.println("Armando Lista");
        System.out.println("Armando Lista");
        System.out.println("Armando Lista");
        System.out.println("Armando Lista");
        System.out.println("Armando Lista");
        System.out.println("Armando Lista");
        System.out.println("Armando Lista");
        System.out.println("Armando Lista");
        System.out.println("Armando Lista");
        System.out.println("Armando Lista");
        System.out.println("Armando Lista");

        value.put("sKeyTexto", "Menciones");
        value.put("sKeyFondo", R.drawable.ic_forum_white_36dp);
        Values.add(value);

        valu = new HashMap<String,Object>();
        valu.put("sKeyTexto", "Perfil");
        valu.put("sKeyFondo", R.drawable.ic_face_white_36dp);
        Values.add(valu);

        val = new HashMap<String,Object>();
        val.put("sKeyTexto", "Historial");
        val.put("sKeyFondo", R.drawable.ic_restore_white_36dp);
        Values.add(val);

        vls = new HashMap<String,Object>();
        vls.put("sKeyTexto", "Noticias");
        vls.put("sKeyFondo", R.drawable.ic_import_contacts_white_36dp);
        Values.add(vls);
        System.out.println("Armando Lista");
        v = new HashMap<String,Object>();
        v.put("sKeyTexto", "Contacto");
        v.put("sKeyFondo", R.drawable.ic_mail_white_36dp);
        Values.add(v);



        sa=new SimpleAdapter(c, Values, R.layout.gridview_layout, keys, controles);

        System.out.println("Saliendo lista!");
        return sa;
    }

}
