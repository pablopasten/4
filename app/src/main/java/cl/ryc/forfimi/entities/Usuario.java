package cl.ryc.forfimi.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RyC on 21/04/2016.
 */
public class Usuario {

    String Nombre1,Nombre2, Apellido1,Apellido2,Empresa1,Empresa2,Password;

    List<RedSocial> Redes;

    List<String> Palabras;

    public Usuario() {
        Redes= new ArrayList<RedSocial>();
        Palabras= new ArrayList<String>();
    }

    public String getNombre1() {
        return Nombre1;
    }

    public void setNombre1(String nombre1) {
        Nombre1 = nombre1;
    }

    public String getNombre2() {
        return Nombre2;
    }

    public void setNombre2(String nombre2) {
        Nombre2 = nombre2;
    }

    public String getApellido1() {
        return Apellido1;
    }

    public void setApellido1(String apellido1) {
        Apellido1 = apellido1;
    }

    public String getApellido2() {
        return Apellido2;
    }

    public void setApellido2(String apellido2) {
        Apellido2 = apellido2;
    }

    public String getEmpresa1() {
        return Empresa1;
    }

    public void setEmpresa1(String empresa1) {
        Empresa1 = empresa1;
    }

    public String getEmpresa2() {
        return Empresa2;
    }

    public void setEmpresa2(String empresa2) {
        Empresa2 = empresa2;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setRedes(RedSocial rs)
    {
        Redes.add(rs);
    }

    public void setPalabras(String palabra)
    {
        Palabras.add(palabra);
    }

    public List<RedSocial> getRedes() {
        return Redes;
    }

    public List<String> getPalabras() {
        return Palabras;
    }
}
