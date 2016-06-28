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

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        }
        ImageView imageR              = (ImageView) convertView.findViewById(R.id.list_item_imageR);
        TextView nombreDesarrollador  = (TextView) convertView.findViewById(R.id.list_item_nombreDesarrollador);
        TextView nombreAplicacion     = (TextView) convertView.findViewById(R.id.list_item_nombreAplicacion);
        TextView estado               = (TextView) convertView.findViewById(R.id.list_item_estado);

        ModelItem modelItem = getItem(position);
        imageR.setImageResource(modelItem.imagenR);
        nombreDesarrollador.setText(modelItem.nombreDesarrollador);
        nombreAplicacion.setText(modelItem.nombreAplicacion);
        estado.setText(modelItem.estado);
        return convertView;
    }
}
