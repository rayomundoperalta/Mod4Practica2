package mx.peta.mod4practica2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by rayo on 6/27/16.
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
        getSupportActionBar().setDisplayShowHomeEnabled(true); // if false oculta el icono

        // getSupportActionBar().setHomeButtonEnabled(true);
    }

    /*
        Los metodos onCreateptionsMenu y onOptionsItemSelected son metodos que están en la
        activity, y se encargan de manejar el menu, aparecen el developers.android.com en la
        parte de menu y se explica como se deben manejar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toobar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu1:
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
