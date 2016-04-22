package cl.ryc.forfimi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import cl.ryc.forfimi.helpers.GlobalPersist;
import cl.ryc.forfimi.view.ViewMenu;


/**
 * Created by RyC on 07/04/2016.
 */
public class Menu extends AppCompatActivity{

    Context c;

    TextView edtNombre;

    GridView gv;
    ViewMenu vm;
    ImageView imv;
    GlobalPersist gp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.menu_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gp= GlobalPersist.getInstance(this);
        getControls();



    }


    public void getControls()
    {
        this.c=this;
        gp= GlobalPersist.getInstance(this.c);
        vm= new ViewMenu(this.c);
        imv=(ImageView) findViewById(R.id.imageView2);
        edtNombre=(TextView) findViewById(R.id.edtNombreUsuario);
        edtNombre.setText("Bienvenido " + gp.getGlobalPersist("NombreUsuario").toUpperCase());
        gv=(GridView) findViewById(R.id.grilla);

        System.out.println("hoooooooooooooooooooooooolaaa");
        gv.setAdapter(vm.toListView());
        gv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent a;
                switch (position) {

                    case 0: {
                        a = new Intent(c.getApplicationContext(), Messages.class);
                        startActivity(a);
                    }
                    break;
                    case 1: {
                        a = new Intent(c.getApplicationContext(), cl.ryc.forfimi.Perfil.class);
                        startActivity(a);
                    }
                    break;
                    case 2: {
                        a = new Intent(c.getApplicationContext(), cl.ryc.forfimi.Menu.class);
                        startActivity(a);
                    }
                    break;
                    case 3: {
                        a = new Intent(c.getApplicationContext(), cl.ryc.forfimi.Menu.class);
                        startActivity(a);
                    }
                    break;
                    case 4: {
                        a = new Intent(c.getApplicationContext(), cl.ryc.forfimi.Menu.class);
                        startActivity(a);
                    }
                    break;


                }


            }

        });

        if(gp.VaidateExistsKey("FacebookID"))
        {
            System.out.println("Estoy aqui");
            imv.setImageBitmap(getFacebookProfilePicture(gp.getGlobalPersist("FacebookID")));
        }
        else
        {
            //imv.setImageBitmap(getFacebookProfilePicture("1214935767"));
        }
    }

    public static Bitmap getFacebookProfilePicture(String userID) {
        URL imageURL = null;
        try {
            imageURL = new URL("https://graph.facebook.com/"+userID+"/picture?type=large");
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


        return getRoundedBitmap(bitmap);
    }

    public static Bitmap getRoundedBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();


        return output;
    }

}
