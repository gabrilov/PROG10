package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import model.Agenda;
import model.Contacto;

/**
 * FXML Controller class. Clase que controla la vista para mostrar todos los
 * elementos de la agenda en una tabla.
 *
 * @author Gabriel Cubillos Rodríguez
 */
public class MostrarContactosController implements IController {

    // Colección con los datos que maneja la aplicación.
    private Agenda agenda;
    /*
    Colección de contactos. Es una lista que permite a los oyentes realizar
    un seguimiento de los cambios cuando se producen.
     */
    private ObservableList<Contacto> contactos;

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
    private Button btnVolver;

    /**
     * Initializes the controller class. Junto con el controlador, inicializamos
     * las columnas de la tabla, vinculando cada una de ellas con una propiedad
     * de cada contacto.
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
        contactos = FXCollections.observableArrayList(agenda.getContactos());
        this.tblContactos.setItems(contactos);
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
