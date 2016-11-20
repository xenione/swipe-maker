package apps.xenione.com.swipelayout.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
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

    private final static String ALBUM_ARG = "album_arg";

    public static Fragment newInstance() {
        return new VerticalSwipeFragment();
    }

    @Bind(R.id.pager)
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vertical_swipe, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        viewPager.setAdapter(new AlbumPagerAdapter(getChildFragmentManager()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private static class AlbumPagerAdapter extends FragmentStatePagerAdapter {

        public AlbumPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return AlbumFragment.newInstance(Album.getAlbum().get(position));
        }

        @Override
        public int getCount() {
            return Album.getAlbum().size();
        }
    }

    public static class AlbumFragment extends Fragment {

        public static Fragment newInstance(Album album) {
            Fragment fragment = new AlbumFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(ALBUM_ARG, album);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Bind(R.id.title)
        TextView titleTV;
        @Bind(R.id.bandName)
        TextView bandNameTV;
        @Bind(R.id.bg_disc)
        ImageView discBg;
        @Bind(R.id.body_text)
        TextView bodyText;

        private Album getAlbumArg() {
            return (Album) getArguments().getSerializable(ALBUM_ARG);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_vertical_swipe_page, container, false);
            ButterKnife.bind(this, view);
            initViews();
            return view;
        }

        public void initViews() {
            Album album = getAlbumArg();
            titleTV.setText(album.getName());
            bandNameTV.setText(album.getBandName());
            Picasso.with(getContext()).load(album.getResource()).placeholder(R.color.placeholder).into(discBg);
            bodyText.setText(Html.fromHtml(album.getDescription()));
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
}
