package mx.peta.mod4practica2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import mx.peta.mod4practica2.R;
import mx.peta.mod4practica2.model.ModelItem;

/**
 * Created by rayo on 6/27/16.
 */
public class AdapterItemList extends ArrayAdapter<ModelItem> {
    public AdapterItemList(Context context, List<ModelItem> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        }
        /*
            Inicializamos los widgets del list_item
         */
        ImageView icono               = (ImageView) convertView.findViewById(R.id.list_item_imageR);
        TextView nombreDesarrollador  = (TextView)  convertView.findViewById(R.id.list_item_nombreDesarrollador);
        TextView nombreAplicacion     = (TextView)  convertView.findViewById(R.id.list_item_nombreAplicacion);
        TextView descripcion          = (TextView)  convertView.findViewById(R.id.list_item_descripcion);
        TextView estado               = (TextView)  convertView.findViewById(R.id.list_item_estado);

        ModelItem modelItem = getItem(position);
        icono.setImageResource(modelItem.appIcono);
        nombreDesarrollador.setText(modelItem.appDesarrollador);
        descripcion.setText(modelItem.appDescripcion);
        nombreAplicacion.setText(modelItem.appName);
        switch (modelItem.appEstado) {
            case ModelItem.ACTUALIZADA:
                estado.setText(convertView.getResources().getString(R.string.actualizada));
                break;
            case ModelItem.INSTALADA:
                estado.setText(convertView.getResources().getString(R.string.instalada));
                break;
            case ModelItem.POR_ACTUALIZAR:
                estado.setText(convertView.getResources().getString(R.string.por_actualizar));
                break;
            case ModelItem.NO_INSTALADA:
                estado.setText(convertView.getResources().getString(R.string.noinstalada));
                break;
        }

        return convertView;
    }
}
