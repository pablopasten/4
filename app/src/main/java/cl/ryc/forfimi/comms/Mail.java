package cl.ryc.forfimi.comms;

/**
 * Created by RyC on 26/04/2016.
 */

import android.content.Context;
import android.util.Log;

import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;
import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;

import cl.ryc.forfimi.helpers.GlobalPersist;


public class Mail extends javax.mail.Authenticator{


    private String _user;
    private String _pass;

    private String[] _to;
    private String _from;

    private String _port;
    private String _sport;

    private String _host;

    private String _subject;
    private String _body;

    private boolean _auth;

    private boolean _debuggable;

    private Multipart _multipart;

    GlobalPersist gp;
    public Mail(String Contenido,Context c)
    {
        _host ="smtp.gmail.com"; // default smtp server
        _port = "587"; // default smtp port
        _sport = "587"; // default socketfactory port
        _to=new String[1];
        _to[0]="no-reply@4fimi.com";

        gp= GlobalPersist.getInstance(c);
        gp.setGlobalPersist("EmailUsuario","pablopasten@gmail.com");
        _user = "no-reply@4fimi.com"; // username
        _pass = "4fimi2016"; // password
        System.out.println(gp.getGlobalPersist("EmailUsuario"));
        _from = gp.getGlobalPersist("EmailUsuario"); // email sent from
        _subject = "Contacto desde Aplicacion Movil"; // email subject

        _body="Tenemos el siguiente mensaje enviado desde la aplicaciòn móvil:\r\n" +
                "El mail del usuario que envia el mensaje es: "+gp.getGlobalPersist("EmailUsuario")+
                "\r\nEl nombre del usuario es: "+gp.getGlobalPersist("NombreUsuario")+
                "\r\nEl cuerpo del mensaje es:\r\n\r\n"+Contenido;//Agregar mas datos;



        _debuggable = false; // debug mode on or off - default off
        _auth = true; // smtp authentication - default on

        _multipart = new MimeMultipart();

        // There is something wrong with MailCap, javamail can not find a handler for the multipart/mixed part, so this bit needs to be added.
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);

    }

    public boolean send() throws Exception
    {
        Properties props = _setProperties();
        boolean res=true;
        try
        {
            if(!_user.equals("") && !_pass.equals("") && _to.length > 0 && !_from.equals("") && !_subject.equals("") && !_body.equals("")) {
                Session session = Session.getInstance(props, this);

                MimeMessage msg = new MimeMessage(session);

                msg.setFrom(new InternetAddress(gp.getGlobalPersist("EmailUsuario")));

                InternetAddress[] addressTo = new InternetAddress[_to.length];
                for (int i = 0; i < _to.length; i++)
                {
                    addressTo[i] = new InternetAddress(_to[i]);
                }
                msg.setRecipients(MimeMessage.RecipientType.TO, addressTo);

                msg.setSubject(_subject);



                // setup message body
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(_body);
                _multipart.addBodyPart(messageBodyPart);

                // Put parts in message
                msg.setContent(_multipart);


                // send email
                Transport.send(msg);

                res= true;
            } else {
                res= false;
            }
        }
        catch(Exception e)
        {
            Log.e(this.getClass().getName(), e.toString());
            System.out.println("Error al enviar correo "+e.getMessage() );
        }
        return res;
    }

    public void addAttachment(String filename) throws Exception
    {
        /*BodyPart messageBodyPart = new MimeBodyPart();
        FileDataSource source = new FileDataSource(Environment.getExternalStorageDirectory()+"/DCIM/"+aerchivo+".ext");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);



        _multipart.addBodyPart(messageBodyPart);*/
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(_user, _pass);
    }
    private Properties _setProperties()
    {
        Properties props = new Properties();

        props.put("mail.smtp.host", _host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        if(_debuggable)
        {
            props.put("mail.debug", "true");
        }

        if(_auth)
        {
            props.put("mail.smtp.auth", "true");
        }

        props.put("mail.smtp.ssl.enable","false");

        props.put("mail.smtp.port", _port);
        props.put("mail.smtp.socketFactory.port", _sport);
        //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        return props;
    }

    // the getters and setters
    public String getBody()
    {
        return _body;
    }

    public void setBody(String _body)
    {
        this._body = _body;
    }
    public void setSubject(String sSubject)
    {
        this._subject=sSubject;
    }
    // more of the getters and setters �..



    public String getUser(String original)
    {
        int flag=original.indexOf("@", 0);
			 /*for (int cont=0;cont<original.length();cont++)
			 {
				 if(original.charAt(cont)==);
				 {
					 flag=cont;
					 break;
				 }
			 }*/

        original=original.substring(0, flag);
        original=original.toUpperCase();
        return original;
    }


}
