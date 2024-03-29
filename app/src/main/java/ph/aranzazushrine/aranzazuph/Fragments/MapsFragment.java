package ph.aranzazushrine.aranzazuph.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ph.aranzazushrine.aranzazuph.Adapters.MapsAdapter;
import ph.aranzazushrine.aranzazuph.MapsDetailActivity;
import ph.aranzazushrine.aranzazuph.Models.Maps;
import ph.aranzazushrine.aranzazuph.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment {
    View v;
    private List<Maps> listMaps;

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_maps, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        MapsAdapter mapsAdapter = new MapsAdapter(getContext(), listMaps);

        mapsAdapter.setOnItemClickListener(new MapsAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                ImageView imageView = v.findViewById(R.id.mapImage);
                TextView titleText = v.findViewById(R.id.mapTitle);
                TextView descText = v.findViewById(R.id.mapDesc);

                Maps mapInfo = listMaps.get(position);
                Log.d("TAG", "onItemClick position: " + mapInfo.getTitle());

                Intent intent = new Intent(getContext(), MapsDetailActivity.class);

                intent.putExtra("image", mapInfo.getImage());
                intent.putExtra("title", mapInfo.getTitle());
                intent.putExtra("desc", mapInfo.getDesc());
                intent.putExtra("directions", mapInfo.getDirections());
                intent.putExtra("contacts", mapInfo.getContacts());

                Pair<View, String> pair = Pair.create((View) imageView, ViewCompat.getTransitionName(imageView));
                Pair<View, String> headerPair = Pair.create((View) titleText, ViewCompat.getTransitionName(titleText));
                Pair<View, String> descPair = Pair.create((View) descText, ViewCompat.getTransitionName(descText));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(Objects.requireNonNull(getActivity()), pair, headerPair, descPair);

                startActivity(intent, optionsCompat.toBundle());
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

        listMaps.add(new Maps(
                "https://upload.wikimedia.org/wikipedia/commons/8/8c/Santuario_de_Nuestra_Se%C3%B1ora_de_Ar%C3%A1nzazu.jpg",
                "Diocesan Shrine and Parish of Nuestra Señora de Aranzazu",
                "Lorem Ipsum Sample Text",
                "Coordinates",
                "+639451494339"));

        listMaps.add(new Maps(
                "",
                "Our Lady of Fatima",
                "",
                "",
                ""));

        listMaps.add(new Maps(
                "",
                "Our Lady of Manaog",
                "",
                "",
                ""));

        listMaps.add(new Maps(
                "",
                "Sta. Ana",
                "",
                "",
                ""));

        listMaps.add(new Maps(
                "",
                "Sr. Sto. Cristo Burgos",
                "",
                "",
                ""));

        listMaps.add(new Maps(
                "",
                "Child Jesus",
                "",
                "",
                ""));

        listMaps.add(new Maps(
                "",
                "Padre Pio",
                "",
                "",
                ""));

        listMaps.add(new Maps(
                "",
                "Our Lady of Lourdes",
                "",
                "",
                ""));

        listMaps.add(new Maps(
                "",
                "San Jose Mangagagawa",
                "",
                "",
                ""));

        listMaps.add(new Maps(
                "",
                "San Andres",
                "",
                "",
                ""));

        listMaps.add(new Maps(
                "",
                "Mother of Perpetual Help",
                "",
                "",
                ""));

        listMaps.add(new Maps(
                "",
                "Sta Clara",
                "",
                "",
                ""));

        listMaps.add(new Maps(
                "",
                "Immaculate Heart of Mary",
                "",
                "",
                ""));

        listMaps.add(new Maps(
                "",
                "Divine Mercy",
                "",
                "",
                ""));

        listMaps.add(new Maps(
                "",
                "San Miguel",
                "",
                "",
                ""));

        listMaps.add(new Maps(
                "",
                "Holy Family",
                "",
                "",
                ""));
    }

}
