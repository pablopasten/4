package cl.ryc.forfimi.entities;

/**
 * Created by RyC on 11/04/2016.
 */

public class Msg {

    int id_usuario_red;
    int id_red_social;
    String comentario;
    String Fecha;
    String TipoComentario;
    String id_nombre_quien_comenta;
    int is_falso_positivo;

    public Msg(){}

    public int getId_usuario_red() {
        return id_usuario_red;
    }

    public void setId_usuario_red(int id_usuario_red) {
        this.id_usuario_red = id_usuario_red;
    }

    public int getId_red_social() {
        return id_red_social;
    }

    public void setId_red_social(int id_red_social) {
        this.id_red_social = id_red_social;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getTipoComentario() {
        return TipoComentario;
    }

    public void setTipoComentario(String tipoComentario) {
        TipoComentario = tipoComentario;
    }

    public String getId_nombre_quien_comenta() {
        return id_nombre_quien_comenta;
    }

    public void setId_nombre_quien_comenta(String id_nombre_quien_comenta) {
        this.id_nombre_quien_comenta = id_nombre_quien_comenta;
    }

    public int getIs_falso_positivo() {
        return is_falso_positivo;
    }

    public void setIs_falso_positivo(int is_falso_positivo) {
        this.is_falso_positivo = is_falso_positivo;
    }
}
