package controller;

import controller.util.Alerta;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Agenda;

/**
 * FXML Controller class. Clase que controla la vista del menú principal.
 *
 * @author Gabriel Cubillos Rodríguez
 */
public class MenuPrincipalController {

    /* * * COMPONENTES DE LA INTERFAZ * * */
 /* * * RUTAS A LOS ARCHIVOS DE LAS VISTAS * * */
    private static final String PATH_AGREGAR_CONTACTO = "/view/AgregarContacto.fxml";
    private static final String PATH_MOSTRAR_CONTACTOS = "/view/MostrarContactos.fxml";
    private static final String PATH_ELIMINAR_CONTACTO = "/view/EliminarContacto.fxml";
    private static final String PATH_BUSCAR_CONTACTO = "/view/BuscarContacto.fxml";

    // Colección con los datos que maneja la aplicación.
    private Agenda agenda;

    /* * * COMPONENTES DE LA INTERFAZ * * */
    @FXML
    private Button btnNavToEliminar;
    @FXML
    private Button btnNavToBuscar;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnNavToAgregar;
    @FXML
    private Button btnNavToContactos;
    @FXML
    private Button btnNumeroContactos;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        agenda = new Agenda();
    }

    /* * * Navegación al resto de ventanas * * */
 /*
    Cada método gestiona el evento de click de un determinado botón de la
    interfaz que lleva a otra ventana, o en el caso del último método, que sale
    del programa.
     */
    @FXML
    private void btnNavToAgregarHandler(ActionEvent event) {
        navegarHacia(PATH_AGREGAR_CONTACTO);
    }

    @FXML
    private void btnNavToContactosHandler(ActionEvent event) {
        navegarHacia(PATH_MOSTRAR_CONTACTOS);
    }

    @FXML
    private void btnNavToEliminarHandler(ActionEvent event) {
        navegarHacia(PATH_ELIMINAR_CONTACTO);
    }

    @FXML
    private void btnNavToBuscarHandler(ActionEvent event) {
        navegarHacia(PATH_BUSCAR_CONTACTO);
    }

    /**
     * Muestra el número de contactos en la agenda en una ventana emergente.
     *
     * @param event
     */
    @FXML
    private void btnNumeroContactosHandler(ActionEvent event) {
        int numeroContactos = agenda.tamanoAgenda();
        String mensaje = switch (numeroContactos) {
            case 0 ->
                "La agenda no tiene contactos";
            case 1 ->
                "La agenda tiene sólo un contacto";
            default ->
                "Actualmente, la agenda tiene "
                + numeroContactos
                + " contactos";
        };
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Número de contactos");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Gestor de eventos del botón salir. Termina el programa.
     *
     * @param event
     */
    @FXML
    private void btnSalirHandler(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Método de navegación hacia otra ventana. El sistema que se usa es el de
     * una sóla ventana por evento. Es decir: si se pulsa en un botón que abre
     * una ventana, se cierra la anterior, y si se cierra la actual, se muestra
     * la anterior, simulando un stack de navegación.
     *
     * @param pathDestino
     */
    private void navegarHacia(String pathDestino) {
        try {
            Stage currentStage = (Stage) this.btnSalir.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(pathDestino));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.getIcons().add(new Image("resources/agenda.png"));
            stage.setScene(scene);
            IController controller = loader.getController();
            controller.initData(agenda);
            // Al momento de abrir la ventana destino, la de origen se oculta.
            currentStage.hide();
            stage.show();
            // Cuando se cierra la ventana de destino, se vuelve a mostrar la
            // de origen.
            stage.setOnCloseRequest(e -> {
                currentStage.show();
            });
        } catch (IOException ex) {
            Alerta.mostrarAlertaError("¡Uy! ¡Algo ha ido mal!");
            Logger.getLogger(MenuPrincipalController.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }
}
