package controller.util;

import model.Contacto;

/**
 * Clase que concentra los tipos de validacíon realizada en toda la aplicación.
 * Se trata de una clase enumerada que asocia a cada elemento de la clase un
 * tipo de validación y un mensaje de error.
 *
 * @author Gabriel Cubillos Rodríguez
 */
public enum Validacion {
    NOMBRE("El nombre debe ser una palabra con la primera letra en mayúscula"),
    TELEFONO("El teléfono debe ser un número de 9 cifras que empieza por 6, 7 ó 9"),
    EMAIL("El email debe tener el formato apropiado. Ej: miusuario@correo.com");

    public final String MENSAJE_ERROR;

    private Validacion(final String MENSAJE_ERROR) {
        this.MENSAJE_ERROR = MENSAJE_ERROR;
    }

    public boolean hayValidacion(String textoValidado) {
        return switch (this) {
            case NOMBRE ->
                Contacto.validarNombre(textoValidado);
            case TELEFONO ->
                Contacto.validarTelefono(textoValidado);
            case EMAIL ->
                Contacto.validarCorreo(textoValidado);
        };
    }

}
