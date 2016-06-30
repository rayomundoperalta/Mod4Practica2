package mx.peta.mod4practica2.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import mx.peta.mod4practica2.ActivityShowDetail;
import mx.peta.mod4practica2.R;
import mx.peta.mod4practica2.SQL.DataSource;
import mx.peta.mod4practica2.adapter.AdapterItemList;
import mx.peta.mod4practica2.model.ModelItem;

/**
 * Created by rayo on 6/29/16.
 */
public class FragmentoLista extends Fragment {
    ListView listView;
    private List<ModelItem> array = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lista_fragmento, container, false);

        // Llenamos la lista de la actividad con la información de la base de datos
        DataSource ds = new DataSource(getContext());
        listView = (ListView) view.findViewById(R.id.lista_fragmento_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AdapterItemList adapter = (AdapterItemList) parent.getAdapter();
                ModelItem modelItem = adapter.getItem(position);
                Intent intent = new Intent(getActivity().getApplicationContext(), ActivityShowDetail.class);
                intent.putExtra(ModelItem.APP_ID, modelItem.id);
                intent.putExtra(ModelItem.APP_NAME, modelItem.appName);
                intent.putExtra(ModelItem.APP_DESCRIPTION, modelItem.appDescripcion);
                intent.putExtra(ModelItem.APP_DESARROLLADOR, modelItem.appDesarrollador);
                intent.putExtra(ModelItem.APP_ICONO, modelItem.appIcono);
                intent.putExtra(ModelItem.APP_ESTADO, modelItem.appEstado);
                startActivity(intent);
            }
        });

        List<ModelItem> modelItemList = ds.getAllItems();

        listView.setAdapter(new AdapterItemList(getContext(),modelItemList));
        return view;
    }
}
