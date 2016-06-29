package mx.peta.mod4practica2.model;

/**
 * Created by rayo on 6/27/16.
 */
public class ModelItem {
    /*
        Valores posibles de la variable estado que representa el estado de la aplicación
     */
    public static final int NO_INSTALADA   = 0;
    public static final int INSTALADA      = 1;
    public static final int POR_ACTUALIZAR = 2;
    public static final int ACTUALIZADA    = 3;

    public int     id;
    public String  appName;
    public String  appDescripcion;
    public String  appDesarrollador;
    public int     appIcono;
    public int     appEstado;

    public ModelItem(String appName, String appDescripcion, String appDesarrollador, int appIcono,
                     int appEstado) {
        this.appName          = appName;
        this.appDescripcion   = appDescripcion;
        this.appDesarrollador = appDesarrollador;
        this.appIcono         = appIcono;
        this.appEstado        = appEstado;
    }
}
