package cl.ryc.forfimi.entities;

/**
 * Created by RyC on 07/04/2016.
 */
public class LoginUsuario {
    int cod_salida;
    String des_salida;
    int estado;
    int idUsuario;
    String nombre;
    int perfil;

    public int getCod_salida() {
        return cod_salida;
    }

    public int getPerfil() {
        return perfil;
    }

    public void setPerfil(int perfil) {
        this.perfil = perfil;
    }

    public int getEstado() {

        return estado;
    }

    public String getDes_salida() {

        return des_salida;
    }

    public void setDes_salida(String des_salida) {
        this.des_salida = des_salida;
    }

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdUsuario() {

        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setEstado(int estado) {

        this.estado = estado;
    }

    public void setCod_salida(int cod_salida) {
        this.cod_salida = cod_salida;

    }

    public LoginUsuario(){}



}
