package controller;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import model.Agenda;
import model.Contacto;

/**
 * FXML Controller class. Esta clase controla la vista para buscar un contacto
 * en la agenda. Fuciona de la siguiente manera: se muestran todos los datos en
 * una tabla. Cuando el usuario selecciona un nombre en el campo
 * correspondiente, en la tabla sólo queda el contacto coincidente. Si no
 * existe, se muestra una alerta informativa. Pulsando un botón se muestran
 * nuevamente todos los contactos en la tabla.
 *
 * @author Gabriel Cubillos Rodríguez
 */
public class BuscarContactoController implements IController {

    /* * * CONSTANTES PARA GUARDAR LOS MENSAJES DE INFO AL USUARIO * * */
    private static final String INFO_ELEMENTO_INEXISTENTE = "Este contacto no se encuentra en la agenda";

    // Colección con los datos que maneja la aplicación. 
    private Agenda agenda;
    /*
    Colección de contactos. Es una lista que permite a los oyentes realizar
    un seguimiento de los cambios cuando se producen.
     */
    ObservableList<Contacto> todosContactos;

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
    private Button btnBuscar;
    @FXML
    private Button btnVolver;
    @FXML
    private Button btnCargarTodos;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        this.colEmail.setCellValueFactory(new PropertyValueFactory("correo"));
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
        // Se inicializa también la colección para disponer los datos en la tabla.
        todosContactos = FXCollections.observableArrayList(agenda.getContactos());
        this.tblContactos.setItems(todosContactos);
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
        Optional<Contacto> resultado = agenda.buscaContacto(txtNombre.getText());
        // Desenvuelve el Optional para comprobar si tiene contenido.
        if (resultado.isPresent()) {
            // Sustituye todos los datos de la agenda, por el obtenido, para
            // mostrar en la tabla.
            todosContactos.setAll(resultado.get());
            btnCargarTodos.setDisable(false);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sin resultados");
            alert.setContentText(INFO_ELEMENTO_INEXISTENTE);
            alert.showAndWait();
        }
    }

    /**
     * Gestor de eventos del botón para cargar todos los datos en la tabla.
     *
     * @param event
     */
    @FXML
    private void btnCargarTodosHandler(ActionEvent event) {
        todosContactos.setAll(agenda.getContactos());
        tblContactos.setItems(todosContactos);
        btnCargarTodos.setDisable(true);
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
}
