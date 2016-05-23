package cl.ryc.forfimi;

import android.content.Context;
import android.content.DialogInterface;
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
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.facebook.login.LoginManager;

import java.io.File;
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
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.teclasverde);*/
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
                        a = new Intent(c.getApplicationContext(), cl.ryc.forfimi.Historial.class);
                        startActivity(a);
                    }
                    break;
                    case 3: {
                        a = new Intent(c.getApplicationContext(), cl.ryc.forfimi.Noticias.class);
                        startActivity(a);
                    }
                    break;
                    case 4: {
                        a = new Intent(c.getApplicationContext(), cl.ryc.forfimi.Contacto.class);
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
            //nO ESTA LOGUEADO CON FACEBOOK ASI QUE VAMOS A BUSCAR SU FOTO

            String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/4fimi/";

            File file = new File (dir+ gp.getGlobalPersist("IdUsuario")+".jpg");

            if(file.exists()) {
                imv.setImageBitmap(getRoundedBitmap(getFoto(dir + gp.getGlobalPersist("IdUsuario") + ".jpg")));
            }

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

    public Bitmap getFoto(String Nombre)
    {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 50, 50);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap= BitmapFactory.decodeFile(Nombre, options);

        if (bitmap.getWidth()>bitmap.getHeight())
        {	System.out.println("Voy a rotar la imagen");
            Matrix matrix = new Matrix();
            matrix.postRotate(90); // anti-clockwise by 90 degrees

            // create a new bitmap from the original using the matrix to transform the result
            bitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap.getWidth(),  bitmap.getHeight(), matrix, true);

            // display the rotated bitmap


        }


        return bitmap;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Atención");

        builder.setMessage("Vas a cerrar sesión, estas seguro?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                gp.clearAll();
                if(gp.VaidateExistsKey("FacebookID")) {
                    LoginManager.getInstance().logOut();

                }
                Close();
            }
        });
        builder.setNegativeButton("No", null);

        builder.show();
    }

    public void Close()
    {
        this.finish();
    }

}
