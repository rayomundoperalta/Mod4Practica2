package mx.peta.mod4practica2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by rayo on 6/27/16.
 * La pagina principal de la app es un activity
 * para descargar app nos iremos a otro activity que se encargará de manejar la descarga
 * para comunicarse de un activity a otro usaremos la base de datos
 * o sea la actividad principal siempre mostrará el contenido de la base de datos
 */
public class ActivityList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        /*
            Esta sección se encraga de manejar el toolbar
            el toolbar aparecera en donde lo situe el xml, se puede poner arriba, en medio o hasta
            abajo. Esto demuestra que el manejo de los eventos esta desligado de la parte grafica.
            Los items del menu se definen en el archivo xml
        */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.mod4practica2);
        getSupportActionBar().setDisplayShowHomeEnabled(false); // if false oculta el icono
        // getSupportActionBar().setHomeButtonEnabled(true);

    }

    /*
        Los metodos onCreateptionsMenu y onOptionsItemSelected son metodos que están en la
        activity, y se encargan de manejar el menu, aparecen el developers.android.com en la
        parte de menu y se explica como se deben manejar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
            la linea siguiente infla el menu del toolbar, si no se llama a inflate
            unicamente no se muestra el menu pero el toolbar funciona perfectamente
         */
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu1: // descarga aplicación
                // arrancamos la actividad DescargarApp para manejar la descarga de aplicaciones
                // cada actividad manejara su propio toolbar para tener la facilidad de
                // cambiar el contenido de los menus
                Intent intent = new Intent(getApplicationContext(), DescargarApp.class);
                startActivity(intent);
                return true;
            case R.id.menu2:
                return true;
            case R.id.menu3:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
