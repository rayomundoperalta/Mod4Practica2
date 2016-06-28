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

    public String  nombreDesarrollador;
    public String  nombreAplicacion;
    public int     imagenR;
    public int     estado;

    public ModelItem(String nombreDesarrollador, String nombreAplicacion, int imagenR,
                     int estado) {
        this.nombreDesarrollador = nombreDesarrollador;
        this.nombreAplicacion    = nombreAplicacion;
        this.imagenR             = imagenR;
        this.estado              = estado;
    }
}
