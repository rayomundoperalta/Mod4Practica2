package mx.peta.mod4practica2.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by rayo on 6/28/16.
 */
public class SqLiteHelper  extends SQLiteOpenHelper {
    private final static String APP_DATABASE_NAME        = "mod4practica2_apps_db";
    private final static int    APP_DATABASE_VERSION     = 1;
    public static final String  APP_TABLE_NAME           = "app_table";
    public static final String  APP_COLUMN_ID            = BaseColumns._ID;
    public static final String  APP_COLUMN_NAME          = "app_name";
    public static final String  APP_COLUMN_DESC          = "app_descr";
    public static final String  APP_COLUMN_DESARROLLADOR = "app_desarrollador";
    public static final String  APP_COLUMN_ICONO         = "app_icono";
    public static final String  APP_COLUMN_ESTADO        = "app_estado";

    private static final String CREATE_APP_TABLE   = "create table " + APP_TABLE_NAME + "(" +
            APP_COLUMN_ID +            " integer primary key autoincrement, " +
            APP_COLUMN_NAME +          " text not null, " +
            APP_COLUMN_DESC +          " text not null, " +
            APP_COLUMN_DESARROLLADOR + " text not null, " +
            APP_COLUMN_ICONO +         " integer," +
            APP_COLUMN_ESTADO +        " integer ) ";

    public SqLiteHelper(Context context) {
        super(context, APP_DATABASE_NAME, null, APP_DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_APP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

