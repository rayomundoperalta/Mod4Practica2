package mx.peta.mod4practica2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import mx.peta.mod4practica2.SQL.DataSource;
import mx.peta.mod4practica2.model.ModelItem;
import mx.peta.mod4practica2.servicios.ServicioActualizar;
import mx.peta.mod4practica2.servicios.ServicioBorraApp;
import mx.peta.mod4practica2.servicios.ServicioDescarga;
import mx.peta.mod4practica2.servicios.ServicioEditar;
import mx.peta.mod4practica2.utileria.SystemMsg;

/**
 * Created by rayo on 6/29/16.
 */
public class ActivityShowDetail extends AppCompatActivity implements View.OnClickListener {
    public static final String CONTADOR_DESCARGAS = "contador_descargas";
    public static final String ACTION_DELETED_APP = "mx.peta.DELETED_APP";
    public static final String INTENT_APP_NAME    = "mx.peta.APP_NAME";
    public static final String ACTION_ACTUALIZADA_APP = "mx.peta.ACTUALIZADA_APP";

    int idDescarga = 0;
    ModelItem modelItem;

    ImageView showDetailImagen;
    EditText  showDetailNombreAplicacion;
    EditText  showDetailDescripcion;
    EditText  showDetailNombreDesarrollador;
    Button    showDetailBtnDesinstalar;
    Button    showDetailBtnAbrir;
    Button    ShowDetailBtnActualizar;
    CheckBox  ShowDetailChbActualizada;
    MenuItem  showDetailMeniEditar;

    DataSource ds;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String appName = intent.getExtras().getString(INTENT_APP_NAME);
            String tmp = showDetailNombreAplicacion.getText().toString();
            if (appName.equals(tmp)) {
                SystemMsg.msg(getApplicationContext(), getString(R.string.desinstalada));
            }
        }
    };

    private BroadcastReceiver broadcastReceiverActualizada = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String appName = intent.getExtras().getString(INTENT_APP_NAME);
            String tmp = showDetailNombreAplicacion.getText().toString();
            if (appName.equals(tmp)) {
                ShowDetailChbActualizada.setChecked(true);
                ShowDetailBtnActualizar.setEnabled(false);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.mod4practica2);
        getSupportActionBar().setDisplayShowHomeEnabled(false); // if false oculta el icono

        ds = new DataSource(getApplicationContext());

        /*
            Recuperamos la información necesaria acerca de la aplicación
            con esto podemos modificar la base de datos
         */
        modelItem = new ModelItem(getIntent().getExtras().getString(ModelItem.APP_NAME),
                                  getIntent().getExtras().getString(ModelItem.APP_DESCRIPTION),
                                  getIntent().getExtras().getString(ModelItem.APP_DESARROLLADOR),
                                  getIntent().getExtras().getInt(ModelItem.APP_ICONO),
                                  getIntent().getExtras().getInt(ModelItem.APP_ESTADO));
        modelItem.id = getIntent().getExtras().getInt(ModelItem.APP_ID);

        /*
            Inicializamos la interface grafica
         */
        showDetailImagen              = (ImageView) findViewById(R.id.show_detail_imagen);
        showDetailNombreAplicacion    = (EditText)  findViewById(R.id.show_detail_nombreAplicacion);
        showDetailDescripcion         = (EditText)  findViewById(R.id.show_detail_descripcion);
        showDetailNombreDesarrollador = (EditText)  findViewById(R.id.show_detail_nombreDesarrollador);
        showDetailBtnDesinstalar      = (Button)    findViewById(R.id.show_detail_btn_desinstalar);
        showDetailBtnAbrir            = (Button)    findViewById(R.id.show_detail_btn_abrir);
        ShowDetailBtnActualizar       = (Button)    findViewById(R.id.show_detail_btn_actualizar);
        ShowDetailChbActualizada      = (CheckBox)  findViewById(R.id.show_detail_actualizada);

        showDetailImagen.setImageResource(modelItem.appIcono);
        showDetailNombreAplicacion.setText(modelItem.appName);
        showDetailDescripcion.setText(modelItem.appDescripcion);
        showDetailNombreDesarrollador.setText(modelItem.appDesarrollador);

        if (modelItem.appEstado == ModelItem.ACTUALIZADA) {  // la aplicacion esta actualizada
            // Hay que inhibir el boton de actualizar y el checkbox
            ShowDetailChbActualizada.setChecked(true);
            ShowDetailBtnActualizar.setEnabled(false);
        }

        showDetailBtnDesinstalar.setOnClickListener(this);
        showDetailBtnAbrir.setOnClickListener(this);
        ShowDetailBtnActualizar.setOnClickListener(this);

        ShowDetailChbActualizada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modelItem.appEstado == ModelItem.ACTUALIZADA) {  // la aplicacion esta actualizada
                    // Hay que inhibir el boton de actualizar y el checkbox
                    ShowDetailChbActualizada.setChecked(true);
                    ShowDetailBtnActualizar.setEnabled(false);
                }
            }
        });


    }

    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_DELETED_APP);
        registerReceiver(broadcastReceiver, filter);
        IntentFilter filtroActualizada = new IntentFilter();
        filtroActualizada.addAction(ACTION_ACTUALIZADA_APP);
        registerReceiver(broadcastReceiverActualizada, filtroActualizada);
    }

    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(broadcastReceiverActualizada);
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
            unicamente no se muestra el toolbar sin el menu, funciona perfectamente
         */
        getMenuInflater().inflate(R.menu.toolbar_menu_detalle,menu);
        showDetailMeniEditar = (MenuItem) menu.findItem(R.id.detalle_menu1);
        return true;
    }

    private void lanzaServicio(Class<?> cls, String msg) {
        if (showDetailNombreAplicacion.getText().toString().isEmpty() ||
                showDetailDescripcion.getText().toString().isEmpty() ||
                showDetailNombreDesarrollador.getText().toString().isEmpty())
            SystemMsg.msg(getApplicationContext(), getString(R.string.capos_obligatorios));
        else {
            ModelItem modelItem = ds.getApp(showDetailNombreAplicacion.getText().toString());
            if (modelItem != null) { // significa que esta en la base de datos
                Intent intent = new Intent(getApplicationContext(), cls);
                intent.putExtra(ServicioActualizar.ID, modelItem.id);
                intent.putExtra(ServicioActualizar.NOMBRE_APLICACION, showDetailNombreAplicacion.getText().toString());
                intent.putExtra(ServicioActualizar.DESCRIPCION, showDetailDescripcion.getText().toString());
                intent.putExtra(ServicioActualizar.NOMBRE_DESARROLLADOR, showDetailNombreDesarrollador.getText().toString());
                intent.putExtra(ServicioActualizar.ICONO, modelItem.appIcono);
                intent.putExtra(ActivityShowDetail.CONTADOR_DESCARGAS, idDescarga++);
                startService(intent);
                // finish();
            } else
                SystemMsg.msg(getApplicationContext(), msg);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
            El toolbar se procesa aqui
         */
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.detalle_menu1: // descarga aplicación
                Log.d("petaplay", "detectamos menu editar descripcion");
                lanzaServicio(ServicioEditar.class, getString(R.string.aplicacion_desconicida));
                return true;
            //case R.id.menu2:
            //    return true;
            //case R.id.menu3:
            //    return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_detail_btn_desinstalar:

                new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.delete_app))
                    .setMessage(String.format(getString(R.string.Desinstalar_pregunta),modelItem.appName))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showDetailBtnDesinstalar.setEnabled(false);
                                showDetailBtnAbrir.setEnabled(false);
                                ShowDetailBtnActualizar.setEnabled(false);
                                showDetailMeniEditar.setEnabled(false);
                                ShowDetailChbActualizada.setEnabled(false);
                                lanzaServicio(ServicioBorraApp.class, getString(R.string.aplicacion_desconicida));

                            }
                        })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setCancelable(false)
                    .create().show();

                break;
            case R.id.show_detail_btn_abrir:
                // Log.d("petaplay", "boton abrir");
                /*
                    La URL tiene se estar completa i.e.
                    http://yahoo.com/ y no yahoo.com
                    el error de dirección aparece en el startActivity y no en el parse
                    bizarro.
                 */
                Uri uriUrl = Uri.parse(getString(R.string.url));
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
                break;
            case R.id.show_detail_btn_actualizar:
                // Log.d("petaplay", "boton actualizar");
                lanzaServicio(ServicioActualizar.class, getString(R.string.aplicacion_desconicida));

                break;
        }
    }
}
