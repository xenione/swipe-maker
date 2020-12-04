package apps.xenione.com.swipelayout.example.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
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

/**
 * Created by Eugeni on 13/04/2016.
 */
public class VerticalSwipeFragment extends Fragment {

    public static final String TAG = "VerticalSwipeFragment";

    private final static String ALBUM_ARG = "album_arg";

    public static Fragment newInstance() {
        return new VerticalSwipeFragment();
    }

    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vertical_swipe, container, false);
        viewPager = view.findViewById(R.id.pager);
        initViews();
        return view;
    }

    private void initViews() {
        viewPager.setAdapter(new AlbumPagerAdapter(getChildFragmentManager()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

        TextView titleTV;
        TextView bandNameTV;
        ImageView discBg;
        TextView bodyText;

        private Album getAlbumArg() {
            return (Album) getArguments().getSerializable(ALBUM_ARG);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_vertical_swipe_page, container, false);
            titleTV = view.findViewById(R.id.title);
            bandNameTV = view.findViewById(R.id.bandName);
            discBg = view.findViewById(R.id.bg_disc);
            bodyText = view.findViewById(R.id.body_text);

            view.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), getString(R.string.delete_label), Toast.LENGTH_LONG).show();
                }
            });

            view.findViewById(R.id.action).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), getString(R.string.action_label), Toast.LENGTH_LONG).show();
                }
            });


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
        }
    }
}
