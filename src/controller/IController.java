package controller;

import model.Agenda;

/**
 * Esta interfaz sirve para desacoplar los controladores entre sí, de forma que
 * puedo usar esta abstracción para generalizar el método de creación de
 * ventanas.
 *
 * @author Gabriel Cubillos Rodríguez
 */
public interface IController {
    /**
     * Método para inicializar los datos que se pasen por parámetro, y otros
     * que se instancien en el controlador después de inicializado éste.
     * @param agenda 
     */
    public void initData(Agenda agenda);
}
