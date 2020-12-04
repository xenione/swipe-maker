package apps.xenione.com.swipelayout.example.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import apps.xenione.com.swipelayout.R;
import apps.xenione.com.swipelayout.example.data.Album;
import apps.xenione.com.swipelayout.example.swipe.TwoStepRightCoordinatorLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class TwoStepRightSwipeAdapter extends RecyclerView.Adapter<TwoStepRightSwipeAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemDismissed(Album album);

        void onItemAction(Album album);
    }

    private List<Album> mAlbums;
    private OnItemClickListener mOnItemClickListener;

    public TwoStepRightSwipeAdapter(List<Album> albums) {
        this.mAlbums = albums;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_two_step_right_swipe, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Album album = getItem(position);
        Picasso.with(context).load(album.getResource()).placeholder(R.color.placeholder).into(holder.discImage);
        holder.title.setText(album.getName());
        holder.bandName.setText(album.getBandName());
        holder.delete.setOnClickListener(new OnDismissListener(album));
        holder.action.setOnClickListener(new OnActionListener(album));
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.coordinatorLayout.sync();
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void deleteItem(Album album) {
        int index = mAlbums.indexOf(album);
        if (index == -1) {
            return;
        }
        mAlbums.remove(index);
        notifyItemRemoved(index);
        notifyItemRangeChanged(index, getItemCount());
    }

    private Album getItem(int position) {
        return mAlbums.get(position);
    }

    private class OnDismissListener implements View.OnClickListener {

        private Album album;

        public OnDismissListener(Album album) {
            this.album = album;
        }

        @Override
        public void onClick(View v) {
            mOnItemClickListener.onItemDismissed(album);
        }
    }

    private class OnActionListener implements View.OnClickListener {

        private Album album;

        public OnActionListener(Album album) {
            this.album = album;
        }

        @Override
        public void onClick(View v) {
            mOnItemClickListener.onItemAction(album);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TwoStepRightCoordinatorLayout coordinatorLayout;
        @Bind(R.id.title)
        public TextView title;
        @Bind(R.id.bandName)
        public TextView bandName;
        @Bind(R.id.bg_disc)
        public ImageView discImage;
        @Bind(R.id.delete)
        public ImageButton delete;
        @Bind(R.id.action)
        public ImageButton action;

        public ViewHolder(View view) {
            super(view);
            coordinatorLayout = (TwoStepRightCoordinatorLayout) view;
            ButterKnife.bind(this, view);
        }
    }
}