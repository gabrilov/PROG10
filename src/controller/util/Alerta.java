package controller.util;

import javafx.scene.control.Alert;

/**
 * Clase de utilidad que sirve a los controladores
 *
 * @author Gabriel Cubillos Rodríguez
 */
public class Alerta {

    /**
     * Muestra una alerta de error con el mensaje que se pase por argumento.
     *
     * @param mensaje
     */
    public static void mostrarAlertaError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    /*
    Aquí podrían ir otros tipos de alerta, si fuese necesario para evitar la repeticón
    del mismo código en distintas clases. Pero no lo es.
    */
}
