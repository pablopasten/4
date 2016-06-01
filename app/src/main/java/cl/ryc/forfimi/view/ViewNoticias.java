package cl.ryc.forfimi.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cl.ryc.forfimi.MyAdapter;
import cl.ryc.forfimi.R;
import cl.ryc.forfimi.entities.Noticia;

/**
 * Created by RyC on 28/04/2016.
 */
public class ViewNoticias {

    List<Noticia> n;
    Context c;
    public ViewNoticias(Context con){

        this.c=con;

    }


    public MyAdapter toListView(List<Noticia> lista)
    {
        MyAdapter sa=null;

        this.n=lista;

        System.out.println("hola");
        List<HashMap<String, Object>> Values = new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object> value,valu,val,vals,va,vls,v;
        int [] controles = new int []{R.id.txtTitulo,R.id.txtContenido,R.id.imgNoticias};
        String [] keys = new String [] {"sKeyTitulo","sKeyContenido","skeyImagen"};

        System.out.println("Armando Lista");
        System.out.println("Armando Lista");


        for(int cont=0; cont<n.size();cont++)
        {
            value = new HashMap<String,Object>();

            value.put("sKeyTitulo",n.get(cont).getTitulo());
            value.put("sKeyContenido",n.get(cont).getContenido());
            value.put("skeyImagen",n.get(cont).getImagen());

            Values.add(value);

        }
       /* value = new HashMap<String,Object>();

        //value.put("sKeyTitulo", this.n.get(cont).getTitulo());
        //value.put("sKeyContenido",this.n.get(cont).getContenido());

        value.put("sKeyTitulo", "Titulo de prueba1");
        value.put("sKeyContenido", "Este es un contenido de prueba");


        System.out.println("ACAAA jdksladjsakld");
        String u="http://yucatan.com.mx/wp-content/uploads/2015/09/asteroides.jpg";


        value.put("skeyImagen", u);


        Values.add(value);

        value.put("sKeyTitulo", "Titulo de prueba2");
        value.put("sKeyContenido", "Este es un contenido de prueba");


        System.out.println("ACAAA jdksladjsakld");
        String vs="http://yucatan.com.mx/wp-content/uploads/2015/09/asteroides.jpg";


        value.put("skeyImagen",vs);


        Values.add(value);
        value.put("sKeyTitulo", "Titulo de prueba3");
        value.put("sKeyContenido", "Este es un contenido de prueba");


        System.out.println("ACAAA jdksladjsakld");
        String vss="http://ichef.bbci.co.uk/onesport/cps/480/cpsprodpb/90A5/production/_88592073_sebvettelgetty.jpg";


        value.put("skeyImagen",vss);


        Values.add(value);
        System.out.println("aaaaQUIIIIIIII");*/

        sa=new MyAdapter(c, Values, R.layout.noti_layout, keys, controles);


        return sa;
    }


    public static Bitmap getImageFromUrl(String Url) {
        URL imageURL = null;
        try {
            imageURL = new URL(Url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


        return bitmap;
    }


    public Noticia getNoticiaByPosition(int Pos){

        return this.n.get(Pos);
    }
}
