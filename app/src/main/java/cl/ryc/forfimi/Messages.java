package cl.ryc.forfimi;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import cl.ryc.forfimi.entities.Msg;
import cl.ryc.forfimi.helpers.GlobalPersist;
import cl.ryc.forfimi.view.ViewMessages;

/**
 * Created by RyC on 08/04/2016.
 */
public class Messages extends AppCompatActivity {

    static ListView lvNegativos;
    static ListView lvPositivos;
    ProgressDialog pd;
    static Context c;
    static ViewMessages vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.c=this;
        getControls();

    }

    public void getControls(){

        ProgressDialog pd;



        lvNegativos=(ListView) findViewById(R.id.lvNegativos);
        lvPositivos=(ListView) findViewById(R.id.lvPositivos);
        pd= new ProgressDialog(c);
        GlobalPersist gp= GlobalPersist.getInstance(c);

        vm= new ViewMessages(this.c,pd, gp.getGlobalPersist("IdUsuario"));

    }


    public static void onAsyncBack(List<Msg> Positivos, List<Msg> Negativos, int Error){

        if(Error==0){

            lvNegativos.setAdapter(vm.toListViewNegativos(Negativos));
            lvPositivos.setAdapter(vm.toListViewPositivos(Positivos));

        }
        else{
            Toast toast = Toast.makeText(c,"Se ha producido un error al obtener tu información", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
            toast.show();
        }


    }
}
