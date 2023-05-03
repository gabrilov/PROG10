package controller;

import controller.util.Alerta;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import model.Agenda;
import model.Contacto;

/**
 * FXML Controller class. Esta clase controla la vista para eliminar un contacto
 * de la colección de datos.
 *
 * @author Gabriel Cubillos Rodríguez
 */
public class EliminarContactoController implements IController {

    /* * * CONSTANTES PARA GUARDAR LOS MENSAJES DE INFO AL USUARIO * * */
    private static final String ERROR_ELEMENTO_INEXISTENTE = "No existe ningún contacto con ese nombre";
    private static final String INFO_ELEMENTO_ELIMINADO = "Se ha eliminado el contacto";
    private static final String ERROR_INDEFINIDO = "Algo ha ido mal y no se ha realizado la operación";
    private static final String WARNING_ELIMINACION = """
                             Se va a eliminar un contacto.
                             Esta operación es IRREVERSIBLE.
                             ¿Quieres continuar?
                             """;

    /* * * COMPONENTES DE LA INTERFAZ * * */
    @FXML
    private TableView<Contacto> tblContactos;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TextField txtNombre;
    @FXML
    private Button btnVolver;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Label lblResultado;

    // Colección con los datos que maneja la aplicación.
    private Agenda agenda;
    // Un objeto que guarda la información de un contacto.
    private Contacto contacto;
    /*
    Colección de contactos. Es una lista que permite a los oyentes realizar
    un seguimiento de los cambios cuando se producen.
     */
    private ObservableList<Contacto> contactosEnTabla;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        this.colEmail.setCellValueFactory(new PropertyValueFactory("correo"));
    }

    /**
     * Manejador del campo de texto del nombre. Activa o desactiva el botón de
     * búsqueda según se disponga la información en él.
     *
     * @param event
     */
    @FXML
    private void txtNombreHandler(KeyEvent event) {
        lblResultado.setText("");
        btnBuscar.setDisable(txtNombre.getText().isEmpty());
    }

    /**
     * Handler del botón "Volver". Dispara el evento de cierre de ventana, que a
     * su vez recibe la ventana del menú principal para mostrarse.
     *
     * @param event
     */
    @FXML
    private void btnVolverHandler(ActionEvent event) {
        Window currentWindow = btnVolver.getScene().getWindow();
        currentWindow.fireEvent(new WindowEvent(
                currentWindow, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    /**
     * Handler del botón de búsqueda. Envía la orden a la clase repositorio y
     * conforme obtenga un objeto o no, muestra los datos o informa al usuario
     * de que no existe el elemento, respectivamente.
     *
     * @param event
     */
    @FXML
    private void btnBuscarHandler(ActionEvent event) {
        contactosEnTabla = FXCollections.observableArrayList();
        Optional<Contacto> resultadoBusqueda = agenda.buscaContacto(txtNombre.getText());
        if (resultadoBusqueda.isPresent()) {
            contacto = resultadoBusqueda.get();
            contactosEnTabla.add(contacto);
            tblContactos.setItems(contactosEnTabla);
            btnEliminar.setDisable(false);
        } else {
            lblResultado.setStyle("-fx-text-fill: red");
            lblResultado.setText(ERROR_ELEMENTO_INEXISTENTE);
        }
    }

    /**
     * Handler del botón eliminar. Dispara una ventana de alerta informando de
     * la trascendencia de la operación al usuario. Si el usuario acepta se
     * elimina el dato.
     *
     * @param event
     */
    @FXML
    private void btnEliminarHandler(ActionEvent event) {
        if (contacto == null) {
            Alerta.mostrarAlertaError(ERROR_INDEFINIDO);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Advertencia");
        alert.setContentText(WARNING_ELIMINACION);
        Optional<ButtonType> eleccion = alert.showAndWait();
        if (eleccion.get() == ButtonType.OK) {
            agenda.eliminaContacto(contacto.getNombre());
            lblResultado.setStyle("-fx-text-fill: green");
            lblResultado.setText(INFO_ELEMENTO_ELIMINADO);
            resetForm();
        }
    }

    /**
     * Método para inicializar objetos después de que cargue todo el
     * controlador, para evitar errores de nulidad.
     *
     * @param agenda Objeto que se inicializa.
     */
    @Override
    public void initData(Agenda agenda) {
        this.agenda = agenda;
    }

    /**
     * Restaura los componentes de la interfaz a su estado inicial.
     */
    private void resetForm() {
        txtNombre.setText("");
        btnEliminar.setDisable(true);
        contactosEnTabla.clear();
    }

}
