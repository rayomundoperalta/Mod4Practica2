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

import mx.peta.mod4practica2.ActivityList;
import mx.peta.mod4practica2.ActivityShowDetail;
import mx.peta.mod4practica2.R;
import mx.peta.mod4practica2.SQL.DataSource;
import mx.peta.mod4practica2.model.ModelItem;

/**
 * Created by rayo on 7/1/16.
 */
public class ServicioEditar extends Service {
    public static final String ID                   = "id";
    public static final String NOMBRE_APLICACION    = "nombreAplicacion";
    public static final String DESCRIPCION          = "descripcion";
    public static final String NOMBRE_DESARROLLADOR = "nombreDesarrollador";
    public static final String ICONO                = "icono";
    public static final int    INTERVALOS_ESPERA    = 3;

    private ModelItem modelItem;

    private MyAsyncTask myAsyncTask;

    private DataSource ds;

    private int idDescarga;

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
        if(intent.getExtras()!=null && intent.getExtras().containsKey(ActivityList.CONTADOR_DESCARGAS)) {
            modelItem = new ModelItem(intent.getExtras().getString(NOMBRE_APLICACION),
                    intent.getExtras().getString(DESCRIPCION),
                    intent.getExtras().getString(NOMBRE_DESARROLLADOR),
                    intent.getExtras().getInt(ICONO),
                    ModelItem.INSTALADA);
            modelItem.id = intent.getExtras().getInt(ID);
            idDescarga = intent.getExtras().getInt(ActivityShowDetail.CONTADOR_DESCARGAS);
        } else
            Log.d("petaplay", "pasamos por el else que no entiendo");
        ds = new DataSource(getApplicationContext());

        if (myAsyncTask == null)
        {
            myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute();
            stopSelf();
        }
        return START_STICKY;
    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, Boolean> {
        private NotificationCompat.Builder mNotif;

        @Override
        protected void onPreExecute() {
            mNotif = new NotificationCompat
                    .Builder(getApplicationContext())
                    .setContentTitle(getString(R.string.editando_aplicacion))
                    .setContentText(getString(R.string.editada) + " " + modelItem.appName)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), modelItem.appIcono))
                    .setSmallIcon(R.drawable.iconbajando);
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {
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
            manager.notify(idDescarga,mNotif.build());
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                mNotif.setProgress(0, 0, false); //elimina progres cuando lo seteamos a 0
                mNotif.setContentTitle(getString(R.string.edicion_completa));
                mNotif.setContentText(modelItem.appName);
                mNotif.setContentInfo(getString(R.string.editada));
                mNotif.setAutoCancel(true);
                mNotif.setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.edicion_completa_largo) + " " + modelItem.appName));

                PendingIntent pendingIntent = PendingIntent.
                        getActivity(getApplicationContext(),
                                0, new Intent(getApplicationContext(),
                                        ActivityList.class), PendingIntent.FLAG_UPDATE_CURRENT);
                mNotif.setContentIntent(pendingIntent);

                NotificationManager manager = (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(idDescarga, mNotif.build());

                /*
                    En este punto lo unico que hay que hacer es almacenar la nueva app en la
                    base de datos de aplicaciones
                 */
                ds.updateApp(modelItem);
                /*
                    Investigar como avisarle a la otra actividad que se actualizó la base de datos
                 */
                Intent i = new Intent(ActivityList.ACTION_UPDATED_DB);
                sendBroadcast(i);
            }
            myAsyncTask = null;
            stopSelf();
        }
    }
}
