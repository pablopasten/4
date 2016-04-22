package cl.ryc.forfimi;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cl.ryc.forfimi.comms.CommsEdicionPerfil;
import cl.ryc.forfimi.comms.CommsPerfil;
import cl.ryc.forfimi.entities.Usuario;

import static cl.ryc.forfimi.R.drawable.ic_visibility_black_24dp;
import static cl.ryc.forfimi.R.drawable.ic_visibility_off_black_24dp;

/**
 * Created by RyC on 20/04/2016.
 */
public class Perfil extends AppCompatActivity {

    static Context c;
    static EditText Password, Empresa1,Empresa2,Palabras,Twitter,Instagram;
    Button btnModificar;
    ImageButton ShowPSW;
    ProgressDialog pd;
    boolean Mostrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.c=this;
        getControls();
        pd= new ProgressDialog(c);
        pd.setMessage("Estamos buscando tu info");

        CommsPerfil pf= new CommsPerfil(this,pd);
        pf.execute("");



    }

    public void getControls()
    {
        Password=(EditText) findViewById(R.id.edtPSW);
        Empresa1=(EditText) findViewById(R.id.edtEActual);
        Empresa2=(EditText) findViewById(R.id.edtEAnterior);
        Palabras=(EditText) findViewById(R.id.edtPalabras);
        Twitter= (EditText) findViewById(R.id.edtTWT);
        Instagram=(EditText) findViewById(R.id.edtInsta);
        btnModificar=(Button) findViewById(R.id.btnModificar);
        ShowPSW=(ImageButton) findViewById(R.id.btnMostrar);
        Mostrar=false;
        ShowPSW.setOnClickListener(onShow);
        btnModificar.setOnClickListener(onModificar);
    }



    public String[] getPalabras(String texto)
    {


        String[] arr = texto.split(" ");

        for ( String ss : arr) {

            System.out.println(ss);
        }

        return arr;
    }

    View.OnClickListener onModificar = new View.OnClickListener()
    {

        public void onClick(View v)
        {
            String[] listado=getPalabras(Palabras.getText().toString());
            CommsEdicionPerfil cmep= new CommsEdicionPerfil(c,pd,Password.getText().toString(),Empresa1.getText().toString(),
                    Empresa2.getText().toString(),Twitter.getText().toString(),Instagram.getText().toString(),listado);

            cmep.execute("");
        }


    };


    public static void backFromGetDataUser(Usuario user)
    {
        if (user!=null){
            Password.setText(user.getPassword());
            Empresa1.setText(user.getEmpresa1());
            Empresa2.setText(user.getEmpresa2());


        }
        else{
            Toast toast = Toast.makeText(c,"Se ha Producido un error al recuperar tus datos de usuario", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
            toast.show();

        }
    }

    View.OnClickListener onShow = new View.OnClickListener()
    {

        public void onClick(View v)
        {
            if(Mostrar==false){
                Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                Mostrar=true;
                ShowPSW.setImageResource(ic_visibility_off_black_24dp);
            }
            else{
                Mostrar=false;
                Password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                ShowPSW.setImageResource(ic_visibility_black_24dp);

            }
        }


    };

}
