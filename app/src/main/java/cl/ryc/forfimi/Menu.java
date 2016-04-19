package cl.ryc.forfimi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import cl.ryc.forfimi.helpers.GlobalPersist;
import cl.ryc.forfimi.view.ViewMenu;


/**
 * Created by RyC on 07/04/2016.
 */
public class Menu extends AppCompatActivity{

    Context c;

    TextView edtNombre;
    GlobalPersist gp;
    GridView gv;
    ViewMenu vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gp= GlobalPersist.getInstance(this);
        getControls();



    }


    public void getControls()
    {
        this.c=this;
        vm= new ViewMenu(this.c);
        edtNombre=(TextView) findViewById(R.id.edtNombreUsuario);
        edtNombre.setText("Bienvenido "+gp.getGlobalPersist("NombreUsuario").toUpperCase());
        gv=(GridView) findViewById(R.id.grilla);
        gv.setAdapter(vm.toListView());
        gv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent a;
                switch (position){

                    case 0:{
                        a= new Intent(c.getApplicationContext(), Messages.class);
                        startActivity(a);
                    }
                    break;
                    case 1:{
                        a= new Intent(c.getApplicationContext(), cl.ryc.forfimi.Menu.class);
                        startActivity(a);
                    }
                    break;
                    case 2:{
                        a= new Intent(c.getApplicationContext(), cl.ryc.forfimi.Menu.class);
                        startActivity(a);
                    }
                    break;
                    case 3:{
                        a= new Intent(c.getApplicationContext(), cl.ryc.forfimi.Menu.class);
                        startActivity(a);
                    }
                    break;
                    case 4:{
                        a= new Intent(c.getApplicationContext(), cl.ryc.forfimi.Menu.class);
                        startActivity(a);
                    }
                    break;




                }





            }

        });


    }



}
