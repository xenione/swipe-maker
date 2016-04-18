package apps.xenione.com.swipelayout.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import apps.xenione.com.swipelayout.R;
import apps.xenione.com.swipelayout.example.adapter.RightSwipeAdapter;
import apps.xenione.com.swipelayout.example.data.Album;

/**
 * Created by Eugeni on 13/04/2016.
 */
public class RightSwipeFragment extends Fragment implements RightSwipeAdapter.OnItemDismissListener, RightSwipeAdapter.OnItemSelectListener {

    public static final String TAG = "RightSwipeFragment";

    public static Fragment newInstance() {
        return new RightSwipeFragment();
    }

    private RightSwipeAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swipe, container, false);
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.swipe_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RightSwipeAdapter(Album.getAlbum());
        mAdapter.setOnItemDismissListener(this);
        mAdapter.setOnItemItemSelectListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemDismissed(int position) {
        mAdapter.deleteItem(position);
        Toast.makeText(getContext(), "item deleted at position : " + position, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(int position) {
        Toast.makeText(getContext(), "item selected at position : " + position, Toast.LENGTH_LONG).show();
    }
}
