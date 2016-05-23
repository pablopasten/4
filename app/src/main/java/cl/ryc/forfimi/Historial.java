package cl.ryc.forfimi;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import cl.ryc.forfimi.entities.Msg;
import cl.ryc.forfimi.helpers.GlobalPersist;
import cl.ryc.forfimi.view.ViewHistorial;
import cl.ryc.forfimi.view.ViewMessages;

/**
 * Created by RyC on 10/05/2016.
 */
public class Historial extends AppCompatActivity {


    static ListView lvNegativos;
    static Context c;
    ProgressDialog pd;
    static ViewHistorial vm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        this.c=this;
        getControls();

    }


    public void getControls(){

        lvNegativos=(ListView) findViewById(R.id.lvNegativos);
        pd= new ProgressDialog(c);
        GlobalPersist gp= GlobalPersist.getInstance(c);
        vm= new ViewHistorial(this.c,pd, gp.getGlobalPersist("IdUsuario"));

    }

    public static void onAsyncBack(List<Msg> Negativos, int Error){

        if(Error==0){

            lvNegativos.setAdapter(vm.toListViewNegativos(Negativos));

        }
        else{
            Toast toast = Toast.makeText(c,"Se ha producido un error al obtener tu información", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
            toast.show();
        }


    }

}


