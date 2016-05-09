package cl.ryc.forfimi.entities;

/**
 * Created by RyC on 28/04/2016.
 */
public class Noticia {

    String Titulo;
    String Imagen;
    String Contenido;
    String TipoNoticia;


    public Noticia() {
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getContenido() {
        return Contenido;
    }

    public void setContenido(String contenido) {
        Contenido = contenido;
    }

    public String getTipoNoticia() {
        return TipoNoticia;
    }

    public void setTipoNoticia(String tipoNoticia) {
        TipoNoticia = tipoNoticia;
    }
}
