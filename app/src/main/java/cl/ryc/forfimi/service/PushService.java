package cl.ryc.forfimi.service;

/**
 * Created by RyC on 18/04/2016.
 */
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import cl.ryc.forfimi.Login;
import cl.ryc.forfimi.R;
import cl.ryc.forfimi.helpers.GlobalPersist;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class PushService extends IntentService
{
    private static final int NOTIF_ALERTA_ID = 1;
    public Vibrator vibrator;
    public PushService()
    {
        super("PushService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        // TODO Auto-generated method stub
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);
        Bundle extras = intent.getExtras();

        if (!extras.isEmpty())
        {
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType))
            {
                getMensaje(extras.getString("message"));

                String Mensaje="";

                /*if(GlobalPersist.getGlobalPersist("tipoPush").equals("cancelacion"))
                {
                    GlobalPersist.setGlobalPersist("postulando", "false");
                    GlobalPersist.setGlobalPersist("carreraencurso","false");
                    ServiceQuestions.cancelarTimer();
                    Mensaje="Lo sentimos el usuario ha cancelado la carrera";
                }
                else if(GlobalPersist.getGlobalPersist("tipoPush").equals("confirmacion"))
                {
                    //GlobalPersist.setGlobalPersist("carreraencurso","true");
                    Mensaje="El pasajero está esperandote";
                }
                else if(GlobalPersist.getGlobalPersist("tipoPush").equals("nueva"))
                {
                    Mensaje ="Postula YA! hay una nueva carrera!";
                }*/

           /*    try {
                Thread.sleep(1000);//voy a dormirlo un segundo para que alcance a crearse la pantalla en caso de que caiga otra autmaticamente
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
                mostrarNotification(Mensaje);

                /*if(GlobalPersist.getGlobalPersist("silencio").equals("false"))
                {
                    //Start the vibration
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    // Vibrate for 300 milliseconds
                    v.vibrate(1000);

                    MediaPlayer mMediaPlayer = new MediaPlayer();

                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.play();

                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.setLooping(false);
                    mMediaPlayer.setVolume(MODE_MULTI_PROCESS, BIND_IMPORTANT);
                    mMediaPlayer.start();

                }*/


            }
        }


        GCMBroadcastReceiver.completeWakefulIntent(intent);

    }

    private void mostrarNotification(String msg)
    {




        NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notIntent =  new Intent(this, Login.class);

        notIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(notIntent);
        NotificationCompat.Builder mBuilders =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("4Fimi ")
                            .setContentText("Prueba")
                            .setUsesChronometer(true);


        notIntent.putExtra("ContentMessage",GlobalPersist.getGlobalPersist("ContentMessage"));
        notIntent.setAction(Intent.ACTION_MAIN);
        notIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent contIntent = PendingIntent.getActivity(
                    this, 0, notIntent, PendingIntent.FLAG_UPDATE_CURRENT);



        mBuilders.setContentIntent(contIntent);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Vibrate for 300 milliseconds
        v.vibrate(1000);

        MediaPlayer mMediaPlayer = new MediaPlayer();

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.setVolume(MODE_MULTI_PROCESS, BIND_IMPORTANT);
        mMediaPlayer.start();

        mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilders.build());





    }


    public void getMensaje (String mensaje)
    {

        System.out.println(mensaje);
        try {
            JSONObject jo= new JSONObject(mensaje);
           /* GlobalPersist gl= GlobalPersist.getInstance(getApplicationContext());
            if(!jo.isNull("tipomensaje")&&jo.getString("tipomensaje").equals("cancelacion"))
            {
                System.out.println("cancelando la carrera");
                GlobalPersist.setGlobalPersist("tipoPush", "cancelacion");
            }
            else if(!jo.isNull("tipomensaje")&&jo.getString("tipomensaje").equals("confirmacion"))
            {
                System.out.println("confirmando la carrera");
                GlobalPersist.setGlobalPersist("tipoPush", "confirmacion");
            }
            else if(!jo.isNull("tipomensaje")&&jo.getString("tipomensaje").equals("freemessage"))
            {
                GlobalPersist.setGlobalPersist("tipoPush","freemessage");
                GlobalPersist.setGlobalPersist("ContentMessage", jo.getString("contenido").replace("_", " "));
            }
            else if(jo.isNull("tipomensaje"))
            {
                String Nombre=jo.getString("nombre").replace("_"," ");

                GlobalPersist.setGlobalPersist("tipoPush", "nueva");
                GlobalPersist.setGlobalPersist("idCarrera", jo.getString("id_carrera"));
                GlobalPersist.setGlobalPersist("idUsuario", jo.getString("id_usuario"));
                GlobalPersist.setGlobalPersist("latitud", jo.getString("latitud"));
                GlobalPersist.setGlobalPersist("longitud", jo.getString("longitud"));
                GlobalPersist.setGlobalPersist("telefono", jo.getString("telefono"));
                GlobalPersist.setGlobalPersist("comuna", jo.getString("comuna"));
                GlobalPersist.setGlobalPersist("NombrePasajero", Nombre);
                GlobalPersist.setGlobalPersist("direccion",jo.getString("direccion").replace("_", " "));
                GlobalPersist.setGlobalPersist("destino",jo.getString("destino").replace("_"," "));

            }*/


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




    }


}