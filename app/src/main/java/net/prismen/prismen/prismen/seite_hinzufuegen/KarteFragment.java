package net.prismen.prismen.prismen.seite_hinzufuegen;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.prismen.prismen.R;

/**
 * Created by iri on 07.01.16.
 */
public class KarteFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_seite_typ_karte,container,false);
        return view;
    }
}
