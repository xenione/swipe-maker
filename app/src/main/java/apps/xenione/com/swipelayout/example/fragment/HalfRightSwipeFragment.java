package apps.xenione.com.swipelayout.example.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import apps.xenione.com.swipelayout.R;
import apps.xenione.com.swipelayout.example.adapter.HalfRightSwipeAdapter;
import apps.xenione.com.swipelayout.example.data.Album;

/**
 * Created by Eugeni on 13/04/2016.
 */
public class HalfRightSwipeFragment extends Fragment implements HalfRightSwipeAdapter.OnItemDismissListener, HalfRightSwipeAdapter.OnItemSelectListener {

    public static final String TAG = "HalfRightSwipeFragment";

    public static Fragment newInstance() {
        return new HalfRightSwipeFragment();
    }

    private HalfRightSwipeAdapter mAdapter;

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
        mAdapter = new HalfRightSwipeAdapter(Album.getAlbum());
        mAdapter.setOnItemDismissListener(this);
        mAdapter.setOnItemItemSelectListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemDismissed(Album album) {
        mAdapter.deleteItem(album);
        Toast.makeText(getContext(), "item deleted with title :" + album.getName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(Album album) {
        Toast.makeText(getContext(), "item selected with title :" + album.getName(), Toast.LENGTH_LONG).show();

    }
}
