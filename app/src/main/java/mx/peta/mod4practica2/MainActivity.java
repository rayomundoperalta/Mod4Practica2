package mx.peta.mod4practica2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
            Esta actividad unicamente muestra el nombre de la aplicación y simula que se está
            inicializando, transfiere el control a la ActivityList que ya tiene funcionalidad
         */
        Handler h = new Handler();
        // new Handler()
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), ActivityList.class);
                startActivity(intent);
                finish();
            }
        }, 1000 * 2);
    }


}
