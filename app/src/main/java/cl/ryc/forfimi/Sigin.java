package cl.ryc.forfimi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cl.ryc.forfimi.comms.CommsSignIn;
import cl.ryc.forfimi.comms.LoginComms;
import cl.ryc.forfimi.entities.LoginUsuario;
import cl.ryc.forfimi.helpers.GlobalPersist;
import cl.ryc.forfimi.helpers.ValidateHelper;

/**
 * Created by RyC on 11/04/2016.
 */
public class Sigin extends AppCompatActivity {

    static Context c;
    static ProgressDialog pd;
    EditText Nombre1,Nombre2,Apellido1,Apellido2,Twitter;
    static EditText Mail,Password;
    Button btnCrear;
    ValidateHelper helper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getControls();

    }


    public void getControls()
    {
        this.c=this;
        helper= ValidateHelper.getInstance();
        Nombre1=(EditText) findViewById(R.id.edtNombre1);
        Nombre2=(EditText) findViewById(R.id.edtNombre2);
        Apellido1=(EditText) findViewById(R.id.edtApellido1);
        Apellido2=(EditText) findViewById(R.id.edtApellido2);
        Mail=(EditText) findViewById(R.id.edtMail);
        Twitter=(EditText) findViewById(R.id.edtTwitter);
        Password=(EditText) findViewById(R.id.edtPassword);
        btnCrear=(Button) findViewById(R.id.btnUsuario);

        btnCrear.setOnClickListener(onCrear);

    }

    View.OnClickListener onCrear = new View.OnClickListener()
    {

        public void onClick(View v)
        {
            if (helper.isHaveConnection(c))
            {
                int Estado=ValidarCampos();

                switch (Estado){
                    case 0:{
                        pd= new ProgressDialog(c);
                        pd.setMessage("Estamos creando tu cuenta.");
                        CommsSignIn cms= new CommsSignIn(pd,c,Nombre1.getText().toString(),Nombre2.getText().toString(),Apellido1.getText().toString(),
                        Apellido2.getText().toString(),Mail.getText().toString(),Password.getText().toString(),Twitter.getText().toString(),1);

                        cms.execute("");
                    }
                    break;
                    case 1:{
                        Snackbar.make(v, "Los Campos: Mail,Contraseña,Primer Nombre, Apellido Pat y Twiiter son Obligatorios", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    break;
                    case 2:{
                        Snackbar.make(v, "Debes ingresar un correo valido", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    break;
                    case 3:{
                        Snackbar.make(v, "El usuario twitter debe comenzar con @", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }
            else
            {
                Snackbar.make(v, "No tienes conexión a internet, intenta màs tarde", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        }
    };


    public int ValidarCampos()
    {
        if(Mail.getText().toString().equals("")|| Password.getText().toString().equals("")||
                Nombre1.getText().toString().equals("")||Apellido1.getText().toString().equals("")||
                Twitter.getText().toString().equals("")){
            return 1;
        }
        else if(!helper.validateEmail(Mail.getText().toString())){
            return 2;
        }
        else if(!helper.UsuarioTwitter(Twitter.getText().toString())){

            return 3;
        }

        return 0;

    }


    public static void onCreateUserAsyncBack(int Estado){

        if (Estado==0){

            pd= new ProgressDialog(c);
            LoginComms cl= new LoginComms(c,pd,Mail.getText().toString(),Password.getText().toString(),0);
            cl.execute("");

        }
        else{
            Toast toast = Toast.makeText(c,"Se produjo un error al crear tu usuario. Intenta nuevamente", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
            toast.show();
        }


    }


    public static void onBackCommsLogIn(LoginUsuario lu){

        if(lu.getCod_salida()!=0){
            Toast toast = Toast.makeText(c,"Error:"+lu.getDes_salida(), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
            toast.show();
        }
        else{

            GlobalPersist gp= GlobalPersist.getInstance(c);
            gp.setGlobalPersist("NombreUsuario",lu.getNombre());
            gp.setGlobalPersist("IdUsuario",""+lu.getIdUsuario());
            gp.setGlobalPersist("TipoPerfil", "" + lu.getPerfil());
            Intent intent = new Intent(c.getApplicationContext(), cl.ryc.forfimi.Menu.class);
            c.startActivity(intent);
        }

    }

}
