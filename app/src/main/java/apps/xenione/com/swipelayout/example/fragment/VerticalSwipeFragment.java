package apps.xenione.com.swipelayout.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import apps.xenione.com.swipelayout.R;
import apps.xenione.com.swipelayout.example.data.Album;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Eugeni on 13/04/2016.
 */
public class VerticalSwipeFragment extends Fragment {

    public static final String TAG = "VerticalSwipeFragment";

    @Bind(R.id.title)
    TextView titleTV;
    @Bind(R.id.bandName)
    TextView bandNameTV;
    @Bind(R.id.bg_disc)
    ImageView discBg;
    @Bind(R.id.body_text)
    TextView bodyText;

    public static Fragment newInstance() {
        return new VerticalSwipeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vertical_swipe, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    public void initViews() {
        Album album = Album.getAlbum().get(0);
        titleTV.setText(album.getName());
        bandNameTV.setText(album.getBandName());
        Picasso.with(getContext()).load(album.getResource()).placeholder(R.color.placeholder).into(discBg);
        bodyText.setText(Html.fromHtml(getString(R.string.gossa_sorda_intro)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.delete)
    public void delete() {
        Toast.makeText(getActivity(), getString(R.string.delete_label), Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.action)
    public void action() {
        Toast.makeText(getActivity(), getString(R.string.action_label), Toast.LENGTH_LONG).show();
    }
}
