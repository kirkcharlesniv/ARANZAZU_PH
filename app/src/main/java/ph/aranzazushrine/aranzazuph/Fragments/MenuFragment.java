package ph.aranzazushrine.aranzazuph.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
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

import ph.aranzazushrine.aranzazuph.Adapters.MenuAdapter;
import ph.aranzazushrine.aranzazuph.Models.MenuItems;
import ph.aranzazushrine.aranzazuph.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    View v;
    private List<MenuItems> listMenu;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_menu, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        MenuAdapter menuAdapter = new MenuAdapter(getContext(), listMenu);

        menuAdapter.setOnItemClickListener(new MenuAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                MenuItems menuInfo = listMenu.get(position);
                Log.d("TAG", "onItemClick position: " + menuInfo.getTitle());
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(menuAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listMenu = new ArrayList<>();
        listMenu.add(new MenuItems(
                "Sign Out",
                R.drawable.ic_directions));
    }
}
