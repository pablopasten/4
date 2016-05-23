package cl.ryc.forfimi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cl.ryc.forfimi.comms.CommsEdicionPerfil;
import cl.ryc.forfimi.comms.CommsPerfil;
import cl.ryc.forfimi.entities.Usuario;
import cl.ryc.forfimi.helpers.GlobalPersist;
import cl.ryc.forfimi.helpers.ImageManipulation;

import static cl.ryc.forfimi.R.drawable.ic_visibility_black_24dp;
import static cl.ryc.forfimi.R.drawable.ic_visibility_off_black_24dp;

/**
 * Created by RyC on 20/04/2016.
 */
public class Perfil extends AppCompatActivity {

    protected static final int REQUEST_CAMERA = 0;
    static Context c;
    static EditText Password, Empresa1,Empresa2,Palabras,Twitter,Instagram;
    Button btnModificar,foto;
    ImageButton ShowPSW;
    ProgressDialog pd;
    boolean Mostrar;
    File newdir;
    String file, files;
    String uriUltimaFoto;
    boolean SacoFoto;
    String dir;
    GlobalPersist gp;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_activity);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);*/
        this.c=this;
        gp= GlobalPersist.getInstance(c);
        getControls();
        pd= new ProgressDialog(c);
        pd.setMessage("Estamos buscando tu info");

        CommsPerfil pf= new CommsPerfil(this,pd);
        pf.execute("");



    }

    public void getControls()
    {

        dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/4fimi/";
        newdir = new File(dir);
        newdir.mkdirs();
        System.out.println("aaaaQUIIIIIIII");
        Password=(EditText) findViewById(R.id.edtPSW);
        Empresa1=(EditText) findViewById(R.id.edtEActual);
        Empresa2=(EditText) findViewById(R.id.edtEAnterior);
        Palabras=(EditText) findViewById(R.id.edtPalabras);
        Twitter= (EditText) findViewById(R.id.edtTWT);
        Instagram=(EditText) findViewById(R.id.edtInsta);
        btnModificar=(Button) findViewById(R.id.btnModificar);
        foto=(Button) findViewById(R.id.btnModificarFoto);
        ShowPSW=(ImageButton) findViewById(R.id.btnMostrar);
        Mostrar=false;
        ShowPSW.setOnClickListener(onShow);
        btnModificar.setOnClickListener(onModificar);
        foto.setOnClickListener(onFoto);
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
            for(int cont=0;cont<user.getRedes().size();cont++){
                if(user.getRedes().get(cont).getIdRedSocial()==2){
                    if(Twitter.getText().toString().equals("")) {
                        Twitter.setText(Twitter.getText().toString() + " " + user.getRedes().get(cont).getNombreUsuarioRedSocial());
                    }
                }
                else if(user.getRedes().get(cont).getIdRedSocial()==3)
                {
                    if(Instagram.getText().toString().equals("")) {
                        Instagram.setText(Instagram.getText().toString() + " " + user.getRedes().get(cont).getNombreUsuarioRedSocial());
                    }
                }
            }


            for(int conta=0;conta<user.getPalabras().size();conta++) {

                Palabras.setText(Palabras.getText().toString() + " " + user.getPalabras().get(conta));
            }




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

    View.OnClickListener onFoto = new View.OnClickListener()
    {

        public void onClick(View v)
        {

            if(SacoFoto)
            {
                SacoFoto=false;
            }

            files =gp.getGlobalPersist("IdUsuario")+".jpg";
            file=dir+files;
            count++;
            File newfile = new File(file);
            try {
                newfile.createNewFile();
            } catch (IOException e) {}


            Uri outputFileUri = Uri.fromFile(newfile);
            uriUltimaFoto=outputFileUri.toString();

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

            //cameraIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(cameraIntent, REQUEST_CAMERA);    //startActivity(cameraIntent);


        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //super.onActivityResult(requestCode, resultCode, data);
        System.out.println(resultCode);
        switch(resultCode) {
            case 0:
            {
                //no tomo foto;
            }
            break;
            case -1:
            {
		    	  /* BitmapFactory.Options options = new BitmapFactory.Options();
		    	   options.inSampleSize=4;
		    	   Bitmap mBitmap = BitmapFactory.decodeFile(uriUltimaFoto,options);*/

                // tomo foto
                ImageManipulation im= new ImageManipulation(c);
                System.out.println(dir+gp.getGlobalPersist("IdUsuario")+".jpg");

               // im.compressImage(gp.getGlobalPersist("IdUsuario")+".jpg");



            }
            break;
            case -2:
            {

            }
            break;
            case -3:
            {

            }
            case 100:
            {

            }
            break;
            default:
            {

            }
            break;
        }
    }//onActivityResult






}
