package cl.ryc.forfimi;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cl.ryc.forfimi.comms.Mail;

/**
 * Created by RyC on 26/04/2016.
 */
public class Contacto extends AppCompatActivity {

    EditText Contenido;
    Button Enviar;
    Context c;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        c=this;
        getControls();

    }


    public void getControls(){


        Contenido=(EditText) findViewById(R.id.editTextoContacto);
        Enviar=(Button) findViewById(R.id.btnContacto);

        Enviar.setOnClickListener(onEnviar);

    }


    View.OnClickListener onEnviar = new View.OnClickListener()
    {

        public void onClick(View v)
        {

            if(Contenido.getText().toString().equals("")){
                Toast toast = Toast.makeText(c,"Debes completar el campo mensaje", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                toast.show();
            }
            else{
                //Aca vamos a enviar el mensaje;

                Mail m= new Mail(Contenido.getText().toString(),c);

                try {
                    m.send();

                    Toast toast = Toast.makeText(c,"Gracias por contactarnos, pronto responderemos a tu correo.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                    toast.show();
                    cerrar();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(c,"Ocurrió un error al enviar tu mensaje.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                    toast.show();
                }

            }
        }
    };


    public void cerrar()
    {
        this.finish();
    }
}
