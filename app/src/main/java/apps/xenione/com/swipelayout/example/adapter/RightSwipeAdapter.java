package apps.xenione.com.swipelayout.example.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import apps.xenione.com.swipelayout.R;
import apps.xenione.com.swipelayout.example.data.Album;
import apps.xenione.com.swipelayout.example.swipe.RightCoordinatorLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class RightSwipeAdapter extends RecyclerView.Adapter<RightSwipeAdapter.ViewHolder> {

    public interface OnItemDismissListener {
        void onItemDismissed(Album album);
    }

    public interface OnItemSelectListener {
        void onItemSelected(Album album);
    }

    private List<Album> mAlbums;
    private OnItemDismissListener mOnItemDismissListener;
    private OnItemSelectListener mOnItemSelectListener;

    public RightSwipeAdapter(List<Album> albums) {
        this.mAlbums = albums;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_right_swipe, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Album album = getItem(position);
        Picasso.with(context).load(album.getResource()).placeholder(R.color.placeholder).into(holder.discImage);
        holder.foreground.setOnClickListener(new OnItemSelectedClick(album));
        holder.coordinatorLayout.setOnDismissListener(new OnItemDismiss(album));
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

    public void setOnItemDismissListener(OnItemDismissListener listener) {
        mOnItemDismissListener = listener;
    }

    public void setOnItemItemSelectListener(OnItemSelectListener listener) {
        mOnItemSelectListener = listener;
    }

    public class OnItemDismiss implements RightCoordinatorLayout.OnDismissListener {

        private Album album;

        public OnItemDismiss(Album album) {
            this.album = album;
        }

        @Override
        public void onDismissed() {
            if (mOnItemDismissListener != null) {
                mOnItemDismissListener.onItemDismissed(album);
            }
        }
    }

    private class OnItemSelectedClick implements View.OnClickListener{

        private Album album;

        public OnItemSelectedClick(Album album) {
            this.album = album;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemDismissListener != null) {
                mOnItemSelectListener.onItemSelected(album);
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
        @Bind(R.id.foregroundView)
        public View foreground;
        public RightCoordinatorLayout coordinatorLayout;

        public ViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
            coordinatorLayout = (RightCoordinatorLayout) view;
        }
    }
}