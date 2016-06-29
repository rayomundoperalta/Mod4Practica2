package mx.peta.mod4practica2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import mx.peta.mod4practica2.SQL.DataSource;
import mx.peta.mod4practica2.adapter.AdapterItemList;
import mx.peta.mod4practica2.model.ModelItem;
import mx.peta.mod4practica2.servicios.ServicioDescarga;
import mx.peta.mod4practica2.utileria.SystemMsg;

/**
 * Created by rayo on 6/28/16.
 */
public class DescargarApp extends AppCompatActivity implements View.OnClickListener {

    EditText nombreAplicacion;
    EditText descrpcion;
    EditText nombreDesarrollador;

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

        // inicializamos los campos editables que tienen la informacion de la descarga
        nombreAplicacion    = (EditText) findViewById(R.id.descargar_edittext_nombreAplicacion);
        descrpcion          = (EditText) findViewById(R.id.descargar_edittext_descripcion);
        nombreDesarrollador = (EditText) findViewById(R.id.descargar_edittext_nombreDesarrollador);

        // se inicializan los manejadores de eventos de los botones
        findViewById(R.id.descargar_btn_descarga).setOnClickListener(this);
    }

    /*
    Los metodos onCreateptionsMenu y onOptionsItemSelected son metodos que están en la
    activity, y se encargan de manejar el menu, estan documentados en developers.android.com en la
    parte de menu y se explica como se deben manejar
 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.toolbar_menu_descargar,menu);
        return true;
    }

    /*
        Aunque no se infla el menu el resto del toolbar esta presente y funcionando, por lo que es
        necesario atender al icono de home
     */
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

    @Override
    public void onClick(View view) {
        DataSource ds = new DataSource(getApplicationContext());
        switch (view.getId()) {
            //mandamos llamar al servicio que se va a encargar de descargar la aplicación
            //le pasamos los parametros necesarios para que se inicialize la BD en la simulación
            //el servicio se va a encargar de cargar la imagen aleatoria que le corresponda
            //a la aplicación y llenara el estado de la aplicación apropiado en la BD
            case R.id.descargar_btn_descarga:
                ModelItem modelItem = ds.getApp(nombreAplicacion.getText().toString());
                if (modelItem == null) {
                    Intent intent = new Intent(getApplicationContext(), ServicioDescarga.class);
                    intent.putExtra(ServicioDescarga.NOMBRE_APLICACION, nombreAplicacion.getText().toString());
                    intent.putExtra(ServicioDescarga.DESCRIPCION, descrpcion.getText().toString());
                    intent.putExtra(ServicioDescarga.NOMBRE_DESARROLLADOR, nombreDesarrollador.getText().toString());
                    startService(intent);
                    finish();
                } else
                    SystemMsg.msg(getApplicationContext(),getString(R.string.aplicacionrepetida));
                break;
        }
    }
}
