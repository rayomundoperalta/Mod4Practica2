package mx.peta.mod4practica2.fragmentos;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import mx.peta.mod4practica2.R;
import mx.peta.mod4practica2.SQL.DataSource;
import mx.peta.mod4practica2.adapter.AdapterItemList;
import mx.peta.mod4practica2.model.ModelItem;

/**
 * Created by rayo on 6/29/16.
 */
public class FragmentoLista extends Fragment {
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lista_fragmento, container, false);

        // Llenamos la lista de la actividad con la información de la base de datos
        DataSource ds = new DataSource(getContext());
        listView = (ListView) view.findViewById(R.id.lista_fragmento_listview);

        List<ModelItem> modelItemList = ds.getAllItems();

        listView.setAdapter(new AdapterItemList(getContext(),modelItemList));
        return view;
    }
}
