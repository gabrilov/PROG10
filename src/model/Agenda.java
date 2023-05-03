package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Esta clase hace las veces de repositorio de datos de una agrupación de
 * contactos.
 * 
 * @author Gabriel Cubillos Rodríguez
 */
public class Agenda {

    /**
     * Cambié el nombre porque me liaba que se llamara como una instancia de la
     * clase.
     */
    private ArrayList<Contacto> contactos = new ArrayList();

    public ArrayList<Contacto> getContactos() {
        return contactos;
    }

    /**
     * En este método de agregar contacto, se elimina la responsabilida de
     * comprobar si el método existe, y se limita a agregar un contacto a la
     * colección.
     *
     * @param nombre
     * @param telefono
     * @param correo
     * @return
     */
    public boolean anadeContacto(String nombre, String telefono, String correo) {
        Contacto nuevoContacto = new Contacto(nombre, telefono, correo);
        return contactos.add(nuevoContacto);
    }

    /**
     * He agregado este método para comprobar si un contacto existe a partir del
     * nombre que se pase por parámetro.
     *
     * @param nombre
     * @return
     */
    public boolean existeContacto(String nombre) {
        return contactos.stream()
                .anyMatch(contacto -> contacto.getNombre().equals(nombre));
    }

    /**
     * Método de búsqueda. Se elimina la parte de interacción con el usuario y
     * el método se limita a buscar y devolver lo que encuentra.
     *
     * @param nombre
     * @return Devuelve un objeto Optional que envuelve el resultado para evitar
     * problemas con la posible nulidad de los valores.
     * @throws IOException
     */
    public Optional<Contacto> buscaContacto(String nombre) {
        return contactos.stream()
                .filter(contacto -> contacto.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }

    /**
     * Elimina el contacto cuyo nombre se pasa por parámetro.
     *
     * @param nombre
     */
    public void eliminaContacto(String nombre) {
        contactos.removeIf(c -> c.getNombre().equals(nombre));
    }

    public int tamanoAgenda() {
        return contactos.size();
    }
}
