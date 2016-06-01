package cl.ryc.forfimi.helpers;

import android.net.ParseException;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cl.ryc.forfimi.error.ErrorHandler;

/**
 * Created by RyC on 07/04/2016.
 */
public class DataFromServer {

    final static int TimeOut=100000;

    final static int NoError=0;
    final static int ConnectionError=1;
    final static int JsonProblem=2;
    final static int JsonEmpty=3;
    final static int unknownError=100;
    final static int JsonNotInterpreted=11;


    ErrorHandler eh;

    public DataFromServer(){
        eh=ErrorHandler.getInstance();
    }

    public JSONArray GetDataFromServer(String Uri, int Metodo)
    {
        // Este metodo entrega un Arreglo Json cuando no sabemos cual es la cabecera inicial del objeto json

        if(Uri.contains(" "))
        {
            Uri=Uri.replace(" ", "_");
        }

        JSONArray jsonArray=null;
        System.out.println("entre al get data desde el thread");
        boolean isHaveConnect=false;

        String result = null;
        InputStream is = null;
        StringBuilder sb=null;

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


        try
        {
            // Para conectarnos a la web verificaremos que exista conexion usando el time out.
            HttpParams httpParameters = new BasicHttpParams();
            int timeoutConnection =TimeOut;
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                    timeoutConnection);
            int timeoutSocket =TimeOut ;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpResponse response;

            if(Uri.contains("á")||Uri.contains("Á")||Uri.contains("é")||Uri.contains("É")||Uri.contains("í")||Uri.contains("Í")||
                    Uri.contains("ó")||Uri.contains("Ó")||Uri.contains("ú")||Uri.contains("Ú")||Uri.contains("ü")||Uri.contains("Ü"))
            {
                Uri=Uri.replace("á", "a");
                Uri=Uri.replace("Á", "A");
                Uri=Uri.replace("é", "e");
                Uri=Uri.replace("É", "E");
                Uri=Uri.replace("í", "i");
                Uri=Uri.replace("Í", "I");
                Uri=Uri.replace("ó", "o");
                Uri=Uri.replace("Ó", "O");
                Uri=Uri.replace("ú", "u");
                Uri=Uri.replace("Ú", "U");
                Uri=Uri.replace("ü", "u");
                Uri=Uri.replace("Ü", "u");
            }
            if(Metodo==0)
            {
                HttpPost httppost = new HttpPost(Uri);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                response = httpclient.execute(httppost);
            }
            else
            {
                HttpGet httpget = new HttpGet(Uri);


                response = httpclient.execute(httpget);
            }

            HttpEntity entity = response.getEntity();

            is = entity.getContent();


            isHaveConnect=true;


        }
        catch(Exception e)
        {
            Log.e("log_tag", "Error in http connection" + e.toString());
            System.out.println(e.toString());
            System.out.println("---------------------------------------------");
            System.out.println("       El json con error es        ");
            System.out.println("---------------------------------------------");

            eh.setLastError(ConnectionError);
        }
        if(isHaveConnect && eh.getLastError()==NoError)
        {
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                sb = new StringBuilder();
                sb.append(reader.readLine() + "\n");

                String line="0";
                System.out.println(sb);
                System.out.println("TOTAL DE OBJETOS RECEPCIONADOS: "+sb.length());
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                    System.out.println(line + "\n");

                }
                is.close();
                if(!sb.toString().startsWith("["))
                {
                    result="["+sb.toString()+"]";
                    System.out.println("Agregué corchetes");
                    System.out.println(result);
                }
                else
                {
                    result=sb.toString();
                }

                jsonArray = new JSONArray(result);
                eh.setLastError(NoError);

                JsonArrayHelper.setJsonHelper(jsonArray);
            }
            catch(JSONException e1)
            {


                eh.setLastError(JsonEmpty);

            }
            catch (ParseException e1)
            {
                System.out.println(e1.toString());
                eh.setLastError(JsonNotInterpreted);
                System.out.println("---------------------------------------------");
                System.out.println("       El json con error es        ");
                System.out.println("---------------------------------------------");
                System.out.println("result");

            }
            catch(Exception e)
            {
                System.out.println("Error converting result "+e.toString());
                System.out.println(e.toString());
                System.out.println("---------------------------------------------");
                System.out.println("       El json con error es        ");
                System.out.println("---------------------------------------------");


                eh.setLastError(unknownError);


            }




        }


        return jsonArray;
    }
}
