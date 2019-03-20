package ph.aranzazushrine.aranzazuph.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ph.aranzazushrine.aranzazuph.API.Maps;
import ph.aranzazushrine.aranzazuph.Adapters.MapsAdapter;
import ph.aranzazushrine.aranzazuph.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {
    View v;
    RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private MapsAdapter mapsAdapter;
    private List<Maps> listMaps;

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_events, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        MapsAdapter mapsAdapter = new MapsAdapter(getContext(), listMaps);
        mapsAdapter.setOnItemClickListener(new MapsAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Maps mapInfo = listMaps.get(position);
                Log.d("TAG", "onItemClick position: " + mapInfo.getmTitle());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mapsAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listMaps = new ArrayList<>();
        listMaps.add(new Maps("https://pbs.twimg.com/media/Du8aCxKUcAAmgXy.jpg", "Dulong Bayan Chapel", "Built in the 1980s style, Dulong Bayan Chapel is one of the most beautiful chapels San Mateo offers."));
        listMaps.add(new Maps("https://pbs.twimg.com/media/D1XSiSsX0AAk9gn.jpg", "Diocesan Shrine and Parish of Nuestra Señora de Aranzazu", "Sample Description. TODO."));
    }

}
