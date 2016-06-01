package cl.ryc.forfimi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by RyC on 31/05/2016.
 */
public class News extends AppCompatActivity {

    Context c;
    String Contenido,Titulo,Imagen;
    ImageView imgview;
    TextView Tit, Content;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_news);
        c=this;
        Bundle extras = getIntent().getExtras();
        Titulo=extras.getString("titulo");
        Contenido=extras.getString("contenido");
        Imagen=extras.getString("imagen");
        getControls();

    }

    public void getControls(){

        Tit=(TextView) findViewById(R.id.titulonoticia);
        Content=(TextView) findViewById(R.id.contenidonoticia);
        imgview=(ImageView) findViewById(R.id.imagenNoticia);
        Content.setMovementMethod(new ScrollingMovementMethod());

        Tit.setText(Titulo);
        Content.setText(Contenido);

        Bitmap btmp=getImage(Imagen);
        if (btmp!=null)
        {
            imgview.setImageBitmap(btmp);
        }


    }

    public Bitmap getImage(String u)
    {
        URL imageURL = null;
        try {
            imageURL = new URL(u);
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


}
