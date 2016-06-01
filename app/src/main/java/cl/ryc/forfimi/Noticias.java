package cl.ryc.forfimi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;

import cl.ryc.forfimi.comms.CommsNoticias;
import cl.ryc.forfimi.entities.Noticia;
import cl.ryc.forfimi.view.ViewNoticias;

/**
 * Created by RyC on 26/04/2016.
 */
public class Noticias extends AppCompatActivity {

    static Context c;
    static ListView lv;
    static ViewNoticias vn;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        c=this;
        getControls();

    }


    public void getControls(){

        lv=(ListView) findViewById(R.id.listaNoticias);
        System.out.println("aaaaQUIIIIIIII");
        ProgressDialog pd= new ProgressDialog(this.c);
        CommsNoticias cn= new CommsNoticias(this.c,pd);
        cn.execute("");


    }

    public static void backOnGetNoticias(SimpleAdapter sa,ViewNoticias vns)
    {
        System.out.println("aaaaQUIIIIIIII");
        System.out.println("aaaaQUIIIIIIII");
        vn=vns;

        lv.setAdapter(sa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Noticia n= vn.getNoticiaByPosition(position);
                    Intent a = new Intent(c.getApplicationContext(),News.class );
                    a.putExtra("titulo", n.getTitulo());
                    a.putExtra("contenido",n.getContenido());
                    a.putExtra("imagen",n.getImagen());

                    c.startActivity(a);

            }
        });
    }



}
