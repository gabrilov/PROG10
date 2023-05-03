package controller;

import controller.util.Alerta;
import controller.util.Validacion;
import model.Agenda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class. Esta clase controla la vista para agregar un contacto
 * a la agenda. Contiene tres campos y un espacio para mostrar mensajes de
 * información al usuario sobre validación de campos y resultado del proceso.
 * Los mensajes de validación de los campos se muestran según se va escribiendo
 * en el campo correspondiente. Existen dos tipos de validación: el que activa
 * el votón para agregar, y otro en el código que muestra un alert si alguno de
 * los campos queda vacío.
 *
 * @author Gabriel Cubillos Rodríguez
 */
public class AgregarContactoController implements IController {

    /* * * CONSTANTES PARA GUARDAR LOS MENSAJES DE INFO AL USUARIO * * */
    private static final String MENSAJE_EXITO = "Contacto añadido";
    private static final String MENSAJE_ERROR = "¡Error! No se ha agregado";

    // Colección con los datos que maneja la aplicación. 
    private Agenda agenda;
    // Variables que indican si un campo ha sido validado o no.
    private boolean nombreValidado, telefonoValidado, emailValidado;

    /* * * COMPONENTES DE LA INTERFAZ * * */
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtTelefono;
    @FXML
    private Button btnAgregarContacto;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnVolver;
    @FXML
    private Label lblNombreError;
    @FXML
    private Label lblTelefonoError;
    @FXML
    private Label lblEmailError;
    @FXML
    private Label lblResultado;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        resetForm();
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
     * Método que gestiona el evento en el botón para agregar contactos.
     * Comprueba las validaciones y procede en consecuencia realizando o no la
     * operación e informando al usuario del resultado.
     *
     * @param event
     */
    @FXML
    private void btnAgregarContactoHandler(ActionEvent event) {
        String nombre = txtNombre.getText();
        String telefono = txtTelefono.getText();
        String correo = txtEmail.getText();
        if (hayCamposVacios()) {
            Alerta.mostrarAlertaError("Todos los campos son obligatorios");
            return;
        }
        boolean hayExito = agenda.anadeContacto(nombre, telefono, correo);
        if (hayExito) {
            lblResultado.setStyle("-fx-text-fill: green");
            lblResultado.setText(MENSAJE_EXITO);
        } else {
            lblResultado.setStyle("-fx-text-fill: red");
            lblResultado.setText(MENSAJE_ERROR);
        }
        resetForm();
    }

    /**
     * Método para comprobar si algún campo ha quedado vacío.
     *
     * @return Devuelve true si hay campos vacíos.
     */
    private boolean hayCamposVacios() {
        return txtNombre.getText().isEmpty()
                || txtTelefono.getText().isEmpty()
                || txtEmail.getText().isEmpty();
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
     * Método que gestiona los eventos en el campo del nombre de contacto.
     * Realiza la validación según se introducen caractere, mostrando un mensaje
     * mientras el campo no se valide existosamente. Finalmente, comprueba si el
     * resto de campos también están validados para activar el botón de envío de
     * datos. También realiza la comprobación sobre si ya existe el contacto.
     *
     * @param event
     */
    @FXML
    private void txtNombreHandler(KeyEvent event) {
        lblResultado.setText("");
        nombreValidado = validarTexto(
                txtNombre,
                Validacion.NOMBRE,
                lblNombreError);
        if (nombreValidado && agenda.existeContacto(txtNombre.getText())) {
            lblNombreError.setText("Este usuario ya existe");
            nombreValidado = false;
        }
        comprobarValidacionCompleta();
    }

    /**
     * Método que gestiona los eventos en el campo del nombre de teléfono.
     * Realiza la validación según se introducen caractere, mostrando un mensaje
     * mientras el campo no se valide existosamente. Finalmente, comprueba si el
     * resto de campos también están validados para activar el botón de envío de
     * datos.
     *
     * @param event
     */
    @FXML
    private void txtTelefonoHandler(KeyEvent event) {
        lblResultado.setText("");
        telefonoValidado = validarTexto(
                txtTelefono,
                Validacion.TELEFONO,
                lblTelefonoError);
        comprobarValidacionCompleta();
    }

    /**
     * Método que gestiona los eventos en el campo del correo electrónico.
     * Realiza la validación según se introducen caractere, mostrando un mensaje
     * mientras el campo no se valide existosamente. Finalmente, comprueba si el
     * resto de campos también están validados para activar el botón de envío de
     * datos.
     *
     * @param event
     */
    @FXML
    private void txtEmailHandler(KeyEvent event) {
        lblResultado.setText("");
        emailValidado = validarTexto(
                txtEmail,
                Validacion.EMAIL,
                lblEmailError);
        comprobarValidacionCompleta();
    }

    /**
     * Realiza la validación del texto de un campo conforme a una norma de
     * validación concreta, y muestra un resultado de información al usuario en
     * una etiqueta.
     *
     * @param textField
     * @param validacion
     * @param lblOutput
     * @return
     */
    private boolean validarTexto(TextField textField, Validacion validacion, Label lblOutput) {
        if (validacion.hayValidacion(textField.getText())) {
            textField.setStyle("-fx-border-color:blue");
            lblOutput.setText("");
            return true;
        } else if (textField.getText().isEmpty()) {
            return false;
        } else {
            textField.setStyle("-fx-border-color:red");
            lblOutput.setText(validacion.MENSAJE_ERROR);
            return false;
        }

    }

    /**
     * Devuelve los componentes y variables de validación a su estado original.
     */
    private void resetForm() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        nombreValidado = false;
        telefonoValidado = false;
        emailValidado = false;
        btnAgregarContacto.setDisable(true);
    }

    /**
     * Comprueba si todos los campos del formulario han pasado las pruebas de
     * validación.
     */
    private void comprobarValidacionCompleta() {
        if (nombreValidado
                && telefonoValidado
                && emailValidado) {
            btnAgregarContacto.setDisable(false);
        }
    }
}
