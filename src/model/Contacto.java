package model;

public class Contacto {

    private String nombre;
    private String telefono;
    private String correo;

    public Contacto(String nombre, String telefono, String correo) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public static boolean validarNombre(String nombre) {
        return nombre.matches("[A-Z][a-z]+");
    }

    public static boolean validarTelefono(String telefono) {

        return telefono.matches("[679][0-9]{8}");
    }

    public static boolean validarCorreo(String correo) {
        return correo.matches("[a-z0-9._-]+@[a-z]+\\.[a-z]{2,4}");
    }

    @Override
    public String toString() {
        return "Contacto:\n"
                + "nombre=" + nombre
                + ", teléfono=" + telefono
                + ", correo=" + correo;
    }

}
