package mx.peta.mod4practica2.utileria;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by rayo on 6/28/16.
 */
public class SystemMsg {
    public static void msg(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
