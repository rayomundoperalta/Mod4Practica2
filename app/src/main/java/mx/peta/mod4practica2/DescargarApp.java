package mx.peta.mod4practica2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by rayo on 6/28/16.
 */
public class DescargarApp extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargar);

        /* Hacemos que se vea el toolbar en la pagina de descarga de aplicaciones
        *  hay que recordar que cada activity maneja su propio toolbar.
        */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.descarga_app);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.mod4practica2);
        getSupportActionBar().setDisplayShowHomeEnabled(false); // false oculta el icono, true lo muestra
    }

    /*
    Los metodos onCreateptionsMenu y onOptionsItemSelected son metodos que están en la
    activity, y se encargan de manejar el menu, aparecen el developers.android.com en la
    parte de menu y se explica como se deben manejar
 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.toolbar_menu_descargar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.descargar_menu1:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
