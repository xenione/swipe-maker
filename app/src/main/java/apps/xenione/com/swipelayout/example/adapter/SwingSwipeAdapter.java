package apps.xenione.com.swipelayout.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import apps.xenione.com.swipelayout.R;
import apps.xenione.com.swipelayout.example.data.Album;
import apps.xenione.com.swipelayout.example.swipe.SwingCoordinatorLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class SwingSwipeAdapter extends RecyclerView.Adapter<SwingSwipeAdapter.ViewHolder> {

    public interface OnItemDismissListener {
        void onRightItemDismissed(int position);

        void onLeftItemDismissed(int position);
    }

    private List<Album> mAlbums;
    private OnItemDismissListener mOnItemDismissListener;

    public SwingSwipeAdapter(List<Album> albums) {
        this.mAlbums = albums;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_swing_swipe, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Album album = getItem(position);
        Picasso.with(context).load(album.getResource()).placeholder(R.color.placeholder).into(holder.discImage);
        holder.coordinatorLayout.setOnDismissListener(new OnItemDismiss(position));
        holder.title.setText(album.getName());
        holder.bandName.setText(album.getBandName());
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

    public void deleteItem(int position) {
        mAlbums.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    private Album getItem(int position) {
        return mAlbums.get(position);
    }

    public void setOnItemDismissListener(OnItemDismissListener listener) {
        mOnItemDismissListener = listener;
    }

    public class OnItemDismiss implements SwingCoordinatorLayout.OnDismissListener {

        private int position;

        public OnItemDismiss(int position) {
            this.position = position;
        }

        @Override
        public void onRightDismissed() {
            if (mOnItemDismissListener != null) {
                mOnItemDismissListener.onRightItemDismissed(position);
            }
        }

        @Override
        public void onLeftDismissed() {
            if (mOnItemDismissListener != null) {
                mOnItemDismissListener.onLeftItemDismissed(position);
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        public TextView title;
        @Bind(R.id.bandName)
        public TextView bandName;
        @Bind(R.id.bg_disc)
        public ImageView discImage;

        public SwingCoordinatorLayout coordinatorLayout;

        public ViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
            coordinatorLayout = (SwingCoordinatorLayout) view;
        }
    }
}