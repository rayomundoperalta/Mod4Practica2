package mx.peta.mod4practica2.fragmentos;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.peta.mod4practica2.R;

/**
 * Created by rayo on 6/29/16.
 */
public class FragmentoListaVacia extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lista_vacia, container, false);
        return view;
    }
}
