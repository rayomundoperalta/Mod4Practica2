package mx.peta.mod4practica2.servicios;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Random;

import mx.peta.mod4practica2.R;
import mx.peta.mod4practica2.SQL.DataSource;
import mx.peta.mod4practica2.model.ModelItem;


/**
 * Created by rayo on 6/28/16.
 */
public class ServicioDescarga extends Service {

    public static final String NOMBRE_APLICACION    = "nombreAplicacion";
    public static final String DESCRIPCION          = "descripcion";
    public static final String NOMBRE_DESARROLLADOR = "nombreDesarrollador";

    private static final int INTERVALOS_ESPERA = 6;

    private ModelItem modelItem;

    private MyAsyncTask myAsyncTask;

    private DataSource ds;

    private static final int MIN = 1;
    private static final int MAX = 10;
    Random r = new Random();

    /*  Como generar numeros al azar
        int min = 65;
        int max = 80;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
     */

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int idIcono = 0;
        // Siempre que se llame al servicio se tienen que incluir parametros en el intent
        Log.d("petaplay", "se arranca el servicio");
        // es necesario escoger aleatoriamente el icono que se le asocia
        switch (r.nextInt(MAX - MIN + 1) + MIN) {
            case 1:
                idIcono = R.drawable.icono1;
                break;
            case 2:
                idIcono = R.drawable.icono2;
                break;
            case 3:
                idIcono = R.drawable.icono3;
                break;
            case 4:
                idIcono = R.drawable.icono4;
                break;
            case 5:
                idIcono = R.drawable.icono5;
                break;
            case 6:
                idIcono = R.drawable.icono6;
                break;
            case 7:
                idIcono = R.drawable.icono7;
                break;
            case 8:
                idIcono = R.drawable.icono8;
                break;
            case 9:
                idIcono = R.drawable.icono9;
                break;
            case 10:
                idIcono = R.drawable.icono10;
                break;
        }
        modelItem = new ModelItem(intent.getExtras().getString(NOMBRE_APLICACION),
                                  intent.getExtras().getString(DESCRIPCION),
                                  intent.getExtras().getString(NOMBRE_DESARROLLADOR),
                                  idIcono, ModelItem.INSTALADA);
        ds = new DataSource(getApplicationContext());

        if (myAsyncTask == null)
        {
            myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute();
        }

        return START_STICKY;
    }

    private class MyAsyncTask extends AsyncTask<Integer,Integer,Boolean>
    {
        private NotificationCompat.Builder mNotif;

        @Override
        protected void onPreExecute() {

            mNotif = new NotificationCompat
                    .Builder(getApplicationContext())
                    .setContentTitle(getString(R.string.descargando_aplicacion))
                    .setContentText(getString(R.string.descargando) + " " + modelItem.appName)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), modelItem.appIcono))
                    .setSmallIcon(R.drawable.iconbajando);
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            for (int i = 0; i < INTERVALOS_ESPERA; i++)
            {
                publishProgress(i);
                try {
                    Thread.sleep(1000 * 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mNotif.setProgress(INTERVALOS_ESPERA, values[0], false);
            NotificationManager manager  = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0,mNotif.build());
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result)
            {
                mNotif.setProgress(0,0,false); //elimina progres cuando lo seteamos a 0
                mNotif.setContentTitle(getString(R.string.descarga_completa));
                mNotif.setContentText(getString(R.string.descarga_completa_largo) + " " + modelItem.appName);
                mNotif.setContentInfo(getString(R.string.descargado));
                mNotif.setAutoCancel(true);
                mNotif.setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.descarga_completa_largo) + " " + modelItem.appName));

                NotificationManager manager  = (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0,mNotif.build());

                /*
                    En este punto lo unico que hay que hacer es almacenar la nueva app en la
                    base de datos de aplicaciones
                 */
                ds.writeApp(modelItem);
                /*
                    Investigar como avisarle a la otra actividad que se actualizó la base de datos
                 */
            }
            myAsyncTask=null;
            stopSelf();
        }
    }

}
