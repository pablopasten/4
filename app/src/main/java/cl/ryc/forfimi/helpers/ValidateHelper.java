package cl.ryc.forfimi.helpers;

/**
 * Created by RyC on 07/04/2016.
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ValidateHelper {

    private  final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    private static ValidateHelper instance;
    private ValidateHelper()
    {

    }


    public static ValidateHelper getInstance()
    {
        if(instance==null)
        {
            instance=new ValidateHelper();
        }


        return instance;
    }

    public boolean validateEmail(String email) {
        try{
            // Compiles the given regular expression into a pattern.
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            // Match the given input against this pattern
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public boolean validarRut(String rut) {

        rut=rut.trim();

        boolean validacion = false;
        try {
            rut =  rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }


    public boolean ValidateisEmptyString(String field)
    {
        if(!field.equals(""))
        {
            return false;// el campo está completo
        }
        else
        {
            return true;// el campo está vacio
        }
    }

    public static boolean isHaveConnection(Context c)
    {
        boolean Status=false;
        ConnectivityManager cm =(ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            Status=true;
        }
        return Status;
    }


    public static boolean UsuarioTwitter(String usuario)
    {
        if(usuario.toString().startsWith("@"))
        {
            return true;
        }
        else{
            return false;
        }
    }




}

