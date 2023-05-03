package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Clase desde la que inicia la aplicación.
 *
 * @author Gabriel Cubillos Rodríguez
 */
public class Principal extends Application {

    /**
     * Método que inicia la aplicación. En este caso se limita a lanzar la
     * ventana que contendrá el menú principal.
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MenuPrincipal.fxml"));
            Scene scene = new Scene(root);
            primaryStage.getIcons().add(new Image("resources/agenda.png"));
            primaryStage.setTitle("Agenda");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
