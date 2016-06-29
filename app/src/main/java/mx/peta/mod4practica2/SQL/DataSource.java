package mx.peta.mod4practica2.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mx.peta.mod4practica2.model.ModelItem;

/**
 * Created by rayo on 6/28/16.
 */
public class DataSource {
    private static SQLiteDatabase db;

    public DataSource(Context context) {
        SqLiteHelper helper = new SqLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    public void writeApp(ModelItem app) {
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(SqLiteHelper.APP_COLUMN_NAME, app.appName);
            values.put(SqLiteHelper.APP_COLUMN_DESC, app.appDescripcion);
            values.put(SqLiteHelper.APP_COLUMN_DESARROLLADOR, app.appDesarrollador);
            values.put(SqLiteHelper.APP_COLUMN_ICONO, app.appIcono);
            values.put(SqLiteHelper.APP_COLUMN_ESTADO, app.appEstado);
            db.insert(SqLiteHelper.APP_TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /*
        Vamos a recuperar el password de un usuario que esta dado de alta en la base de datos
        si no se encuentra al usuario hay que regresar null
        El query debe ser: select password from table where User = user
     */
    public ModelItem getApp(String appName) {
        String QUERY_PASSWORD = "select " +
                SqLiteHelper.APP_COLUMN_NAME          + ", " +
                SqLiteHelper.APP_COLUMN_DESC          + ", " +
                SqLiteHelper.APP_COLUMN_DESARROLLADOR + ", " +
                SqLiteHelper.APP_COLUMN_ICONO         + ", " +
                SqLiteHelper.APP_COLUMN_ESTADO        +
                " from " + SqLiteHelper.APP_TABLE_NAME + " where " + SqLiteHelper.APP_COLUMN_NAME +
                " = ?";
        // rawQuery("SELECT id, name FROM people WHERE name = ? AND id = ?", new String[] {"David", "2"});
        Cursor cursor = db.rawQuery(QUERY_PASSWORD, new String[] {appName});
        if (cursor.moveToFirst())
            return new ModelItem( cursor.getString(cursor.getColumnIndexOrThrow(SqLiteHelper.APP_COLUMN_NAME)),
                                  cursor.getString(cursor.getColumnIndexOrThrow(SqLiteHelper.APP_COLUMN_DESC)),
                                  cursor.getString(cursor.getColumnIndexOrThrow(SqLiteHelper.APP_COLUMN_DESARROLLADOR)),
                                  cursor.getInt(cursor.getColumnIndexOrThrow(SqLiteHelper.APP_COLUMN_ICONO)),
                                  cursor.getInt(cursor.getColumnIndexOrThrow(SqLiteHelper.APP_COLUMN_ESTADO)));
        else
            return null;
    }

    public void deleteItem(ModelItem modelItem)
    {
        db.delete(SqLiteHelper.APP_TABLE_NAME,SqLiteHelper.APP_COLUMN_ID + "=?",
                new String[]{String.valueOf(modelItem.id)});
    }

    public static List<ModelItem> getAllItems()
    {
        List<ModelItem> modelItemList = new ArrayList<>();
        Cursor cursor = db.query(SqLiteHelper.APP_TABLE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            int id                  = cursor.getInt(cursor.getColumnIndexOrThrow(SqLiteHelper.APP_COLUMN_ID));
            String appName          = cursor.getString(cursor.getColumnIndexOrThrow(SqLiteHelper.APP_COLUMN_NAME));
            String appDesccription  = cursor.getString(cursor.getColumnIndexOrThrow(SqLiteHelper.APP_COLUMN_DESC));
            String appDesarrollador = cursor.getString(cursor.getColumnIndexOrThrow(SqLiteHelper.APP_COLUMN_DESARROLLADOR));
            int appIcono            = cursor.getInt(cursor.getColumnIndexOrThrow(SqLiteHelper.APP_COLUMN_ICONO));
            int appEstado           = cursor.getInt(cursor.getColumnIndexOrThrow(SqLiteHelper.APP_COLUMN_ESTADO));

            ModelItem modelItem   = new ModelItem(appName, appDesccription, appDesarrollador, appIcono, appEstado);
            modelItem.id          = id;

            modelItemList.add(modelItem);
        }
        return modelItemList;
    }
}
